package com.itzilly.shadowOverlay.objects;

import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class BedwarsPlayerStats {
    private SimpleStringProperty playerName;
    private SimpleIntegerProperty stars;
    private SimpleIntegerProperty index;
    private SimpleDoubleProperty fkdr;
    private SimpleIntegerProperty finalKills;
    private SimpleIntegerProperty wins;
    private SimpleDoubleProperty winLossRatio;
    private SimpleIntegerProperty winstreak;


    public BedwarsPlayerStats(String playerName, int stars, int index, double fkdr, int finalKills, int wins, double winLossRatio, int winstreak) {
        this.playerName = new SimpleStringProperty(playerName);
        this.stars = new SimpleIntegerProperty(stars);
        this.index = new SimpleIntegerProperty(index);
        this.fkdr = new SimpleDoubleProperty(fkdr);
        this.finalKills = new SimpleIntegerProperty(finalKills);
        this.wins = new SimpleIntegerProperty(wins);
        this.winLossRatio = new SimpleDoubleProperty(winLossRatio);
        this.winstreak = new SimpleIntegerProperty(winstreak);
    }

    public String getPlayerName() {
        return playerName.get();
    }

    public SimpleStringProperty playerNameProperty() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName.set(playerName);
    }

    public int getStars() {
        return stars.get();
    }

    public SimpleIntegerProperty starsProperty() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars.set(stars);
    }

    public int getIndex() {
        return index.get();
    }

    public SimpleIntegerProperty indexProperty() {
        return index;
    }

    public void setIndex(int index) {
        this.index.set(index);
    }

    public double getFkdr() {
        return fkdr.get();
    }

    public SimpleDoubleProperty fkdrProperty() {
        return fkdr;
    }

    public void setFkdr(double fkdr) {
        this.fkdr.set(fkdr);
    }

    public int getFinalKills() {
        return finalKills.get();
    }

    public SimpleIntegerProperty finalKillsProperty() {
        return finalKills;
    }

    public void setFinalKills(int finalKills) {
        this.finalKills.set(finalKills);
    }

    public int getWins() {
        return wins.get();
    }

    public SimpleIntegerProperty winsProperty() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins.set(wins);
    }

    public double getWinLossRatio() {
        return winLossRatio.get();
    }

    public SimpleDoubleProperty winLossRatioProperty() {
        return winLossRatio;
    }

    public void setWinLossRatio(double winLossRatio) {
        this.winLossRatio.set(winLossRatio);
    }

    public int getWinstreak() {
        return winstreak.get();
    }

    public SimpleIntegerProperty winstreakProperty() {
        return winstreak;
    }

    public void setWinstreak(int winstreak) {
        this.winstreak.set(winstreak);
    }

}
