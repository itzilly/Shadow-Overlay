package com.itzilly.shadowOverlay;

import com.itzilly.shadowOverlay.ui.MainWindow;

public class ShadowOverlay {
    public static void main(String[] args) {
        // Generate default config file
        new YmlConfig();
 
        // Show main window
        MainWindow.showOverlay();
    }
}