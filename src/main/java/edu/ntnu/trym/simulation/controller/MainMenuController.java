package edu.ntnu.trym.simulation.controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;



public class MainMenuController extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        SceneHandler.openStartScene(stage);
    }

    @FXML
    void goToHelp(ActionEvent event) throws IOException {
        SceneHandler.switchScene("HelpPage", event);
    }

    @FXML
    void openSettings(ActionEvent event) throws IOException {
        SceneHandler.switchScene("Settings", event);
    }

    @FXML
    void startSimulator(ActionEvent event) {

    }

    @FXML
    void exitApp(ActionEvent event) {
        System.exit(0);
    }

}
