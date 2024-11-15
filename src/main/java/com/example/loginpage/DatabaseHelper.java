package com.example.loginpage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bankmanagementsystem";
    private static final String USER = "root";
    private static final String PASSWORD = "shamim";

    /**
     * Retrieves the first name of a user based on their user ID.
     *
     * @param userId The user ID to query.
     * @return An Optional containing the first name if found, or an empty Optional if not.
     */
    public static Optional<String> getUserName(int userId) {
        String query = "SELECT firstName FROM register WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(resultSet.getString("firstName"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            // Log this exception instead of printing in a real application
        }
        return Optional.empty();
    }
}
