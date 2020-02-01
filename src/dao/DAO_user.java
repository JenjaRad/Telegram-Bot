package dao;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO_user implements DAO<User> {
    Connection connection = DB_ConnectionMySQL.getConnection();

    public static void main(String[] args) {
        DAO_user dao_user = new DAO_user();
        User user = new User("Serhei12", "Sidorov", "jhdhs@ukr.net", "+380847474665", "user159", "user9", "coach");
        dao_user.create(user);
        //dao_user.delete("user6");
        //dao_user.search("Serhei");
//        user.email = "222222222.COM";
//        dao_user.update(user);
//        dao_user.delete(user.login);
    }

    @Override
    public boolean create(User user) {
        try {
            //  Connection connection = DB_Connection.getConnection();
            java.sql.Statement statement = connection.createStatement();
            String query = "{ call create_user(?,?,?,?,?,?,?) }";
            java.sql.CallableStatement callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, user.name);
            callableStatement.setString(2, user.surname);
            callableStatement.setString(3, user.email);
            callableStatement.setString(4, user.numberOfPhone);
            callableStatement.setString(5, user.login);
            callableStatement.setString(6, user.password);
            callableStatement.setString(7, user.typeOfUser);
            callableStatement.execute();
//            PreparedStatement prs = connection.prepareStatement("INSERT INTO Users ( name, surname, email,number_of_phone," +
//                    " login, password, type_of_user) values (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
//            prs.setString(1, user.name);
//            prs.setString(2, user.surname);
//            prs.setString(3, user.email);
//            prs.setString(4, user.numberOfPhone);
//            prs.setString(5, user.login);
//            prs.setString(6, user.password);
//            prs.setString(7, user.typeOfUser);
            int id = 0;
            ResultSet resultSet = callableStatement.getGeneratedKeys();
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            //  ResultSet idNew = statement.executeQuery("select seq,* from sqlite_sequence where  name='Users'");

            //  int id = idNew.getInt(1);

//    int countColumn=idNew.getMetaData().getColumnCount();
//    for(int i=1;i<countColumn;i++){
//        System.out.print(idNew.getString(i)+" , ");
//    }
            user.setId(id);
            System.out.println(id);

            // connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void update(User user) {
        try {
            //   Connection connection = DB_Connection.getConnection();
            //java.sql.Statement statement = connection.createStatement();
            String queryUpdate = "{ call update_user(?,?,?,?,?,?,?) }";
            java.sql.CallableStatement updateCallableStatement = connection.prepareCall(queryUpdate);
//            PreparedStatement prs = connection.prepareStatement("UPDATE  Users set name =?,surname=?,email=?," +
//                    "number_of_phone=?,login=?,password=?,type_of_user=? where  id=?");
            updateCallableStatement.setString(1, user.name);
            updateCallableStatement.setString(2, user.surname);
            updateCallableStatement.setString(3, user.email);
            updateCallableStatement.setString(4, user.numberOfPhone);
            updateCallableStatement.setString(5, user.login);
            updateCallableStatement.setString(6, user.password);
            updateCallableStatement.setString(7, user.typeOfUser);
            updateCallableStatement.setInt(8, user.getId());
            updateCallableStatement.execute();
//            statement.executeUpdate("UPDATE  Users set name = '"+obj.name+"', surname='"+obj.surname+"'," +
//                            "email='"+obj.email+"', number_of_phone = '"+obj.numberOfPhone+"'," +  "login = '"+obj.login+"', password = '"
//                             +obj.password+"', type_of_user = '"+obj.typeOfUser+"' where id=");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean delete(String login) {
        try {
            java.sql.Statement statement = connection.createStatement();
            String queryDelete = "{ call delete_user(?) }";
            java.sql.CallableStatement deleteCallableStatement = connection.prepareCall(queryDelete);
            //  Connection connection = DB_Connection.getConnection();
            //statement.executeUpdate("DELETE FROM Users WHERE  login = '" + login + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(int id) {
        try {
            //  Connection connection = DB_Connection.getConnection();
            java.sql.Statement statement = connection.createStatement();

            //statement.executeUpdate("DELETE FROM Users WHERE  id = " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<User> search(String name) {
        List<User> result = new ArrayList<>();
        try {
            //  Connection connection = DB_Connection.getConnection();
            Statement statement = connection.createStatement();
            String querySearch = "{ call search_user(?) }";
            CallableStatement searchCallableStatement = connection.prepareCall(querySearch);
            ResultSet resultSet = searchCallableStatement.getResultSet();
            //ResultSet resultSet = statement.executeQuery("SELECT * from Users where name like '%" + name + "%'");
            while (resultSet.next()) {
                String getName = resultSet.getString(2);
                String getSurname = resultSet.getString(3);
                String getEmail = resultSet.getString(4);
                String getNumberOfPhone = resultSet.getString(5);
                String getLogin = resultSet.getString(6);
                String getPassword = resultSet.getString(7);
                String getTypeOfUser = resultSet.getString(8);
                User user = new User(getName, getSurname, getEmail, getNumberOfPhone, getLogin, getPassword, getTypeOfUser);
                result.add(user);
                System.out.println(result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
