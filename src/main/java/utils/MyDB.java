package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDB {

    private final String USER = "root";
    private final String PWD = "";
    private final String URL = "jdbc:mysql://localhost:3306/coworking";
    private Connection connection;
    public static MyDB instance;

    private MyDB() {
        try {
            connection = DriverManager.getConnection(URL, USER, PWD);
            System.out.println("connected");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(" nest pas connecter");

        }
    }

    public static MyDB getInstance() {
        if (instance == null) {
            instance = new MyDB();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
