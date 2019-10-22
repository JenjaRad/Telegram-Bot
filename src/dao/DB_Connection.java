package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_Connection {
    static final String DATA_DRIVER = "org.sqlite.JDBC";
    static final String DATA_URL = "jdbc:sqlite:D:/123/Fitness_DB.db";

    private DB_Connection() {
    }
 public   static  Connection getConnection(){
        Connection connection = null;
            try {
        Class.forName(DATA_DRIVER);

        connection = DriverManager.getConnection(DATA_URL);

        } catch (Exception e) {
            e.printStackTrace();
        }
      return connection;
    }

    }
