package soulcasters.server;

import java.sql.*;

public class GameDatabase {
	String url = "jdbc:mysql://localhost:3306/student_space";
    String user = "student";
    String password = "hello";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void createPlayersTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS players (" +
                "player_id NUMBER AUTO_INCREMENT PRIMARY KEY, " +
                "username VARCHAR(50) NOT NULL UNIQUE, " +
                "password VARCHAR(255) NOT NULL, " +
                "email VARCHAR(100) NOT NULL UNIQUE, " +
                "score NUMBER DEFAULT 0, " +
                "games_won NUMBER DEFAULT 0, " +
                "games_lost NUMBER DEFAULT 0, " +
                "resources NUMBER DEFAULT 0, " +
                "session_token CHAR(36), " +
                "token_expires TIMESTAMP, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
        executeStatement(sql);
        System.out.println("Players table created successfully.");
    }

    public static void createGameSessionsTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS game_sessions (" +
                "session_id NUMBER AUTO_INCREMENT PRIMARY KEY, " +
                "player1_id NUMBER, " +
                "player2_id NUMBER, " +
                "winner_id NUMBER, " +
                "status VARCHAR(20) NOT NULL, " +
                "start_time TIMESTAMP, " +
                "end_time TIMESTAMP" +
                ")";
        executeStatement(sql);
        System.out.println("Game sessions table created successfully.");
    }

    public static void createUnitsTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS units (" +
                "unit_id NUMBER AUTO_INCREMENT PRIMARY KEY, " +
                "player_id NUMBER NOT NULL, " +
                "type VARCHAR(20) NOT NULL, " +
                "task VARCHAR(20) NOT NULL, " +
                "location_x NUMBER NOT NULL, " +
                "location_y NUMBER NOT NULL, " +
                "health NUMBER NOT NULL, " +
                "attack_power NUMBER, " +
                "defense_power NUMBER, " +
                "resource_capacity NUMBER" +
                ")";
        executeStatement(sql);
        System.out.println("Units table created successfully.");
    }

    private static void executeStatement(String sql) throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public static boolean createNewAccount(String username, String email, String password) {
        String checkUserQuery = "SELECT username FROM players WHERE username = ?";
        String insertPlayerQuery = "INSERT INTO players (username, email, password) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkUserQuery)) {

            checkStmt.setString(1, username);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    return false; // Username already exists
                }
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertPlayerQuery)) {
                insertStmt.setString(1, username);
                insertStmt.setString(2, email);
                insertStmt.setString(3, password); // Simple password storage (not secure)

                int affectedRows = insertStmt.executeUpdate();
                return affectedRows > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean verifyAccount(String username, String password) {
        String query = "SELECT player_id FROM players WHERE username = ? AND password = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password); // Simple password check (not secure)

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // If a row is returned, the user is verified
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        try {
            createPlayersTable();
            createGameSessionsTable();
            createUnitsTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
