package db;

import ministore.Purchase;

import java.sql.Timestamp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDAO {
    Connection connection;

    public PurchaseDAO(Connection connection) {
        this.connection = connection;
    }

    // Añadir compra a la base de datos
    public void addPurchase(Purchase purchase) throws SQLException {
        String sql = "INSERT INTO Purchases (buyer, date, cost) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, purchase.buyer());
            preparedStatement.setTimestamp(2, purchase.date());
            preparedStatement.setFloat(3, purchase.cost());
            preparedStatement.executeUpdate();
        }
    }

    // Método para obtener todas las fechas de compra de un usuario
    public List<Purchase> getPurchases(String buyer) throws SQLException {
        List<Purchase> purchases = new ArrayList<>();
        String sql = "SELECT date, cost FROM Purchases WHERE buyer = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, buyer);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    purchases.add(new Purchase(buyer, rs.getTimestamp("date"), rs.getFloat("cost")));
                }
            }
        }
        return purchases;
    }
    
    
}
