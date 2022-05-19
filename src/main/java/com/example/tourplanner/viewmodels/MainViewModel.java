package com.example.tourplanner.viewmodels;

import com.example.tourplanner.bl.AppLogicFactory;
import com.example.tourplanner.bl.IAppLogic;
import com.example.tourplanner.bl.events.*;
import com.example.tourplanner.models.Tour;
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

        // TODO: the same as above for "TourLogs"
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
        this.currTourName.setValue(tour.getName().getValue());
        this.currTourDescription.setValue(tour.getDescription().getValue());
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
}
