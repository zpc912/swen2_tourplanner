package com.example.tourplanner.dal.postgres;

import com.example.tourplanner.bl.ConfigurationManager;
import com.example.tourplanner.dal.common.IDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database implements IDatabase {

    private String connectionString;


    public Database(String connectionString) {
        this.connectionString = connectionString;
    }


    private Connection createConnection() throws SQLException {
        String db_username = ConfigurationManager.getConfigProperty("DbUser");
        String db_password = ConfigurationManager.getConfigProperty("DbPassword");

        try {
            return DriverManager.getConnection(connectionString, db_username, db_password);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        throw new SQLException("Establishing database connection failed.");
    }


    @Override
    public <T> List<T> read(String sqlQuery) throws SQLException {
        try(
                Connection conn = createConnection();
                Statement statement = conn.createStatement();
        ) {
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            return (List<T>) GetDataFromResultSet(resultSet);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        throw new SQLException("Reading data failed.");
    }


    @Override
    public <T> List<T> read(String sqlQuery, ArrayList<Object> parameters) throws SQLException {
        try(
                Connection conn = createConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
        ) {
            // Dynamic parameters:
            for(int i=0; i<parameters.size(); i++) {
                if(parameters.get(i) != null) {
                    preparedStatement.setString(i+1, parameters.get(i).toString());
                }
                else {
                    preparedStatement.setNull(i+1, java.sql.Types.NULL);
                }
            }

            //ResultSet resultSet = preparedStatement.executeQuery(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            return (List<T>) GetDataFromResultSet(resultSet);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        throw new SQLException("Reading data failed.");
    }


    @Override
    public String insertNew(String sqlQuery, ArrayList<Object> parameters) throws SQLException {
        try(
                Connection conn = createConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
        ) {
            // Dynamically add parameters:
            for(int i=0; i<parameters.size(); i++) {
                if(parameters.get(i) != null) {
                    preparedStatement.setString(i+1, parameters.get(i).toString());
                }
                else {
                    preparedStatement.setNull(i+1, java.sql.Types.NULL);
                }
            }

            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows > 0) {
                try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if(generatedKeys.next()) {
                        return generatedKeys.getString(1);
                    }
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        throw new SQLException("Inserting data failed.");
    }


    @Override
    public boolean update(String sqlQuery, ArrayList<Object> parameters) throws SQLException {
        try(
                Connection conn = createConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
                ) {
            for(int i=0; i<parameters.size(); i++) {
                if(parameters.get(i) != null) {
                    preparedStatement.setString(i+1, parameters.get(i).toString());
                }
                else {
                    preparedStatement.setNull(i+1, java.sql.Types.NULL);
                }
            }

            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows > 0) {
                return true;
            }
            else {
                return false;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        throw new SQLException("Updating data failed.");
    }


    @Override
    public boolean delete(String sqlQuery, ArrayList<Object> parameters) throws SQLException {
        try(
                Connection conn = createConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
                ) {
            // Dynamically add parameters:
            for(int i=0; i<parameters.size(); i++) {
                if(parameters.get(i) != null) {
                    preparedStatement.setString(i+1, parameters.get(i).toString());
                }
                else {
                    preparedStatement.setNull(i+1, java.sql.Types.NULL);
                }
            }

            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows > 0) {
                return true;
            }
            else {
                return false;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        throw new SQLException("Deleting data failed.");
    }


    private List<Map<String, Object>> GetDataFromResultSet(ResultSet resultSet) throws SQLException {
        List<Map<String, Object>> arrayList = new ArrayList<>();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columns = resultSetMetaData.getColumnCount();

        while(resultSet.next()) {
            HashMap<String, Object> row = new HashMap<>(columns);

            for(int i=1; i<=columns; ++i) {
                row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
            }
            arrayList.add(row);
        }

        return arrayList;
    }
}
