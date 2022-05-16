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
    private Button changeArmyButton1;

    @FXML
    private Button changeArmyButton2;

    @FXML
    private Button removeArmyButton1;

    @FXML
    private Button removeArmyButton2;

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
    void createArmy(ActionEvent event) throws IOException {
        SimulationSingleton.INSTANCE.setCurrentArmy(armyNumberByButton(event));

        SceneHandler.loadArmyConstruction(event);
    }

    @FXML
    void loadArmy(ActionEvent event) {
        SimulationSingleton.INSTANCE.setCurrentArmy(armyNumberByButton(event));
        //Scenehandler
    }

    @FXML
    void changeArmy(ActionEvent event) {
        SimulationSingleton.INSTANCE.setCurrentArmy(armyNumberByButton(event));
    }

    @FXML
    void removeArmy(ActionEvent event) {
        if(armyNumberByButton(event) == 1) SimulationSingleton.INSTANCE.setArmy1(null);
        else SimulationSingleton.INSTANCE.setArmy2(null);
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


    private void initialData(){
        terrainComboBox.getItems().addAll(TerrainType.values());
        terrainComboBox.setValue(SimulationSingleton.INSTANCE.getCurrentTerrain());

        if(armyNameText1 != null && armyNameText2 != null) displayArmy();

    }



    private void displayArmy(){
        boolean displayInfo = false;
        if(SimulationSingleton.INSTANCE.getArmy1() != null) {
            displayInfo = true;
            armyNameText1.setText(SimulationSingleton.INSTANCE.getArmy1().getName());
            createArmyTable(SimulationSingleton.INSTANCE.getArmy1(), army1Table);
        }
        else armyNameText1.setText("No Army Equipped");

        loadArmyButton1.setVisible(!displayInfo);
        createArmyButton1.setVisible(!displayInfo);
        orText.setVisible(!displayInfo);

        army1Table.setVisible(displayInfo);
        changeArmyButton1.setVisible(displayInfo);
        removeArmyButton1.setVisible(displayInfo);

        displayInfo = false;

        if(SimulationSingleton.INSTANCE.getArmy2() != null){
            displayInfo = true;
            armyNameText2.setText(SimulationSingleton.INSTANCE.getArmy2().getName());
            createArmyTable(SimulationSingleton.INSTANCE.getArmy2(), army2Table);
        }
        else armyNameText2.setText("No Army Equipped");

        loadArmyButton2.setVisible(!displayInfo);
        createArmyButton2.setVisible(!displayInfo);
        orText2.setVisible(!displayInfo);

        army2Table.setVisible(displayInfo);
        changeArmyButton2.setVisible(displayInfo);
        removeArmyButton2.setVisible(displayInfo);
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

    private int armyNumberByButton(ActionEvent event){
        if(event.getSource().toString().contains("1")){
            return 1;
        }
        return 2;
    }

}

//Maybe add a button for editing an existing army and one for removing