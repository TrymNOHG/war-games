package edu.ntnu.trym.simulation.controller;

import edu.ntnu.trym.simulation.model.*;
import edu.ntnu.trym.simulation.model.filehandling.ArmyFileHandler;
import edu.ntnu.trym.simulation.model.units.Unit;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ArmyConstructionController implements Initializable {

    private Army armyConstructed;

    @FXML
    private TextField amountOfUnitsInput;

    @FXML
    private TextField armorInput;

    @FXML
    private TextField armyNameText;

    @FXML
    private TableView<Unit> armyTable;

    @FXML
    private TextField attackInput;

    @FXML
    private TextField healthInput;

    @FXML
    private TextField unitNameInput;

    private Unit selectedUnit;

    private boolean defaultUnit = true;

    private ArmyFileHandler armyFileHandler = new ArmyFileHandler();

    @FXML
    private ComboBox<UnitType> unitTypeBox = new ComboBox<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialData();

        //TODO: Listeners on both armor and attack input to see whether default or specialized

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

    //TODO: Make the input boxes size with the screen so that default is spelled out

    @FXML
    void addUnit(ActionEvent event) {
        armyConstructed.addAll(createUnitsFromInputData());
        armyTable.refresh();
    }

    @FXML
    void removeUnit(ActionEvent event) {
        try {
            Objects.requireNonNull(selectedUnit);
            armyTable.getItems().remove(selectedUnit);
        }
        catch (NullPointerException e){
            e.printStackTrace();
            //TODO: add error message of some sort, maybe pop-up
        }
    }

    @FXML
    void saveLoadArmy(ActionEvent event) throws IOException {

        String fileName = AlertDialog.createTextInputDialog("Save Army", "Type the name of the army's file",
                "File name: ");

        //TODO: check if there is a better way of telling whether the cancel button was pressed.
        if(fileName == null) return;
        if(this.armyConstructed.getAllUnits().isEmpty()){
            AlertDialog.showError("The army is empty and can, therefore, not be saved.");
            return;
        }

        //Check if a file with that name already exists and if so, does the person want to overwrite it?

        try{
            this.armyConstructed.setName(this.armyNameText.getText());
            SimulationSingleton.INSTANCE.setArmyOfCurrentArmy(this.armyConstructed);
            armyFileHandler.isFileNameValid(fileName);

            if(armyFileHandler.fileExists(new File(armyFileHandler.getFileSourcePath(fileName)))){
                if(AlertDialog.showConfirmation("A file with this already exists. If you press OK, then" +
                        " that file will be overwritten by this army. Press CANCEL to go back.", "Are you " +
                        "sure you want to overwrite?")){
                    armyFileHandler.overwriteExistingArmyFile(this.armyConstructed,
                            new File(armyFileHandler.getFileSourcePath(fileName)));
                }
                else return;
            }
            else{
                armyFileHandler.createAndWriteNewArmyFile(this.armyConstructed,
                        new File(armyFileHandler.getFileSourcePath(fileName)));
            }

        } catch (Exception e) {
            AlertDialog.showError(e.getMessage());
            return;
        }

        SceneHandler.loadBattlePreparation(event);
        //Save the army to file but maybe get some information from a pop-up on what the name should be.
    }




    @FXML
    void backToBattlePrep(ActionEvent event) throws IOException {
        SceneHandler.loadBattlePreparation(event);
    }

    private void initialData(){
        //TODO: Make sure the name is changed before allowing the army to be constructed

        armyConstructed = new Army("Temporary Name", new ArrayList<>());
        unitTypeBox.getItems().addAll(UnitType.values());
        createArmyTable();
        attackInput.setPromptText("DEFAULT");
        armorInput.setPromptText("DEFAULT");

        setSelectedUnit();
    }

    private void createArmyTable(){

        TableColumn<Unit, String> firstColumn = new TableColumn<>("Unit Type");
        firstColumn.setCellValueFactory(new PropertyValueFactory<>("unitType"));

        TableColumn<Unit, String> secondColumn = new TableColumn<>("Name");
        secondColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Unit, String> thirdColumn = new TableColumn<>("Health");
        thirdColumn.setCellValueFactory(new PropertyValueFactory<>("health"));

        TableColumn<Unit, String> fourthColumn = new TableColumn<>("Attack");
        fourthColumn.setCellValueFactory(new PropertyValueFactory<>("attack"));

        TableColumn<Unit, String> fifthColumn = new TableColumn<>("Armor");
        fifthColumn.setCellValueFactory(new PropertyValueFactory<>("armor"));

        //TODO: add amount once everything else works
//        TableColumn<Army, String> sixthColumn = new TableColumn<>("Amount");
//        sixthColumn.setCellValueFactory(new PropertyValueFactory<>("attack"));

        //TODO: check better options for this:
        armyTable.getColumns().addAll(firstColumn, secondColumn, thirdColumn, fourthColumn, fifthColumn);
        armyTable.setItems(FXCollections.observableList(armyConstructed.getAllUnits()));

    }

    //TODO: Name better?
    private void setSelectedUnit(){
        armyTable.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY){
                selectedUnit = armyTable.getSelectionModel().getSelectedItem();
            }
        });
    }

    private List<Unit> createUnitsFromInputData(){
        if(!isUnitReadyToBeCreated())return null;

        //Default Unit
        if(defaultUnit){
            return UnitFactory.getMultipleUnits(Integer.parseInt(amountOfUnitsInput.getText()), unitTypeBox.getValue(),
                    unitNameInput.getText(), Integer.parseInt(healthInput.getText()));
        }
        //Specialized Unit
        else{
            return UnitFactory.getMultipleUnits(Integer.parseInt(amountOfUnitsInput.getText()), unitTypeBox.getValue(),
                    unitNameInput.getText(), Integer.parseInt(healthInput.getText()),
                    Integer.parseInt(attackInput.getText()), Integer.parseInt(armorInput.getText()));
        }
    }

    private boolean isUnitReadyToBeCreated(){
        if(unitTypeBox.getValue() == null){
            AlertDialog.showError("Please choose a unit type before adding a unit!");
            return false;
        }
        //Check whether the health and such are valid
        return true;
    }


}

//Add a listener to unit type, name text field, health, and amount. Once these have been properly changed, then the
// add unit button is not disabled