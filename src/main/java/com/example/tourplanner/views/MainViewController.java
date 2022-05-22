package com.example.tourplanner.views;
import com.example.tourplanner.viewmodels.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    private final MainViewModel viewModel = new MainViewModel();

    @FXML
    public TextField searchField;
    @FXML
    private Text currTourName;
    @FXML
    private Text currTourDescription;
    @FXML
    private ImageView currTourImage;
    @FXML
    private ListView<TourViewModel> tourListView;
    @FXML
    private TableView<TourLogViewModel> currTourLogs;
    @FXML
    private TableColumn<TourLogViewModel, LocalDate> dateValue;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchField.textProperty().bindBidirectional(viewModel.getSearchField());
        currTourName.textProperty().bindBidirectional(viewModel.getCurrTourName());
        currTourDescription.textProperty().bindBidirectional(viewModel.getCurrTourDescription());
        currTourImage.imageProperty().bindBidirectional(viewModel.getCurrTourImage());
        tourListView.setItems(viewModel.getFilteredTourList());
        currTourLogs.setItems(viewModel.getLogsOfTours());
    }


    public void openHelpWindow(ActionEvent actionEvent) {
        HelpViewController.helpWindow();
    }


    public void createNewTour(ActionEvent actionEvent) {
        Node newNode = (Node) actionEvent.getSource();
        Stage newStage = (Stage) newNode.getScene().getWindow();

        CreateTourViewController.newTourWindow(newStage);
    }


    public void fillInSelectedTour() {
        TourViewModel tour = tourListView.getSelectionModel().getSelectedItem();

        if(tour != null) {
            viewModel.fillInCurrentTourDetails(tour);
        }
    }


    public void deleteSelectedTour(ActionEvent actionEvent) {
        TourViewModel tour = tourListView.getSelectionModel().getSelectedItem();

        if(tour != null && tour.getId().getValue() != null) {
            String tourId = tour.getId().getValue();
            viewModel.deleteTour(tourId);
        }
    }


    public void editSelectedTour(ActionEvent actionEvent) {
        TourViewModel tour = tourListView.getSelectionModel().getSelectedItem();

        if(tour != null) {
            Node newNode = (Node) actionEvent.getSource();
            Stage newStage = (Stage) newNode.getScene().getWindow();

            EditTourViewController.editTourWindow(newStage, tour);
        }
    }


    public void createTourLog(ActionEvent actionEvent) {
        TourViewModel tour = tourListView.getSelectionModel().getSelectedItem();

        if(tour != null) {
            Node newNode = (Node) actionEvent.getSource();
            Stage newStage = (Stage) newNode.getScene().getWindow();

            CreateTourLogViewController.newTourLogWindow(newStage, tour);
        }
    }


    public void deleteSelectedTourLog(ActionEvent actionEvent) {
        TourLogViewModel tourLog = currTourLogs.getSelectionModel().getSelectedItem();

        if(tourLog != null && tourLog.getTourLogIdValue() != null) {
            String tourLogId = tourLog.getTourLogIdValue();
            viewModel.deleteTourLog(tourLogId);
        }
    }


    public void editSelectedTourLog(ActionEvent actionEvent) {
        TourLogViewModel tourLog = currTourLogs.getSelectionModel().getSelectedItem();

        if(tourLog != null) {
            Node newNode = (Node) actionEvent.getSource();
            Stage newStage = (Stage) newNode.getScene().getWindow();

            EditTourLogViewController.editTourLogWindow(newStage, tourLog);
        }
    }
}