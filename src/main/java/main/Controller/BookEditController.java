package main.Controller;

import main.DAO.BookDao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.DTO.Book;
import java.time.Year;

public class BookEditController {
    @FXML
    private Button backButton;
    @FXML
    private Button doneButton;

    @FXML
    private Label titleLabel;

    @FXML
    private TextField nameField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField genreField;
    @FXML
    private TextField languageField;
    @FXML
    private TextField publisherField;
    @FXML
    private TextField yearField;

    private static Book book;
    private final BookDao bookDao = new BookDao();


    @FXML
    private void initialize() {
        if(book == null) {
            titleLabel.setText("Створення нової книги");
        } else {
            titleLabel.setText("Редагування книги");
            setData();
        }

        backButton.setOnAction(event -> backButton.getScene().getWindow().hide());

        doneButton.setOnAction(event -> {
            if (isInputValid()) {
                if (isTypeValid()) {
                    Book newBook = new Book(
                            null,
                            nameField.getText().trim(),
                            authorField.getText().trim(),
                            genreField.getText().trim(),
                            languageField.getText().trim(),
                            publisherField.getText().trim(),
                            Year.parse(yearField.getText().trim())
                    );

                    if (book == null) {
                        Integer id = bookDao.save(newBook);
                        newBook.setBookId(id);

                        Main.getAdminLibraryMenuController().addBook(newBook);
                    } else {
                        newBook.setBookId(book.getBookId());
                        bookDao.update(newBook);

                        Main.getAdminLibraryMenuController().setBookTable();
                    }

                    doneButton.getScene().getWindow().hide();
                }
            }
        });
    }

    public static void initializeData(Book book) {
        BookEditController.book = book;
    }

    private void setData() {
        nameField.setText(book.getName());
        authorField.setText(book.getAuthor());
        genreField.setText(book.getGenre());
        languageField.setText(book.getLanguage());
        publisherField.setText(book.getPublisher());
        yearField.setText(book.getYear().toString());
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText().trim().length() == 0) {
            errorMessage += "Не вказано назву!\n";
        }
        if (authorField.getText().trim().length() == 0) {
            errorMessage += "Не вказано автора!\n";
        }
        if (genreField.getText().trim().length() == 0) {
            errorMessage += "Не вказано жанр!\n";
        }
        if (languageField.getText().trim().length() == 0) {
            errorMessage += "Не вказано мову!\n";
        }
        if (genreField.getText().trim().length() == 0) {
            errorMessage += "Не вказано видавництво!\n";
        }
        if (languageField.getText().trim().length() == 0) {
            errorMessage += "Не вказано рік видання!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Test task 1");
            alert.setHeaderText("Будь ласка, заповніть усі поля");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }

    private boolean isTypeValid() {
        String errorMessage = "";
        String input = yearField.getText().trim();
        String pattern = "^[0-9]{4}$";

        if (!input.matches(pattern) || Integer.parseInt(yearField.getText().trim()) < 1901 || Integer.parseInt(yearField.getText().trim()) > 2155) {
            errorMessage += "Рік вказано неправильно!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Test task 1");
            alert.setHeaderText("Будь ласка, заповніть усі поля правильно");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }
}
