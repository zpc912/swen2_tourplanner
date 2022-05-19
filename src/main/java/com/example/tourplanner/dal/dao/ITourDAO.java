package com.example.tourplanner.dal.dao;

import com.example.tourplanner.models.Tour;

import java.sql.SQLException;
import java.util.List;

public interface ITourDAO {

    Tour findById(String tourId) throws SQLException;

    List<Tour> getAllTours() throws SQLException;

    String addNewTour(Tour newTour) throws SQLException;

    boolean updateTour(String tourId, Tour tour) throws SQLException;

    boolean deleteTour(String tourId) throws SQLException;
}
