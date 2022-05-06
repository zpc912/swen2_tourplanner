package com.example.tourplanner.models;

import java.time.LocalDate;

public class TourLog {

    private int tourLogId;
    private Tour tour;
    private LocalDate date;
    private String comment;
    private int difficulty;
    private String totalTime;
    private int rating;


    public int getTourLogId() {
        return tourLogId;
    }

    public void setTourLogId(int tourLogId) {
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

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
