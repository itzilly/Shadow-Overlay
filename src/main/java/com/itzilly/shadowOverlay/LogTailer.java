package com.itzilly.shadowOverlay;

import com.itzilly.shadowOverlay.parsers.GameLineParser;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.*;



public class LogTailer implements Runnable {
    private boolean shouldRun;
    private GameLineParser gameLineParser;


    @Override
    public void run() {
        if (Constants.LOG_TAILER_IS_RUNNING) {
            System.out.println("LogTailer is already running!");
            return;
        }
        System.out.println("Starting LogTailer");
        Constants.LOG_TAILER_IS_RUNNING = true;
        shouldRun = true;
        try {
            runTrailer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private boolean isInvalidLog(String logPath) {
        File file = new File(logPath);
        if (file.exists() && file.isFile()) {
            return false;
        }
        return true;
    }


    private void runTrailer() throws IOException {
        System.out.println("Running Trailer");
        gameLineParser = GameLineParser.BEDWARS;
        if (isInvalidLog(Constants.LOG_LOCATION)) {
            shouldRun = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Log File");
            alert.setHeaderText("You have entered an invalid log file!");
            alert.setContentText("Shadow Overlay will not work without a valid log file");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    return;
                }
            });
            System.out.println("Invalid log file: " + Constants.LOG_LOCATION);
            return;
        }

        BufferedReader bufferedReader = new BufferedReader(new FileReader(Constants.LOG_LOCATION));

        boolean isCounting = true;
        int linesCount = 0;
        while (isCounting) {
            String line = bufferedReader.readLine();
            if (line != null) {
                linesCount += 1;
            } else {
                isCounting = false;
            }
        }

        int lineNumber = 0;

        while (shouldRun) {
            String currentLine = bufferedReader.readLine();
            if (currentLine != null) {
                if (lineNumber < linesCount) {
                    gameLineParser.getLineParser().parseLine(currentLine);
                } else {
                    lineNumber += 1;
                }
                continue;
            }
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        shouldRun = false;
    }
}
