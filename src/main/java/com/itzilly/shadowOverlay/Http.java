package com.itzilly.shadowOverlay;

import me.kbrewster.exceptions.APIException;
import me.kbrewster.hypixelapi.HypixelAPI;

import java.io.IOException;

public class Http {
    public static boolean isValidKey(String key) {
        HypixelAPI hypixelAPI = new HypixelAPI(key);
        try {
            hypixelAPI.getKeyInfo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (APIException e) {
            if (e.getMessage().equals("Invalid API key")) {
                return false;
            }
            throw new RuntimeException(e);
        }
        return true;
    }
}
