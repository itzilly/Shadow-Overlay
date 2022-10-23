package com.itzilly.shadowOverlay;

import com.itzilly.shadowOverlay.ui.MainWindow;
import org.ini4j.Ini;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class ShadowOverlay {
    public static void main(String[] args) throws Exception {
        System.setOut(new PrintStream(new FileOutputStream("latest.out")));
        System.setErr(new PrintStream(new FileOutputStream("latest.err")));

        genConfig();

        // Show main window
        MainWindow.showOverlay();
    }

    private static void genConfig() {
        File configDir = new File("config/");
        if (!configDir.exists()) {
            boolean wasMade = configDir.mkdir();
        }

        File configFile = new File("config/config.properties");
        if (configFile.exists()) {
            return;
        }


        Ini writeIni = new Ini();
        writeIni.put("GENERAL", "API_KEY", "");
        writeIni.put("GENERAL", "LOG_PATH", "");
        try {
            writeIni.store(new FileOutputStream("config/config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}