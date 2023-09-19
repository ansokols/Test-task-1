module main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens main.Controller to javafx.fxml;
    exports main.Controller;
    opens main.DTO to javafx.fxml;
    exports main.DTO;

//    exports main.DAO;
//    opens main.DAO to UNNAMED;
}