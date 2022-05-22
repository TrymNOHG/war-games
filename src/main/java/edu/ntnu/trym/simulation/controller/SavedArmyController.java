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

//TODO: make sure the number of pages after corrupted file is correct.

/**
 * This class handles the interactions between the backend and the saved army scene. As such, it displays all the armies
 * that are saved to the army-files folder and are of the correct format. Furthermore, it allows the user to select and
 * load one of the armies.
 *
 * @author Trym Hamer Gudvangen
 */
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

    /**
     * This method sets up all the information concerning the scene before loading it. This includes setting up
     * listeners, loading variable data, and constructing the GUI aspects of the scene.
     */
    public void initialize() {
        try {
            initialData();
            addListeners();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This method is used to set all the initial information necessary throughout the class. This includes setting the
     * number of pages needed based on the number of valid files {@link #numberOfPagesNeeded()}.Furthermore, it edits
     * the GUI to contain the information of each army file {@link #setSaveSlotProperties()}.
     *
     * @throws IOException      This exception is thrown if the army files are invalid.
     */
    private void initialData() throws IOException {
        currentPage = 1;
        totalPages = numberOfPagesNeeded();
        setSaveSlotProperties();
    }

    /**
     * This method adds all the listeners necessary for the class to get the information in needs as the application
     * is running.
     * @throws IOException  This exception is thrown if the army files are invalid.
     */
    private void addListeners() throws IOException {
        updatePage();

        addPageButtonListeners();
        addListenerToArmySlots();
    }

    /**
     * This method adds listeners to the buttons that allow the user to change the pages. In the listeners, the current
     * page the user is on is updated.
     */
    private void addPageButtonListeners(){
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
    }

    /**
     * This method adds listeners to all the different save slots, the regions of army information. As such, if a region
     * is clicked, the listeners will register it and set the corresponding army as the selected army.
     */
    private void addListenerToArmySlots(){
        saveSlot1.setOnMouseClicked(mouseEvent -> {
            selectedArmy = army1;
            loadArmyButton.setId("main-load-button");
        });
        saveSlot2.setOnMouseClicked(mouseEvent -> {
            selectedArmy = army2;
            loadArmyButton.setId("main-load-button");
        });
        saveSlot3.setOnMouseClicked(mouseEvent -> {
            selectedArmy = army3;
            loadArmyButton.setId("main-load-button");
        });
        saveSlot4.setOnMouseClicked(mouseEvent -> {
            selectedArmy = army4;
            loadArmyButton.setId("main-load-button");

        });
    }

    /**
     * This method updates all the slot information (such as visibility and armies) as well as the buttons.
     * @throws IOException      This exception is thrown if the army files are invalid.
     */
    private void updatePage() throws IOException {
        clearAllSlotsInfo();
        updateButtonVisibility();
        updateArmyOnPage();
    }

    /**
     * This method updates the visibility of the page buttons, so that if there aren't any previous or latter pages
     * then the corresponding arrows aren't visible.
     */
    private void updateButtonVisibility(){
        this.previousPageButton.setVisible(currentPage != 1);
        this.nextPageButton.setVisible(currentPage != totalPages);

    }

    /**
     * This method goes into the army-files directory and looks at every existing file there. It, then, uses the method
     * {@link #setArmyBasedOnPage(int, File)} in order to use that army's information and place it in one of the four
     * slots. If the file is corrupt, then a warning alert box pops up using {@link AlertDialog#showWarning(String)}
     * and the file is, thereafter, deleted.
     * @throws IOException      This exception is thrown if the army files are invalid.
     */
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

    /**
     * This method takes in a number from 1-4, representing the slot, and an army file in order to set the army's file
     * within that slot. For a specific slot, this method utilizes the {@link #setSlotContent(ScrollPane, File)}.
     * @param counter                   The slot, represented as an integer.
     * @param file                      The army file, represented using a File object.
     * @throws IOException              This exception is thrown if the file trying to be accessed is invalid.
     * @throws InstantiationException   This exception is thrown if an army cannot be instantiated from the file.
     */
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

    /**
     * This method takes in an army file and use the {@link ArmyDisplay.Builder#build()} in order to create a VBox
     * containing the information of both the file and the army. This information is then attached to a given
     * Scrollpane, the slots.
     * @param saveSlot                  The save slot, represented using a ScrollPane object.
     * @param file                      The army file, represented using an Army object.
     * @throws IOException              This exception is thrown if the file trying to be accessed is invalid.
     * @throws InstantiationException   This exception is thrown if an army cannot be instantiated from the file.
     */
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

    /**
     * This method clears all the slots using {@link #clearSlotInfo(ScrollPane)} and annuls all the armies.
     */
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

    /**
     * This method takes in a save slot and sets all the content and styles attached to it back to null.
     * @param saveSlot  The save slot to be annulled, represented using a ScrollPane object.
     */
    private void clearSlotInfo(ScrollPane saveSlot){
        saveSlot.setContent(null);
        saveSlot.setId(null);
    }

    /**
     * This method sets all the slots' dimension properties.
     */
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

    /**
     * This method calculates the number of pages needed based on the number of CSV files that exist in the army-files
     * directory. This number is found using the {@link FileHandler#getNumberOfCSVFiles()}.
     * @return                  The number of pages needed, represented using an integer.
     * @throws IOException      This exception is thrown if the directory path is invalid.
     */
    private int numberOfPagesNeeded() throws IOException {
        return (int) Math.ceil(FileHandler.getNumberOfCSVFiles()/4.0);
    }

    /**
     * This method is called when the user presses the back to battle preparations button. As the name implies, using
     * {@link SceneHandler#loadBattlePreparation(ActionEvent)}, the battle preparations scene is loaded.
     * @param event             The button being pressed, given as an Event object.
     * @throws IOException      This exception is thrown if the path to the battle preparation scene is invalid.
     */
    @FXML
    void backToBattlePrep(ActionEvent event) throws IOException {
        SceneHandler.loadBattlePreparation(event);
    }

    /**
     * This method is called when the user presses the load button. The information of the selected army is sent to
     * the {@link SimulationSingleton}. Using {@link SceneHandler#loadBattlePreparation(ActionEvent)}, the battle
     * preparations scene is loaded.
     * @param event             The button being pressed, given as an Event object.
     * @throws IOException      This exception is thrown if the path to the battle preparation scene is invalid.
     */
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
