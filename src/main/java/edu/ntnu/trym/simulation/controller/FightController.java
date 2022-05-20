package edu.ntnu.trym.simulation.controller;

import edu.ntnu.trym.simulation.model.Army;
import edu.ntnu.trym.simulation.model.Battle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class FightController implements Initializable {
    @FXML
    private Button skipButton;

    private Battle battle;

    private Army winnerArmy;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialData();
        conductBattle();
    }

    @FXML
    void skipFight(ActionEvent event) {
        //This goes to the result screen
    }

    private void initialData(){
        this.battle = new Battle(SimulationSingleton.INSTANCE.getArmy1(), SimulationSingleton.INSTANCE.getArmy2(),
                SimulationSingleton.INSTANCE.getCurrentTerrain());
    }

    private void conductBattle(){
        this.winnerArmy = this.battle.simulate();
        this.skipButton.setDisable(false);
        this.skipButton.setId("main-load-button");
    }

}
