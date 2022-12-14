package com.itzilly.shadowOverlay;

import com.itzilly.shadowOverlay.objects.OnlinePlayersList;
import com.itzilly.shadowOverlay.objects.OverlayPlayer;
import com.itzilly.shadowOverlay.ui.MainWindow;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import me.kbrewster.exceptions.APIException;
import me.kbrewster.exceptions.InvalidPlayerException;
import me.kbrewster.mojangapi.MojangAPI;
import org.ini4j.Ini;

import java.io.*;
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
                    parseLine(currentLine);
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

    private void parseLine(String currentLine) {
        if (currentLine.length() < 34) {
            return;
        }

        String content = currentLine.substring(33);

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
            Ini writeIni = new Ini();
            writeIni.put("GENERAL", "API_KEY", key);
            try {
                writeIni.store(new FileOutputStream(Constants.APPDATA_PATH() + File.separator + "config.properties"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
                if (playerUuid == null) {
                    try {
                        UUID newPlayerUuid = MojangAPI.getUUID(playerName);
                        MainWindow.getMainController().addPlayerToList(new OverlayPlayer(newPlayerUuid));
                    } catch (APIException | IOException ex) {
                        ex.printStackTrace();
                    }
                }
                if (playerUuid == null) {
                    return;
                }
                try {
                    MainWindow.getMainController().addPlayerToList(new OverlayPlayer(playerUuid));
                } catch (APIException | IOException ex) {
                    ex.printStackTrace();
                }

            }
        } catch (InvalidPlayerException exc) {
            exc.printStackTrace();
            System.out.println(exc.getMessage());
        }
    }


    public void stop() {
        shouldRun = false;
    }
}
