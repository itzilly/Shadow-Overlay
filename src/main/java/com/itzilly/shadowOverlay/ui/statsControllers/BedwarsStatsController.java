package com.itzilly.shadowOverlay.ui.statsControllers;

import com.itzilly.shadowOverlay.objects.BedwarsPlayerStats;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class BedwarsStatsController {
    @FXML private TableView<BedwarsPlayerStats> myTableView;
    @FXML private TableColumn<BedwarsPlayerStats, String> playerColumn;
    @FXML private TableColumn<BedwarsPlayerStats, Integer> starsColumn;
    @FXML private TableColumn<BedwarsPlayerStats, Integer> indexColumn;
    @FXML private TableColumn<BedwarsPlayerStats, Double> fkdrColumn;
    @FXML private TableColumn<BedwarsPlayerStats, Integer> finalKillsColumn;
    @FXML private TableColumn<BedwarsPlayerStats, Integer> winsColumn;
    @FXML private TableColumn<BedwarsPlayerStats, Double> winLossRatioColumn;
    @FXML private TableColumn<BedwarsPlayerStats, Integer> winstreakColumn;

    private ObservableList<BedwarsPlayerStats> playerStatsList = FXCollections.observableArrayList();

    public void initialize() {
        playerColumn.setCellValueFactory(cellData -> cellData.getValue().playerNameProperty());
        starsColumn.setCellValueFactory(cellData -> cellData.getValue().starsProperty().asObject());
        indexColumn.setCellValueFactory(cellData -> cellData.getValue().indexProperty().asObject());
        fkdrColumn.setCellValueFactory(cellData -> cellData.getValue().fkdrProperty().asObject());
        finalKillsColumn.setCellValueFactory(cellData -> cellData.getValue().finalKillsProperty().asObject());
        winsColumn.setCellValueFactory(cellData -> cellData.getValue().winsProperty().asObject());
        winLossRatioColumn.setCellValueFactory(cellData -> cellData.getValue().winLossRatioProperty().asObject());
        winstreakColumn.setCellValueFactory(cellData -> cellData.getValue().winstreakProperty().asObject());

        myTableView.setItems(playerStatsList);
    }

    public void setPlayerStats(List<BedwarsPlayerStats> playerStats) {
        playerStatsList.clear();
        playerStatsList.addAll(playerStats);
    }
}
