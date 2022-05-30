package com.example.tourplanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Main extends Application {

    private final Logger log = LogManager.getLogger(Main.class);


    @Override
    public void start(Stage stage) throws IOException {
        log.info("Starting Tour Planner ...");
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("Tour Planner");
        stage.setScene(scene);
        stage.setMinWidth(500);
        stage.setMinHeight(400);
        stage.show();
    }


    @Override
    public void stop() {
        log.info("Exiting Tour Planner ...");
    }

    public static void main(String[] args) {
        launch();
    }
}