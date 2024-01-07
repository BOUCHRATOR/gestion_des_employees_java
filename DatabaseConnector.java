package meniprojet;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/entreprise";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    private Connection connection;
    public Connection getConnection() {
        return connection;
    }
    public DatabaseConnector() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Autres méthodes pour effectuer les opérations CRUD sur la base de données
}