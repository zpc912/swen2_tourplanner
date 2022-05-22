package com.example.tourplanner.dal.postgres;

import com.example.tourplanner.dal.common.DALFactory;
import com.example.tourplanner.dal.common.IDatabase;
import com.example.tourplanner.dal.dao.ITourDAO;
import com.example.tourplanner.dal.dao.ITourLogDAO;
import com.example.tourplanner.models.Tour;
import com.example.tourplanner.models.TourLog;

import java.sql.Array;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TourLogDAO implements ITourLogDAO {

    private final String GET_BY_ID = "SELECT * FROM public.\"tourlogs\" WHERE \"tourLogId\" = ?;";
    private final String GET_ALL = "SELECT * FROM public.\"tourlogs\";";
    private final String INSERT_NEW = "INSERT INTO public.\"tourlogs\" (\"tourLogId\", \"tourId\", \"date\", \"comment\", \"difficulty\", \"totalTime\", \"rating\") VALUES (?,?,?,?,?,?,?);";
    private final String DELETE = "DELETE FROM public.\"tourlogs\" WHERE \"tourLogId\" = ?;";
    private final String UPDATE = "UPDATE public.\"tourlogs\" SET \"date\" = ?, \"comment\" = ?, \"difficulty\" = ?, \"totalTime\" = ?, \"rating\" = ? WHERE \"tourLogId\" = ?;";
    private final String GET_BY_TOUR_ID = "SELECT * FROM public.\"tourlogs\" WHERE \"tourId\" = ?;";

    private IDatabase db;
    private ITourDAO tourDAO;


    public TourLogDAO() {
        this.db = DALFactory.getDatabase();
        this.tourDAO = DALFactory.createTourDAO();
    }


    @Override
    public TourLog findById(String tourLogId) {
        return null;
    }


    @Override
    public String addNewTourLog(TourLog tourLog, Tour tour) throws SQLException {
        ArrayList<Object> parameters = new ArrayList<>();

        parameters.add(tourLog.getTourLogId());
        parameters.add(tour.getTourId());
        parameters.add(tourLog.getDate());
        parameters.add(tourLog.getComment());
        parameters.add(tourLog.getDifficulty());
        parameters.add(tourLog.getTotalTime());
        parameters.add(tourLog.getRating());

        return db.insertNew(INSERT_NEW, parameters);
    }


    @Override
    public boolean updateTourLog(String tourLogId, TourLog tourLog) throws SQLException {
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(tourLog.getDate());
        parameters.add(tourLog.getComment());
        parameters.add(tourLog.getDifficulty());
        parameters.add(tourLog.getTotalTime());
        parameters.add(tourLog.getRating());
        parameters.add(tourLogId);

        return db.update(UPDATE, parameters);
    }


    @Override
    public boolean deleteTourLog(String tourLogId) throws SQLException {
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(tourLogId);

        return db.delete(DELETE, parameters);
    }


    @Override
    public List<TourLog> getAllTourLogs() {
        return null;
    }


    @Override
    public List<TourLog> getLogOfTour(Tour tour) throws SQLException {
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(tour.getTourId());

        List<Map<String, Object>> rows = db.read(GET_BY_TOUR_ID, parameters);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<TourLog> tourLogs = new ArrayList<>();
        for(Map<String, Object> row : rows) {
            TourLog tourLog = new TourLog(
                    (String) row.get("tourLogId"),
                    tour,
                    LocalDate.parse((String) row.get("date"), dateTimeFormatter),
                    (String) row.get("comment"),
                    (String) row.get("difficulty"),
                    (String) row.get("totalTime"),
                    (String) row.get("rating"));

            tourLogs.add(tourLog);
        }

        return tourLogs;
    }
}
