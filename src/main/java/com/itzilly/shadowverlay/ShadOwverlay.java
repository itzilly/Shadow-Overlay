package com.itzilly.shadowverlay;

import com.itzilly.shadowverlay.ui.MainWindow;

public class ShadOwverlay {
    public static String BUILD_NUMBER = "b3";

    public static String VERSION = "A0.1.1";
    public static String VERSION_SHORTHAND = VERSION + "-" + BUILD_NUMBER;

    public static void main(String[] args) {
        MainWindow.show();
    }
}
