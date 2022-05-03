package edu.ntnu.trym.simulation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelpPageController implements Initializable {
    private int currentPage;

    @FXML
    private Text helpPageHeader;

    @FXML
    private Text informationText;

    @FXML
    private ImageView leftArrow;

    @FXML
    private ImageView rightArrow;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentPage = 1;

    }

    @FXML
    void goToMainMenu(ActionEvent event) throws IOException {
        SceneHandler.switchScene("MainMenu", event);
    }

    @FXML
    void nextPage(MouseEvent event) {
        currentPage++;
        leftArrow.setVisible(true);
        displayCurrentPageInfo();
        if(currentPage == 6){
            rightArrow.setVisible(false);
        }
    }

    @FXML
    void previousPage(MouseEvent event) {
        currentPage--;
        rightArrow.setVisible(true);
        displayCurrentPageInfo();
        if(currentPage == 1){
            leftArrow.setVisible(false);
        }
    }

    private void displayCurrentPageInfo(){
        switch (this.currentPage){
            case 1 -> displayFirstPageInfo();
            case 2 -> displaySecondPageInfo();
            case 3 -> displayThirdPageInfo();
            case 4 -> displayFourthPageInfo();
            case 5 -> displayFifthPageInfo();
            case 6 -> displaySixthPageInfo();

            default -> throw new IllegalStateException("Unexpected value: " + this.currentPage);
        }
    }

    private void displayFirstPageInfo(){
        helpPageHeader.setText("Getting started");
        informationText.setText("This application attempts to mimic  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwwwThis " +
                "application attempts to mimic  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwwwThis application attempts to mimic" +
                "  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwwwThis application attempts to mimic  ererrrrrrrrrrrrrrr. " +
                "wwwwwwwwwwwwwwwwThis application attempts to mimic  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwwwThis " +
                "application attempts to mimic  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwww");
    }

    private void displaySecondPageInfo(){
        helpPageHeader.setText("Unit Information");
        informationText.setText("This application attempts to mimic  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwwwThis " +
                "application attempts to mimic  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwwwThis application attempts to mimic" +
                "  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwwwThis application attempts to mimic  ererrrrrrrrrrrrrrr. " +
                "wwwwwwwwwwwwwwwwThis application attempts to mimic  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwwwThis " +
                "application attempts to mimic  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwww");
    }

    private void displayThirdPageInfo(){
        helpPageHeader.setText("Terrain Information");
        informationText.setText("This application attempts to mimic  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwwwThis " +
                "application attempts to mimic  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwwwThis application attempts to mimic" +
                "  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwwwThis application attempts to mimic  ererrrrrrrrrrrrrrr. " +
                "wwwwwwwwwwwwwwwwThis application attempts to mimic  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwwwThis " +
                "application attempts to mimic  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwww");
    }

    private void displayFourthPageInfo(){
        helpPageHeader.setText("Creating an Army");
        informationText.setText("This application attempts to mimic  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwwwThis " +
                "application attempts to mimic  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwwwThis application attempts to mimic" +
                "  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwwwThis application attempts to mimic  ererrrrrrrrrrrrrrr. " +
                "wwwwwwwwwwwwwwwwThis application attempts to mimic  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwwwThis " +
                "application attempts to mimic  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwww");
    }
    
    private void displayFifthPageInfo(){
        helpPageHeader.setText("Loading an Army");
        informationText.setText("This application attempts to mimic  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwwwThis " +
                "application attempts to mimic  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwwwThis application attempts to mimic" +
                "  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwwwThis application attempts to mimic  ererrrrrrrrrrrrrrr. " +
                "wwwwwwwwwwwwwwwwThis application attempts to mimic  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwwwThis " +
                "application attempts to mimic  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwww");
    }

    private void displaySixthPageInfo(){
        helpPageHeader.setText("Simulating");
        informationText.setText("This application attempts to mimic  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwwwThis " +
                "application attempts to mimic  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwwwThis application attempts to mimic" +
                "  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwwwThis application attempts to mimic  ererrrrrrrrrrrrrrr. " +
                "wwwwwwwwwwwwwwwwThis application attempts to mimic  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwwwThis " +
                "application attempts to mimic  ererrrrrrrrrrrrrrr. wwwwwwwwwwwwwwww");
    }
}

//TODO: change the name of the display method to what they display (such as the header)
