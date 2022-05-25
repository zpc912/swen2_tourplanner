package com.example.tourplanner.dal.common;

import java.io.File;

public interface IFileAccess {

    String createFile(String fileName, byte[] byteArray);

    File readFile(String fileName);

    boolean deleteFile(String fileName);
}
