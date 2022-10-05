package com.itzilly.shadowverlay.ui;

import com.itzilly.shadowverlay.Constants;
import com.itzilly.shadowverlay.LogTailer;
import com.itzilly.shadowverlay.objects.GamePlayer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML
    public TextField txtbxApiKey;
    @FXML
    public Button btnStop;
    @FXML
    private Button btnStart;
    @FXML TextField txtbxLogPath;


    // Table
    @FXML
    private TableView<GamePlayer> myTableView;

    // Columns
    @FXML
    private TableColumn<GamePlayer, String> playerColumn;
    @FXML
    private TableColumn<GamePlayer, Integer> starsColumn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hideStopBtn();
        playerColumn.setCellValueFactory(new PropertyValueFactory<GamePlayer, String>("Name"));
        starsColumn.setCellValueFactory(new PropertyValueFactory<GamePlayer, Integer>("Stars"));

//        GamePlayer illyum = new GamePlayer("illyum", 872);
//        ObservableList<GamePlayer> players = myTableView.getItems();
//        players.add(illyum);
//        myTableView.setItems(players);

    }

    private void hideStopBtn() {
        btnStop.setScaleX(0);
        btnStop.setScaleY(0);
        btnStop.setScaleZ(0);
    }

    private void showStopBtn() {
        btnStop.setScaleX(1);
        btnStop.setScaleY(1);
        btnStop.setScaleZ(1);
    }

//    public TableView<List<String>> readTabDelimitedFileIntoTable(Path file) throws IOException {
//        TableView<List<String>> table = new TableView<>();
//        Files.lines(file).map(line -> line.split("\t")).forEach(values -> {
//            // Add extra columns if necessary:
//            for (int i = table.getColumns().size(); i < values.length; i++) {
//                TableColumn<List<String>, String> col = new TableColumn<>("Column "+(i+1));
//                col.setMinWidth(80);
//                final int colIndex = i ;
//                col.setCellValueFactory(data -> {
//                    List<String> rowValues = data.getValue();
//                    String cellValue ;
//                    if (colIndex < rowValues.size()) {
//                        cellValue = rowValues.get(colIndex);
//                    } else {
//                        cellValue = "" ;
//                    }
//                    return new ReadOnlyStringWrapper(cellValue);
//                });
//                table.getColumns().add(col);
//            }
//
//            // add row:
//            table.getItems().add(Arrays.asList(values));
//        });
//        return table ;
//    }

    public TableView<List<String>> getTableView() {
        TableView<List<String>> tableView = new TableView<>();
        TableColumn<List<String>, String> column = new TableColumn<>("Column Title?");
        column.setMinWidth(80);
        tableView.getColumns().add(column);
        return tableView;
    }

    @FXML
    void onBtnStartAction(ActionEvent actionEvent) {
        Constants.API_KEY = txtbxApiKey.getText().strip();
        Constants.LOG_LOCATION = txtbxLogPath.getText().replace('\\', '/').strip();
        Constants.logTailer = new LogTailer();
        Constants.logTailerThread = new Thread(Constants.logTailer);
        Constants.logTailerThread.setDaemon(true);
        Constants.logTailerThread.start();
        showStopBtn();
    }

    public void onBtnStopAction(ActionEvent actionEvent) {
        Constants.logTailer.stop();
        hideStopBtn();
    }
}
