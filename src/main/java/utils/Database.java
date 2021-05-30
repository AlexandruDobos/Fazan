package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection conn = null;

    private Database() {
        String url = "jdbc:postgresql://localhost:5432/Fazan";
        String username = "postgres";
        String password = "student";
        try {
            conn = DriverManager.getConnection(url, username, password);
            conn.setAutoCommit(true);
            System.out.println("Connected");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (conn == null) {
            new Database();
        }
        return conn;
    }
}
