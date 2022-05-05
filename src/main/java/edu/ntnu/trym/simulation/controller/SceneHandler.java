package edu.ntnu.trym.simulation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/**
 * This class concerns itself with the handling of scenes. As such, one if its methods can switch to a desired scene,
 * by taking the desired scene's location, as well as the event that triggered the switch, and creating a new scene.
 * Since it handles scenes, it also opens the starting scene.
 */
public class SceneHandler {
    private static Parent root;
    private static Scene scene;
    private static Stage stage;

    private static final int minimumHeight = 450;
    private static final int minimumWidth = 600;
    private static final Dimension maxWindowDimension = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * This method provides a way for the scene builder to create a new scene/open a new FXML file.
     * @param location     The directory location of the scene that will be switched to, represented as a String
     * @param actionEvent  The type of event that caused the new scene to switch
     * @throws IOException An Input-Output exception is thrown if the path to the directory does not exist
     */
    private static void loadScene(String location, ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(SceneHandler.class.getResource("/view/" + location + ".fxml")));
        scene = new Scene(root, maxWindowDimension.width, maxWindowDimension.height);
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        addStageProperties(stage, scene);
    }

    /**
     * This method opens the first scene by loading the MainMenu fxml file. Furthermore, it sets the desired properties
     * on the stage that is used.
     * @param stage         The stage for the application, represented as a Stage object
     * @throws IOException  This exception is thrown if the fxml file is invalid
     */
    public static void loadStartScene(Stage stage) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(SceneHandler.class.getResource("/view/MainMenu.fxml")));
        scene = new Scene(root, maxWindowDimension.width, maxWindowDimension.height);
//        stage.setFullScreenExitHint("Press ESC key or F to enter/exit fullscreen!");
//        stage.setFullScreenExitKeyCombination(KeyCombination.keyCombination("F"));
        addStageProperties(stage, scene);
//        stage.setFullScreenExitHint("");
    }

    /**
     * This method switches the current scene with the main menu scene {@link #loadScene(String, ActionEvent)}
     * @param event         The action that caused the switch, represented as an Event object
     * @throws IOException  This exception is thrown in case the path for FXML loading is invalid
     */
    public static void loadMainMenu(ActionEvent event) throws IOException {
        loadScene("MainMenu", event);
    }

    /**
     * This method switches the current scene with the help page scene {@link #loadScene(String, ActionEvent)}
     * @param event         The action that caused the switch, represented as an Event object
     * @throws IOException  This exception is thrown in case the path for FXML loading is invalid
     */
    public static void loadHelpPage(ActionEvent event) throws IOException {
        loadScene("HelpPage", event);
    }

    /**
     * This method switches the current scene with the settings scene {@link #loadScene(String, ActionEvent)}
     * @param event         The action that caused the switch, represented as an Event object
     * @throws IOException  This exception is thrown in case the path for FXML loading is invalid
     */
    public static void loadSettings(ActionEvent event) throws IOException {
        loadScene("Settings", event);
    }

    /**
     * This method switches the current scene with the unit information scene {@link #loadScene(String, ActionEvent)}
     * @param event         The action that caused the switch, represented as an Event object
     * @throws IOException  This exception is thrown in case the path for FXML loading is invalid
     */
    public static void loadUnitInformation(ActionEvent event) throws IOException {
        loadScene("UnitInfo", event);
    }

    /**
     * This method switches the current scene with the battle preparation scene {@link #loadScene(String, ActionEvent)}
     * @param event         The action that caused the switch, represented as an Event object
     * @throws IOException  This exception is thrown in case the path for FXML loading is invalid
     */
    public static void loadBattlePreparation(ActionEvent event) throws IOException {
        SceneHandler.loadScene("BattlePrep", event);
    }

    /**
     * This method adds the stage properties that every stage should have. This includes settings for fullscreen,
     * and resizability.
     * @param stage The window of the application, represented as a Stage object
     * @param scene The current page/scene of the application, represented as a Scene object
     */
    private static void addStageProperties(Stage stage, Scene scene){
        stage.setTitle("War Games");
        stage.setScene(scene);
        if(!stage.getStyle().equals(StageStyle.UNIFIED)) stage.initStyle(StageStyle.UNIFIED);
        stage.setMinHeight(minimumHeight);
        stage.setMinWidth(minimumWidth);
//        stage.setFullScreen(true);
        stage.setResizable(true);
        stage.show();
    }

}


/*
In order to have the black screen sweep effect use stage.initStyle(StageStyle.TRANSPARENT);
However, it also kinda breaks the smoothness of scene switches
 */

/*
For fullscreen use, scene = new Scene(root, maxWindowDimension.width, maxWindowDimension.height);
 */
