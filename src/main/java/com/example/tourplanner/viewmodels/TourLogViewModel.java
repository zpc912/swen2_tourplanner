package com.example.tourplanner.viewmodels;

import com.example.tourplanner.bl.AppLogicFactory;
import com.example.tourplanner.bl.IAppLogic;
import com.example.tourplanner.bl.events.EventManagerFactory;
import com.example.tourplanner.bl.events.IEventManager;
import com.example.tourplanner.models.Tour;
import com.example.tourplanner.models.TourLog;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class TourLogViewModel {

    private final IEventManager eventManager = EventManagerFactory.getEventManager();
    private final IAppLogic appLogic = AppLogicFactory.getAppLogic();

    private final StringProperty tourLogId;
    private Tour tour;
    private final ObjectProperty<LocalDate> date;
    private final StringProperty comment;
    private final StringProperty difficulty;
    private final StringProperty totalTime;
    private final StringProperty rating;


    public TourLogViewModel() {
        this.tourLogId = null;
        this.tour = null;
        this.date = new SimpleObjectProperty<>();
        this.comment = new SimpleStringProperty("");
        this.difficulty = new SimpleStringProperty("");
        this.totalTime = new SimpleStringProperty("");
        this.rating = new SimpleStringProperty("");
    }


    public TourLogViewModel(TourLog tourLog) {
        this.tourLogId = new SimpleStringProperty(tourLog.getTourLogId());
        this.tour = tourLog.getTour();
        this.date = new SimpleObjectProperty<>(tourLog.getDate());
        this.comment = new SimpleStringProperty(tourLog.getComment());
        this.difficulty = new SimpleStringProperty(tourLog.getDifficulty());
        this.totalTime = new SimpleStringProperty(tourLog.getTotalTime());
        this.rating = new SimpleStringProperty(tourLog.getRating());
    }


    public StringProperty tourLogIdProperty() { return tourLogId; }
    public String getTourLogIdValue() { return tourLogId.getValue(); }

    public Tour getTour() { return tour; }
    public void setTour(Tour tour) { this.tour = tour; }

    public ObjectProperty<LocalDate> dateProperty() { return date; }
    public LocalDate getDateValue() { return date.getValue(); }

    public StringProperty commentProperty() { return comment; }
    public String getCommentValue() { return comment.getValue(); }

    public StringProperty difficultyProperty() { return difficulty; }
    public String getDifficultyValue() { return difficulty.getValue(); }

    public StringProperty totalTimeProperty() { return totalTime; }
    public String getTotalTimeValue() { return totalTime.getValue(); }

    public StringProperty ratingProperty() { return rating; }
    public String getRatingValue() { return rating.getValue(); }


    public String createNewTourLog() {
        TourLog newTourLog = this.initializeTourLog();
        String result = appLogic.createTourLog(newTourLog, tour);

        if(result != null) {
            eventManager.notify("tourlog-created", result);
        }

        return result;
    }


    public TourLog initializeTourLog() {
        UUID uuid = UUID.randomUUID();
        String tourLogId = ""+uuid;
        TourLog newTourLog = new TourLog(tourLogId, tour, date.getValue(), comment.getValue(), difficulty.getValue(), totalTime.getValue(), rating.getValue());

        return newTourLog;
    }


    public boolean editTourLog() {
        TourLog updatedTourLog = new TourLog(getTourLogIdValue(), getTour(), getDateValue(), getCommentValue(), getDifficultyValue(), getTotalTimeValue(), getRatingValue());
        boolean result = appLogic.updateTourLog(updatedTourLog);

        if(result) {
            eventManager.notify("tourlog-updated", getTourLogIdValue());
        }

        return result;
    }
}
