package edu.ntnu.trym.simulation.controller;

import edu.ntnu.trym.simulation.model.*;
import edu.ntnu.trym.simulation.model.armydisplay.ArmyTable;
import edu.ntnu.trym.simulation.model.filehandling.ArmyFileHandler;
import edu.ntnu.trym.simulation.model.filehandling.FileHandler;
import edu.ntnu.trym.simulation.model.units.Unit;
import edu.ntnu.trym.simulation.model.units.UnitFactory;
import edu.ntnu.trym.simulation.model.units.UnitType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * This class handles the interactions between the backend and the ArmyConstruction scene. As such, it allows for the
 * creation of an army through the creation and removal of units. Furthermore, it catches exceptions when the user
 * provides invalid information. Finally, when the army has been constructed, it prompts the user to insert a name for
 * the army file and saves it.
 *
 * @author Trym Hamer Gudvangen
 */
public class ArmyConstructionController {

    private Army armyConstructed;

    @FXML
    private TextField amountOfUnitsInput;

    @FXML
    private TextField armorInput;

    @FXML
    private TextField armyNameText;

    @FXML
    private TableView<Unit> armyTable = new TableView<>();

    @FXML
    private TextField attackInput;

    @FXML
    private TextField healthInput;

    @FXML
    private TextField unitNameInput;

    private Unit selectedUnit;

    private boolean defaultUnit = true;

    private final ArmyFileHandler armyFileHandler = new ArmyFileHandler();

    @FXML
    private ComboBox<UnitType> unitTypeBox = new ComboBox<>();

    /**
     * This method sets up all the information concerning the scene before loading it. This includes setting up
     * listeners, loading variable data, and constructing the GUI aspects of the scene.
     */
    public void initialize() {
        initialData();

        attackInput.setOnKeyTyped((action) -> {
            if(attackInput.getText().isEmpty()){
                defaultUnit = true;
                armorInput.setPromptText("DEFAULT");
            }
            else{
                defaultUnit = false;
                armorInput.setPromptText("");
            }
        });

        armorInput.setOnKeyTyped((action) -> {
            if(armorInput.getText().isEmpty()){
                defaultUnit = true;
                attackInput.setPromptText("DEFAULT");
            }
            else{
                defaultUnit = false;
                attackInput.setPromptText("");
            }
        });


    }

    /**
     * This method is called when the add unit button is pressed. It, thereafter, uses the
     * {@link #createUnitsFromInputData()} method in order to create a unit and then adds it to the armyConstructed's
     * unit list.
     * @param event The button being pressed, given as an Event object.
     */
    @FXML
    void addUnit(ActionEvent event) {
        List<Unit> unitList = createUnitsFromInputData();
        armyConstructed.addAll(unitList);
        if(unitList != null) {
            armyTable.getFocusModel().focus(armyTable.getItems().size() - 1);
            armyTable.getSelectionModel().select(unitList.get(0));
        }
        armyTable.refresh();
    }

    /**
     * This method is called when the remove unit button is pressed. It, thereafter, checks if a unit has been selected
     * in the TableView. If so, then it is removed from the tableview and consequently the armyConstructed. If no unit
     * is selected, then a warning dialog is shown.
     * @param event The button being pressed, given as an Event object.
     */
    @FXML
    void removeUnit(ActionEvent event) {
        try {
            Objects.requireNonNull(selectedUnit);
            armyTable.getItems().remove(selectedUnit);
        }
        catch (NullPointerException e){
            AlertDialog.showWarning("A unit was not selected and can therefore not be removed!");
        }
    }

