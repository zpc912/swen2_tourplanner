package com.example.tourplanner.views;
import com.example.tourplanner.Main;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelpViewController {

    public static void helpWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("help-view.fxml"));
            Stage helpStage = new Stage();
            helpStage.setTitle("Help");
            Scene helpWindow = new Scene(fxmlLoader.load(), 600, 400);
            helpStage.setScene(helpWindow);
            helpStage.setMinWidth(600);
            helpStage.setMinHeight(400);
            helpStage.show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
