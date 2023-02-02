package com.itzilly.shadowOverlay.ui;

import com.itzilly.shadowOverlay.Constants;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.ini4j.Ini;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class MainWindow extends Application {
    public static MainWindowController mainWindowController = null;
    public static MainWindowController getMainController() {
        return mainWindowController;
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("main-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        String userStyleSheet = getUserStylesheet();
        if (userStyleSheet != null) {
            scene.getStylesheets().add(getUserStylesheet());
        } else {
            scene.getStylesheets().add(String.valueOf(MainWindow.class.getResource("styles/main.css")));
        }
        mainWindowController = fxmlLoader.getController();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(Constants.WINDOW_TITLE);
        stage.setOnCloseRequest(e -> Platform.exit());
        stage.show();
    }


    private String getUserStylesheet() {
        Ini readIni = new Ini();
        try {
            readIni.load(new FileReader(Constants.APPDATA_PATH() + File.separator + "config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String userStylesheetPath = readIni.get("PREFERENCES", "STYLESHEET", String.class);
        if (userStylesheetPath == null) {
            return null;
        }
        if (userStylesheetPath.length() == 0) {
            return null;
        }
        File userStylesheetFile = new File(userStylesheetPath);
        if (!(userStylesheetFile.exists() && userStylesheetFile.isFile() && userStylesheetFile.getPath().endsWith(".SHADOWTHEME"))) {
            return null;
        }
        return userStylesheetFile.getAbsolutePath();
    }

    public static void showOverlay() {
        launch();
    }

}
