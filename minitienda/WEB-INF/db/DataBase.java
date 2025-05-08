import org.intellij.lang.annotations.Language;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class DataBase {
    private static DataBase currentDB;
    private static Connection connection;
    private final static int MIN_USERNAME_LENGTH = 3;

    public static final String CONFIGURATION_FILE = "/database.properties";

    public DataBase() throws Exception {
        Properties configuration = new Properties();

        /* Cargar el archivo de configuración */
        try (InputStream configurationFile = DataBase.class.getResourceAsStream(CONFIGURATION_FILE)) {
            if (configurationFile == null) {
                throw new IOException("Fichero de configuración de la base de datos no encontrado: " + CONFIGURATION_FILE);
            }
            configuration.load(configurationFile);
        }

        Properties user = new Properties();
        user.setProperty("user", configuration.getProperty("user"));
        user.setProperty("password", configuration.getProperty("password"));

        String manager = configuration.getProperty("manager");
        connection = DriverManager.getConnection("jdbc:" + manager + "://" +
                        configuration.getProperty("server") + ":" +
                        configuration.getProperty("port") + "/" +
                        configuration.getProperty("dataBase"),
                        user);
    }

    public static DataBase getCurrentDB() throws Exception {
        if (DataBase.currentDB == null) {
            DataBase.currentDB = new DataBase();
        }
        return DataBase.currentDB;
    }

    public static void closeCurrentDB() throws SQLException {
        if (currentDB != null && connection != null) {
            connection.close();
            connection = null;
        }
    }

    /**
     * Lectura completa de la tabla Usuarios.
     * @return La lista de nombres de usuarios.
     */
    public ArrayList<String> getUsers() throws SQLException {
        ArrayList<String> users = new ArrayList<>();

        @Language("SQL")
        String query = "SELECT username FROM users";

        try (PreparedStatement preparedStatement = prepareQuery(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                users.add(resultSet.getString("username"));
            }
        }

        return users;
    }


    // Registro de un nuevo usuario
    public void registerUser(String username, byte[] password) throws Exception {
        // Comprobación de que la longitud del nombre es correcta
        if(username.length() < MIN_USERNAME_LENGTH) {
            throw new SQLException("El nombre de usuario " + username + " es demasiado corto. La longitud mínima es " + MIN_USERNAME_LENGTH + " caracteres");
        }
        if (username.contains(",")) {
            throw new SQLException("El nombre de usuario no puede contener \",\"");
        }

        ArrayList<String> users = getUsers();
        // Comprobación de que no existe un usuario registrado con el mismo nombre
        if (users.contains(username)) {
            throw new SQLException("El usuario " + username + " ya está registrado");
        }

        @Language("SQL")
        String query = "INSERT INTO users (username, password, salt) VALUES (?, ?, ?)";

        byte[] salt = Security.generateNonce();
        byte[] hashedPassword = Security.digest(password, salt);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setBytes(2, hashedPassword);
            preparedStatement.setBytes(3, salt);
            preparedStatement.executeUpdate();
        }
    }

    // Inicio de sesión de un usuario
    public void loginUser(String username, byte[] password) throws Exception {
        @Language("SQL")
        String query = "SELECT password, salt FROM users WHERE username = ?";

        try (PreparedStatement preparedStatement = prepareQuery(query, username);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) { /* Asegurarse de que el resultado tiene por lo menos una fila */
                byte[] salt = resultSet.getBytes("salt");
                byte[] hashedPassword = Security.digest(password, salt);
                if (!Arrays.equals(hashedPassword, resultSet.getBytes("password"))) {
                    throw new SQLException("La contraseña del usuario " + username + " es incorrecta");
                }
            } else {
                throw new SQLException("El usuario " + username + " no está registrado");
            }
        }
    }


    private int executeUpdate(String query, String... missingFields) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < missingFields.length; i++) {
                preparedStatement.setString(i + 1, missingFields[i]);
            }
            return preparedStatement.executeUpdate();
        }
    }

    private PreparedStatement prepareQuery(String query, String... missingFields) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (int i = 0; i < missingFields.length; i++) {
            preparedStatement.setString(i + 1, missingFields[i]);
        }
        return preparedStatement;
    }

}
