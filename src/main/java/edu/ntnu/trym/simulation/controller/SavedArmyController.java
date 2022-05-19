package edu.ntnu.trym.simulation.controller;

import edu.ntnu.trym.simulation.model.Army;
import edu.ntnu.trym.simulation.model.ArmyFileDisplay;
import edu.ntnu.trym.simulation.model.filehandling.ArmyFileHandler;
import edu.ntnu.trym.simulation.model.filehandling.FileHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class SavedArmyController implements Initializable {

    @FXML
    private Text nextPageButton;

    @FXML
    private Text previousPageButton;

    @FXML
    private BorderPane saveSlot1;

    @FXML
    private BorderPane saveSlot2;

    @FXML
    private BorderPane saveSlot3;

    @FXML
    private BorderPane saveSlot4;

    private int currentPage;

    private int totalPages;

    private Army army1;
    private Army army2;
    private Army army3;
    private Army army4;

    private Army selectedArmy;

    private final ArmyFileHandler armyFileHandler = new ArmyFileHandler();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentPage = 1;
        try {
            totalPages = numberOfPagesNeeded();
            addListeners();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void addListeners() throws IOException {
        updatePage();
        this.nextPageButton.setOnMouseClicked(event -> {
            currentPage++;
            try {
                updatePage();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
        this.previousPageButton.setOnMouseClicked(event -> {
            currentPage--;
            try {
                updatePage();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        addListenerToArmySlots();
    }

    private void addListenerToArmySlots(){
        saveSlot1.setOnMouseClicked(mouseEvent -> {
            //Do stuff when the slot is pressed such as make the region blue
            selectedArmy = army1;
        });
        saveSlot2.setOnMouseClicked(mouseEvent -> {
            //Do stuff when the slot is pressed
            selectedArmy = army2;
        });
        saveSlot3.setOnMouseClicked(mouseEvent -> {
            //Do stuff when the slot is pressed
            selectedArmy = army3;
        });
        saveSlot4.setOnMouseClicked(mouseEvent -> {
            //Do stuff when the slot is pressed
            selectedArmy = army4;
        });

    }

    private void updatePage() throws IOException {
       updateButtonVisibility();
       updateArmyOnPage();
    }

    private void updateButtonVisibility(){
        System.out.println(currentPage);
        System.out.println(totalPages);
        this.previousPageButton.setVisible(currentPage != 1);
        this.nextPageButton.setVisible(currentPage != totalPages);

    }

    //TODO: This needs to be refactored and made more concise
    private void updateArmyOnPage() throws IOException {
        AtomicInteger counter = new AtomicInteger();
        Stream<Path> fileWalk = FileHandler.getFilesInDirectory();
            fileWalk.skip((currentPage-1) * 4L + 1).forEach(path -> {
                if(counter.get() == 4) return;

                try{
                    File file = new File(String.valueOf(path));
                    Army currentArmy = armyFileHandler.readFromArmyFile(file);

                    if(counter.get() % 4 == 0){
                        army1 = currentArmy;
                        saveSlot1.setCenter(new ArmyFileDisplay(file).getArmyDisplay());
                    }
                    else if(counter.get() % 4 == 1){
                        army2 = currentArmy;
                        saveSlot2.setCenter(new ArmyFileDisplay(file).getArmyDisplay());
                    }
                    else if(counter.get() % 4 == 2){
                        army3 = currentArmy;
                        saveSlot3.setCenter(new ArmyFileDisplay(file).getArmyDisplay());
                    }
                    else if(counter.get() % 4 == 3){
                        army4 = currentArmy;
                        saveSlot4.setCenter(new ArmyFileDisplay(file).getArmyDisplay());
                    }

                    counter.getAndIncrement();
                }
                catch (IOException | InstantiationException e) {
                    throw new RuntimeException(e);
                }
            });
        fileWalk.close();
    }

    private int numberOfPagesNeeded() throws IOException {
        return (int) Math.ceil(FileHandler.getNumberOfCSVFiles()/4.0);
    }

    /*
    Add listeners to the previous and next page buttons which update the pages, as well as changes the page number. This
    can also be done for the help page!!!
     */


    @FXML
    void backToBattlePrep(ActionEvent event) throws IOException {
        SceneHandler.loadBattlePreparation(event);
    }

    @FXML
    void loadArmy(ActionEvent event) {

    }



}
