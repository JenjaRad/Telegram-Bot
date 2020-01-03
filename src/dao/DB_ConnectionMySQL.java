package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB_ConnectionMySQL {

    static final String DATA_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DATA_URL = "jdbc:mysql://localhost:3306/fitness_db?serverTimezone=UTC";

    private DB_ConnectionMySQL() {
    }
    public static Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName(DATA_DRIVER);

            connection = DriverManager.getConnection(DATA_URL,"super","Vovvov2003");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

}

