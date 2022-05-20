package edu.ntnu.trym.simulation.controller;

import edu.ntnu.trym.simulation.model.Army;
import edu.ntnu.trym.simulation.model.Battle;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class FightController implements Initializable {
    private Battle battle;

    private Army winnerArmy;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialData();
        conductBattle();
    }

    private void initialData(){
        this.battle = new Battle(SimulationSingleton.INSTANCE.getArmy1(), SimulationSingleton.INSTANCE.getArmy2(),
                SimulationSingleton.INSTANCE.getCurrentTerrain());
    }

    private void conductBattle(){
        this.winnerArmy = this.battle.simulate();

    }
}
