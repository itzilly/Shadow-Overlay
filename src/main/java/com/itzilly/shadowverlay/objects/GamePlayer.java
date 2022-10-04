package com.itzilly.shadowverlay.objects;

public class GamePlayer {
    private String name;
    private int stars;

    public GamePlayer(String name, int stars) {
        this.name = name;
        this.stars = stars;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
