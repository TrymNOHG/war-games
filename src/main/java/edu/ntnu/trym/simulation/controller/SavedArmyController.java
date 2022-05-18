package edu.ntnu.trym.simulation.controller;

import edu.ntnu.trym.simulation.model.ArmyFileDisplay;
import edu.ntnu.trym.simulation.model.filehandling.ArmyFileHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SavedArmyController implements Initializable {

    @FXML
    private BorderPane saveSlot1;

    @FXML
    private BorderPane saveSlot2;

    @FXML
    private BorderPane saveSlot3;

    @FXML
    private BorderPane saveSlot4;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Hello");
        VBox vBox;
        try {
            vBox = new ArmyFileDisplay
                    (new File(new ArmyFileHandler().getFileSourcePath("Test"))).getArmyDisplay();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        saveSlot4.setCenter(vBox);
//        saveSlot1.setCenter(new Text("Hello"));

    }

    @FXML
    void backToBattlePrep(ActionEvent event) throws IOException {
        SceneHandler.loadBattlePreparation(event);
    }

    @FXML
    void loadArmy(ActionEvent event) {

    }



}
