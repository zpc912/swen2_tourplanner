package com.example.tourplanner.dal.fileaccess;

import com.example.tourplanner.bl.ConfigurationManager;
import com.example.tourplanner.dal.common.IFileAccess;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileAccess implements IFileAccess {

    private static String getImageDir() {
        Path dir = Paths.get("src", "main", "resources", "com", "example", "tourplanner", "staticmaps");
        String saveDir = dir.toFile().getAbsolutePath();

        return saveDir;
    }


    @Override
    public String createFile(String fileName, byte[] byteArray) {
        String saveDir = getImageDir();

        try {
            File fileToSave = new File(saveDir, fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(fileToSave);
            fileOutputStream.write(byteArray);
            fileOutputStream.close();

            String filePath = saveDir + fileName;
            return filePath;

        } catch(IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public File readFile(String fileName) {
        String saveDir = getImageDir();
        String filePath = saveDir + "\\" +  fileName + ".jpg";

        File file = new File(filePath);

        return file;
    }


    @Override
    public boolean deleteFile(String fileName) {
        String saveDir = getImageDir();
        String filePath = saveDir + "\\" +  fileName + ".jpg";

        File file = new File(filePath);

        if(file.delete()) {
            return true;
        }
        else {
            return false;
        }
    }
}
