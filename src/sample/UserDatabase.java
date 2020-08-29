package sample;

import java.sql.*;

public class UserDatabase {
    public void signUpUser(Users users){
        try {
            Connection databaseConnection = DatabaseConnection.getInstance().getConnection();
            String sql = "INSERT INTO " + Const.USER_TABLE + "(" +Const.USER_NAME + "," +Const.USER_SURNAME + "," + Const.USER_USERNAME + "," +Const.USER_PASSWORD + ")" +"VALUES(?,?,?,?)";
            PreparedStatement prSt = databaseConnection.prepareStatement(sql);
            prSt.setString(1, users.getName());
            prSt.setString(2, users.getSurname());
            prSt.setString(3, users.getUsername());
            prSt.setString(4, users.getPassword());
            prSt.executeUpdate();
        }catch (SQLException e){
            System.out.println("something is wrong" + " " + e.getMessage());
        }
    }
    public ResultSet getUser(Users users){
        Connection databaseConnection = DatabaseConnection.getInstance().getConnection();
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USER_USERNAME + "=? AND " + Const.USER_PASSWORD + "=?";
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(select);
            preparedStatement.setString(1, users.getUsername());
            preparedStatement.setString(2, users.getPassword());
            resultSet = preparedStatement.executeQuery();

        }catch (Exception e){
            System.out.println("something is wrong" + " " + e.getMessage());
        } return resultSet;
    }
}
