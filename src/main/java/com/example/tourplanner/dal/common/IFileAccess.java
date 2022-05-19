package com.example.tourplanner.dal.common;

import java.io.File;

public interface IFileAccess {

    File createFile(String path);

    File readFile(String path);

    boolean deleteFile(String path);
}
