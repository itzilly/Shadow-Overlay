package com.itzilly.shadowOverlay.ui;

import com.itzilly.shadowOverlay.*;
import com.itzilly.shadowOverlay.objects.BedwarsPlayerStats;
import com.itzilly.shadowOverlay.objects.NickedPlayer;
import com.itzilly.shadowOverlay.objects.OverlayPlayer;
import com.itzilly.shadowOverlay.ui.statsControllers.BedwarsStatsController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.ini4j.Ini;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    private Ini readIni;
    private Ini writeIni;
    private String log_path;
    private String api_key;

    private ObservableList<OverlayPlayer> playersList;


    @FXML
    public TableView<OverlayPlayer> myTableView;
    @FXML
    public TableColumn<OverlayPlayer, String> playerColumn;
    @FXML
    public TableColumn<OverlayPlayer, Integer> starsColumn;
    @FXML
    public TableColumn<OverlayPlayer, Double> indexColumn;
    
    @FXML
    TableColumn<OverlayPlayer, Double> fkdrColumn;
    @FXML
    public TableColumn<OverlayPlayer, Long> finalKillsColumn;
    @FXML
    public TableColumn<OverlayPlayer, Long> winsColumn;
    @FXML
    public TableColumn<OverlayPlayer, Double> winLossRatioColumn;
    @FXML
    public TableColumn<OverlayPlayer, Long> winstreakColumn;
    @FXML
    public Button btnStart;
    @FXML
    public TextField txtbxApiKey;
    @FXML
    public TextField txtbxLogPath;
    @FXML
    public Button btnStop;

    private final String configFilename = "config.properties";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadConfig();

        btnStop.setVisible(false);
        // Note: "Name" below corresponds to the variable name WITHIN THE CLASS (this.name), this is NOT the name of the column title!
        playerColumn.setCellValueFactory(new PropertyValueFactory<OverlayPlayer, String>("name"));
        starsColumn.setCellValueFactory(new PropertyValueFactory<OverlayPlayer, Integer>("bedwarsLevel"));
        indexColumn.setCellValueFactory(new PropertyValueFactory<OverlayPlayer, Double>("index"));
        fkdrColumn.setCellValueFactory(new PropertyValueFactory<OverlayPlayer, Double>("fkdr"));
        finalKillsColumn.setCellValueFactory(new PropertyValueFactory<OverlayPlayer, Long>("finalKills"));
        winsColumn.setCellValueFactory(new PropertyValueFactory<OverlayPlayer, Long>("wins"));
        winLossRatioColumn.setCellValueFactory(new PropertyValueFactory<OverlayPlayer, Double>("wlr"));
        winstreakColumn.setCellValueFactory(new PropertyValueFactory<OverlayPlayer, Long>("winstreak"));


        txtbxApiKey.setText(api_key);
        txtbxLogPath.setText(log_path);

        playersList = myTableView.getItems();
    }

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        loadConfig();
//        btnStop.setVisible(false);
//
//        txtbxApiKey.setText(api_key);
//        txtbxLogPath.setText(log_path);
//
//        playersList = myTableView.getItems();
//        BedwarsStatsController bedwarsStatsController = new BedwarsStatsController();
//    }

    private void loadConfig() {
        readIni = new Ini();
        try {
            readIni.load(new FileReader(Constants.APPDATA_PATH() + File.separator + configFilename));
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
            writeIni.store(new FileOutputStream(Constants.APPDATA_PATH() + File.separator + configFilename));
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
        if (playersList.contains(overlayPlayer)) {
            return;
        }
        playersList.add(overlayPlayer);
        playersList.sort(comparatorOverlayPlayer_byStar);
        myTableView.setItems(playersList);
    }

    public void removePlayerFromList(String playerName) {
        playersList.removeIf(player -> player.getName().equalsIgnoreCase(playerName));
    }

    public void addNickedPlayer(NickedPlayer nickedPlayer) {
        playersList.add(nickedPlayer);
    }

    Comparator<? super OverlayPlayer> comparatorOverlayPlayer_byStar = new Comparator<OverlayPlayer>() {
        @Override
        public int compare(OverlayPlayer o1, OverlayPlayer o2) {
            double o1Stat = o1.getIndex();
            double o2Stat = o2.getIndex();

            if(o1Stat == o2Stat) return 1;
            if(o1Stat > o2Stat)
                return 0;
            else
                return 1;
        }
    };

    public void onMnuItmSettings(ActionEvent actionEvent) {
        // Open Settings Panel
        Parent root;
        try {
            root = FXMLLoader.load(MainWindow.class.getResource("settings-window.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Settings");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onMnuItmClose(ActionEvent actionEvent) {
        // Close Shadow Overlay
    }
}
