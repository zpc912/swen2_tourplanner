package com.example.tourplanner.bl;

import com.example.tourplanner.bl.events.EventManagerFactory;
import com.example.tourplanner.bl.events.IEventListener;
import com.example.tourplanner.bl.events.IEventManager;
import com.example.tourplanner.dal.common.DALFactory;
import com.example.tourplanner.dal.dao.ITourDAO;
import com.example.tourplanner.dal.dao.ITourLogDAO;
import com.example.tourplanner.models.Tour;
import com.example.tourplanner.models.TourLog;

import java.sql.SQLException;
import java.util.List;

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
            this.retrieveTourDetails((String) object);
            this.retrieveTourMap((String) object);
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


    public boolean retrieveTourDetails(String tourId) {
        // TODO
        return true;
    }


    public boolean retrieveTourMap(String tourId) {
        // TODO
        return true;
    }
}
