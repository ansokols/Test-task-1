package main.DAO;

import main.DTO.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDao extends ConnectionManager {

    public UserDao() {
        createTable();
    }

    public User checkPassword(String login, String password) {
        User user = null;

        byte[] encryptedPassword = null;
        try {
            encryptedPassword = MessageDigest.getInstance("MD5").digest(password.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : encryptedPassword) {
            stringBuilder.append(String.format("%02X", b));
        }

        try (
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM user " +
                                "WHERE login = ? AND password = ?")
        ){
            statement.setString(1, login);
            statement.setString(2, stringBuilder.toString());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                user = new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("name"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("role")
                );

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return user;
    }

    private void createTable() {
        String sqlCreate = "CREATE TABLE IF NOT EXISTS user"
                + " (user_id int NOT NULL AUTO_INCREMENT,"
                + " name VARCHAR(255),"
                + " login VARCHAR(255),"
                + " password VARCHAR(255),"
                + " role VARCHAR(255),"
                + " PRIMARY KEY (user_id))";

        try {
            Statement statement = connection.createStatement();
            statement.execute(sqlCreate);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
