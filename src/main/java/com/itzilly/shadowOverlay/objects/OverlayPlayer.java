package com.itzilly.shadowOverlay.objects;

import com.itzilly.shadowOverlay.Constants;
import me.kbrewster.exceptions.APIException;
import me.kbrewster.exceptions.InvalidPlayerException;
import me.kbrewster.hypixelapi.HypixelAPI;
import me.kbrewster.hypixelapi.player.HypixelPlayer;
import me.kbrewster.hypixelapi.player.stats.bedwars.Bedwars;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.UUID;

public class OverlayPlayer {
    private String name;
    private UUID uuid;
    private long bedwarsLevel;
    private double fkdr;
    private long finalKills;
    private double wlr;
    private double bblr;
    private long winstreak;
    private float index;
    private long wins;

    public OverlayPlayer(String name) throws APIException, IOException, InvalidPlayerException {
        this.name = name;
        HypixelAPI hypixelAPI = new HypixelAPI(Constants.API_KEY);
        HypixelPlayer hypixelPlayer = hypixelAPI.getPlayer(name);

        Bedwars bwStats = hypixelPlayer.getStats().getBedwars();


        // Set Stats
        this.uuid = stringToUuid(hypixelPlayer.getUuid());
        this.bedwarsLevel = hypixelPlayer.getAchievements().getBedwarsLevel();
        this.winstreak = bwStats.getWinstreak();

        this.finalKills = bwStats.getFinalKillsBedwars();
        long finalDeaths = bwStats.getFinalDeathsBedwars();
        this.fkdr = Double.parseDouble(new DecimalFormat("0.00").format(getRatio(this.finalKills, finalDeaths)));

        this.wins = bwStats.getWinsBedwars();
        long losses = bwStats.getLossesBedwars();
        this.wlr = Double.parseDouble(new DecimalFormat("0.00").format(getRatio(wins, losses)));

        long bedsBroken = bwStats.getBedsBrokenBedwars();
        long bedsLost = bwStats.getBedsLostBedwars();
        this.bblr = getRatio(bedsBroken, bedsLost);

        float dx;
        dx = (float) (fkdr * fkdr);
        dx = bedwarsLevel * dx;
        dx = dx / 10;

        this.index = Float.parseFloat(new DecimalFormat("0.00").format(dx));


        // Handle Cache
        if (Constants.UUID_CACHE.containsKey(name)) {
            Constants.UUID_CACHE.replace(name, uuid);
        } else {
            Constants.UUID_CACHE.put(name, uuid);
        }
    }

    public OverlayPlayer(UUID uuid) throws APIException, IOException, InvalidPlayerException {
        this.uuid = uuid;

        HypixelAPI hypixelAPI = new HypixelAPI(Constants.API_KEY);
        HypixelPlayer hypixelPlayer = hypixelAPI.getPlayer(uuid);

        Bedwars bwStats = hypixelPlayer.getStats().getBedwars();


        // Set Stats
        this.name = hypixelPlayer.getDisplayname();
        this.bedwarsLevel = hypixelPlayer.getAchievements().getBedwarsLevel();
        this.winstreak = bwStats.getWinstreak();

        this.finalKills = bwStats.getFinalKillsBedwars();
        long finalDeaths = bwStats.getFinalDeathsBedwars();
        this.fkdr = Double.parseDouble(new DecimalFormat("0.00").format(getRatio(this.finalKills, finalDeaths)));

        long wins = bwStats.getWinsBedwars();
        long losses = bwStats.getLossesBedwars();
        this.wlr = Double.parseDouble(new DecimalFormat("0.00").format(getRatio(wins, losses)));

        long bedsBroken = bwStats.getBedsBrokenBedwars();
        long bedsLost = bwStats.getBedsLostBedwars();
        this.bblr = getRatio(bedsBroken, bedsLost);

        float dx;
        dx = (float) (fkdr * fkdr);
        dx = bedwarsLevel * dx;
        dx = dx / 10;

        this.index = Float.parseFloat(new DecimalFormat("0.00").format(dx));


        // Handle Cache
        if (Constants.UUID_CACHE.containsKey(name)) {
            Constants.UUID_CACHE.replace(name, uuid);
        } else {
            Constants.UUID_CACHE.put(name, uuid);
        }
    }

    private UUID stringToUuid(String uuid) {
        String uuidPattern = "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)";
        return UUID.fromString(uuid.replaceFirst(uuidPattern, "$1-$2-$3-$4-$5"));
    }

    private double getRatio(long top, long bottom) {
        return ((double) top / bottom);
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public long getBedwarsLevel() {
        return bedwarsLevel;
    }

    public double getFkdr() {
        return fkdr;
    }

    public long getFinalKills() {
        return finalKills;
    }

    public double getWlr() {
        return wlr;
    }

    public double getBblr() {
        return bblr;
    }

    public long getWinstreak() {
        return winstreak;
    }

    public float getIndex() { return index; }

    public long getWins() { return wins; }
}
