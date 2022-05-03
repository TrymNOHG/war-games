package edu.ntnu.trym.simulation.model;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class SwitchScene {

    /**
     * This method provides a way for the scene builder to create a new scene/open a new FXML file.
     * @param location     The directory location of the scene that will be switched to, represented as a String
     * @param actionEvent  The type of event that caused the new scene to switch
     * @throws IOException An Input-Output exception is thrown if the path to the directory does not exist
     */
    public static void switchScene(String location, ActionEvent actionEvent) throws IOException {
        Parent viewPage = FXMLLoader.load(Objects.requireNonNull(SwitchScene.class.getResource("/view/" + location + ".fxml")));
        Scene page = new Scene(viewPage);
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(page);
        window.show();
    }
}
