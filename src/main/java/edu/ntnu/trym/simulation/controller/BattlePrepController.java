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

/**
 * This class handles the interactions between the backend and the BattlePrep scene. As such, it guides the user to
 * make two armies in multiple ways: constructing an army froms scratch, loading an army, or altering a saved army.
 * Additionally, the user gets to set the terrain of the battle and when they are ready, the battle can be initiated.
 * Furthermore, the armies selected are displayed on the scene through this class.
 *
 * @author Trym Hamer Gudvangen
 */
public class BattlePrepController {

    @FXML
    private Text armyNameText1;

    @FXML
    private Text armyNameText2;

    @FXML
    private Button armyInformation1;

    @FXML
    private Button armyInformation2;

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

    /**
     * This method sets up all the information concerning the scene before loading it. This includes setting up
     * listeners, loading variable data, and constructing the GUI aspects of the scene.
     */
    public void initialize() {
        initialData();
        addTerrainListener();
    }

    /**
     * This method is called when the user presses the create army button. This may be for either army1 (top army) or
     * for army2 (bottom army). This method uses the {@link #armyNumberByButton(ActionEvent)} in order to determine
     * which army is being altered. This information is sent to the dispatcher, {@link SimulationSingleton}. After that,
     * the scene is switched to the army construction scene
     * {@link SceneHandler#loadArmyConstruction(ActionEvent)}.
     * @param event         The button being pressed, given as an Event object.
     * @throws IOException  This exception is thrown if the path to the army construction scene is invalid.
     */
    @FXML
    void createArmy(ActionEvent event) throws IOException {
        SimulationSingleton.INSTANCE.setCurrentArmy(armyNumberByButton(event));
        SceneHandler.loadArmyConstruction(event);
    }

    /**
     * This method is called when the user presses the load army button. This may be for either army1 (top army) or
     * for army2 (bottom army). This method uses the {@link #armyNumberByButton(ActionEvent)} in order to determine
     * which army is being altered. This information is sent to the dispatcher, {@link SimulationSingleton}. After that,
     * the scene is switched to the loadSavedArmies scene {@link SceneHandler#loadSavedArmies(ActionEvent)}.
     * @param event         The button being pressed, given as an Event object.
     * @throws IOException  This exception is thrown if the path to the saved army scene is invalid.
     */
    @FXML
    void loadArmy(ActionEvent event) throws IOException {
        SimulationSingleton.INSTANCE.setCurrentArmy(armyNumberByButton(event));
        SceneHandler.loadSavedArmies(event);
    }


    /**
     * This method is called when the user presses the show army information button. This may be for either army1
     * (top army) or for army2 (bottom army). This method uses the {@link #armyNumberByButton(ActionEvent)} in order to
     * determine which army is being altered. This information is sent to the dispatcher, {@link SimulationSingleton}.
     * After that, the scene is switched to the loadSavedArmies scene {@link SceneHandler#loadSavedArmies(ActionEvent)}.
     * @param event         The button being pressed, given as an Event object.
     * @throws IOException  This exception is thrown if the path to the saved army scene is invalid.
     */
    @FXML
    void showArmyInformation(ActionEvent event) throws IOException {
        SimulationSingleton.INSTANCE.setCurrentArmy(armyNumberByButton(event));
        SceneHandler.loadArmyInformation(event);
    }

    /**
     * This method is called when the user presses the change army button. This may be for either army1 (top army) or
     * for army2 (bottom army). This method uses the {@link #armyNumberByButton(ActionEvent)} in order to determine
     * which army is being altered. This information is sent to the dispatcher, {@link SimulationSingleton}.
     * After that, the scene is switched to the army construction scene {@link SceneHandler#loadArmyConstruction(ActionEvent)}
     * with the information of which scene.
     * @param event         The button being pressed, given as an Event object.
     * @throws IOException  This exception is thrown if the path to the army construction scene is invalid.
     */
    @FXML
    void changeArmy(ActionEvent event) throws IOException {
        SimulationSingleton.INSTANCE.setCurrentArmy(armyNumberByButton(event));
        SceneHandler.loadArmyConstruction(event);
    }

    /**
     * This method is called when the user presses the remove army button. This may be for either army1 (top army) or
     * for army2 (bottom army). This method uses the {@link #armyNumberByButton(ActionEvent)} in order to determine
     * which army is being altered. Thereafter, the respective army in the dispatcher, {@link SimulationSingleton}, is
     * set to null. Finally, the battle prep data is updated, {@link #updateBattlePrepData()}.
     * @param event         The button being pressed, given as an Event object.
     */
    @FXML
    void removeArmy(ActionEvent event) {
        if(armyNumberByButton(event) == 1) SimulationSingleton.INSTANCE.setArmy1(null);
        else SimulationSingleton.INSTANCE.setArmy2(null);
        updateBattlePrepData();
    }

