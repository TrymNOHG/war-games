package edu.ntnu.trym.simulation.controller;

import edu.ntnu.trym.simulation.model.AlertDialog;
import edu.ntnu.trym.simulation.model.TerrainBackground;
import edu.ntnu.trym.simulation.model.armydisplay.ArmyTable;
import edu.ntnu.trym.simulation.model.TerrainType;
import edu.ntnu.trym.simulation.model.units.Unit;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;

public class BattlePrepController {

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
    private Button fightButton;

    @FXML
    private Pane backgroundPane = new Pane();

    @FXML
    private ComboBox<TerrainType> terrainComboBox = new ComboBox<>();

    @FXML
    private TableView<Unit> army1Table;

    @FXML
    private TableView<Unit> army2Table;

    public void initialize() {
        initialData();
        addTerrainListener();
    }

    @FXML
    void createArmy(ActionEvent event) throws IOException {
        SimulationSingleton.INSTANCE.setCurrentArmy(armyNumberByButton(event));
        SceneHandler.loadArmyConstruction(event);
    }

    @FXML
    void loadArmy(ActionEvent event) throws IOException {
        SimulationSingleton.INSTANCE.setCurrentArmy(armyNumberByButton(event));
        SceneHandler.loadSavedArmies(event);
    }

    @FXML
    void changeArmy(ActionEvent event) throws IOException {
        SimulationSingleton.INSTANCE.setCurrentArmy(armyNumberByButton(event));
        SceneHandler.loadArmyConstruction(event);
    }

    @FXML
    void removeArmy(ActionEvent event) {
        if(armyNumberByButton(event) == 1) SimulationSingleton.INSTANCE.setArmy1(null);
        else SimulationSingleton.INSTANCE.setArmy2(null);
        updateBattlePrepData();
    }

    @FXML
    void backToMainMenu(ActionEvent event) throws IOException {
        SceneHandler.loadMainMenu(event);
    }

    @FXML
    void continueButton(ActionEvent event) throws IOException {
        SceneHandler.loadBattlePreparation(event);
        AlertDialog.showInformation("In order to look through all the items in the tableview, you may" +
                " need to use tab. This will allow you to select the tableview. From there, you can use the arrow" +
                "keys!", "Accessing Army TableView in Battle Preparations");
    }

    @FXML
    void backToUnitInfo(ActionEvent event) throws IOException {
        SceneHandler.loadUnitInformation(event);
    }


    @FXML
    void simulateFight(ActionEvent event) throws IOException {
        SceneHandler.loadFightScreen(event);
    }

    //TODO: check that initialData only works with initial data. Maybe make updateData method
    private void initialData(){
        terrainComboBox.getItems().addAll(TerrainType.values());
        terrainComboBox.setValue(SimulationSingleton.INSTANCE.getCurrentTerrain());

        TerrainBackground.setBackgroundByTerrain(terrainComboBox.getValue(), backgroundPane);

        updateBattlePrepData();
    }

    private void updateBattlePrepData(){
        if(armyNameText1 != null && armyNameText2 != null) {
            displayArmy();
        }

        if(readyToFight() && this.fightButton != null){
            fightButton.setDisable(false);
            fightButton.setId("main-load-button");
        }
        else if(this.fightButton != null){
            fightButton.setDisable(true);
            fightButton.setId("main-button");
        }
    }

    /**
     * This method adds a listener to the ComboBox{@code <TerrainType>} terrainComboBox, so when the terrain choice
     * changes, the enum value can be stored in the SimulationSingleton class.
     */
    private void addTerrainListener(){
        terrainComboBox.setOnAction(event -> {
            SimulationSingleton.INSTANCE.setCurrentTerrain(terrainComboBox.getValue());
            TerrainBackground.setBackgroundByTerrain(terrainComboBox.getValue(), backgroundPane);
        });
    }

    //TODO: refactor to make the code more readable
    private void displayArmy(){
        displayArmy1();
        displayArmy2();
    }

