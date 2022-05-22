package com.example.tourplanner.views;

import com.example.tourplanner.Main;
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

public class CreateTourLogViewController implements Initializable {

    private TourViewModel tourViewModel;
    private final TourLogViewModel tourLogViewModel = new TourLogViewModel();

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


    public void initializeTourViewModel(TourViewModel tourViewModel) {
        this.tourViewModel = tourViewModel;
        tourLogViewModel.setTour(tourViewModel.initializeTour());
    }


    public static void newTourLogWindow(Stage stage, TourViewModel tourViewModel) {
        try {
            CreateTourLogViewController createTourLogViewController = new CreateTourLogViewController();
            createTourLogViewController.initializeTourViewModel(tourViewModel);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("edit-tour-log-view.fxml"));
            fxmlLoader.setController(createTourLogViewController);
            Parent root = fxmlLoader.load();
            Stage newTourLogStage = new Stage();
            newTourLogStage.setTitle("New Tour Log");
            Scene newTourLogScene = new Scene(root, 600, 400);
            newTourLogStage.setScene(newTourLogScene);
            newTourLogStage.setMinWidth(600);
            newTourLogStage.setMinHeight(400);
            newTourLogStage.initOwner(stage);
            newTourLogStage.initModality(Modality.APPLICATION_MODAL);
            newTourLogStage.showAndWait();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    public void cancelTourLogButton(ActionEvent actionEvent) {
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }


    public void applyTourLogButton(ActionEvent actionEvent) {
        if(tourLogViewModel.createNewTourLog() != null) {
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }
    }
}
