package com.itzilly.shadowOverlay.ui;

import com.itzilly.shadowOverlay.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWindow extends Application {
    public static MainWindowController mainWindowController = null;
    public static MainWindowController getMainController() {
        return mainWindowController;
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("main-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(String.valueOf(MainWindow.class.getResource("styles/main.css")));
        mainWindowController = fxmlLoader.getController();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(Constants.WINDOW_TITLE);
        stage.show();
    }

    public static void showOverlay() {
        launch();
    }

}
