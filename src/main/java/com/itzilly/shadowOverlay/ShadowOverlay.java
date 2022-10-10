package com.itzilly.shadowOverlay;

import com.itzilly.shadowOverlay.ui.MainWindow;

public class ShadowOverlay {
    public static void main(String[] args) {
        Constants.SETTINGS_MANAGER = new SettingsManager();
        MainWindow.showOverlay();
    }
}