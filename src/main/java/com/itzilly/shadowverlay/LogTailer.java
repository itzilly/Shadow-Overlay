package com.itzilly.shadowverlay;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class LogTailer implements Runnable {
    private boolean shouldRun;


    @Override
    public void run() {
        System.out.println("Starting LogTailer");
        shouldRun = true;
        try {
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

    private void parseLine(String currentLine) {
        String content = currentLine.substring(11);

        // Online Message
        if (content.startsWith(Constants.LIST_MESSAGE_PREFIX)) {
            String onlineMessage = content.replace(Constants.LIST_MESSAGE_PREFIX, "");
            List<String> players = new ArrayList<>();
            for (String player : onlineMessage.split(",")) {
                String playername = player.trim();
                players.add(playername);
                System.out.println(playername);
                
            }
            UUID onlineMessageUuid = UUID.randomUUID();
            // MyStatsify.mostRecentOnlineUuid = onlineMessageUuid;
            // MyStatsify.printPlayers(players, onlineMessageUuid);
            // Http.printPlayerStars(players, onlineMessageUuid);
        }

        // '/msg .playername' in-game command
        else if (content.startsWith(Constants.PLAYER_QUERY_PREFIX)) {
            String targetPlayer = content.replace(Constants.PLAYER_QUERY_PREFIX, "").split("'")[0];
            System.out.println("Looking up stats for: '" + targetPlayer + "'");
        }

        // New Api Key
        else if (content.startsWith(Constants.NEW_KEY_PREFIX)) {
            String key = content.replace(Constants.NEW_KEY_PREFIX, "").trim();
            System.out.println("Using new key: '" + key);
            // MyStatsify.config.setProperty("hypixel.apikey", "thisismyapikey");
            // MyStatsify.showKey();
        }
    }


    public void stop() {
        shouldRun = false;
    }
}