    /**
     * This method is called when the save and load button is pressed. After this, a text input dialog is created,
     * {@link AlertDialog#createTextInputDialog(String, String, String)} asking the user what they would like to name
     * the file. This method handles invalid inputs such as the name of the file and whether a file is being
     * overwritten. Finally, the battle preparation is loaded again,
     * {@link SceneHandler#loadBattlePreparation(ActionEvent)}.
     * @param event             The button being pressed, given as an Event object.
     * @throws IOException      This exception is thrown if the path to the next scene is invalid.
     */
    @FXML
    void saveLoadArmy(ActionEvent event) throws IOException {

        String fileName = AlertDialog.createTextInputDialog("Save Army", "Type the name of the army's file",
                "File name: ");

        if(fileName == null) return;
        if(this.armyConstructed.getAllUnits().isEmpty()){
            AlertDialog.showError("The army is empty and can, therefore, not be saved.");
            return;
        }

        try{
            this.armyConstructed.setName(this.armyNameText.getText());
            SimulationSingleton.INSTANCE.setArmyOfCurrentArmy(this.armyConstructed);
            FileHandler.isFileNameValid(fileName);

            if(FileHandler.fileExists(new File(FileHandler.getFileSourcePath(fileName)))){
                if(AlertDialog.showConfirmation("A file with this already exists. If you press OK, then" +
                        " that file will be overwritten by this army. Press CANCEL to go back.", "Are you " +
                        "sure you want to overwrite?")){
                    armyFileHandler.overwriteExistingArmyFile(this.armyConstructed,
                            new File(FileHandler.getFileSourcePath(fileName)));
                }
                else return;
            }
            else{
                armyFileHandler.createAndWriteNewArmyFile(this.armyConstructed,
                        new File(FileHandler.getFileSourcePath(fileName)));
            }

        } catch (Exception e) {
            AlertDialog.showError(e.getMessage());
            return;
        }

        SceneHandler.loadBattlePreparation(event);
    }

    /**
     * This method is called when the back to battle preparations button is pressed. As such, it will bring
     * the user back to the battle preparations scene.
     * @param event         The button being pressed, given as an Event object.
     * @throws IOException  This exception is thrown if the path to the next scene is invalid.
     */
    @FXML
    void backToBattlePrep(ActionEvent event) throws IOException {
        SceneHandler.loadBattlePreparation(event);
    }

    /**
     * This method is used to set all the initial information necessary throughout the class. It does so through
     * retrieving army information from {@link SimulationSingleton}. Furthermore, it edits the GUI to contain the
     * information that was retrieved.
     */
    private void initialData(){
        if(SimulationSingleton.INSTANCE.getArmyOfCurrentArmy() != null){
            armyConstructed = SimulationSingleton.INSTANCE.getArmyOfCurrentArmy();
            armyNameText.setText(armyConstructed.getName());
        }
        else armyConstructed = new Army("Temporary Name", new ArrayList<>());
        unitTypeBox.getItems().addAll(UnitType.values());
        createArmyTable();
        attackInput.setPromptText("DEFAULT");
        armorInput.setPromptText("DEFAULT");

        setSelectedUnit();
    }

    /**
     * This method sets up the army table, given as a TableView, through the use of the
     * {@link edu.ntnu.trym.simulation.model.armydisplay.ArmyTable.Builder}. Furthermore, it adds all the information
     * from the armyConstructed object into an {@link ObservableList} which is attached to the table.
     */
    private void createArmyTable(){

        TableView<Unit> armyTableView = new ArmyTable.Builder()
                .addUnitColumn("Unit Type", "unitType")
                .addUnitColumn("Name", "name")
                .addUnitColumn("Health", "health")
                .addUnitColumn("Attack", "attack")
                .addUnitColumn("Armor", "armor")
                .build();

        armyTable.getColumns().addAll(armyTableView.getColumns());
        armyTable.setItems(FXCollections.observableList(armyConstructed.getAllUnits()));

    }

