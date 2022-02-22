module com.example.swen2_tourplanner {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.swen2_tourplanner to javafx.fxml;
    exports com.example.swen2_tourplanner;
}