    private void displayArmy1(){
        boolean displayInfo = false;

        if(SimulationSingleton.INSTANCE.getArmy1() != null) {
            displayInfo = true;
            armyNameText1.setText(SimulationSingleton.INSTANCE.getArmy1().getName());
            //Try .toFront() on table to allow it to be used. Remember toback when not in use
            if(this.army1Table.getColumns().size() == 0) army1Table.getColumns().addAll(createArmyTable().getColumns());
            army1Table.setItems(FXCollections.observableList(SimulationSingleton.INSTANCE.getArmy1().getAllUnits()));
        }
        else armyNameText1.setText("No Army Equipped");

        loadArmyButton1.setVisible(!displayInfo);
        createArmyButton1.setVisible(!displayInfo);
        orText.setVisible(!displayInfo);

        army1Table.setVisible(displayInfo);
        changeArmyButton1.setVisible(displayInfo);
        removeArmyButton1.setVisible(displayInfo);
    }

    private void displayArmy2(){
        boolean displayInfo = false;

        if(SimulationSingleton.INSTANCE.getArmy2() != null){
            displayInfo = true;
            armyNameText2.setText(SimulationSingleton.INSTANCE.getArmy2().getName());
            if(this.army2Table.getColumns().size() == 0) army2Table.getColumns().addAll(createArmyTable().getColumns());
            army2Table.setItems(FXCollections.observableList(SimulationSingleton.INSTANCE.getArmy2().getAllUnits()));
        }
        else armyNameText2.setText("No Army Equipped");

        loadArmyButton2.setVisible(!displayInfo);
        createArmyButton2.setVisible(!displayInfo);
        orText2.setVisible(!displayInfo);

        army2Table.setVisible(displayInfo);
        changeArmyButton2.setVisible(displayInfo);
        removeArmyButton2.setVisible(displayInfo);
    }

    private boolean readyToFight(){
        return SimulationSingleton.INSTANCE.getArmy1() != null && SimulationSingleton.INSTANCE.getArmy2() != null;
    }

    /**
     * This method creates a table containing all the information of a unit. This is done by using the army table
     * class to build the columns desired {@link ArmyTable#ArmyTable(ArmyTable.Builder)}.
     */
    private TableView<Unit> createArmyTable(){

        return new ArmyTable.Builder()
                .addUnitColumn("Unit Type", "unitType")
                .addUnitColumn("Name", "name")
                .addUnitColumn("Health", "health")
                .addUnitColumn("Attack", "attack")
                .addUnitColumn("Armor", "armor")
                .build();
    }

    /**
     * This method takes the javafx id of a given event's button and finds out whether the event belonged
     * to army one, indicated by a 1 in the id, or belongs to army2, indicated by a 2.
     * @param event An action taken by the user such as a button click, represented using an Event object.
     * @return      A number representing which army the action adheres to, using the ints 1 for army1 and 2 for army2.
     */
    private int armyNumberByButton(ActionEvent event){
        if(event.getSource().toString().contains("1")){
            return 1;
        }
        return 2;
    }
}


/*
TODO:
        Fix visibility of text and try and fix the tableview in battle prep
        4. Add methods for actually conducting the fight in the fight controller.
                    - Attempt to create a text that rolls down the screen for different events
                    (with a skip button that appears when the match is actually complete)
        5. Add results scene and controller
        7. Fix the unit information screen and add proper info as well as for the help page.
        8. Add a warning dialog that pops up if a file that was attempted to be loaded was corrupt. Make sure amount of pages is correct
        9. Add background picture to saved armies screen
        Fix text in battle preparation to be more visible (try out text with border)
        10. Javadoc all classes and methods
        11. Look for places that need refactoring
        12. Look over all tests
        13. Try to break the simulation. (Check where error and warning boxes may be used.
        14. If there is extra time, allow the saved files to be organized
            - Based on Army Name, File name, save time, etc.

 */

//     * @param army      The army that provides the information to be displayed, given as an Army object.
//        * @param tableView The table where the information will be displayed, given as a TableView object.