package edu.ntnu.trym.simulation.model;

import edu.ntnu.trym.simulation.model.filehandling.ArmyFileHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * This class represents a depiction of an army saved to a file. The file and the army's information will be
 * instantiated as text and displayed in the GUI.
 */
public class ArmyFileDisplay {
    private File armyFile;

    private VBox armyDisplay;

    private String location;
    private Army army;


    /**
     * This constructor represents a display filled with a given army's information. It, therefore, takes in an army
     * file and creates VBox during instantiation.
     * @param armyFile      The file containing the army, represented using a File object.
     * @throws IOException  This exception is thrown if the file stated is invalid.
     */
    public ArmyFileDisplay(File armyFile) throws IOException, InstantiationException {
        this.armyFile = armyFile;
        setArmyInformation();
        constructRegion();
    }

    /**
     * This method creates the necessary text boxes in order to display the information. It, then, attaches these
     * boxes to a given region.
     */
    public void constructRegion(){
        HBox armyNameInfo = PaneSpacing.createHBoxWithSpacing(new Text(army.getName()));

        HBox infantryNumInfo = createUnitInformation(UnitType.INFANTRY);
        HBox cavalryNumInfo = createUnitInformation(UnitType.CAVALRY);
        HBox rangedNumInfo = createUnitInformation(UnitType.RANGED);
        HBox commanderNumInfo = createUnitInformation(UnitType.COMMANDER);

        HBox timeSavedInfo = createTimeSavedInfo();
        HBox fileLocationInfo = createFileLocationInfo();
        HBox fileNameInfo = createFileNameInfo();


        this.armyDisplay = new VBox(armyNameInfo, infantryNumInfo, cavalryNumInfo, rangedNumInfo, commanderNumInfo,
                timeSavedInfo, fileLocationInfo, fileNameInfo);
//        this.armyDisplay.setPadding(new Insets(20));
        this.armyDisplay.autosize();
    }

    /**
     * This method creates the file name information of the army file.
     * @return A horizontal layout with the file name information, represented using an HBox object
     */
    private HBox createFileNameInfo(){
        Text description = new Text("File Name: ");
        Text fileName = new Text(this.armyFile.getName());
        return PaneSpacing.createHBoxWithSpacing(description, fileName);
    }

    /**
     * This method creates the time saved information of the army file.
     * @return A horizontal layout with the file save information, represented using an HBox object
     */
    private HBox createTimeSavedInfo(){
        Text savedText = new Text("Saved: ");
        Text timeSaved = new Text(String.valueOf(this.armyFile.lastModified()));
        return PaneSpacing.createHBoxWithSpacing(savedText, timeSaved);
    }

    /**
     * This method creates the file location information of the army file.
     * @return A horizontal layout with the file location information,represented using an HBox object
     */
    private HBox createFileLocationInfo(){
        Text locationText = new Text("Location: ");
        Text pathSaved = new Text(this.location);
        return PaneSpacing.createHBoxWithSpacing(locationText, pathSaved);
    }

    /**
     * This method takes a unit type and finds all the units from the army that are of that type using the Unit stream
     * methods, for example for ranged the getRangedUnits is used {@link Army#getRangedUnits()}.
     * @param unitType  The type of unit, represented as a UnitType enumeration
     * @return          A horizontal layout with the unit information,represented using an HBox object
     */
    private HBox createUnitInformation(UnitType unitType){
        Text unitNameText = new Text(unitType + ": ");
        Text unitNumberText = new Text(String.valueOf(this.army.getUnitsByType(unitType).size()));
        return PaneSpacing.createHBoxWithSpacing(unitNameText, unitNumberText);
    }

    /**
     * This method retrieves the location and army information from the army file.
     * @throws IOException This exception is thrown if the file read is invalid.
     */
    private void setArmyInformation() throws IOException, InstantiationException {
        this.location = this.armyFile.getAbsolutePath();
        this.army = new ArmyFileHandler().readFromArmyFile(this.armyFile);
    }

    /**
     * This method retrieves the VBox containing the army information
     * @return A vertical box of army information, represented as a VBox object
     */
    public VBox getArmyDisplay() {
        return armyDisplay;
    }
}

//TODO: test the setArmyInformation method by checking that the location and army is correct.
