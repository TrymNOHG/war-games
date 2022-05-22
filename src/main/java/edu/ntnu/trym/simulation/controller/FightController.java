package edu.ntnu.trym.simulation.controller;

import edu.ntnu.trym.simulation.model.Army;
import edu.ntnu.trym.simulation.model.TerrainType;
import edu.ntnu.trym.simulation.model.armydisplay.ArmyTable;
import edu.ntnu.trym.simulation.model.battle.Battle;
import edu.ntnu.trym.simulation.model.armydisplay.ArmyDisplay;
import edu.ntnu.trym.simulation.model.units.Unit;
import edu.ntnu.trym.simulation.model.units.UnitType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;

//TODO: add battlelog information to javadocs

/**
 * This class handles the interactions between the backend, the Fight scene, and the Results scene. As such, it runs
 * the simulation with the armies selected. Additionally, the user gets to set the terrain of the battle and when they are ready, the battle can be
 * initiated. Furthermore, the armies selected are displayed on the scene through this class.
 *
 * @author Trym Hamer Gudvangen
 */
public class FightController {
    @FXML
    private Button skipButton;

    @FXML
    private Button startButton;

    @FXML
    private Pane iconArmy1;

    @FXML
    private Pane iconArmy2;

    @FXML
    private Pane resultsArmy1;

    @FXML
    private Pane resultsArmy2;

    @FXML
    private TableView<Unit> armyTable1;

    @FXML
    private TableView<Unit> armyTable2;

    @FXML
    private Text informationText;

    private Battle battle;

    private Army winnerArmy;

    private Army army1;

    private Army army2;

    /**
     * This method sets up all the information concerning the scene before loading it. This includes setting up
     * listeners, loading variable data, and constructing the GUI aspects of the scene.
     */
    public void initialize() {
        initialData();
        if(this.battle == null) setUpArmyTables();
        else displayResults();

    }

    @FXML
    void startBattle(ActionEvent event) {
        this.startButton.setVisible(false);
        initiateFight();
    }

    /**
     * This method is called when the user presses the skip button. Using
     * {@link SceneHandler#loadResultScreen(ActionEvent)}, the results scene is loaded.
     * @param event             The button being pressed, given as an Event object.
     * @throws IOException      This exception is thrown if the path to the result scene is invalid.
     */
    @FXML
    void skipFight(ActionEvent event) throws IOException {
        SceneHandler.loadResultScreen(event);
    }

    /**
     * This method is called when the user presses the back to battle preparations button. As the name implies, using
     * {@link SceneHandler#loadBattlePreparation(ActionEvent)}, the battle preparations scene is loaded.
     * @param event             The button being pressed, given as an Event object.
     * @throws IOException      This exception is thrown if the path to the battle preparation scene is invalid.
     */
    @FXML
    void backToBattlePrep(ActionEvent event) throws IOException {
        SimulationSingleton.INSTANCE.setBattle(null);
        SceneHandler.loadBattlePreparation(event);
    }

    /**
     * This method is used to set all the initial information necessary throughout the class. It does so through
     * retrieving army information from {@link SimulationSingleton}. Furthermore, it edits the GUI to contain the
     * information that was retrieved such as the results of the simulation.
     */
    private void initialData(){
        army1 = new Army(SimulationSingleton.INSTANCE.getArmy1());
        army2 = new Army(SimulationSingleton.INSTANCE.getArmy1());
        this.battle = SimulationSingleton.INSTANCE.getBattle();
        if(this.battle != null) this.winnerArmy = new Army(this.battle.getWinnerArmy());
    }

    /**
     * This method sets up both army tables through the use of {@link #setUpArmyTable(TableView, Army)}.
     */
    private void setUpArmyTables(){
        setUpArmyTable(armyTable1, army1);
        setUpArmyTable(armyTable2, army2);
    }

    /**
     * This method sets up the army table and observable list of the units, allowing the tracking of the units as the
     * simulation is running. The table was constructed using {@link ArmyTable#ArmyTable(ArmyTable.Builder)}.
     * @param armyTable     The table to be set up, represented using a TableView{@code <Unit>} object.
     * @param army          The army to be placed in the table, represented using an Army object.
     */
    private void setUpArmyTable(TableView<Unit>  armyTable, Army army){
        TableView<Unit> armyTableView = new ArmyTable.Builder()
                .addUnitColumn("Unit Type", "unitType")
                .addUnitColumn("Name", "name")
                .addUnitColumn("Health", "health")
                .addUnitColumn("Attack", "attack")
                .addUnitColumn("Armor", "armor")
                .build();

        armyTable.getColumns().addAll(armyTableView.getColumns());
        armyTable.setItems((ObservableList<Unit>) army.getAllUnits());
    }

    /**
     * This method initiates the battle between the two selected armies by making a new battle object
     * {@link Battle#Battle(Army, Army, TerrainType)} and {@link #conductBattle()}.
     */
    private void initiateFight(){
        this.battle = new Battle(army1, army2, SimulationSingleton.INSTANCE.getCurrentTerrain());
        conductBattle();
    }

    /**
     * This method simulates the actual battle using the {@link Battle#simulate()} method. It, also, sets the
     * accessibility of the skip button.
     */
    private void conductBattle(){
        this.battle.simulate();
        SimulationSingleton.INSTANCE.setBattle(this.battle);
        this.skipButton.setDisable(false);
        this.skipButton.setId("main-load-button");
    }

    /**
     * This method displays the results of army1 and army2 using {@link #displayArmyResult(Pane, Army)}. It also sets
     * the icons (trophy and white flag) accordingly using {@link #displayResultIcons()}.
     */
    private void displayResults(){
        displayArmyResult(this.resultsArmy1, this.battle.getArmyOne());
        displayArmyResult(this.resultsArmy2, this.battle.getArmyTwo());

        displayResultIcons();
    }

    /**
     * This method creates a VBox of the information of the given army using the army display builder,
     * {@link ArmyDisplay.Builder#build()}.
     * @param armyPane  The pane where the vbox will be placed in the scene, represented using a Pane object.
     * @param army      The army whose results are being placed in the vbox, represented using an Army object.
     */
    private void displayArmyResult(Pane armyPane, Army army){
        armyPane.getChildren().addAll(new ArmyDisplay.Builder(army)
                .addArmyName()
                .addUnitInformation(UnitType.INFANTRY)
                .addUnitInformation(UnitType.RANGED)
                .addUnitInformation(UnitType.CAVALRY)
                .addUnitInformation(UnitType.COMMANDER)
                .build());
        armyPane.setStyle("-fx-alignment: center");
    }

    /**
     * This method finds out which army won and accordingly places a trophy icon over them. The loser gets a white
     * flag icon over their results.
     */
    private void displayResultIcons(){
        this.iconArmy1.autosize();
        this.iconArmy2.autosize();

        if(this.battle.getArmyOne().equals(this.winnerArmy)) {
            this.iconArmy1.setId("trophy-icon");
            this.iconArmy2.setId("white-flag-icon");
        }
        else{
            this.iconArmy2.setId("trophy-icon");
            this.iconArmy1.setId("white-flag-icon");
        }
    }

}
