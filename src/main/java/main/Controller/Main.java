package main.Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.DTO.User;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class Main extends Application {

    private static Stage primaryStage;
    private static User user;

    private static AdminLibraryMenuController adminLibraryMenuController;


    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;
        Parent root = null;
        try {
            URL url = new File("src/main/java/main/View/AuthorizationMenu.fxml").toURI().toURL();
            root = FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        primaryStage.setTitle("Test task 1");
        primaryStage.setScene(new Scene(root, 1024, 640)); //1216, 839
        primaryStage.setMinWidth(854);
        primaryStage.setMinHeight(409);
        primaryStage.show();
    }

    public static void newWindow(URL url) {
        Parent root = null;
        try {
            root = FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, primaryStage.getWidth() - 16, primaryStage.getHeight() - 39));
    }

    public static void newModalWindow(URL url) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Test task 1");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static User getUser() {
        return user;
    }
    public static void setUser(User user) {
        Main.user = user;
    }

    public static AdminLibraryMenuController getAdminLibraryMenuController() {
        return adminLibraryMenuController;
    }
    public static void setAdminLibraryMenuController(AdminLibraryMenuController adminLibraryMenuController) {
        Main.adminLibraryMenuController = adminLibraryMenuController;
    }
}