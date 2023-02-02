package com.itzilly.shadowOverlay.parsers;

import com.itzilly.shadowOverlay.Constants;
import com.itzilly.shadowOverlay.objects.NickedPlayer;
import com.itzilly.shadowOverlay.objects.OnlinePlayersList;
import com.itzilly.shadowOverlay.objects.OverlayPlayer;
import com.itzilly.shadowOverlay.ui.MainWindow;
import me.kbrewster.exceptions.APIException;
import me.kbrewster.exceptions.InvalidPlayerException;
import me.kbrewster.mojangapi.MojangAPI;
import org.ini4j.Ini;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Pattern;

public class BedwarsLineParser implements LineParser {
    @Override
    public void parseLine(String line) {
        if (line.length() < 34) {
            return;
        }

        String content = line.substring(33);

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

        // Player joined queue (bedwars)
        else if (Pattern.matches(Constants.PLAYER_JOINED_BEDWARS_REGEX, content)) {
            String targetPlayer = content.split(" ")[1];
            addOverlayPlayer(targetPlayer);
        }

        // Player left queue (bedwars)
        else if (Pattern.matches(Constants.PLAYER_LEFT_BEDWARS_REGEX, content)) {
            String targetPlayer = content.split(" ")[1];
            removeOverlayPlayer(targetPlayer);
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
//                        try {
//                            MainWindow.getMainController().addNickedPlayerToList(new NickedPlayer(playerName));
//                        } catch (APIException | IOException exc) {
//                            exc.printStackTrace();
//                        }
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

    private void removeOverlayPlayer(String playerName) {
        MainWindow.getMainController().removePlayerFromList(playerName);
    }

    private void addNickedPlayerToList(NickedPlayer nickedPlayer) {
        MainWindow.getMainController().addNickedPlayer(nickedPlayer);
    }
}
