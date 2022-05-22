package edu.ntnu.trym.simulation.controller;

import edu.ntnu.trym.simulation.model.armydisplay.ArmyDisplay;
import edu.ntnu.trym.simulation.model.units.Unit;
import edu.ntnu.trym.simulation.model.units.UnitType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ArmyInfoController {

    @FXML
    private Pane informationPane;

    @FXML
    private TableView<Unit> armyTable;

    public void initialize(){
        setArmyInformation();
        setArmyTable();
    }

    @FXML
    void backToBattlePrep(ActionEvent event) throws IOException {
        SceneHandler.loadBattlePreparation(event);
    }

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

    private void setArmyTable(){

    }


}
