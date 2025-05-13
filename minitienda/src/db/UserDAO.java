package db;

import ministore.Password;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static db.DataBase.getCurrentDB;

public class UserDAO {
    private final static int MIN_USERNAME_LENGTH = 3;
    Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }


    // Registro de un nuevo usuario
    public void registerUser(String username, Password password) throws Exception {
        // Comprobación de que la longitud del nombre es correcta
        if(username.length() < MIN_USERNAME_LENGTH) {
            throw new SQLException("El nombre de usuario " + username + " es demasiado corto. La longitud mínima es " + MIN_USERNAME_LENGTH + " caracteres");
        }
        if (username.contains(",")) {
            throw new SQLException("El nombre de usuario no puede contener \",\"");
        }

        ArrayList<String> users = getCurrentDB().getUsers();
        // Comprobación de que no existe un usuario registrado con el mismo nombre
        if (users.contains(username)) {
            throw new SQLException("El usuario " + username + " ya está registrado");
        }

        @Language("SQL")
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password.toString());
            preparedStatement.executeUpdate();
        }
    }

    // Inicio de sesión de un usuario
    public void loginUser(String username, Password password) throws Exception {
        @Language("SQL")
        String query = "SELECT password FROM users WHERE username = ?";

        try (PreparedStatement preparedStatement = prepareQuery(query, username);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) { /* Asegurarse de que el resultado tiene por lo menos una fila */
                if (resultSet.getString("password").equals(password.toString())) {
                    throw new SQLException("La contraseña del usuario " + username + " es incorrecta");
                }
            } else {
                throw new SQLException("El usuario " + username + " no está registrado");
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
