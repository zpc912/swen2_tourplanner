package com.example.tourplanner.dal.common;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IDatabase {

    <T> List<T> read(String sqlQuery) throws SQLException;
    <T> List<T> read(String sqlQuery, ArrayList<Object> parameters) throws SQLException;

    String insertNew(String sqlQuery, ArrayList<Object> parameters) throws SQLException;

    boolean update(String sqlQuery, ArrayList<Object> parameters) throws SQLException;

    boolean delete(String sqlQuery, ArrayList<Object> parameters) throws SQLException;
}
