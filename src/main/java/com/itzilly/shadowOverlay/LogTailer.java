package com.itzilly.shadowOverlay;

import com.itzilly.shadowOverlay.objects.OnlinePlayersList;
import com.itzilly.shadowOverlay.objects.OverlayPlayer;
import com.itzilly.shadowOverlay.ui.MainWindow;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import me.kbrewster.exceptions.APIException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
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


    private boolean isInvalidLog(String logPath) {
        File file = new File(logPath);
        if (file.exists() && file.isFile()) {
            return false;
        }
        return true;
    }


    private void runTrailer() throws IOException {
        System.out.println("Running Trailer");
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
            OnlinePlayersList onlinePlayersList = new OnlinePlayersList(content);
            MainWindow.getMainController().myTableView.getItems().clear();
            addOverlayPlayers(onlinePlayersList.getPlayersList());
        }

        // '/msg .playername' in-game command
        else if (content.startsWith(Constants.PLAYER_QUERY_PREFIX)) {
            String targetPlayer = content.replace(Constants.PLAYER_QUERY_PREFIX, "").split("'")[0];
            addOverlayPlayer(targetPlayer);
        }

        // New API Key
        else if (content.startsWith(Constants.NEW_KEY_PREFIX)) {
            String key = content.replace(Constants.NEW_KEY_PREFIX, "").trim();
            Constants.API_KEY = key;
            MainWindow.getMainController().txtbxApiKey.setText(key);
            System.out.println("Using new key: " + key);
        }
    }

    private void addOverlayPlayer(String name) {
        _appendOverlayPlayer(name);
    }

    private void addOverlayPlayers(ArrayList<String> playersList) {
        for (String playerName : playersList) {
            _appendOverlayPlayer(playerName);
        }
    }

    private void _appendOverlayPlayer(String playerName) {
        try {
            MainWindow.getMainController().addPlayerToList(new OverlayPlayer(playerName));
        } catch (IOException | APIException e) {
            // Looked up too recently
            if (e.getMessage().equals(Constants.RECENTLY_SEARCHED_ERMSG)) {
                UUID playerUuid = Constants.UUID_CACHE.get(playerName);
                // TODO: Make Mojang API call if name is null (not in cache)
                try {
                    MainWindow.getMainController().addPlayerToList(new OverlayPlayer(playerUuid));
                } catch (APIException | IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        }
    }


    public void stop() {
        shouldRun = false;
    }
}
