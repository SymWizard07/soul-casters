package soulcasters.server;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/student_space";
        String user = "student";
        String password = "hello";

        return DriverManager.getConnection(url, user, password);
    }

    public boolean createNewAccount(String username, String user_password) {
        // SQL query to check if the username already exists
        String checkUserQuery = "SELECT username FROM User WHERE username = ?";

        // SQL query to insert a new user
        String insertUserQuery = "INSERT INTO User (username, user_password) VALUES (?, ?)";

        try (Connection conn = getConnection();
                // Prepare statement to check if the user exists
                PreparedStatement checkStmt = conn.prepareStatement(checkUserQuery)) {

            checkStmt.setString(1, username);

            try (ResultSet rs = checkStmt.executeQuery()) {
                // Check if the username already exists
                if (rs.next()) {
                    return false;
                }
            }

            // If username does not exist, insert new user
            try (PreparedStatement insertStmt = conn.prepareStatement(insertUserQuery)) {
                insertStmt.setString(1, username);
                insertStmt.setBytes(2, user_password.getBytes(StandardCharsets.UTF_8));

                int affectedRows = insertStmt.executeUpdate();

                // Check if the insert was successful
                if (affectedRows == 0) {
                    throw new SQLException("No new rows created.");
                }
                else {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Account creation failed: " + e.getMessage());
        }
        return false;
    }

    public boolean verifyAccount(String username, String user_password) {
        // SQL query to check if the username and password match
        String query = "SELECT user_id FROM User WHERE username = ? AND user_password = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setString(1, username);
            stmt.setBytes(2, user_password.getBytes(StandardCharsets.UTF_8));
            
            try (ResultSet rs = stmt.executeQuery()) {
                // Check if the query returned a row
                if (rs.next()) {
                    // A matching user was found
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Authentication failed: " + e.getMessage());
        }
        
        // No matching user was found
        return false;
    }
}
