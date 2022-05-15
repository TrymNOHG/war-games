package edu.ntnu.trym.simulation.controller;

import edu.ntnu.trym.simulation.model.Army;
import edu.ntnu.trym.simulation.model.TerrainType;
import edu.ntnu.trym.simulation.model.UnitFactory;
import edu.ntnu.trym.simulation.model.UnitType;
import edu.ntnu.trym.simulation.model.units.RangedUnit;
import edu.ntnu.trym.simulation.model.units.Unit;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

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

    @FXML
    private ComboBox<UnitType> unitTypeBox = new ComboBox<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialData();

        //TODO: Listeners on both armor and attack input to see whether default or specialized




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
    void saveLoadArmy(ActionEvent event) {
        SimulationSingleton.INSTANCE.setArmyOfCurrentArmy(this.armyConstructed);
        //Save the army to file but maybe get some information from a pop-up on what the name should be.
    }

    @FXML
    void backToBattlePrep(ActionEvent event) throws IOException {
        SceneHandler.loadBattlePreparation(event);
    }

    private void initialData(){
        //TODO: Make sure the name is changed before allowing the army to be constructed
        List<Unit> unitList = new ArrayList<>();
        unitList.add(new RangedUnit("Archer", 1, 2, 3));

        armyConstructed = new Army("Temporary Name", unitList);
        unitTypeBox.getItems().addAll(UnitType.values());
        createArmyTable();
        attackInput.setPromptText("DEFAULT");
        armorInput.setPromptText("DEFAULT");

        setSelectedUnit();
    }

    private void createArmyTable(){
//        armyTable.getItems().addAll(armyConstructed);

//        armyTable.getColumns().addAll((Collection<? extends TableColumn<Unit, ?>>) armyConstructed);



        TableColumn<Unit, String> firstColumn = new TableColumn<>("Unit Type");
        firstColumn.setCellValueFactory(new PropertyValueFactory<>("unitType"));

        TableColumn<Unit, String> secondColumn = new TableColumn<>("Name");
        secondColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Unit, String> thirdColumn = new TableColumn<>("Health");
        thirdColumn.setCellValueFactory(new PropertyValueFactory<>("health"));

        TableColumn<Unit, String> fourthColumn = new TableColumn<>("Attack");
        fourthColumn.setCellValueFactory(new PropertyValueFactory<>("attack"));

        TableColumn<Unit, String> fifthColumn = new TableColumn<>("Armor");
        fifthColumn.setCellValueFactory(new PropertyValueFactory<>("attack"));

        //TODO: add amount once everything else works
//        TableColumn<Army, String> sixthColumn = new TableColumn<>("Amount");
//        sixthColumn.setCellValueFactory(new PropertyValueFactory<>("attack"));

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

}

//Add a listener to unit type, name text field, health, and amount. Once these have been properly changed, then the
// add unit button is not disabled