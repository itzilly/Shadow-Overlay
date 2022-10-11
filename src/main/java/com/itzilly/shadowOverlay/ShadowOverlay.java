package com.itzilly.shadowOverlay;

import com.itzilly.shadowOverlay.ui.MainWindow;

import java.io.FileOutputStream;
import java.io.PrintStream;

public class ShadowOverlay {
    public static void main(String[] args) throws Exception {
        System.setOut(new PrintStream(new FileOutputStream("latest.out")));
        System.setErr(new PrintStream(new FileOutputStream("latest.err")));

        // Generate default config file
        new YmlConfig();
 
        // Show main window
        MainWindow.showOverlay();
    }
}