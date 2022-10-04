package com.itzilly.shadowverlay.ui;

import com.itzilly.shadowverlay.ShadOwverlay;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWindow extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("main-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Shadow Overlay " + ShadOwverlay.VERSION_SHORTHAND);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void show() {
        launch();
    }


}
