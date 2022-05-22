package com.example.tourplanner.dal.dao;

import com.example.tourplanner.models.Tour;
import com.example.tourplanner.models.TourLog;

import java.sql.SQLException;
import java.util.List;

public interface ITourLogDAO {

    TourLog findById(String tourLogId) throws SQLException;

    String addNewTourLog(TourLog tourLog, Tour tour) throws SQLException;

    boolean updateTourLog(String tourLogId, TourLog tourLog) throws SQLException;

    boolean deleteTourLog(String tourLogId) throws SQLException;

    List<TourLog> getAllTourLogs() throws SQLException;

    List<TourLog> getLogOfTour(Tour tour) throws SQLException;
}
