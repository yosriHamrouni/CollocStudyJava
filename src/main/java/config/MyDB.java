package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDB {

    public final String USERNAME = "root";
    public final String PWD="";
    public final String URL = "jdbc:mysql://localhost:3306/colocstudy";

    private Connection con;

    public Connection getCon() {
        return con;
    }

    //3 creer une variable static pour stocker l'instance
    public static MyDB instance;


    //1 rendre le constructeur privee
    private MyDB() {
        try {
            con = DriverManager.getConnection(URL, USERNAME, PWD);
            System.out.println("connected to DB");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    //2 creer une methode intermediaire
    public static MyDB getInstance() {
        if (instance == null) {
            instance = new MyDB();
        }
        return instance;
    }


}
