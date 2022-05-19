package com.example.tourplanner.dal.postgres;

import com.example.tourplanner.dal.common.DALFactory;
import com.example.tourplanner.dal.common.IDatabase;
import com.example.tourplanner.dal.dao.ITourDAO;
import com.example.tourplanner.dal.dao.ITourLogDAO;
import com.example.tourplanner.models.TourLog;

import java.util.List;

public class TourLogDAO implements ITourLogDAO {

    private final String GET_BY_ID = "SELECT * FROM public.\"tourlogs\" WHERE \"tourLogId\" = CAST(? AS VARCHAR);";
    private final String GET_ALL = "SELECT * FROM public.\"tourlogs\";";
    private final String INSERT_NEW = "INSERT INTO public.\"tourlogs\" (\"tourId\", \"date\", \"comment\", \"difficulty\", \"totalTime\", \"rating\") VALUES (?,?,?,?,?,?);";

    private IDatabase db;
    private ITourDAO tourDAO;


    public TourLogDAO() {
        this.db = DALFactory.getDatabase();
        this.tourDAO = DALFactory.createTourDAO();
    }


    @Override
    public TourLog findById(int tourLogId) {
        return null;
    }


    @Override
    public TourLog addNewTourLog(TourLog tourLog) {
        return null;
    }


    @Override
    public boolean updateTourLog(int tourLogId, TourLog tourLog) {
        return false;
    }


    @Override
    public boolean deleteTourLog(int tourLogId) {
        return false;
    }


    @Override
    public List<TourLog> getAllTourLogs() {
        return null;
    }
}
