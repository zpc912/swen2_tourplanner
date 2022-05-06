package com.example.tourplanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("Tour Planner");
        stage.setScene(scene);
        stage.setMinWidth(500);
        stage.setMinHeight(400);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}