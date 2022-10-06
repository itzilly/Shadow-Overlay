package com.itzilly.shadowOverlay.objects;

import com.itzilly.shadowOverlay.Constants;
import me.kbrewster.exceptions.APIException;
import me.kbrewster.hypixelapi.HypixelAPI;
import me.kbrewster.hypixelapi.player.HypixelPlayer;

import java.io.IOException;

public class GamePlayer {
    private String name;
    private int stars;

    public GamePlayer(String name) {
        this.name = name;
        HypixelAPI hypixelAPI = new HypixelAPI(Constants.API_KEY);
        try {
            HypixelPlayer hypixelPlayer = hypixelAPI.getPlayer(name);
            this.stars = (int) hypixelPlayer.getAchievements().getBedwarsLevel();
        } catch (APIException | IOException e) {
            this.stars = -1;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
