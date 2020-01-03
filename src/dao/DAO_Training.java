package dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DAO_Training implements DAO<Training> {
    Connection connection = DB_ConnectionMySQL.getConnection();

    public static void main(String[] args) {

        DAO_Training dao_coach = new DAO_Training();
        Training training = new Training(LocalDateTime.of(2019, 7, 30, 15, 30), LocalDateTime.of(2019, 7, 30, 16, 30), "Atlethicks", 2);
        dao_coach.create(training);
        Predicate<Training> predicate1 = new Predicate<Training>() {
            @Override
            public boolean test(Training training) {
                return training.begin_of_training.isBefore(LocalDateTime.of(2019,10,13,0,0));

            }
        };
        List<Predicate<Training>>list = new ArrayList<>();
        list.add(predicate1);
        System.out.println(dao_coach.search(list));
        training.kindOfSport = "Judo";
        dao_coach.update(training);


    }


    @Override
    public boolean create(Training training) {
        try {
            String query = "{call create_training(?,?,?,?) }";
            CallableStatement callableStatement = connection.prepareCall(query);
            Statement statement = connection.createStatement();
            LocalDateTime localDateTime1 = LocalDateTime.of(2019, 11, 22, 2, 57);
            LocalDateTime localDateTime2 = LocalDateTime.of(2019, 11, 22, 4, 00);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Timestamp timestamp1 = Timestamp.valueOf(localDateTime1);
            Timestamp timestamp2 = Timestamp.valueOf(localDateTime2);
            String convertToTimeStamp1 = simpleDateFormat.format(timestamp1);
            String convertToTimeStamp2 = simpleDateFormat.format(timestamp2);
           // PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO coach_par( begin_training, end_training, kind_of_sport,id_coach) values (?,?,?,?)");
            callableStatement.setString(1, convertToTimeStamp1);
            callableStatement.setString(2, convertToTimeStamp2);
            callableStatement.setString(3, training.kindOfSport);
            callableStatement.setInt(4, training.getId_coach());
            callableStatement.execute();
            int id = 0;
            ResultSet resultSet  =  callableStatement.getGeneratedKeys();
            while(resultSet.next()) {
                id = resultSet.getInt(1);
            }
//          //  ResultSet resultSet = statement.executeQuery("SELECT * FROM coach_par ORDER BY id DESC LIMIT 1");
//            int id = resultSet.getInt(1);
           training.setId(1);
//            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public void update(Training training) {
        try {
            String query = "{call update_training(?,?,?,?)}";
            CallableStatement callableStatement = connection.prepareCall(query);
            Statement statement = connection.createStatement();
           // Connection connection = DB_Connection.getConnection();
            //PreparedStatement prs = connection.prepareStatement("UPDATE  coach_par set begin_training =?,end_training=?,kind_of_sport=? where id=?");
            LocalDateTime localDateTime1 = LocalDateTime.of(2019, 11, 22, 2, 57);
            LocalDateTime localDateTime2 = LocalDateTime.of(2019, 11, 22, 4, 00);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Timestamp timestamp1 = Timestamp.valueOf(localDateTime1);
            Timestamp timestamp2 = Timestamp.valueOf(localDateTime2);
            String convertToTimeStamp1 = simpleDateFormat.format(timestamp1);
            String convertToTimeStamp2 = simpleDateFormat.format(timestamp2);
            callableStatement.setString(1, convertToTimeStamp1);
            callableStatement.setString(2, convertToTimeStamp2);
            callableStatement.setString(3, training.kindOfSport);
            callableStatement.setInt(4, training.getId());
            callableStatement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete(String login) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        try {
            java.sql.Statement statement = connection.createStatement();
            String queryDelete = "{ call delete_training(?) }";
            java.sql.CallableStatement deleteCallableStatement = connection.prepareCall(queryDelete);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Training> search(List<Predicate<Training>> listPredicate) {
        List<Training> result = new ArrayList<>();
        try {
            String query = "{call search_training(?)}";
            CallableStatement callableStatement = connection.prepareCall(query);
            Statement statement = connection.createStatement();
            //ResultSet resultSet = statement.executeQuery("SELECT * from coach_par ");
            ResultSet resultSet = callableStatement.getResultSet();
            while (resultSet.next()) {
                String timeBegin= resultSet.getString(2);
                String timeEnd= resultSet.getString(3);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime1 = LocalDateTime.parse(timeBegin, formatter);
                LocalDateTime dateTime2 = LocalDateTime.parse(timeEnd, formatter);
                String getKindOfSport  = resultSet.getString(4);
                int getIdCoach = resultSet.getInt(5);
               Training training = new Training(dateTime1,dateTime2,getKindOfSport,getIdCoach);
                result.add(training);
            }
            connection.close();
            }catch(Exception e){
                e.printStackTrace();

            }
        Predicate<Training> firstCondition = listPredicate.get(0);
        for (Predicate<Training> temp : listPredicate) {
            firstCondition.and(temp);
        }
            return result.stream().filter(firstCondition).collect(Collectors.toList());
        }
}
