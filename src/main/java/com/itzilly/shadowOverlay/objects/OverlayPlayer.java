package com.itzilly.shadowOverlay.objects;

import com.itzilly.shadowOverlay.Constants;
import me.kbrewster.exceptions.APIException;
import me.kbrewster.hypixelapi.HypixelAPI;
import me.kbrewster.hypixelapi.player.HypixelPlayer;

import java.io.IOException;
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

    public OverlayPlayer(String name) throws APIException, IOException {
        // TODO: Improve Memory Efficiency (use int instead of long)

        this.name = name;
        HypixelAPI hypixelAPI = new HypixelAPI(Constants.API_KEY);
        String uuidPattern = "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)";
        HypixelPlayer hypixelPlayer = hypixelAPI.getPlayer(name);
        this.uuid = UUID.fromString(hypixelPlayer.getUuid().replaceFirst(uuidPattern, "$1-$2-$3-$4-$5"));
        this.bedwarsLevel = hypixelPlayer.getAchievements().getBedwarsLevel();
        this.finalKills = hypixelPlayer.getStats().getBedwars().getFinalKillsBedwars();
        long finalDeaths = hypixelPlayer.getStats().getBedwars().getFinalDeathsBedwars();
        // TODO: Optimize (if finalDeaths == 1 fkdr = finalKills) for ALL below
        if (finalDeaths == 0) {
            finalDeaths = 1L;
        }
        this.fkdr = (double) (this.finalKills / finalDeaths);
        long wins = hypixelPlayer.getStats().getBedwars().getWinsBedwars();
        long losses = hypixelPlayer.getStats().getBedwars().getLossesBedwars();
        if (losses == 0) {
            losses = 1;
        }
        this.wlr = (double) (wins / losses);
        long bedBreaks = hypixelPlayer.getStats().getBedwars().getBedsBrokenBedwars();
        long bedLosses = hypixelPlayer.getStats().getBedwars().getBedsLostBedwars();
        if  (bedLosses == 0) {
            bedLosses = 1L;
        }
        this.bblr = (double) (bedBreaks / bedLosses);
        this.winstreak = hypixelPlayer.getStats().getBedwars().getWinstreak();

        if (Constants.UUID_CACHE.containsKey(name)) {
            Constants.UUID_CACHE.replace(name, uuid);
        } else {
            Constants.UUID_CACHE.put(name, uuid);
        }

    }

    public OverlayPlayer(UUID uuid) throws APIException, IOException {
        // TODO: Improve Memory Efficiency (use int instead of long)

        HypixelAPI hypixelAPI = new HypixelAPI(Constants.API_KEY);
        String uuidPattern = "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)";
        HypixelPlayer hypixelPlayer = hypixelAPI.getPlayer(uuid);
        this.uuid = UUID.fromString(hypixelPlayer.getUuid().replaceFirst(uuidPattern, "$1-$2-$3-$4-$5"));
        this.name = hypixelPlayer.getDisplayname();
        this.bedwarsLevel = hypixelPlayer.getAchievements().getBedwarsLevel();
        this.finalKills = hypixelPlayer.getStats().getBedwars().getFinalKillsBedwars();
        long finalDeaths = hypixelPlayer.getStats().getBedwars().getFinalDeathsBedwars();
        // TODO: Optimize (if finalDeaths == 1 fkdr = finalKills) for ALL below
        if (finalDeaths == 0) {
            finalDeaths = 1L;
        }
        this.fkdr = (double) (this.finalKills / finalDeaths);
        long wins = hypixelPlayer.getStats().getBedwars().getWinsBedwars();
        long losses = hypixelPlayer.getStats().getBedwars().getLossesBedwars();
        if (losses == 0) {
            losses = 1;
        }
        this.wlr = (double) (wins / losses);
        long bedBreaks = hypixelPlayer.getStats().getBedwars().getBedsBrokenBedwars();
        long bedLosses = hypixelPlayer.getStats().getBedwars().getBedsLostBedwars();
        if  (bedLosses == 0) {
            bedLosses = 1L;
        }
        this.bblr = (double) (bedBreaks / bedLosses);
        this.winstreak = hypixelPlayer.getStats().getBedwars().getWinstreak();

        if (Constants.UUID_CACHE.containsKey(name)) {
            Constants.UUID_CACHE.replace(name, uuid);
        } else {
            Constants.UUID_CACHE.put(name, uuid);
        }

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

    public long getFinaKills() {
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
}
