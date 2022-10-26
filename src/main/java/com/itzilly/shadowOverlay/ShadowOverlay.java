package com.itzilly.shadowOverlay;

import com.itzilly.shadowOverlay.ui.MainWindow;
import org.ini4j.Ini;

import java.io.*;

public class ShadowOverlay {
    public static void main(String[] args) {
        boolean couldSetStream = false;
        try {
            System.setOut(new PrintStream(new FileOutputStream(Constants.APPDATA_PATH() + File.separator + "latest.out")));
            System.setErr(new PrintStream(new FileOutputStream(Constants.APPDATA_PATH() + File.separator + "latest.err")));
            couldSetStream = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean createdUserDirs = createdUserDirs();

        genConfig();

        if (!couldSetStream) {
            try {
                System.setOut(new PrintStream(new FileOutputStream(Constants.APPDATA_PATH() + File.separator + "latest.out")));
                System.setErr(new PrintStream(new FileOutputStream(Constants.APPDATA_PATH() + File.separator + "latest.err")));
            } catch (FileNotFoundException e) {
                System.exit(1);
            }

        }

        // Show main window
        MainWindow.showOverlay();
    }

    private static void genConfig() {
        File configFile = new File(Constants.APPDATA_PATH() + File.separator + "config.properties");
        if (configFile.exists()) {
            return;
        }


        Ini writeIni = new Ini();
        writeIni.put("GENERAL", "API_KEY", "");
        writeIni.put("GENERAL", "LOG_PATH", "");
        try {
            writeIni.store(new FileOutputStream(Constants.APPDATA_PATH() + File.separator + "config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean createdUserDirs() {
        String programDirectoryPath = Constants.APPDATA_PATH();

        File appdataPath = new File(programDirectoryPath);
        boolean wasCreated = false;

        if (!(appdataPath.exists())) {
            wasCreated = appdataPath.mkdirs();
        }

        return wasCreated;
    }


}