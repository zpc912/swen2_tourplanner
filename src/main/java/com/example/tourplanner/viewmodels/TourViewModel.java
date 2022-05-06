package com.example.tourplanner.viewmodels;

import com.example.tourplanner.bl.events.EventManagerFactory;
import com.example.tourplanner.bl.events.IEventManager;
import com.example.tourplanner.models.Tour;
import javafx.beans.property.*;

public class TourViewModel {

    private final IEventManager eventManager = EventManagerFactory.getEventManager();
    // TODO: add "AppLogic"

    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty from;
    private final StringProperty to;
    private final StringProperty transportType;
    private final StringProperty description;
    private final FloatProperty distance;


    public TourViewModel() {
        this.id = null;
        this.name = new SimpleStringProperty("");
        this.from = new SimpleStringProperty("");
        this.to = new SimpleStringProperty("");
        this.transportType = new SimpleStringProperty("");
        this.description = new SimpleStringProperty("");
        this.distance = new SimpleFloatProperty(0);
    }


    public TourViewModel(Tour tourObj) {
        this.id = new SimpleIntegerProperty(tourObj.getTourId());
        this.name = new SimpleStringProperty(tourObj.getTourName());
        this.from = new SimpleStringProperty(tourObj.getFrom());
        this.to = new SimpleStringProperty(tourObj.getTo());
        this.transportType = new SimpleStringProperty(tourObj.getTransportType());
        this.description = new SimpleStringProperty(tourObj.getTourDesc());
        this.distance = new SimpleFloatProperty(tourObj.getDistance());
    }


    public IntegerProperty getId() { return id; }

    public StringProperty getName() { return name; }

    public StringProperty getFrom() { return from; }

    public StringProperty getTo() { return to; }

    public StringProperty getTransportType() { return transportType; }

    public StringProperty getDescription() { return description; }

    public FloatProperty getDistance() { return distance; }
}
