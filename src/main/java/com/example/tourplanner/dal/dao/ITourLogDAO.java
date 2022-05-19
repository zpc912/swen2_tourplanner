package com.example.tourplanner.dal.dao;

import com.example.tourplanner.models.TourLog;

import java.util.List;

public interface ITourLogDAO {

    TourLog findById(int tourLogId);

    TourLog addNewTourLog(TourLog tourLog);

    boolean updateTourLog(int tourLogId, TourLog tourLog);

    boolean deleteTourLog(int tourLogId);

    List<TourLog> getAllTourLogs();
}
