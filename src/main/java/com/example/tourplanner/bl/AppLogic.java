package com.example.tourplanner.bl;

import com.example.tourplanner.bl.events.EventManagerFactory;
import com.example.tourplanner.bl.events.IEventListener;
import com.example.tourplanner.bl.events.IEventManager;
import com.example.tourplanner.bl.mapquestapi.MapQuestAPI;
import com.example.tourplanner.bl.reports.ReportManager;
import com.example.tourplanner.dal.common.DALFactory;
import com.example.tourplanner.dal.common.IFileAccess;
import com.example.tourplanner.dal.dao.ITourDAO;
import com.example.tourplanner.dal.dao.ITourLogDAO;
import com.example.tourplanner.dal.fileaccess.FileAccess;
import com.example.tourplanner.models.Tour;
import com.example.tourplanner.models.TourLog;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppLogic implements IAppLogic, IEventListener {

    private final IEventManager eventManager = EventManagerFactory.getEventManager();
    private final Logger log = LogManager.getLogger(AppLogic.class);


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
        log.info("Get all tours");
        ITourDAO tourDAO = DALFactory.createTourDAO();

        try {
            if(tourDAO != null) {
                return tourDAO.getAllTours();
            }
        } catch(SQLException e) {
            e.printStackTrace();
            log.error(e);
        }

        return null;
    }


    @Override
    public Tour getTourById(String tourId) {
        log.info("Get tour by ID (ID: " + tourId + ")");
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
            log.error(e);
            return null;
        }
    }


    @Override
    public String createTour(Tour tour) {
        log.info("Create new tour (ID: " + tour.getTourId() + ")");
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
            log.error(e);
        }

        return null;
    }


    @Override
    public boolean updateTour(Tour tour) {
        log.info("Update tour (ID: " + tour.getTourId() + ")");
        ITourDAO tourDAO = DALFactory.createTourDAO();
        String tourId = tour.getTourId();

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
                boolean result = tourDAO.updateTour(tourId, tour);
                return result;
            }
        } catch(SQLException e) {
            e.printStackTrace();
            log.error(e);
        }

        return false;
    }


    @Override
    public boolean deleteTour(String tourId) {
        log.info("Delete tour (ID: " + tourId + ")");
        ITourDAO tourDAO = DALFactory.createTourDAO();

        try {
            if(tourDAO != null) {
                boolean result = tourDAO.deleteTour(tourId);
                return result;
            }
        } catch(SQLException e) {
            e.printStackTrace();
            log.error(e);
        }

        return false;
    }


    @Override
    public String createTourLog(TourLog tourLog, Tour tour) {
        log.info("Create tour log (ID: " + tourLog.getTourLogId() + ")");
        ITourLogDAO tourLogDAO = DALFactory.createTourLogDAO();

        try {
            if(tourLogDAO != null) {
                String result = tourLogDAO.addNewTourLog(tourLog, tour);
                return result;
            }
        } catch(SQLException e) {
            e.printStackTrace();
            log.error(e);
        }

        return null;
    }


    @Override
    public List<TourLog> getLogOfTour(Tour tour) {
        log.info("Get logs of tour (ID: " + tour.getTourId() + ")");
        ITourLogDAO tourLogDAO = DALFactory.createTourLogDAO();

        try {
            if(tourLogDAO != null) {
                List<TourLog> result = tourLogDAO.getLogOfTour(tour);
                return result;
            }
        } catch(SQLException e) {
            e.printStackTrace();
            log.error(e);
        }

        return null;
    }


    @Override
    public boolean deleteTourLog(String tourLogId) {
        log.info("Delete tour log (ID: " + tourLogId + ")");
        ITourLogDAO tourLogDAO = DALFactory.createTourLogDAO();

        try {
            if(tourLogDAO != null) {
                boolean result = tourLogDAO.deleteTourLog(tourLogId);
                return result;
            }
        } catch(SQLException e) {
            e.printStackTrace();
            log.error(e);
        }

        return false;
    }


    @Override
    public boolean updateTourLog(TourLog tourLog) {
        log.info("Update tour log (ID: " + tourLog.getTourLogId() + ")");
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
        log.info("Request tour details from MapQuest (ID: " + tour.getTourId() + ")");
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
            log.error("Requesting tour details from MapQuest failed");
            return null;
        }
    }


    @Override
    public String requestTourMap(Tour tour) {
        log.info("Request tour map from StaticMap (ID: " + tour.getTourId() + ")");
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
                log.error("Requesting tour map failed (route not found)");
                return null;
            }
        }
        else {
            log.error("Requesting tour map failed (invalid data from MapQuest)");
            return null;
        }
    }


    @Override
    public File getTourImage(String fileName) {
        log.info("Loading map image (Map: " + fileName + ")");
        IFileAccess fileAccess = DALFactory.createFileAccess();

        File file = fileAccess.readFile(fileName);

        return file;
    }


    @Override
    public boolean deleteTourImage(String fileName) {
        log.info("Deleting map image (Map: " + fileName + ")");
        IFileAccess fileAccess = DALFactory.createFileAccess();
        boolean result = fileAccess.deleteFile(fileName);

        if(result) {
            return true;
        }
        else {
            log.error("Deleting map image failed (Map: " + fileName + ")");
            return false;
        }
    }


    @Override
    public void generateTourReport(Tour tour, List<TourLog> tourLogs) {
        log.info("Generating single tour report (ID: " + tour.getTourId() + ")");
        ReportManager reportManager = new ReportManager();
        reportManager.generateTourReport(tour, tourLogs);
    }


    @Override
    public void generateSummaryReport(List<Tour> allTours, List<TourLog> allTourLogs) {
        log.info("Generating summary report");
        ReportManager reportManager = new ReportManager();
        reportManager.generateSummaryReport(allTours, allTourLogs);
    }


    @Override
    public List<TourLog> getAllTourLogs() {
        log.info("Get all tour logs");
        ITourLogDAO tourLogDAO = DALFactory.createTourLogDAO();

        try {
            if(tourLogDAO != null) {
                return tourLogDAO.getAllTourLogs();
            }
        } catch(SQLException e) {
            e.printStackTrace();
            log.error(e);
        }

        return null;
    }
}
