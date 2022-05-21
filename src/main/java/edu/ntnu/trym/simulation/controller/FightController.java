package edu.ntnu.trym.simulation.controller;

import edu.ntnu.trym.simulation.model.Army;
import edu.ntnu.trym.simulation.model.Battle;
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
    private ImageView iconArmy1;

    @FXML
    private ImageView iconArmy2;

    @FXML
    private Pane resultsArmy1;

    @FXML
    private Pane resultsArmy2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialData();
        if(this.battle == null) initiateFight();

    }

    @FXML
    void skipFight(ActionEvent event) throws IOException {
        SceneHandler.loadResultScreen(event);
    }


    @FXML
    void backToBattlePrep(ActionEvent event) throws IOException {
        SceneHandler.loadBattlePreparation(event);
    }

    private void initialData(){
        this.battle = SimulationSingleton.INSTANCE.getBattle();
    }

    private void initiateFight(){
        this.battle = new Battle(SimulationSingleton.INSTANCE.getArmy1(), SimulationSingleton.INSTANCE.getArmy2(),
                SimulationSingleton.INSTANCE.getCurrentTerrain());
        conductBattle();
    }

    private void conductBattle(){
        this.winnerArmy = this.battle.simulate();
        SimulationSingleton.INSTANCE.setBattle(this.battle);
        this.skipButton.setDisable(false);
        this.skipButton.setId("main-load-button");
    }

}
