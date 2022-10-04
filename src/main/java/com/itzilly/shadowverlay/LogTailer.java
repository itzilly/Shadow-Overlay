package com.itzilly.shadowverlay;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

import java.time.Duration;
import java.time.LocalTime;


public class LogTailer implements Runnable {
    private boolean shouldRun;


    @Override
    public void run() {
        try {
            shouldRun = true;
            runTrailer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void runTrailer() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(Constants.LOG_LOCATION));
        LocalTime timeOpened = LocalTime.now();
        boolean hasProcessed = false;

        while (shouldRun) {
            String currentLine = bufferedReader.readLine();
            if (currentLine != null) {
                if (hasProcessed) {
                    parseLine(currentLine);
                } else {
                    LocalTime timeNow = LocalTime.now();
                    long duration = Duration.between(timeOpened, timeNow).getSeconds();
                    if (duration > 3) {
                        hasProcessed = true;
                    }
                }
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

    private void parseLine(String currentLine) {
    }


}
