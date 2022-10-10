package com.itzilly.shadowOverlay.ui;

import com.itzilly.shadowOverlay.Constants;
import com.itzilly.shadowOverlay.Http;
import com.itzilly.shadowOverlay.LogTailer;
import com.itzilly.shadowOverlay.ShadowOverlay;
import com.itzilly.shadowOverlay.objects.OverlayPlayer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnStop.setVisible(false);
        // Note: "Name" below corresponds to the variable name WITHIN THE CLASS (this.name), this is NOT the name of the column title!
        playerColumn.setCellValueFactory(new PropertyValueFactory<OverlayPlayer, String>("name"));
        starsColumn.setCellValueFactory(new PropertyValueFactory<OverlayPlayer, Integer>("bedwarsLevel"));

        ObservableList<OverlayPlayer> playersList = myTableView.getItems();
        myTableView.setItems(playersList);

        txtbxApiKey.setText(Constants.yamlConfiguration.get("API_KEY") + "");
        txtbxLogPath.setText(Constants.yamlConfiguration.get("log_path") + "");

    }


    public void onBtnStartAction(ActionEvent actionEvent) {
        // Start Button

        Constants.configManager.save("API_KEY", txtbxApiKey.getText());
        Constants.configManager.save("log_path", txtbxLogPath.getText().replace('\\', '/'));

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

        Constants.SETTINGS_MANAGER.saveApiKey(Constants.API_KEY);
        Constants.SETTINGS_MANAGER.saveLogPath(Constants.LOG_LOCATION);

//        ShadowOverlay.configManager.save("API_KEY", txtbxApiKey.getText().strip());
//        ShadowOverlay.configManager.save("log_path", txtbxLogPath.getText().replace('\\', '/').strip());
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
