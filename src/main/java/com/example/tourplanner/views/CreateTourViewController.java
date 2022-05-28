package com.example.tourplanner.views;

import com.example.tourplanner.Main;
import com.example.tourplanner.viewmodels.TourViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateTourViewController implements Initializable {

    private TourViewModel viewModel = new TourViewModel();

    @FXML
    public TextField tourNameField;
    @FXML
    public TextField tourFromField;
    @FXML
    public TextField tourToField;
    @FXML
    public ComboBox<String> tourTransportMenu;
    @FXML
    public TextArea tourDescField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tourNameField.textProperty().bindBidirectional(viewModel.getName());
        tourFromField.textProperty().bindBidirectional(viewModel.getFrom());
        tourToField.textProperty().bindBidirectional(viewModel.getTo());

        ObservableList<String> transportTypes = FXCollections.observableArrayList();
        transportTypes.add("AUTO");
        transportTypes.add("WALKING");
        transportTypes.add("BICYCLE");
        tourTransportMenu.setItems(transportTypes);
        tourTransportMenu.valueProperty().bindBidirectional(viewModel.getTransportType());

        tourDescField.textProperty().bindBidirectional(viewModel.getDescription());
    }


    public static void newTourWindow(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("edit-tour-view.fxml"));
            fxmlLoader.setController(new CreateTourViewController());
            Parent root = fxmlLoader.load();
            Stage newTourStage = new Stage();
            newTourStage.setTitle("New Tour");
            Scene newTourScene = new Scene(root, 600, 400);
            newTourStage.setScene(newTourScene);
            newTourStage.setMinWidth(600);
            newTourStage.setMinHeight(400);
            newTourStage.initOwner(stage);
            newTourStage.initModality(Modality.APPLICATION_MODAL);
            newTourStage.showAndWait();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    public void tourCancelButton(ActionEvent actionEvent) {
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }


    public void tourApplyButton(ActionEvent actionEvent) {
        if(viewModel.createNewTour() != null) {
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }
    }
}
