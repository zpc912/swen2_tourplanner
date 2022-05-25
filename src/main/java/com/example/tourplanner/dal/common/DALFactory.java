package com.example.tourplanner.dal.common;

import com.example.tourplanner.bl.ConfigurationManager;
import com.example.tourplanner.dal.dao.ITourDAO;
import com.example.tourplanner.dal.dao.ITourLogDAO;
import com.example.tourplanner.dal.fileaccess.FileAccess;
import com.example.tourplanner.dal.postgres.Database;
import com.example.tourplanner.dal.postgres.TourDAO;
import com.example.tourplanner.dal.postgres.TourLogDAO;

import java.sql.SQLException;

public class DALFactory {

    private static IDatabase db;


    public static IDatabase getDatabase() {
        if(db == null) {
            db = createDatabase();
        }

        return db;
    }


    private static IDatabase createDatabase() {
        String connectionString = ConfigurationManager.getConfigProperty("DbConnectionString");
        return createDatabase(connectionString);
    }


    private static IDatabase createDatabase(String connectionString) {
        try {
            Class<Database> cls = (Class<Database>) Class.forName(Database.class.getName());
            return cls.getConstructor(String.class).newInstance(connectionString);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static ITourDAO createTourDAO() {
        try {
            Class<ITourDAO> cls = (Class<ITourDAO>) Class.forName(TourDAO.class.getName());
            return cls.getConstructor().newInstance();
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static ITourLogDAO createTourLogDAO() {
        try {
            Class<ITourLogDAO> cls = (Class<ITourLogDAO>) Class.forName(TourLogDAO.class.getName());
            return cls.getConstructor().newInstance();
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static IFileAccess createFileAccess() {
        try {
            Class<IFileAccess> cls = (Class<IFileAccess>) Class.forName(FileAccess.class.getName());
            return cls.getConstructor().newInstance();
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
