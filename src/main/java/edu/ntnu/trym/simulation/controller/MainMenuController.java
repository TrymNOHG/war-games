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
        SceneHandler.loadStartScene(stage);
    }

    /**
     * {@inheritDoc}
     * @throws Exception
     */
    @Override
    public void init() throws Exception{
        super.init();
    }

    @FXML
    void goToHelp(ActionEvent event) throws IOException {
        SceneHandler.loadHelpPage(event);
    }

    @FXML
    void openSettings(ActionEvent event) throws IOException {
        SceneHandler.loadSettings(event);
    }

    @FXML
    void startSimulator(ActionEvent event) throws IOException {
        SceneHandler.loadUnitInformation(event);
    }

    @FXML
    void exitApp(ActionEvent event) {
        System.exit(0);
    }

}
