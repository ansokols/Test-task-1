package main.DAO;

import main.DTO.Book;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserBookDao extends ConnectionManager {

    public UserBookDao() {
        createTable();
    }

    public List<Book> getAllByUser(int userId) {
        List<Book> bookList = new ArrayList<>();

        try (
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT book.* FROM user" +
                                " JOIN user_book ON user_book.user_id = user.user_id" +
                                " JOIN book ON book.book_id = user_book.book_id" +
                                " WHERE user.user_id = ?"
                )
        ){
            statement.setString(1, String.valueOf(userId));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bookList.add(new Book(
                        resultSet.getInt("book_id"),
                        resultSet.getString("name"),
                        resultSet.getString("author"),
                        resultSet.getString("genre"),
                        resultSet.getString("language"),
                        resultSet.getString("publisher"),
                        Year.parse(resultSet.getString("year"), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return bookList;
    }

    public void add(int userId, int bookId) {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO user_book (user_id, book_id)" +
                                "VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setInt(1, userId);
            statement.setInt(2, bookId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int userId, int bookId) {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM user_book WHERE user_id = ? AND book_id = ?"
                )
        ) {
            statement.setInt(1, userId);
            statement.setInt(2, bookId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable() {
        String sqlCreate = "CREATE TABLE IF NOT EXISTS user_book"
                + " (user_book_id int NOT NULL AUTO_INCREMENT,"
                + " user_id int,"
                + " book_id int,"
                + " PRIMARY KEY (user_book_id),"
                + " FOREIGN KEY (user_id) REFERENCES user (user_id),"
                + " FOREIGN KEY (book_id) REFERENCES book (book_id))";

        try {
            Statement statement = connection.createStatement();
            statement.execute(sqlCreate);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
