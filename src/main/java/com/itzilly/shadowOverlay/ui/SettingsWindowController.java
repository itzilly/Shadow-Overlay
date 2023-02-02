package com.itzilly.shadowOverlay.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.collections.ObservableList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsWindowController implements Initializable {

    public TextField txtbxApiKey;
    public Label lblLogFilePath;
    private String logPath;

    @FXML
    private ChoiceBox chBxClientsList;
    public Label lblLogPath;
    public Button btnSelectLogFile;
    
    private final String SEP = File.separator;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblLogPath.setVisible(false);
        btnSelectLogFile.setVisible(false);
        ObservableList<String> clientsList = chBxClientsList.getItems();
        clientsList.add("Minecraft");
        clientsList.add("Lunar Client");
        // clientsList.add("Badlion Client"); // CURRENTLY UNSUPPORTED
        clientsList.add("Custom Installation");
        chBxClientsList.setValue("Minecraft");

        chBxClientsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                String selectedItem = (String) chBxClientsList.getValue();

                String osName = getOperatingSystemType();
                switch (selectedItem) {
                    case "Minecraft" -> {
                        // Vanilla Minecraft
                        lblLogPath.setVisible(false);
                        btnSelectLogFile.setVisible(false);
                        logPath = getVanillaLogPath(osName);
                        lblLogFilePath.setText(logPath);
                    }
                    case "Lunar Client" -> {
                        // Lunar Client
                        lblLogPath.setVisible(false);
                        btnSelectLogFile.setVisible(false);
                        logPath = getLunarLogPath(osName);
                        lblLogFilePath.setText(logPath);
                    }
                    case "Badlion Client" -> {
                        // Badlion Client
                        lblLogPath.setVisible(false);
                        btnSelectLogFile.setVisible(false);
                    }
                    case "Custom Installation" -> {
                        // Custom Install
                        lblLogPath.setVisible(true);
                        btnSelectLogFile.setVisible(true);
                        lblLogFilePath.setText("");
                    }
                }
            }
        });
    }

    private String getOperatingSystemType() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")){
            return "WINDOWS";
        }
        else if (os.contains("osx")){
            return "MACOS";
        }
        else if (os.contains("nix") || os.contains("aix") || os.contains("nux")){
            return "LINUX";
        }
        return null;
    }

    public void onBtnAction_SelectLogFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Log File");
        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("log files (*.log)", "*.log");
        fileChooser.getExtensionFilters().add(extFilter);

        // fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".log"));
        File logFile = fileChooser.showOpenDialog(chBxClientsList.getParent().getScene().getWindow());
        btnSelectLogFile.setVisible(false);

        lblLogFilePath.setText(logFile.getAbsolutePath());
    }

    public void onBtnAction_CancelChanges(ActionEvent actionEvent) {
        // Close settings panel without saving changes
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) chBxClientsList.getScene().getWindow();
        stage.close();
    }

    private String getVanillaLogPath(String os) {
        String minecraftDir;
        if (os.equals("WINDOWS")) {
            minecraftDir = System.getenv("APPDATA" + SEP + ".minecraft");
        } else if (os.equals("MACOS")) {
            minecraftDir = System.getProperty("user.home") + SEP + "minecraft";
        } else if (os.equals("LINUX")) {
            minecraftDir = System.getProperty("user.home") + SEP + ".minecraft";
        } else {
            return null;
        }

        String logPath = minecraftDir + SEP + "logs" + SEP + "latest.log";
        return logPath;
    }

    private String getLunarLogPath(String os) {
        String lunarDir = System.getProperty("user.home") + ".lunarClient" + SEP + "offline" + SEP + "multiver";
        String logPath = lunarDir + SEP + "logs" + SEP + "latest.log";
        return logPath;
    }

}

