package com.example.tourplanner.dal.postgres;

import com.example.tourplanner.dal.common.DALFactory;
import com.example.tourplanner.dal.common.IDatabase;
import com.example.tourplanner.dal.dao.ITourDAO;
import com.example.tourplanner.models.Tour;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TourDAO implements ITourDAO {

    private final String GET_BY_ID = "SELECT * FROM public.\"tours\" WHERE \"tourId\" = ?;";
    private final String GET_ALL = "SELECT * FROM public.\"tours\";";
    private final String INSERT_NEW = "INSERT INTO public.\"tours\" (\"tourId\", \"tourName\", \"tourDesc\", \"from\", \"to\", \"transportType\", \"distance\", \"estTime\", \"routeInfo\") VALUES (?,?,?,?,?,?,CAST(? AS DECIMAL),?,?);";
    private final String DELETE = "DELETE FROM public.\"tours\" WHERE \"tourId\" = ?;";
    private final String UPDATE = "UPDATE public.\"tours\" SET \"tourName\" = ?, \"tourDesc\" = ?, \"from\" = ?, \"to\" = ?, \"transportType\" = ?, \"distance\" = CAST(? AS DECIMAL), \"estTime\" = ?, \"routeInfo\" = ? WHERE \"tourId\" = ?;";

    private IDatabase db;


    public TourDAO() {
        this.db = DALFactory.getDatabase();
    }


    @Override
    public List<Tour> getAllTours() throws SQLException {
        List<Map<String, Object>> rows = db.read(GET_ALL);
        List<Tour> allTours = parseDataFromResultSet(rows);

        return allTours;
    }


    @Override
    public Tour findById(String tourId) throws SQLException {
        ArrayList<Object> params = new ArrayList<>();
        params.add(tourId);

        List<Map<String, Object>> rows = db.read(GET_BY_ID, params);
        List<Tour> selectedTours = parseDataFromResultSet(rows);

        //return (Tour) selectedTours;
        if(selectedTours.size() > 0) {
            return selectedTours.get(0);
        }
        else {
            return null;
        }
    }


    @Override
    public String addNewTour(Tour newTour) throws SQLException {
        ArrayList<Object> parameters = new ArrayList<>();

        parameters.add(newTour.getTourId());
        parameters.add(newTour.getTourName());
        parameters.add(newTour.getTourDesc());
        parameters.add(newTour.getFrom());
        parameters.add(newTour.getTo());
        parameters.add(newTour.getTransportType());
        parameters.add(newTour.getDistance());
        parameters.add(newTour.getEstTime());
        parameters.add(newTour.getRouteInfo());

        return db.insertNew(INSERT_NEW, parameters);
    }


    @Override
    public boolean updateTour(String tourId, Tour tour) throws SQLException {
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(tour.getTourName());
        parameters.add(tour.getTourDesc());
        parameters.add(tour.getFrom());
        parameters.add(tour.getTo());
        parameters.add(tour.getTransportType());
        parameters.add(tour.getDistance());
        parameters.add(tour.getEstTime());
        parameters.add(tour.getRouteInfo());
        parameters.add(tourId);

        return db.update(UPDATE, parameters);
    }


    @Override
    public boolean deleteTour(String tourId) throws SQLException {
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(tourId);

        return db.delete(DELETE, parameters);
    }


    private List<Tour> parseDataFromResultSet(List<Map<String, Object>> rows) {
        List<Tour> selectedTours = new ArrayList<>();

        for(Map<String, Object> row : rows) {
            selectedTours.add(new Tour(
                    (String) row.get("tourId"),
                    (String) row.get("tourName"),
                    (String) row.get("tourDesc"),
                    (String) row.get("from"),
                    (String) row.get("to"),
                    (String) row.get("transportType"),
                    ((BigDecimal) row.get("distance")).floatValue(),
                    (String) row.get("estTime"),
                    (String) row.get("routeInfo")
            ));
        }

        return selectedTours;
    }
}
