package main.Controller;

import main.DAO.UserDao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.DTO.User;
import java.io.File;
import java.net.MalformedURLException;


public class AuthorizationMenuController {
    @FXML
    private Button enterButton;

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;


    private final UserDao userDao = new UserDao();


    @FXML
    void initialize() {
        enterButton.setOnAction(event -> {
            if(!loginField.getText().trim().equals("") && !passwordField.getText().trim().equals("")) {
                User user = userDao.checkPassword(loginField.getText().trim(), passwordField.getText().trim());
                if (user == null) {
                    Main.setUser(null);

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Test task 1");
                    alert.setHeaderText("Введено неправильний логін або пароль");
                    alert.showAndWait();
                } else {
                    Main.setUser(user);
                    if (user.getRole().equals("admin")) {
                        try {
                            Main.newWindow(new File("src/main/java/main/View/AdminLibraryMenu.fxml").toURI().toURL());
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (user.getRole().equals("default")) {
                        try {
                            Main.newWindow(new File("src/main/java/main/View/UserLibraryMenu.fxml").toURI().toURL());
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Test task 1");
                alert.setHeaderText("Будь ласка, заповніть усі поля");
                alert.showAndWait();
            }
        });
    }
}
