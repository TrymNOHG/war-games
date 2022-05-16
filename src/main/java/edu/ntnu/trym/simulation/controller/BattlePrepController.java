package edu.ntnu.trym.simulation.controller;

import edu.ntnu.trym.simulation.model.Army;
import edu.ntnu.trym.simulation.model.TerrainType;
import edu.ntnu.trym.simulation.model.units.Unit;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

    @FXML
    private TableView<Unit> army1Table;

    @FXML
    private TableView<Unit> army2Table;

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
            SimulationSingleton.INSTANCE.setCurrentArmy(1);
        }
        else SimulationSingleton.INSTANCE.setCurrentArmy(2); //Army constructionArmy = army2
        SceneHandler.loadArmyConstruction(event);
    }

    //I could make a method which checks which army is being worked on.

    @FXML
    void loadArmy(ActionEvent event) {
        if(event.getSource().toString().contains("loadArmyButton1")){
            SimulationSingleton.INSTANCE.setCurrentArmy(1);
        }
        else SimulationSingleton.INSTANCE.setCurrentArmy(2); //Army constructionArmy = army2
        //Scenehandler
    }


    private void initialData(){
        terrainComboBox.getItems().addAll(TerrainType.values());
        terrainComboBox.setValue(SimulationSingleton.INSTANCE.getCurrentTerrain());

        //Try to generalize so that all buttons can be displayed using these methods

        if(SimulationSingleton.INSTANCE.getArmy1() != null){
            displayArmy(SimulationSingleton.INSTANCE.getArmy1(), 1);
        }
        if(SimulationSingleton.INSTANCE.getArmy2() != null){
            displayArmy(SimulationSingleton.INSTANCE.getArmy2(), 2);
        }
    }

    private void displayArmy(Army army, int armyNumber){
        if(armyNumber == 1){
            armyNameText1.setText(army.getName());
            loadArmyButton1.setVisible(false);
            createArmyButton1.setVisible(false);
            orText.setVisible(false);

            createArmyTable(army, army1Table);
            army1Table.setVisible(true);
        }
        else{
            armyNameText2.setText(army.getName());
            loadArmyButton2.setVisible(false);
            createArmyButton2.setVisible(false);
            orText2.setVisible(false);

            createArmyTable(army, army2Table);
            army2Table.setVisible(true);
        }
    }

    private void createArmyTable(Army army, TableView<Unit> tableView){
        TableColumn<Unit, String> firstColumn = new TableColumn<>("Unit Type");
        firstColumn.setCellValueFactory(new PropertyValueFactory<>("unitType"));

        TableColumn<Unit, String> secondColumn = new TableColumn<>("Name");
        secondColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Unit, String> thirdColumn = new TableColumn<>("Health");
        thirdColumn.setCellValueFactory(new PropertyValueFactory<>("health"));

        TableColumn<Unit, String> fourthColumn = new TableColumn<>("Attack");
        fourthColumn.setCellValueFactory(new PropertyValueFactory<>("attack"));

        TableColumn<Unit, String> fifthColumn = new TableColumn<>("Armor");
        fifthColumn.setCellValueFactory(new PropertyValueFactory<>("armor"));


        tableView.getColumns().addAll(firstColumn, secondColumn, thirdColumn, fourthColumn, fifthColumn);
        tableView.setItems(FXCollections.observableList(army.getAllUnits()));
    }


}

//Maybe add a button for editing an existing army and one for removing