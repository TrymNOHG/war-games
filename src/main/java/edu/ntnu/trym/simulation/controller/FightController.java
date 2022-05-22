package edu.ntnu.trym.simulation.controller;

import edu.ntnu.trym.simulation.model.Army;
import edu.ntnu.trym.simulation.model.Battle;
import edu.ntnu.trym.simulation.model.armydisplay.ArmyDisplay;
import edu.ntnu.trym.simulation.model.units.UnitType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FightController implements Initializable {
    @FXML
    private Button skipButton;

    private Battle battle;

    private Army winnerArmy;

    @FXML
    private Pane iconArmy1;

    @FXML
    private Pane iconArmy2;

    @FXML
    private Pane resultsArmy1;

    @FXML
    private Pane resultsArmy2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialData();
        if(this.battle == null) initiateFight();
        else displayResults();

    }

    @FXML
    void skipFight(ActionEvent event) throws IOException {
        SceneHandler.loadResultScreen(event);
    }


    @FXML
    void backToBattlePrep(ActionEvent event) throws IOException {
        SimulationSingleton.INSTANCE.setBattle(null);
        SceneHandler.loadBattlePreparation(event);
    }

    private void initialData(){
        this.battle = SimulationSingleton.INSTANCE.getBattle();
        if(this.battle != null) this.winnerArmy = new Army(this.battle.getWinnerArmy());
    }

    private void initiateFight(){
        this.battle = new Battle(new Army(SimulationSingleton.INSTANCE.getArmy1()),
                new Army(SimulationSingleton.INSTANCE.getArmy2()),
                SimulationSingleton.INSTANCE.getCurrentTerrain());
        conductBattle();
    }

    private void conductBattle(){
        this.battle.simulate();
        SimulationSingleton.INSTANCE.setBattle(this.battle);
        this.skipButton.setDisable(false);
        this.skipButton.setId("main-load-button");
    }

    private void displayResults(){
        displayArmyResult(this.resultsArmy1, this.battle.getArmyOne());
        displayArmyResult(this.resultsArmy2, this.battle.getArmyTwo());

        displayResultIcons();
    }

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
