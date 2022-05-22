package com.example.tourplanner.models;

import java.time.LocalDate;

public class TourLog {

    private String tourLogId;
    private Tour tour;
    private LocalDate date;
    private String comment;
    private String difficulty;
    private String totalTime;
    private String rating;


    public TourLog(String tourLogId, Tour tour, LocalDate date, String comment, String difficulty, String totalTime, String rating) {
        this.tourLogId = tourLogId;
        this.tour = tour;
        this.date = date;
        this.comment = comment;
        this.difficulty = difficulty;
        this.totalTime = totalTime;
        this.rating = rating;
    }


    public String getTourLogId() {
        return tourLogId;
    }

    public void setTourLogId(String tourLogId) {
        this.tourLogId = tourLogId;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
