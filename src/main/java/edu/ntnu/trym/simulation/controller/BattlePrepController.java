package edu.ntnu.trym.simulation.controller;

import edu.ntnu.trym.simulation.model.TerrainType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BattlePrepController implements Initializable {


    @FXML
    private Text armyNameText1;

    @FXML
    private Text armyNameText12;

    @FXML
    private Text orText;

    @FXML
    private Text orText2;

    @FXML
    private ComboBox<TerrainType> terrainComboBox = new ComboBox<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        terrainComboBox.getItems().addAll(TerrainType.values());
        terrainComboBox.setValue(TerrainType.DEFAULT);
    }

    @FXML
    void backToMainMenu(ActionEvent event) throws IOException {
        SceneHandler.loadMainMenu(event);
    }

    @FXML
    void continueButton(ActionEvent event) throws IOException {
        SceneHandler.loadBattlePreparation(event);
    }

    @FXML
    void backToUnitInfo(ActionEvent event) throws IOException {
        SceneHandler.loadUnitInformation(event);
    }

}