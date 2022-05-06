package com.example.tourplanner.views;

import com.example.tourplanner.viewmodels.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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

    // TODO: "onAction" methods
}
