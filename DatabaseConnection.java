package model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        String serverName = "localhost";
        String port = "1433";
        String databaseName = "OOP"; 
        String connectionUrl = "jdbc:sqlserver://" + serverName + ":" + port 
                + ";databaseName=" + databaseName 
                + ";integratedSecurity=true;" 
                + ";encrypt=true;trustServerCertificate=true;"; 
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            this.connection = DriverManager.getConnection(connectionUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}