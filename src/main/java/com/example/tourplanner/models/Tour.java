package com.example.tourplanner.models;

public class Tour {

    private int tourId;
    private String tourName;
    private String tourDesc;
    private String from;
    private String to;
    private String transportType;
    private float distance;
    private String estTime;
    private String routeInfo;


    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getTourDesc() {
        return tourDesc;
    }

    public void setTourDesc(String tourDesc) {
        this.tourDesc = tourDesc;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getEstTime() {
        return estTime;
    }

    public void setEstTime(String estTime) {
        this.estTime = estTime;
    }

    public String getRouteInfo() {
        return routeInfo;
    }

    public void setRouteInfo(String routeInfo) {
        this.routeInfo = routeInfo;
    }
}
