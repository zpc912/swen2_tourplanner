package com.example.tourplanner.views;

import com.example.tourplanner.Main;
import com.example.tourplanner.models.TourLog;
import com.example.tourplanner.viewmodels.TourLogViewModel;
import com.example.tourplanner.viewmodels.TourViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditTourLogViewController implements Initializable {

    private TourLogViewModel tourLogViewModel;

    @FXML
    public DatePicker dateSelection;
    @FXML
    public TextArea commentTextArea;
    @FXML
    public TextField difficultyField;
    @FXML
    public TextField totalTimeField;
    @FXML
    public TextField ratingField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dateSelection.valueProperty().bindBidirectional(tourLogViewModel.dateProperty());
        commentTextArea.textProperty().bindBidirectional(tourLogViewModel.commentProperty());
        difficultyField.textProperty().bindBidirectional(tourLogViewModel.difficultyProperty());
        totalTimeField.textProperty().bindBidirectional(tourLogViewModel.totalTimeProperty());
        ratingField.textProperty().bindBidirectional(tourLogViewModel.ratingProperty());
    }


    public void setTourLogViewModel(TourLogViewModel tourLogViewModel) { this.tourLogViewModel = tourLogViewModel; }
    public TourLogViewModel getTourLogViewModel() { return tourLogViewModel; }


    public static void editTourLogWindow(Stage stage, TourLogViewModel tourLogViewModel) {
        try {
            EditTourLogViewController editTourLogViewController = new EditTourLogViewController();
            editTourLogViewController.setTourLogViewModel(tourLogViewModel);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("edit-tour-log-view.fxml"));
            fxmlLoader.setController(editTourLogViewController);
            Parent root = fxmlLoader.load();
            Stage editTourStage = new Stage();
            editTourStage.setTitle("Edit Tour Log");
            Scene editTourLogScene = new Scene(root, 600, 400);
            editTourStage.setScene(editTourLogScene);
            editTourStage.setMinWidth(600);
            editTourStage.setMinHeight(400);
            editTourStage.initOwner(stage);
            editTourStage.initModality(Modality.APPLICATION_MODAL);
            editTourStage.showAndWait();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    public void cancelTourLogButton(ActionEvent actionEvent) {
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }


    public void applyTourLogButton(ActionEvent actionEvent) {
        if(tourLogViewModel.editTourLog()) {
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }
    }
}
