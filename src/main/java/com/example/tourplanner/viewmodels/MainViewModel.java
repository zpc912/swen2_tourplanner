package com.example.tourplanner.viewmodels;

import com.example.tourplanner.bl.AppLogicFactory;
import com.example.tourplanner.bl.IAppLogic;
import com.example.tourplanner.bl.events.*;
import com.example.tourplanner.models.Tour;
import com.example.tourplanner.models.TourLog;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.image.Image;

import java.sql.SQLException;

public class MainViewModel implements IEventListener {

    private final IEventManager eventManager = EventManagerFactory.getEventManager();
    private final IAppLogic appLogic = AppLogicFactory.getAppLogic();
    // TODO: add "Logger"

    private final StringProperty id = new SimpleStringProperty("");
    private final StringProperty searchField = new SimpleStringProperty("");
    private final StringProperty currTourName = new SimpleStringProperty("");
    private final StringProperty currTourDescription = new SimpleStringProperty("");
    private final ObjectProperty<Image> currTourImage = new SimpleObjectProperty<>();
    private final ObservableList<TourViewModel> allTours = FXCollections.observableArrayList();
    FilteredList<TourViewModel> filteredTourList = new FilteredList<>(allTours, s -> true);
    private final ObservableList<TourLogViewModel> logsOfTours = FXCollections.observableArrayList();


    public MainViewModel() {
        eventManager.subscribe("tour-created", this);
        eventManager.subscribe("tour-updated", this);
        eventManager.subscribe("tour-deleted", this);
        eventManager.subscribe("tourlog-created", this);
        eventManager.subscribe("tourlog-updated", this);
        eventManager.subscribe("tourlog-deleted", this);

        for(Tour tour : appLogic.getAllTours()) {
            allTours.add(new TourViewModel(tour));
        }
    }


    @Override
    public void update(String event, Object object) {
        if("tour-updated".equals(event)) {
            this.syncAllTours();
            this.syncCurrentTour();
        }

        if("tour-created".equals(event) || "tour-deleted".equals(event)) {
            this.syncAllTours();
        }

        if("tourlog-created".equals(event) || "tourlog-updated".equals(event) || "tourlog-deleted".equals(event)) {
            this.syncCurrentTourLog();
        }
    }


    public StringProperty getId() { return id; }

    public StringProperty getSearchField() { return searchField; }

    public StringProperty getCurrTourName() { return currTourName; }

    public StringProperty getCurrTourDescription() { return currTourDescription; }

    public ObjectProperty<Image> getCurrTourImage() { return currTourImage; }

    public ObservableList<TourViewModel> getAllTours() { return allTours; }

    public FilteredList<TourViewModel> getFilteredTourList() { return filteredTourList; }

    public ObservableList<TourLogViewModel> getLogsOfTours() { return logsOfTours; }


    private void syncAllTours() {
        allTours.clear();

        for(Tour tour : appLogic.getAllTours()) {
            allTours.add(new TourViewModel(tour));
        }
    }


    public void fillInCurrentTourDetails(TourViewModel tour) {
        this.id.setValue(tour.getId().getValue());
        this.currTourName.setValue(tour.getName().getValue() + " (Distance: " + tour.getDistance().getValue() + "km)");
        this.currTourDescription.setValue(tour.getDescription().getValue());
        this.loadCurrentTourLogs(tour);
    }


    private void syncCurrentTour() {
        Tour currTour = appLogic.getTourById(id.getValue());

        this.fillInCurrentTourDetails(new TourViewModel(currTour));
    }


    public boolean deleteTour(String tourId) {
        boolean result = appLogic.deleteTour(tourId);

        if(result) {
            eventManager.notify("tour-deleted", tourId);
        }

        return result;
    }


    private void syncCurrentTourLog() {
        Tour currTour = appLogic.getTourById(id.getValue());
        this.loadCurrentTourLogs(new TourViewModel(currTour));
    }


    public void loadCurrentTourLogs(TourViewModel currTour) {
        logsOfTours.clear();

        Tour tour = currTour.initializeTour();
        for(TourLog tourLog : appLogic.getLogOfTour(tour)) {
            TourLogViewModel tourLogViewModel = new TourLogViewModel(tourLog);
            logsOfTours.add(tourLogViewModel);
        }
    }


    public boolean deleteTourLog(String tourLogId) {
        boolean result = appLogic.deleteTourLog(tourLogId);

        if(result) {
            eventManager.notify("tourlog-deleted", tourLogId);
        }

        return result;
    }
}