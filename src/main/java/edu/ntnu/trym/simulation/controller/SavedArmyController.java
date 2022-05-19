package edu.ntnu.trym.simulation.controller;

import edu.ntnu.trym.simulation.model.AlertDialog;
import edu.ntnu.trym.simulation.model.Army;
import edu.ntnu.trym.simulation.model.ArmyFileDisplay;
import edu.ntnu.trym.simulation.model.filehandling.ArmyFileHandler;
import edu.ntnu.trym.simulation.model.filehandling.FileHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
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
    private ScrollPane saveSlot1;

    @FXML
    private ScrollPane saveSlot2;

    @FXML
    private ScrollPane saveSlot3;

    @FXML
    private ScrollPane saveSlot4;

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
        try {
            initialData();
            addListeners();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void initialData() throws IOException {
        currentPage = 1;
        totalPages = numberOfPagesNeeded();
        setSaveSlotProperties();
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
        clearSlotInfo();
        updateButtonVisibility();
        updateArmyOnPage();
    }

    private void updateButtonVisibility(){
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

                    setArmyBasedOnPage(counter.get(), file);

                    counter.getAndIncrement();
                }
                catch (IOException | InstantiationException e) {
                    throw new RuntimeException(e);
                }
            });
        fileWalk.close();
    }

    private void setArmyBasedOnPage(int counter, File file) throws IOException, InstantiationException {
        Army currentArmy = armyFileHandler.readFromArmyFile(file);

        if(counter % 4 == 0){
            army1 = currentArmy;
            saveSlot1.setContent(new ArmyFileDisplay(file).getArmyDisplay());
            saveSlot1.setId("save-slot");
        }
        else if(counter % 4 == 1){
            army2 = currentArmy;
            saveSlot2.setContent(new ArmyFileDisplay(file).getArmyDisplay());
            saveSlot2.setId("save-slot");

        }
        else if(counter % 4 == 2){
            army3 = currentArmy;
            saveSlot3.setContent(new ArmyFileDisplay(file).getArmyDisplay());
            saveSlot3.setId("save-slot");

        }
        else if(counter % 4 == 3){
            army4 = currentArmy;
            saveSlot4.setContent(new ArmyFileDisplay(file).getArmyDisplay());
            saveSlot4.setId("save-slot");

        }
    }

    private void clearSlotInfo(){
        saveSlot1.setContent(null);
        saveSlot1.setId(null);
        army1 = null;


        saveSlot2.setContent(null);
        saveSlot2.setId(null);
        army2 = null;


        saveSlot3.setContent(null);
        saveSlot3.setId(null);
        army3 = null;


        saveSlot4.setContent(null);
        saveSlot4.setId(null);
        army4 = null;
    }


    private void setSaveSlotProperties(){
        saveSlot1.fitToHeightProperty().set(true);
        saveSlot1.fitToWidthProperty().set(true);

        saveSlot2.fitToHeightProperty().set(true);
        saveSlot2.fitToWidthProperty().set(true);

        saveSlot3.fitToHeightProperty().set(true);
        saveSlot3.fitToWidthProperty().set(true);

        saveSlot4.fitToHeightProperty().set(true);
        saveSlot4.fitToWidthProperty().set(true);
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
    void loadArmy(ActionEvent event) throws IOException {
        if(selectedArmy == null) {
            AlertDialog.showError("No Army is currently selected.");
        }
        else{
            SimulationSingleton.INSTANCE.setArmyOfCurrentArmy(selectedArmy);
            SceneHandler.loadBattlePreparation(event);
        }
    }



}


//Things that need to be done:
/*
       Make the display of army information a lot better
       Allow for the selection of an army
       Create the actual loading of the army

 */