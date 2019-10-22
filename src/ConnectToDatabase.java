import java.sql.*;

public class ConnectToDatabase {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        System.out.println(connectToDatabase());
    }
    public static  String connectToDatabase() throws ClassNotFoundException, SQLException {
        final  String DATA_DRIVER = "org.sqlite.JDBC";
        final String DATA_URL = "jdbc:sqlite:D:/123/Fitness_DB.db";
        String result = "";
        Class.forName(DATA_DRIVER);

        Connection connection = null;

        connection = DriverManager.getConnection(DATA_URL);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("Select * from users");
        while(resultSet.next()==true){
            result+=resultSet.getString(2);
        }




        return result;
    }
}
