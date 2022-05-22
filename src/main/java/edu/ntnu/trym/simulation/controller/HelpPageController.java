package edu.ntnu.trym.simulation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * This class handles the help page display in the GUI. As such, it has methods for changing pages as well as setting
 * desired information for a page.
 *
 * @author Trym Hamer Gudvangen
 */
public class HelpPageController {
    private int currentPage;

    @FXML
    private Text helpPageHeader;

    @FXML
    private Text informationText;

    @FXML
    private ImageView leftArrow;

    @FXML
    private ImageView rightArrow;

    /**
     * This method sets up all the information concerning the scene before loading it. This includes setting up
     * listeners, loading variable data, and constructing the GUI aspects of the scene.
     */
    public void initialize() {
        currentPage = 1;
        displayCurrentPageInfo();
    }

    /**
     * This method is called when the user presses the back to main menu button. As the name implies, using
     * {@link SceneHandler#loadMainMenu(ActionEvent)}, the main menu scene is loaded.
     * @param event             The button being pressed, given as an Event object.
     * @throws IOException      This exception is thrown if the path to the main menu scene is invalid.
     */
    @FXML
    void goToMainMenu(ActionEvent event) throws IOException {
        SceneHandler.loadMainMenu(event);
    }

    /**
     * This method is called when the next page arrow (right arrow) is pressed. From there, it changes the page count
     * and checks whether the right arrow should still be usable. Finally, it sets the information needed for that page
     * using the {@link #displayCurrentPageInfo()} method.
     * @param event             The button being pressed, given as an Event object.
     */
    @FXML
    void nextPage(MouseEvent event) {
        currentPage++;
        leftArrow.setVisible(true);
        displayCurrentPageInfo();
        if(currentPage == 6){
            rightArrow.setVisible(false);
        }
    }

    /**
     * This method is called when the previous page arrow (left arrow) is pressed. From there, it changes the page count
     * and checks whether the left arrow should still be usable. Finally, it sets the information needed for that page
     * using the {@link #displayCurrentPageInfo()} method.
     * @param event             The button being pressed, given as an Event object.
     */
    @FXML
    void previousPage(MouseEvent event) {
        currentPage--;
        rightArrow.setVisible(true);
        displayCurrentPageInfo();
        if(currentPage == 1){
            leftArrow.setVisible(false);
        }
    }

    /**
     * This method checks the page number and accordingly sets the information.
     */
    private void displayCurrentPageInfo(){
        switch (this.currentPage){
            case 1 -> displayGettingStartedInfo();
            case 2 -> displayUnitInfo();
            case 3 -> displayTerrainInfo();
            case 4 -> displayArmyCreationInfo();
            case 5 -> displayLoadArmyInfo();
            case 6 -> displaySimulationInfo();

            default -> throw new IllegalStateException("Unexpected value: " + this.currentPage);
        }
    }

    /**
     * This method displays the getting started information by changing the information text.
     */
    private void displayGettingStartedInfo(){
        helpPageHeader.setText("Getting started");
        informationText.setText("This application attempts to mimic the battle between two armies. In order to " +
                "attain as realistic of a simulation as possible, various units were created with unique abilities " +
                "when it comes to attacking and defending. Furthermore, terrains were implemented in order to change the" +
                "balance of fights according to where units have an advantage. In order to get started, it is wise to " +
                "through the information concerning the units, terrain, as well as the mechanics of the game. Good " +
                "luck!");
    }

    /**
     * This method displays the unit information by changing the information text.
     */
    private void displayUnitInfo(){
        helpPageHeader.setText("Unit Information");
        informationText.setText("""
                The Infantry Unit has a default attack of 15 and armor of 10.\s
                Infantry units are especially strong in the FOREST terrain with a significant attack and resistance bonus.

                The Ranged Unit has a default attack of 15 and armor of 8.\s
                Since Ranged units initially attack from a distance, they have a high bonus resistance during the first 2 attacks.
                Ranged units have an advantage in the HILL terrain, due to its openness. However, the opposite can be\040
                said for the FOREST terrain, where the Ranged unit is at a disadvantage.
                
                The Cavalry Unit has a default attack of 20 and armor of 12.\s
                However, it also specializes in charge attacks. Therefore, its default, initial attack bonus is 6;\040
                however, as the fighting continues, the default CavalryUnit's attack bonus drops to 2.
                When the terrain is PLAINS, a 10 point bonus is added to the attack bonus, regardless of when.
                
                The Commander Unit has a default attack of 25 and armor of 15.\s
                It may be thought of as a powered-up Cavalry unit and therefore benefits from the same strengths.
                However, it also specializes in charge attacks. Therefore, the first attack does more damage""");
    }

    /**
     * This method displays the terrain information by changing the information text.
     */
    private void displayTerrainInfo(){
        helpPageHeader.setText("Terrain Information");
        informationText.setText("Currently, there are only 4 terrains: Default, plains, hills, and forest. As seen " +
                "on the previous help page, each unit has a special advantage when it comes to terrain. The terrain " +
                "can be selected in the battle preparation scene on the left side.");
    }

    /**
     * This method displays the army creation information by changing the information text.
     */
    private void displayArmyCreationInfo(){
        helpPageHeader.setText("Creating an Army");
        informationText.setText("When you enter the battle preparation stage of the simulation, you will have the" +
                "opportunity to create an army. Depending on if you press the top army's or bottom army's create " +
                "army button, you will be taken to a construction page. There you will have many options on how to " +
                "customize your army. So, go wild!");
    }

    /**
     * This method displays the loading army information by changing the information text.
     */
    private void displayLoadArmyInfo(){
        helpPageHeader.setText("Loading an Army");
        informationText.setText("Again, in the battle preparation stage, you will have the option to load an army. " +
                "This may include armies you have created before if you saved them in the construction stage. Or " +
                "you may want to use a pre-saved one. Once you have loaded an army, you will get the additional " +
                "functionality of changing that armies units. In order to do this, press the change army button!");
    }

    /**
     * This method displays the simulation information by changing the information text.
     */
    private void displaySimulationInfo(){
        helpPageHeader.setText("Simulating");
        informationText.setText("Once you have chosen two worthy armies, you are ready to begin the actual fight part " +
                "of the simulation. As the name implies, the fight button will initiate the fight. From there, " +
                "all you have to do is sit back and enjoy the simulation. At the end, you will presented with the " +
                "results of the battle and an option to continue with more simulating!");
    }
}
