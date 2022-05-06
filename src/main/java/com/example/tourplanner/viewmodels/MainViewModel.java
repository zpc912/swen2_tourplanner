package com.example.tourplanner.viewmodels;

import com.example.tourplanner.bl.events.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.image.Image;

public class MainViewModel implements IEventListener {

    private final IEventManager eventManager = EventManagerFactory.getEventManager();
    // TODO: add "Logger"
    // TODO: add "AppLogic"

    private IntegerProperty id = new SimpleIntegerProperty(-1);
    private StringProperty searchField = new SimpleStringProperty("");
    private StringProperty currentTourName = new SimpleStringProperty("");
    private StringProperty currentTourDescription = new SimpleStringProperty("");
    private ObjectProperty<Image> currentTourImage = new SimpleObjectProperty<>();
    private final ObservableList<TourViewModel> tours = FXCollections.observableArrayList();
    FilteredList<TourViewModel> filteredTourList = new FilteredList<>(tours, s -> true);
    private final ObservableList<TourLogViewModel> logsOfTours = FXCollections.observableArrayList();


    public MainViewModel() {
        eventManager.subscribe("tour.save", this);
        eventManager.subscribe("tour.update", this);
        eventManager.subscribe("tour.delete", this);
        eventManager.subscribe("tour-log.save", this);
        eventManager.subscribe("tour-log.update", this);
        eventManager.subscribe("tour-log.delete", this);
    }


    @Override
    public void update(String event, Object object) {
        // TODO: updates for the subscribers
    }


    public IntegerProperty getId() { return id; }

    public StringProperty getSearchField() {
        return searchField;
    }

    public StringProperty getCurrentTourName() { return currentTourName; }

    public StringProperty getCurrentTourDescription() { return currentTourDescription; }

    public ObjectProperty<Image> getCurrentTourImage() { return currentTourImage; }

    public ObservableList<TourViewModel> getTours() { return tours; }

    public FilteredList<TourViewModel> getFilteredTourList() { return filteredTourList; }

    public ObservableList<TourLogViewModel> getLogsOfTours() { return logsOfTours; }
}
