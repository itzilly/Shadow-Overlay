package com.itzilly.shadowOverlay;


import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class Constants {

    public static String BUILD_NUMBER = "b26";
    public static String VERSION = "A0.3.6";
    public static String VERSION_SHORTHAND = VERSION + "-" + BUILD_NUMBER;
    public static String WINDOW_TITLE = "Shadow Overlay " + VERSION_SHORTHAND;

    public static String LOG_LOCATION = null;
    public static String API_KEY = null;

    public static String LIST_MESSAGE_PREFIX = "[CHAT] ONLINE: ";
    public static String PLAYER_QUERY_PREFIX = "[CHAT] Can't find a player by the name of '.";
    public static String NEW_KEY_PREFIX = "[CHAT] Your new API key is ";
    public static String PLAYER_JOINED_BEDWARS_REGEX = "\\[CHAT\\]\\s[a-zA-Z0-9_]+\\shas\\sjoined\\s\\(\\d+\\/\\d+\\)!";
    public static String PLAYER_LEFT_BEDWARS_REGEX = "\\[CHAT\\]\\s[a-zA-Z0-9_]+\\shas\\squit\\!";

    public static LogTailer LOG_TAILER = null;
    public static Thread LOG_TAILER_THREAD = null;
    public static Boolean LOG_TAILER_IS_RUNNING = false;

    public static String RECENTLY_SEARCHED_ERMSG = "You have already looked up this name recently";

    public static HashMap<String, UUID> UUID_CACHE = new HashMap<>();

    private static String appdataPath = null;

    public static String APPDATA_PATH() {
        if (appdataPath != null) {
            return appdataPath;
        }

        String systemName = System.getProperty("os.name");
        String userFilePath = System.getProperty("user.home");
        String programDirectoryPath = userFilePath;
        String sep = File.separator;
        if  (systemName.equals("Windows 10") || systemName.equals("Windows 11") || systemName.equals("Windows 8")) {
            programDirectoryPath = programDirectoryPath + sep + "AppData" + sep + "Roaming" + sep + "Shadow Overlay";
        } else if (systemName.toLowerCase().startsWith("mac")) {
            programDirectoryPath = programDirectoryPath + sep + "Library" + sep + "Application Support" + sep + "Shadow Overlay";
        } else {
            programDirectoryPath = userFilePath + sep + "Shadow Overlay";
        }
        appdataPath = programDirectoryPath;
        return programDirectoryPath;
    }

}
