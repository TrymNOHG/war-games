package edu.ntnu.trym.simulation.controller;

import edu.ntnu.trym.simulation.model.TerrainType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BattlePrepController implements Initializable {

    @FXML
    private Text armyNameText1;

    @FXML
    private Text armyNameText2;

    @FXML
    private Button createArmyButton1;

    @FXML
    private Button createArmyButton2;

    @FXML
    private Button loadArmyButton1;

    @FXML
    private Button loadArmyButton2;

    @FXML
    private Text orText;

    @FXML
    private Text orText2;

    @FXML
    private ComboBox<TerrainType> terrainComboBox = new ComboBox<>();
    //Check if there were previous armies, if so load them, if not let the default be loaded.

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialData();
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

    @FXML
    void createArmy(ActionEvent event) throws IOException {
        if(event.getSource().toString().contains("createArmyButton1")){
            //Army constructionArmy = army1
        }
        else ; //Army constructionArmy = army2
        SceneHandler.loadArmyConstruction(event);
    }

    @FXML
    void loadArmy(ActionEvent event) {
        if(event.getSource().toString().contains("loadArmyButton1")){
            //Army constructionArmy = army1
        }
        else ; //Army constructionArmy = army2
        //Scenehandler
    }


    private void initialData(){
        terrainComboBox.getItems().addAll(TerrainType.values());
        //Always set the value to the singleton terrain, and the singleton terrain should default be default.
        terrainComboBox.setValue(TerrainType.DEFAULT);
    }

}
