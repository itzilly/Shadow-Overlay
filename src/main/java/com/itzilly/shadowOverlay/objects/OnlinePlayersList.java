package com.itzilly.shadowOverlay.objects;

import com.itzilly.shadowOverlay.Constants;

import java.util.ArrayList;
import java.util.UUID;

public class OnlinePlayersList {
    private UUID listId;
    private ArrayList<String> playersList;

    public OnlinePlayersList(String message) {
        this.playersList = new ArrayList<>();

        String content = message;

        if (content.startsWith(Constants.LIST_MESSAGE_PREFIX)) {
            content = message.replace(Constants.LIST_MESSAGE_PREFIX, "");
        } else {
            throw new RuntimeException(new Exception("Invalid online message"));
        }

        for (String player : content.split(",")) {
            this.playersList.add(player.trim());
        }
    }

    public UUID getListId() {
        return listId;
    }

    public void setListId(UUID listId) {
        this.listId = listId;
    }

    public ArrayList<String> getPlayersList() {
        return playersList;
    }

    public void setPlayersList(ArrayList<String> playersList) {
        this.playersList = playersList;
    }
}
