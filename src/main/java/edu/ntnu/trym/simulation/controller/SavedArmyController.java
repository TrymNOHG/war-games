package edu.ntnu.trym.simulation.controller;

import edu.ntnu.trym.simulation.model.AlertDialog;
import edu.ntnu.trym.simulation.model.Army;
import edu.ntnu.trym.simulation.model.armydisplay.ArmyDisplay;
import edu.ntnu.trym.simulation.model.filehandling.ArmyFileHandler;
import edu.ntnu.trym.simulation.model.filehandling.FileHandler;
import edu.ntnu.trym.simulation.model.units.UnitType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class SavedArmyController {

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

    @FXML
    private Button loadArmyButton;

    private int currentPage;

    private int totalPages;

    private Army army1;
    private Army army2;
    private Army army3;
    private Army army4;

    private Army selectedArmy;

    private final ArmyFileHandler armyFileHandler = new ArmyFileHandler();


    public void initialize() {
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

    //TODO: refactor this method since it is weirdly structured.
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

    //TODO: maybe refactor this so that one method sets all listeners and one method sets on listener.
    private void addListenerToArmySlots(){
        saveSlot1.setOnMouseClicked(mouseEvent -> {
            //Do stuff when the slot is pressed such as make the region blue
            selectedArmy = army1;
            loadArmyButton.setId("main-load-button");
            //TODO: Check if adding a listener to a slot being selected is possible. So if a slot isn't selected, the
            //setId is just main-button
        });
        saveSlot2.setOnMouseClicked(mouseEvent -> {
            //Do stuff when the slot is pressed
            selectedArmy = army2;
            loadArmyButton.setId("main-load-button");
        });
        saveSlot3.setOnMouseClicked(mouseEvent -> {
            //Do stuff when the slot is pressed
            selectedArmy = army3;
            loadArmyButton.setId("main-load-button");
        });
        saveSlot4.setOnMouseClicked(mouseEvent -> {
            //Do stuff when the slot is pressed
            selectedArmy = army4;
            loadArmyButton.setId("main-load-button");

        });
    }

    private void updatePage() throws IOException {
        clearAllSlotsInfo();
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

                File file = null;
                try{
                    file = new File(String.valueOf(path));

                    setArmyBasedOnPage(counter.get(), file);

                    counter.getAndIncrement();
                }
                catch (IOException | InstantiationException e) {
                    AlertDialog.showWarning("Due to " + e.getMessage() + ": " + path.getFileName() + " was deleted");
                    file.delete();

                    //TODO: make sure the correct amount of pages is changed. So, that if there was 5 slots and 2 pages
                    //then there are only 4 and 1 after.

                    //TODO: when NumberFormat is thrown...
                }
            });
        fileWalk.close();
    }

    private void setArmyBasedOnPage(int counter, File file) throws IOException, InstantiationException {
        Army currentArmy = armyFileHandler.readFromArmyFile(file);

        if(counter % 4 == 0){
            army1 = currentArmy;
            setSlotContent(saveSlot1, file);

            saveSlot1.setId("save-slot");
        }
        else if(counter % 4 == 1){
            army2 = currentArmy;
            setSlotContent(saveSlot2, file);
            saveSlot2.setId("save-slot");

        }
        else if(counter % 4 == 2){
            army3 = currentArmy;
            setSlotContent(saveSlot3, file);
            saveSlot3.setId("save-slot");

        }
        else if(counter % 4 == 3){
            army4 = currentArmy;
            setSlotContent(saveSlot4, file);
            saveSlot4.setId("save-slot");

        }
    }

    private void setSlotContent(ScrollPane saveSlot, File file) throws IOException, InstantiationException {
        VBox armyVBox = new ArmyDisplay.Builder(file)
                .addArmyName()
                .addUnitInformation(UnitType.INFANTRY)
                .addUnitInformation(UnitType.CAVALRY)
                .addUnitInformation(UnitType.RANGED)
                .addUnitInformation(UnitType.COMMANDER)
                .addTimeSavedInfo()
                .addFileLocationInfo()
                .addFileNameInfo()
                .build();

//        armyVBox.setPadding(new Insets(20));
        armyVBox.getStylesheets().add(String.valueOf(SceneHandler.class.getResource("/stylesheets/Background.css")));
        armyVBox.setId("save-slot");

        saveSlot.setContent(armyVBox);
    }

    private void clearAllSlotsInfo(){
        clearSlotInfo(saveSlot1);
        army1 = null;

        clearSlotInfo(saveSlot2);
        army2 = null;

        clearSlotInfo(saveSlot3);
        army3 = null;

        clearSlotInfo(saveSlot4);
        army4 = null;
    }

    private void clearSlotInfo(ScrollPane saveSlot){
        saveSlot.setContent(null);
        saveSlot.setId(null);
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
