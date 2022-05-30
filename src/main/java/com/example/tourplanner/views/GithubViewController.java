package com.example.tourplanner.views;

import com.example.tourplanner.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GithubViewController implements Initializable {

    private final String githubRepository = "https://github.com/zpc912/tourplanner_swen2";

    @FXML
    private WebEngine webEngine;
    @FXML
    public WebView githubWebView = new WebView();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        webEngine = githubWebView.getEngine();
        webEngine.load(githubRepository);
    }


    public static void openGithubWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("github-web-view.fxml"));
            Stage githubStage = new Stage();
            githubStage.setTitle("Source Code (Github)");
            Scene githubWindow = new Scene(fxmlLoader.load(), 800, 600);
            githubStage.setScene(githubWindow);
            githubStage.setMinWidth(800);
            githubStage.setMinHeight(600);
            githubStage.show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }



}
