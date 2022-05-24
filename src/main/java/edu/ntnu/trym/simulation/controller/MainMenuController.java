package edu.ntnu.trym.simulation.controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class acts as the hub for all the other functionalities in the application. As such, when the user starts the
 * application, they are immediately greeted with the scene that accompanies this controller (the main menu scene). On
 * the main menu page, there are many buttons which are connected to the other scenes using this class.
 *
 * @author Trym Hamer Gudvangen
 */
public class MainMenuController extends Application {

    /**
     * This method starts the program.
     * @param args  The arguments that are used to launch the application.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * {@inheritDoc}
     * This method starts the actual javafx portion of the application.
     * @param stage         The stage generated from starting the application with javafx.
     * @throws Exception    This exception is thrown if any errors occur
     */
    @Override
    public void start(Stage stage) throws Exception {
        SceneHandler.loadStartScene(stage);
    }

    /**
     * {@inheritDoc}
     * This method dictates what the program should do before the scene starts.
     * @throws Exception    This exception is thrown if any errors occur
     */
    @Override
    public void init() throws Exception{
        super.init();
    }

    /**
     * This method is called when the user presses the help button. As the name implies, using
     * {@link SceneHandler#loadHelpPage(ActionEvent)}, the help page is loaded.
     * @param event             The button being pressed, given as an Event object.
     * @throws IOException      This exception is thrown if the path to the help page scene is invalid.
     */
    @FXML
    void goToHelp(ActionEvent event) throws IOException {
        SceneHandler.loadHelpPage(event);
    }

    /**
     * This method is called upon when the user presses the settings button.
     * @param event             The button being pressed, given as an Event object.
     * @throws IOException      This exception is thrown if the path to the load settings scene is invalid.
     * @deprecated              Since the settings weren't completed, this method is deprecated and cannot be accessed.
     */
    @Deprecated
    @FXML
    void openSettings(ActionEvent event) throws IOException {
        SceneHandler.loadSettings(event);
    }

    /**
     * This method is called when the user presses the help button. Using
     * {@link SceneHandler#loadUnitInformation(ActionEvent)}, the unit information scene is loaded.
     * @param event             The button being pressed, given as an Event object.
     * @throws IOException      This exception is thrown if the path to the unit information scene is invalid.
     */
    @FXML
    void startSimulator(ActionEvent event) throws IOException {
        SceneHandler.loadUnitInformation(event);
    }

    /**
     * This method is called when the exit button is pressed. As the name implies, the application stops running once
     * this method has been called.
     * @param event             The button being pressed, given as an Event object.
     */
    @FXML
    void exitApp(ActionEvent event) {
        System.exit(0);
    }

}
