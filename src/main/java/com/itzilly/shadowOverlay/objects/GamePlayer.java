package com.itzilly.shadowOverlay.objects;

public class GamePlayer {
    private String name;
    private int stars;

    public GamePlayer(String name) {
        this.name = name;
        this.stars = 0;
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
