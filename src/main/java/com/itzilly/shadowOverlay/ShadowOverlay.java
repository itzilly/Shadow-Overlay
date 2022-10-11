package com.itzilly.shadowOverlay;

import com.itzilly.shadowOverlay.ui.MainWindow;

public class ShadowOverlay {
    static YmlConfig ymlConfig = new YmlConfig();
    public static void main(String[] args) {

        // Change api key
        ymlConfig.set("API_KEY", "123456");

        // Get information from config
        System.out.println(ymlConfig.getString("API_KEY"));

        MainWindow.showOverlay();
    }
}