    /**
     * This method is called when the user presses the back to main menu button. As the name implies, using
     * {@link SceneHandler#loadMainMenu(ActionEvent)}, the main menu scene is loaded.
     * @param event             The button being pressed, given as an Event object.
     * @throws IOException      This exception is thrown if the path to the main menu scene is invalid.
     */
    @FXML
    void backToMainMenu(ActionEvent event) throws IOException {
        SceneHandler.loadMainMenu(event);
    }

    /**
     * This method is called when the user presses the continue button. As the name implies, using
     * {@link SceneHandler#loadBattlePreparation(ActionEvent)}, the battle preparation scene is loaded. Furthermore,
     * since the tableview can be a little confusing, an information dialog pops up,
     * {@link AlertDialog#showInformation(String, String)}.
     * @param event             The button being pressed, given as an Event object.
     * @throws IOException      This exception is thrown if the path to the battle preparation scene is invalid.
     */
    @FXML
    void continueButton(ActionEvent event) throws IOException {
        SceneHandler.loadBattlePreparation(event);
        AlertDialog.showInformation("In order to look through all the items in the tableview, you may" +
                " need to use tab. This will allow you to select the tableview. From there, you can use the arrow" +
                "keys!", "Accessing Army TableView in Battle Preparations");
    }

    /**
     * This method is called when the user presses the back button. Using
     * {@link SceneHandler#loadUnitInformation(ActionEvent)}, the unit information scene is loaded.
     * @param event             The button being pressed, given as an Event object.
     * @throws IOException      This exception is thrown if the path to the unit information scene is invalid.
     */
    @FXML
    void backToUnitInfo(ActionEvent event) throws IOException {
        SceneHandler.loadUnitInformation(event);
    }

    /**
     * This method is called when the user presses the fight button. As the name implies, using
     * {@link SceneHandler#loadFightScreen(ActionEvent)}, the fight screen is loaded.
     * @param event             The button being pressed, given as an Event object.
     * @throws IOException      This exception is thrown if the path to the fight scene is invalid.
     */
    @FXML
    void simulateFight(ActionEvent event) throws IOException {
        SceneHandler.loadFightScreen(event);
    }

    /**
     * This method is used to set all the initial information necessary throughout the class. It does so through
     * retrieving army information from {@link SimulationSingleton}. Furthermore, it edits the GUI to contain the
     * information that was retrieved such as the terrain background.
     */
    private void initialData(){
        terrainComboBox.getItems().addAll(TerrainType.values());
        terrainComboBox.setValue(SimulationSingleton.INSTANCE.getCurrentTerrain());

        TerrainBackground.setBackgroundByTerrain(terrainComboBox.getValue(), backgroundPane);

        updateBattlePrepData();
    }

    /**
     * This method updates the battle preparation data. This implies displaying the necessary armies as table views and
     * making certain buttons accessible and others not.
     */
    private void updateBattlePrepData(){
        if(armyNameText1 != null && armyNameText2 != null) {
            displayArmys();
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

    /**
     * This method displays both armies through the use of {@link #displayArmy1()} and {@link #displayArmy2()}.
     */
    private void displayArmys(){
        displayArmy1();
        displayArmy2();
    }

    /**
     * This method checks if army1 is supposed to be displayed as a tableview. It does so through monitoring the
     * {@link SimulationSingleton} army1 to see if it is non-null. If so, then the buttons that are usually placed
     * where the tableview is, for example the buttons create army and load army, are made invisible. The table
     * is then filled with the units' information. Otherwise, those buttons and text fields are made visible.
     */
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
        armyInformation1.setVisible(displayInfo);
        changeArmyButton1.setVisible(displayInfo);
        removeArmyButton1.setVisible(displayInfo);
    }

    /**
     * This method checks if army2 is supposed to be displayed as a tableview. It does so through monitoring the
     * {@link SimulationSingleton} army2 to see if it is non-null. If so, then the buttons that are usually placed
     * where the tableview is, for example the buttons create army and load army, are made invisible. The table
     * is then filled with the units' information. Otherwise, those buttons and text fields are made visible.
     */
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
        armyInformation2.setVisible(displayInfo);
        changeArmyButton2.setVisible(displayInfo);
        removeArmyButton2.setVisible(displayInfo);
    }

    /**
     * This method checks whether the user has selected two armies or not. This is done through monitoring the two
     * armies saved in the {@link SimulationSingleton}.
     * @return  A boolean representing whether both armies are selected, {@code true}, or not, {@code false}.
     */
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