    /**
     * This method adds a listener to the armyTable such that if a unit is selected, this class is notified.
     */
    private void setSelectedUnit(){
        armyTable.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY){
                selectedUnit = armyTable.getSelectionModel().getSelectedItem();
            }
        });

    }

    /**
     * This method takes the information from the various input boxes. It, then, checks that all the inputs are valid
     * and if so, then it instantiates a unit using {@link UnitFactory#getMultipleUnits(int, UnitType, String, int)} for
     * default units and {@link UnitFactory#getMultipleUnits(int, UnitType, String, int)} for special units.
     * @return  A list of newly created units, represented using a List{@code <Unit>} object
     */
    private List<Unit> createUnitsFromInputData(){
        try {
            Objects.requireNonNull(unitTypeBox.getValue());
            checkForValidInput();

            //Default Unit
            if (defaultUnit) {
                return UnitFactory.getMultipleUnits(Integer.parseInt(amountOfUnitsInput.getText()), unitTypeBox.getValue(),
                        unitNameInput.getText(), Integer.parseInt(healthInput.getText()));
            }
            //Specialized Unit
            else {
                return UnitFactory.getMultipleUnits(Integer.parseInt(amountOfUnitsInput.getText()), unitTypeBox.getValue(),
                        unitNameInput.getText(), Integer.parseInt(healthInput.getText()),
                        Integer.parseInt(attackInput.getText()), Integer.parseInt(armorInput.getText()));
            }

        }
        catch (IllegalArgumentException e){
            AlertDialog.showError(e.getMessage());
            return null;
        }
        catch (NullPointerException e){
            AlertDialog.showError("Please choose a unit type before adding a unit!");
            return null;
        }

    }

    /**
     * This method checks if the input values for creating a unit are valid before being parsed. It does so by
     * setting exceptions using {@link #setBlankEmptyTextException(String, String)} and
     * {@link #setIntegerParseExceptions(String, String)}.
     */
    private void checkForValidInput() throws IllegalArgumentException{
        setBlankEmptyTextException(amountOfUnitsInput.getText() ,"The amount of units");
        setIntegerParseExceptions(amountOfUnitsInput.getText() ,"The amount of units");

        setBlankEmptyTextException(healthInput.getText(), "The health value");
        setIntegerParseExceptions(healthInput.getText(), "The health value");


        if(!(checkIfBlankOrEmpty(attackInput.getText()) && checkIfBlankOrEmpty(armorInput.getText()))){
            setBlankEmptyTextException(attackInput.getText(), "The attack value");
            setIntegerParseExceptions(attackInput.getText(), "The attack value");

            setBlankEmptyTextException(armorInput.getText(), "The armor value");
            setIntegerParseExceptions(armorInput.getText(), "The armor value");
        }

    }

    /**
     * This method checks if the input text is blank or empty.
     * @param text  The input text, represented as a String.
     * @return      The status of whether the input text is blank or empty, {@code false}, or if it's not, {@code true}.
     */
    private boolean checkIfBlankOrEmpty(String text){
        return text.isEmpty() || text.isBlank();
    }

    /**
     * This method takes in an input and checks that it is valid. If not, then it creates an IllegalArgumentException
     * with an appropriate message.
     * @param text          The text to be checked, represented as a String
     * @param nameOfInput   The name of the input, represented as a String.
     */
    private void setBlankEmptyTextException(String text, String nameOfInput){
        if(checkIfBlankOrEmpty(text)) throw new IllegalArgumentException(nameOfInput + " cannot be blank nor empty.");
    }

    /**
     * This method takes in an input and checks if it can be parsed as an Integer without throwing any errors. If not,
     * then it creates an IllegalArgumentException with an appropriate message.
     * @param text          The text to be checked, represented as a String.
     * @param nameOfInput   The name of the input, represented as a String.
     */
    private void setIntegerParseExceptions(String text, String nameOfInput) throws IllegalArgumentException{
        try{
            Integer.parseInt(text);
        }
        catch (NumberFormatException e){
            throw new IllegalArgumentException(nameOfInput + " has to be an integer!");
        }
    }

}