package db;

import ministore.Password;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {
    private final static int MIN_USERNAME_LENGTH = 3;
    Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }


    /**
     * Lectura completa de la tabla Usuarios.
     * @return La lista de nombres de usuarios.
     */
    public ArrayList<String> getUsers() throws SQLException {
        ArrayList<String> users = new ArrayList<>();

        String query = "SELECT username FROM users";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                users.add(resultSet.getString("username"));
            }
        }

        return users;
    }

    // Registro de un nuevo usuario
    public void registerUser(String username, Password password, String cardType, String cardNumber) throws Exception {
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

        String query = "INSERT INTO users (username, password, type, card) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password.toString());
            preparedStatement.setString(3, cardType);
            preparedStatement.setString(4, cardNumber);
            preparedStatement.executeUpdate();
        }
    }

    // Inicio de sesión de un usuario
    public void loginUser(String username, Password password) throws Exception {
        String query = "SELECT * FROM users WHERE username = ? and password = ?";

        try (PreparedStatement preparedStatement = prepareQuery(query, username, password.toString());
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (!resultSet.next()) { /* Asegurarse de que el resultado tiene por lo menos una fila */
                throw new SQLException("Usuario o contraseña incorrectos");
            }
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
