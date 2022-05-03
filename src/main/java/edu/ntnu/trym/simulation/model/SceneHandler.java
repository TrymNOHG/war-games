package edu.ntnu.trym.simulation.model;

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
    public static final Dimension maxWindowDimension = Toolkit.getDefaultToolkit().getScreenSize();
    /**
     * This method provides a way for the scene builder to create a new scene/open a new FXML file.
     * @param location     The directory location of the scene that will be switched to, represented as a String
     * @param actionEvent  The type of event that caused the new scene to switch
     * @throws IOException An Input-Output exception is thrown if the path to the directory does not exist
     */
    public static void switchScene(String location, ActionEvent actionEvent) throws IOException {
        Parent viewPage = FXMLLoader.load(Objects.requireNonNull(SceneHandler.class.getResource("/view/" + location + ".fxml")));
        Scene page = new Scene(viewPage, maxWindowDimension.width, maxWindowDimension.height);
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.setTitle("War Games");
        window.setScene(page);
        window.setFullScreen(true);
        window.setFullScreenExitKeyCombination(KeyCombination.keyCombination("F"));
        window.setResizable(false);
        window.show();
    }

    /**
     * This method opens the first scene by loading the MainMenu fxml file. Furthermore, it sets the desired properties
     * on the stage that is used.
     * @param stage         The stage for the application, represented as a Stage object
     * @throws IOException  This exception is thrown if the fxml file is invalid
     */
    public static void openStartScene(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneHandler.class.getResource("/view/MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), maxWindowDimension.width, maxWindowDimension.height);
        stage.setTitle("War Games");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("Press ESC key or F to enter/exit fullscreen!");
        stage.setFullScreenExitKeyCombination(KeyCombination.keyCombination("F"));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.show();
    }

}
