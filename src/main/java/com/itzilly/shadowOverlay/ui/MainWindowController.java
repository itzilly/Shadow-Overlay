package com.itzilly.shadowOverlay.ui;

import com.itzilly.shadowOverlay.*;
import com.itzilly.shadowOverlay.objects.OverlayPlayer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.ini4j.Ini;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    private Ini readIni;
    private Ini writeIni;
    private String log_path;
    private String api_key;


    @FXML
    public TableView<OverlayPlayer> myTableView;
    @FXML
    public TableColumn<OverlayPlayer, String> playerColumn;
    @FXML
    public TableColumn<OverlayPlayer, Integer> starsColumn;
    @FXML
    public Button btnStart;
    @FXML
    public TextField txtbxApiKey;
    @FXML
    public TextField txtbxLogPath;
    @FXML
    public Button btnStop;
    private final String config_path = "config/config.properties";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadConfig();

        btnStop.setVisible(false);
        // Note: "Name" below corresponds to the variable name WITHIN THE CLASS (this.name), this is NOT the name of the column title!
        playerColumn.setCellValueFactory(new PropertyValueFactory<OverlayPlayer, String>("name"));
        starsColumn.setCellValueFactory(new PropertyValueFactory<OverlayPlayer, Integer>("bedwarsLevel"));

        txtbxApiKey.setText(api_key);
        txtbxLogPath.setText(log_path);

    }

    private void loadConfig() {
        readIni = new Ini();
        try {
            readIni.load(new FileReader(config_path));
        } catch (IOException e) {
            e.printStackTrace();
            log_path = "";
            api_key = "";
        }
        api_key = readIni.get("GENERAL", "API_KEY", String.class);
        log_path = readIni.get("GENERAL", "LOG_PATH", String.class);
    }


    public void onBtnStartAction(ActionEvent actionEvent) {
        // Start Button


        writeIni = new Ini();
        writeIni.put("GENERAL", "API_KEY", txtbxApiKey.getText().strip());
        writeIni.put("GENERAL", "LOG_PATH", txtbxLogPath.getText().replace('\\', '/').replace("\"", ""));
        try {
            writeIni.store(new FileOutputStream(config_path));
            System.out.println("Written!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Constants.API_KEY = txtbxApiKey.getText().strip();
        if (!Http.isValidKey(Constants.API_KEY)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid API Key");
            alert.setHeaderText("You have entered an invalid API key!");
            alert.setContentText("Shadow Overlay will not work without a valid key");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    return;
                }
            });
            return;
        }
        // TODO: Don't restrict user for in valid api key

        /*
        * If there isn't a key entered, don't stop the program from running, instead allow
        * the user to type '/api new' on hypixel and then keep the program parsing lines
        */


        // TODO: Ensure log file exists

        // TODO: Add log file detection/selection

        Constants.LOG_LOCATION = txtbxLogPath.getText().replace('\\', '/').strip();
        Constants.LOG_TAILER = new LogTailer();
        Constants.LOG_TAILER_THREAD = new Thread(Constants.LOG_TAILER);
        Constants.LOG_TAILER_THREAD.setDaemon(true);
        Constants.LOG_TAILER_THREAD.start();
        btnStop.setVisible(true);
    }

    public void onBtnStopAction(ActionEvent actionEvent) {
        // Stop Button

        Constants.LOG_TAILER.stop();
        myTableView.getItems().clear();

        btnStop.setVisible(false);
    }

    public void addPlayerToList(OverlayPlayer overlayPlayer) {
        ObservableList<OverlayPlayer> playersList = myTableView.getItems();
        playersList.add(overlayPlayer);
        myTableView.setItems(playersList);
    }

}
