package edu.ntnu.trym.simulation.controller;

import edu.ntnu.trym.simulation.model.armydisplay.ArmyDisplay;
import edu.ntnu.trym.simulation.model.armydisplay.ArmyTable;
import edu.ntnu.trym.simulation.model.units.Unit;
import edu.ntnu.trym.simulation.model.units.UnitType;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * This class presents all the vital information of an army to the user through a simple javafx interface. The class
 * does this through creating a VBox filled with general unit information such as total amount and amounts of each type.
 * The scene connected to this controller also contains a table view, which this class fills with unit information.
 *
 * @author Trym Hamer Gudvangen
 */
public class ArmyInfoController {

    @FXML
    private Pane informationPane;

    @FXML
    private TableView<Unit> armyTable;

    /**
     * This method sets up all the information concerning the scene before loading it. This includes setting up the
     * VBox filled with unit information, using the {@link #setArmyInformation()} method, and setting up the army
     * table, using the {@link #setArmyTable()} method.
     */
    public void initialize(){
        setArmyInformation();
        setArmyTable();
    }

    /**
     * This method is called when the back to battle preparations button is pressed. As such, it will bring
     * the user back to the battle preparations scene.
     * @param event         The button being pressed, given as an Event object.
     * @throws IOException  This exception is thrown if the path to the next scene is invalid.
     */
    @FXML
    void backToBattlePrep(ActionEvent event) throws IOException {
        SceneHandler.loadBattlePreparation(event);
    }

    /**
     * This method sets up the army information using a VBox created by the {@link ArmyDisplay.Builder#build()} method.
     * It, thereafter, attaches this VBox to the pane that exists in the scene.
     */
    private void setArmyInformation(){
        VBox armyVBox = new ArmyDisplay.Builder(SimulationSingleton.INSTANCE.getArmyOfCurrentArmy())
                .addArmyName()
                .addAmountOfUnitInformation()
                .addUnitInformation(UnitType.INFANTRY)
                .addUnitInformation(UnitType.CAVALRY)
                .addUnitInformation(UnitType.RANGED)
                .addUnitInformation(UnitType.COMMANDER)
                .build();

        this.informationPane.getChildren().setAll(armyVBox);
    }

    /**
     * This method sets up the army's tableview. This is done through creating a tableview using the
     * {@link ArmyTable.Builder#build()} method and then filling the table with the selected army's information,
     * taken from the {@link SimulationSingleton}.
     */
    private void setArmyTable(){

        TableView<Unit> armyTableView = new ArmyTable.Builder()
                .addUnitColumn("Unit Type", "unitType")
                .addUnitColumn("Name", "name")
                .addUnitColumn("Health", "health")
                .addUnitColumn("Attack", "attack")
                .addUnitColumn("Armor", "armor")
                .build();

        armyTable.getColumns().addAll(armyTableView.getColumns());
        armyTable.setItems(FXCollections.observableList(SimulationSingleton.INSTANCE.getArmyOfCurrentArmy().getAllUnits()));
    }


}
