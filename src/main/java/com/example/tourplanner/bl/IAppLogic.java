package com.example.tourplanner.bl;

import com.example.tourplanner.models.*;

import java.sql.SQLException;
import java.util.List;

public interface IAppLogic {

    List<Tour> getAllTours();

    Tour getTourById(String tourId);

    String createTour(Tour tour);

    boolean updateTour(Tour tour);

    boolean deleteTour(String tourId);
}
