module com.example.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires io;
    requires kernel;
    requires layout;


    opens com.example.tourplanner to javafx.fxml;
    exports com.example.tourplanner;
    exports com.example.tourplanner.views;
    opens com.example.tourplanner.views to javafx.fxml;
    opens com.example.tourplanner.viewmodels;
    exports com.example.tourplanner.bl;
    exports com.example.tourplanner.models;
    exports com.example.tourplanner.dal.common;
    exports com.example.tourplanner.dal.dao;
    exports com.example.tourplanner.dal.fileaccess;
    exports com.example.tourplanner.dal.postgres;
}