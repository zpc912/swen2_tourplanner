package com.example.tourplanner.views;
import com.example.tourplanner.viewmodels.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    private final MainViewModel viewModel = new MainViewModel();

    @FXML
    public TextField searchField;
    @FXML
    private ListView<TourViewModel> tourListView;
    @FXML
    private Text currentTourName;
    @FXML
    private Text currentTourDescription;
    @FXML
    private ImageView currentTourImage;
    @FXML
    private TableView<TourLogViewModel> currentTourLogs;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchField.textProperty().bindBidirectional(viewModel.getSearchField());
        tourListView.setItems(viewModel.getFilteredTourList());
        currentTourName.textProperty().bindBidirectional(viewModel.getCurrentTourName());
        currentTourDescription.textProperty().bindBidirectional(viewModel.getCurrentTourDescription());
        currentTourImage.imageProperty().bindBidirectional(viewModel.getCurrentTourImage());
        currentTourLogs.setItems(viewModel.getLogsOfTours());
    }


    public void openHelpWindow(ActionEvent actionEvent) {
        HelpViewController.helpWindow();
    }
}