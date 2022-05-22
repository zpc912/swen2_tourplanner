package com.example.tourplanner.viewmodels;

import com.example.tourplanner.bl.AppLogicFactory;
import com.example.tourplanner.bl.IAppLogic;
import com.example.tourplanner.bl.events.EventManagerFactory;
import com.example.tourplanner.bl.events.IEventManager;
import com.example.tourplanner.models.Tour;
import javafx.beans.property.*;

import java.util.UUID;

public class TourViewModel {

    private final IEventManager eventManager = EventManagerFactory.getEventManager();
    private final IAppLogic appLogic = AppLogicFactory.getAppLogic();

    private final StringProperty id;
    private final StringProperty name;
    private final StringProperty description;
    private final StringProperty from;
    private final StringProperty to;
    private final StringProperty transportType;
    private final FloatProperty distance;
    private final StringProperty estTime;
    private final StringProperty routeInfo;


    public TourViewModel() {
        this.id = null;
        this.name = new SimpleStringProperty("");
        this.description = new SimpleStringProperty("");
        this.from = new SimpleStringProperty("");
        this.to = new SimpleStringProperty("");
        this.transportType = new SimpleStringProperty("");
        this.distance = new SimpleFloatProperty(0);
        this.estTime = new SimpleStringProperty("");
        this.routeInfo = new SimpleStringProperty("");
    }


    public TourViewModel(Tour tourObj) {
        this.id = new SimpleStringProperty(tourObj.getTourId());
        this.name = new SimpleStringProperty(tourObj.getTourName());
        this.description = new SimpleStringProperty(tourObj.getTourDesc());
        this.from = new SimpleStringProperty(tourObj.getFrom());
        this.to = new SimpleStringProperty(tourObj.getTo());
        this.transportType = new SimpleStringProperty(tourObj.getTransportType());
        this.distance = new SimpleFloatProperty(tourObj.getDistance());
        this.estTime = new SimpleStringProperty(tourObj.getEstTime());
        this.routeInfo = new SimpleStringProperty(tourObj.getRouteInfo());
    }


    @Override
    public String toString() {
        return name.getValue();
    }


    public StringProperty getId() { return id; }

    public StringProperty getName() { return name; }

    public StringProperty getDescription() { return description; }

    public StringProperty getFrom() { return from; }

    public StringProperty getTo() { return to; }

    public StringProperty getTransportType() { return transportType; }

    public FloatProperty getDistance() { return distance; }

    public StringProperty getEstTime() { return estTime; }

    public StringProperty getRouteInfo() { return routeInfo; }


    public String createNewTour() {
        UUID uuid = UUID.randomUUID();
        String tourId = ""+uuid;
        float mockValue = 0.0f; // PLACEHOLDER VALUE
        Tour tour = new Tour(tourId, name.getValue(), description.getValue(), from.getValue(), to.getValue(), transportType.getValue(), mockValue, null, null);
        String result = appLogic.createTour(tour);

        if(result != null) {
            eventManager.notify("tour-created", result);
        }

        return result;
    }


    public boolean editTour() {
        Tour updatedTour = new Tour(id.getValue(), name.getValue(), description.getValue(), from.getValue(), to.getValue(), transportType.getValue(), distance.getValue(), estTime.getValue(), routeInfo.getValue());
        boolean result = appLogic.updateTour(updatedTour);

        if(result) {
            eventManager.notify("tour-updated", id.getValue());
        }

        return result;
    }


    public Tour initializeTour() {
        Tour tour = new Tour(id.getValue(), name.getValue(), description.getValue(), from.getValue(), to.getValue(), transportType.getValue(), distance.getValue(), estTime.getValue(), routeInfo.getValue());
        return tour;
    }
}
