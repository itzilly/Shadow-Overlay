package com.itzilly.shadowOverlay;

public class Constants {

    public static String BUILD_NUMBER = "b5";
    public static String VERSION = "A0.2.2";
    public static String VERSION_SHORTHAND = VERSION + "-" + BUILD_NUMBER;
    public static String WINDOW_TITLE = "Shadow Overlay " + VERSION_SHORTHAND;

    public static String LOG_LOCATION = null;
    public static String API_KEY = null;

    public static String LIST_MESSAGE_PREFIX = "[Client thread/INFO]: [CHAT] ONLINE: ";
    public static String PLAYER_QUERY_PREFIX = "[Client thread/INFO]: [CHAT] Can't find a player by the name of '.";
    public static String NEW_KEY_PREFIX = "[Client thread/INFO]: [CHAT] Your new API key is ";

    public static LogTailer LOG_TAILER = null;
    public static Thread LOG_TAILER_THREAD = null;
}
