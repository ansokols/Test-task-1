package main.DAO;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.sql.*;
import java.util.Optional;

public abstract class ConnectionManager {
    protected Connection connection;

    public ConnectionManager() {
        String driverClassName = "com.mysql.cj.jdbc.Driver";
        String dbURL = "jdbc:mysql://localhost:3306/test_task_1";
        createDatabase();

        try {
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(dbURL, "root", "root");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Test task 1");
            alert.setHeaderText("З'єднання з базою даних втрачено");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                ((Stage) alert.getDialogPane().getScene().getWindow()).close();
            }
        }
    }

    public static void createDatabase() {
        String URL = "jdbc:mysql://localhost:3306";

        try (
                Connection con = DriverManager.getConnection(URL, "root", "root");
                PreparedStatement statement = con.prepareStatement("CREATE DATABASE IF NOT EXISTS test_task_1")
        ) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
