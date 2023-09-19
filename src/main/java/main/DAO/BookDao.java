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

public class BookDao extends ConnectionManager {

    public BookDao() {
        createTable();
    }

    public Book get(int id) {
        Book book = null;

        try (
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM book" +
                                " WHERE book.book_id = ?"
                )
        ){
            statement.setString(1, String.valueOf(id));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                book = new Book(
                        resultSet.getInt("book_id"),
                        resultSet.getString("name"),
                        resultSet.getString("author"),
                        resultSet.getString("genre"),
                        resultSet.getString("language"),
                        resultSet.getString("publisher"),
                        Year.parse(resultSet.getString("year"), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return book;
    }

    public List<Book> getAll() {
        List<Book> bookList = new ArrayList<>();

        try (
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM book")
        ){
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

    public int save(Book book) {
        Integer id = null;

        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO book (name, author, genre, language, publisher, year)" +
                                "VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, book.getName());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getGenre());
            statement.setString(4, book.getLanguage());
            statement.setString(5, book.getPublisher());
            statement.setInt(6, book.getYear().getValue());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating failed, no rows affected");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Creating failed, no ID obtained");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    public void update(Book book) {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE book" +
                                " SET name = ?" +
                                ", author = ?" +
                                ", genre = ?" +
                                ", language = ?" +
                                ", publisher = ?" +
                                ", year = ?" +
                                " WHERE book_id = ?"
                )
        ) {
            statement.setString(1, book.getName());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getGenre());
            statement.setString(4, book.getLanguage());
            statement.setString(5, book.getPublisher());
            statement.setInt(6, book.getYear().getValue());
            statement.setInt(7,book.getBookId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Book book) {
        try (
                PreparedStatement statement1 = connection.prepareStatement(
                        "DELETE FROM user_book WHERE book_id = ?"
                );
                PreparedStatement statement2 = connection.prepareStatement(
                        "DELETE FROM book WHERE book_id = ?"
                )
        ) {
            statement1.setInt(1, book.getBookId());
            statement2.setInt(1, book.getBookId());

            statement1.executeUpdate();
            statement2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable() {
        String sqlCreate = "CREATE TABLE IF NOT EXISTS book"
                + " (book_id int NOT NULL AUTO_INCREMENT,"
                + " name VARCHAR(255),"
                + " author VARCHAR(255),"
                + " genre VARCHAR(255),"
                + " language VARCHAR(255),"
                + " publisher VARCHAR(255),"
                + " year YEAR(4),"
                + " PRIMARY KEY (book_id))";

        try {
            Statement statement = connection.createStatement();
            statement.execute(sqlCreate);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
