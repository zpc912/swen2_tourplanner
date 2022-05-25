package com.example.tourplanner.bl;

import com.example.tourplanner.bl.events.EventManagerFactory;
import com.example.tourplanner.bl.events.IEventListener;
import com.example.tourplanner.bl.events.IEventManager;
import com.example.tourplanner.bl.mapquestapi.MapQuestAPI;
import com.example.tourplanner.dal.common.DALFactory;
import com.example.tourplanner.dal.common.IFileAccess;
import com.example.tourplanner.dal.dao.ITourDAO;
import com.example.tourplanner.dal.dao.ITourLogDAO;
import com.example.tourplanner.dal.fileaccess.FileAccess;
import com.example.tourplanner.models.Tour;
import com.example.tourplanner.models.TourLog;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppLogic implements IAppLogic, IEventListener {

    private final IEventManager eventManager = EventManagerFactory.getEventManager();
    // TODO: add "Logger"


    public AppLogic() {
        eventManager.subscribe("tour-created", this);
        eventManager.subscribe("tour-updated", this);
        eventManager.subscribe("tour-deleted", this);
    }


    @Override
    public void update(String event, Object object) {
        if("tour-created".equals(event) || "tour-updated".equals(event) || "tour-deleted".equals(event)) {
            //this.retrieveTourDetails((String) object);
            //this.retrieveTourMap((String) object);
        }
    }


    @Override
    public List<Tour> getAllTours() {
        ITourDAO tourDAO = DALFactory.createTourDAO();

        try {
            if(tourDAO != null) {
                return tourDAO.getAllTours();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public Tour getTourById(String tourId) {
        ITourDAO tourDAO = DALFactory.createTourDAO();

        try {
            Tour tour = tourDAO.findById(tourId);

            if(tour != null) {
                return tour;
            }
            else {
                return null;
            }
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public String createTour(Tour tour) {
        ITourDAO tourDAO = DALFactory.createTourDAO();

        // Call method to get information about the route from MapQuest:
        ArrayList<String> routeInformation = requestTourDetails(tour);

        // Assign the received distance and estimated duration to the tour:
        float distance = Float.parseFloat(routeInformation.get(0));
        tour.setDistance(distance);
        tour.setEstTime(routeInformation.get(1));

        // Call method to get an image of the tour map:
        String routeMap = requestTourMap(tour);
        // Assign the file path of the saved image to the given tour:
        tour.setRouteInfo(routeMap);

        try {
            if(tourDAO != null) {
                String result = tourDAO.addNewTour(tour);
                return result;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public boolean updateTour(Tour tour) {
        ITourDAO tourDAO = DALFactory.createTourDAO();
        String tourId = tour.getTourId();

        try {
            if(tourDAO != null) {
                boolean result = tourDAO.updateTour(tourId, tour);
                return result;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public boolean deleteTour(String tourId) {
        ITourDAO tourDAO = DALFactory.createTourDAO();

        try {
            if(tourDAO != null) {
                boolean result = tourDAO.deleteTour(tourId);
                return result;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public String createTourLog(TourLog tourLog, Tour tour) {
        ITourLogDAO tourLogDAO = DALFactory.createTourLogDAO();

        try {
            if(tourLogDAO != null) {
                String result = tourLogDAO.addNewTourLog(tourLog, tour);
                return result;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public List<TourLog> getLogOfTour(Tour tour) {
        ITourLogDAO tourLogDAO = DALFactory.createTourLogDAO();

        try {
            if(tourLogDAO != null) {
                List<TourLog> result = tourLogDAO.getLogOfTour(tour);
                return result;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public boolean deleteTourLog(String tourLogId) {
        ITourLogDAO tourLogDAO = DALFactory.createTourLogDAO();

        try {
            if(tourLogDAO != null) {
                boolean result = tourLogDAO.deleteTourLog(tourLogId);
                return result;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public boolean updateTourLog(TourLog tourLog) {
        ITourLogDAO tourLogDAO = DALFactory.createTourLogDAO();

        try {
            if(tourLogDAO != null) {
                boolean result = tourLogDAO.updateTourLog(tourLog.getTourLogId(), tourLog);
                return result;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public ArrayList<String> requestTourDetails(Tour tour) {
        Map<String, Object> routeInformation = MapQuestAPI.getRouteDirections(tour.getFrom(), tour.getTo(), tour.getTransportType());

        String distance = routeInformation.get("distance").toString();
        String estTime = routeInformation.get("formattedTime").toString();
        ArrayList<String> distAndDur = new ArrayList<>();
        distAndDur.add(distance);
        distAndDur.add(estTime);

        if(distAndDur != null) {
            return distAndDur;
        }
        else {
            return null;
        }
    }


    @Override
    public String requestTourMap(Tour tour) {
        ArrayList<String> routeInformation = requestTourDetails(tour);

        if(Float.parseFloat(routeInformation.get(0)) > 0.0f)  {
            byte[] mapBytes = MapQuestAPI.getStaticMap(tour.getFrom(), tour.getTo(), tour.getTransportType());
            IFileAccess fileAccess = DALFactory.createFileAccess();

            String mapName = "map_" + tour.getTourId();
            String filePath = fileAccess.createFile(mapName + ".jpg", mapBytes);

            if(filePath != null) {
                return filePath;
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }


    @Override
    public File getTourImage(String fileName) {
        IFileAccess fileAccess = DALFactory.createFileAccess();

        File file = fileAccess.readFile(fileName);

        return file;
    }


    @Override
    public boolean deleteTourImage(String fileName) {
        IFileAccess fileAccess = DALFactory.createFileAccess();
        boolean result = fileAccess.deleteFile(fileName);

        if(result) {
            return true;
        }
        else {
            return false;
        }
    }
}
