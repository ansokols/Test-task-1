module com.example.testtask1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.testtask1 to javafx.fxml;
    exports com.example.testtask1;


    opens main.Controller to javafx.fxml;
    exports main.Controller;
    //exports main.Model;
    //opens main.Model to javafx.fxml;
}