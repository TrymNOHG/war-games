package edu.ntnu.trym.simulation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ArmyConstructionController {

    @FXML
    private TextField amountOfUnitsInput;

    @FXML
    private TextField armorInput;

    @FXML
    private TextField armyNameText;

    @FXML
    private TableView<?> armyTable;

    @FXML
    private TextField attackInput;

    @FXML
    private TextField healthInput;

    @FXML
    private TextField unitNameInput;

    @FXML
    private ComboBox<?> unitTypeBox = new ComboBox<>();

    @FXML
    void addUnit(ActionEvent event) {

    }

    @FXML
    void backToBattlePrep(ActionEvent event) throws IOException {
        SceneHandler.loadBattlePreparation(event);
    }

    @FXML
    void removeUnit(ActionEvent event) {
    }

    @FXML
    void saveLoadArmy(ActionEvent event) {

    }
}
