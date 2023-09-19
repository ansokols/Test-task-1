package main.Controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.DAO.BookDao;
import main.DTO.Book;
import java.io.File;
import java.net.MalformedURLException;
import java.time.Year;
import java.util.Optional;

public class AdminLibraryMenuController {
    @FXML
    private Button backButton;

    @FXML
    private Button newBookButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    @FXML
    private TableView<Book> bookTable;
    @FXML
    private TableColumn<Book, String> bookNameColumn;
    @FXML
    private TableColumn<Book, String> bookAuthorColumn;
    @FXML
    private TableColumn<Book, String> bookGenreColumn;
    @FXML
    private TableColumn<Book, String> bookLanguageColumn;
    @FXML
    private TableColumn<Book, String> bookPublisherColumn;
    @FXML
    private TableColumn<Book, Year> bookYearColumn;


    private final BookDao bookDao = new BookDao();


    @FXML
    void initialize() {
        Main.setAdminLibraryMenuController(this);
        setBookTable();

        bookTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    editButton.setDisable(false);
                    deleteButton.setDisable(false);
                });

        backButton.setOnAction(event -> {
            try {
                Main.newWindow(new File("src/main/java/main/View/AuthorizationMenu.fxml").toURI().toURL());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });

        newBookButton.setOnAction(event -> {
            BookEditController.initializeData(null);
            try {
                Main.newModalWindow(new File("src/main/java/main/View/BookEdit.fxml").toURI().toURL());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });

        editButton.setOnAction(event -> {
            if (bookTable.getSelectionModel().getSelectedItem() != null) {
                BookEditController.initializeData(bookTable.getSelectionModel().getSelectedItem());
                try {
                    Main.newModalWindow(new File("src/main/java/main/View/BookEdit.fxml").toURI().toURL());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        deleteButton.setOnAction(event -> {
            if (bookTable.getSelectionModel().getSelectedItem() != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Test task 1");
                alert.setHeaderText("Ви впевнені, що бажаєте видалити книгу?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get() == ButtonType.OK) {
                    bookDao.delete(bookTable.getSelectionModel().getSelectedItem());
                    bookTable.getItems().remove(bookTable.getSelectionModel().getSelectedIndex());
                }
            }
        });
    }

    public void setBookTable() {
        int selectedIndex = bookTable.getSelectionModel().getSelectedIndex();

        bookNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName())
        );
        bookAuthorColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAuthor())
        );
        bookGenreColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getGenre())
        );
        bookLanguageColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLanguage())
        );
        bookPublisherColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPublisher())
        );
        bookYearColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getYear())
        );

        bookTable.setItems(FXCollections.observableArrayList(bookDao.getAll()));
        bookTable.getSelectionModel().select(selectedIndex);
    }

    public void addBook(Book book) {
        bookTable.getItems().add(book);
    }
}
