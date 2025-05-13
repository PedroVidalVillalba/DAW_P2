package db;

import ministore.Password;
import ministore.Purchase;
import org.intellij.lang.annotations.Language;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DataBase {
    private static DataBase currentDB;
    private static Connection connection;

    private final UserDAO userDAO;
    private final PurchaseDAO purchaseDAO;

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

        userDAO = new UserDAO(connection);
        purchaseDAO = new PurchaseDAO(connection);
    }

    public static DataBase getCurrentDB() throws Exception {
        if (DataBase.currentDB == null) {
            DataBase.currentDB = new DataBase();
        }
        return DataBase.currentDB;
    }

    public static Connection getConnection() {
        return connection;
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

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                users.add(resultSet.getString("username"));
            }
        }

        return users;
    }

    public void registerUser(String username, Password password) throws Exception {
        userDAO.registerUser(username, password);
    }

    public void loginUser(String username, Password password) throws Exception {
        userDAO.loginUser(username, password);
    }

    public void addPurchase(String username, Purchase purchase) throws Exception {
        purchaseDAO.addPurchase(purchase);
    }

    public List<Purchase> getPurchases(String username) throws Exception {
        return purchaseDAO.getPurchases(username);
    }

}
