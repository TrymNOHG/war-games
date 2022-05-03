package edu.ntnu.trym.simulation.controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MainMenuController extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 3000, 3000);
        stage.setTitle("War Games");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("Press ESC key or F to enter/exit fullscreen!");
        stage.setFullScreenExitKeyCombination(KeyCombination.keyCombination("F"));
        stage.initStyle(StageStyle.TRANSPARENT);
        //No non-full screen, only exit
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void goToHelp(ActionEvent event) {

    }

    @FXML
    void openSettings(ActionEvent event) {

    }

    @FXML
    void startSimulator(ActionEvent event) {
    }

    @FXML
    void exitApp(ActionEvent event) {
        System.exit(0);
    }

}
