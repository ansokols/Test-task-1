module com.example.testtask1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.testtask1 to javafx.fxml;
    exports com.example.testtask1;
}