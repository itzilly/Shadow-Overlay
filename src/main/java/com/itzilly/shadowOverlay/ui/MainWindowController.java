package com.itzilly.shadowOverlay.ui;

import com.itzilly.shadowOverlay.Constants;
import com.itzilly.shadowOverlay.LogTailer;
import com.itzilly.shadowOverlay.objects.GamePlayer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    @FXML
    public TableView<GamePlayer> myTableView;
    @FXML
    public TableColumn<GamePlayer, String> playerColumn;
    @FXML
    public TableColumn<GamePlayer, Integer> starsColumn;
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
        hideStopButton();
        txtbxLogPath.setText("C:\\Users\\illyum\\.lunarclient\\offline\\multiver\\logs\\latest.log");
        playerColumn.setCellValueFactory(new PropertyValueFactory<GamePlayer, String>("Name"));
        starsColumn.setCellValueFactory(new PropertyValueFactory<GamePlayer, Integer>("Stars"));

        GamePlayer gamePlayer = new GamePlayer("illyum");
        ObservableList<GamePlayer> playersList = myTableView.getItems();
        playersList.add(gamePlayer);
        myTableView.setItems(playersList);

//        GamePlayer illyum = new GamePlayer("illyum", 872);
//        ObservableList<GamePlayer> players = myTableView.getItems();
//        players.add(illyum);
//        myTableView.setItems(players);

    }

    public MainWindowController() {
        hideStopButton();
        txtbxLogPath.setText("C:\\Users\\illyum\\.lunarclient\\offline\\multiver\\logs\\latest.log");
        playerColumn.setCellValueFactory(new PropertyValueFactory<GamePlayer, String>("Name"));
        starsColumn.setCellValueFactory(new PropertyValueFactory<GamePlayer, Integer>("Stars"));

        GamePlayer gamePlayer = new GamePlayer("illyum");
        ObservableList<GamePlayer> playersList = myTableView.getItems();
        playersList.add(gamePlayer);
        myTableView.setItems(playersList);

    }


    void hideStopButton() {
        // Hides the 'Stop' button
        btnStop.setScaleX(0);
        btnStop.setScaleY(0);
        btnStop.setScaleZ(0);
    }

    void showStopButton() {
        // Show's the 'Start' button
        btnStop.setScaleX(1);
        btnStop.setScaleY(1);
        btnStop.setScaleZ(1);
    }

    public void onBtnStartAction(ActionEvent actionEvent) {
        // Start Button

        // TODO: Test API Key
        // TODO: Ensure log file exists
        Constants.API_KEY = txtbxApiKey.getText().strip();
        Constants.LOG_LOCATION = txtbxLogPath.getText().replace('\\', '/').strip();
        Constants.LOG_TAILER = new LogTailer();
        Constants.LOG_TAILER_THREAD = new Thread(Constants.LOG_TAILER);
        Constants.LOG_TAILER_THREAD.setDaemon(true);
        Constants.LOG_TAILER_THREAD.start();
        showStopButton();
    }

    public void onBtnStopAction(ActionEvent actionEvent) {
        // Stop Button

        Constants.LOG_TAILER.stop();
        hideStopButton();
    }

    public void addPlayerToList(GamePlayer gamePlayer) {
        ObservableList<GamePlayer> playersList = myTableView.getItems();
        playersList.add(gamePlayer);
        myTableView.setItems(playersList);
    }

}
