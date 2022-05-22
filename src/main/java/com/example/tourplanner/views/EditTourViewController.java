package com.example.tourplanner.views;

import com.example.tourplanner.Main;
import com.example.tourplanner.viewmodels.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditTourViewController implements Initializable {

    private TourViewModel viewModel;

    @FXML
    public TextField tourNameField;
    @FXML
    public TextField tourFromField;
    @FXML
    public TextField tourToField;
    @FXML
    public TextField tourTransportField;
    @FXML
    public TextArea tourDescField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tourNameField.textProperty().bindBidirectional(viewModel.getName());
        tourFromField.textProperty().bindBidirectional(viewModel.getFrom());
        tourToField.textProperty().bindBidirectional(viewModel.getTo());
        tourTransportField.textProperty().bindBidirectional(viewModel.getTransportType());
        tourDescField.textProperty().bindBidirectional(viewModel.getDescription());
    }


    public TourViewModel getViewModel() {
        return viewModel;
    }
    public void setViewModel(TourViewModel viewModel) {
        this.viewModel = viewModel;
    }


    public static void editTourWindow(Stage stage, TourViewModel tourViewModel) {
        try {
            EditTourViewController editTourViewController = new EditTourViewController();
            editTourViewController.setViewModel(tourViewModel);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("edit-tour-view.fxml"));
            fxmlLoader.setController(editTourViewController);
            Parent root = fxmlLoader.load();
            Stage editTourStage = new Stage();
            editTourStage.setTitle("Edit Tour");
            Scene editTourScene = new Scene(root, 600, 400);
            editTourStage.setScene(editTourScene);
            editTourStage.setMinWidth(600);
            editTourStage.setMinHeight(400);
            editTourStage.initOwner(stage);
            editTourStage.initModality(Modality.APPLICATION_MODAL);
            editTourStage.showAndWait();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    public void tourCancelButton(ActionEvent actionEvent) {
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }


    public void tourApplyButton(ActionEvent actionEvent) {
        if(viewModel.editTour()) {
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }
    }
}
