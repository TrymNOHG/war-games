package edu.ntnu.trym.simulation.model.armydisplay;

import edu.ntnu.trym.simulation.model.Army;
import edu.ntnu.trym.simulation.model.filehandling.ArmyFileHandler;
import edu.ntnu.trym.simulation.model.units.UnitType;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;

//TODO: Fix javadoc for class!!!


/**
 * This class represents a depiction of an army saved to a file. The file and the army's information will be
 * instantiated as text and displayed in the GUI.
 */
public class ArmyDisplay extends VBox{


    /**
     * This constructor represents a display filled with a given army's information. It utilizes a builder object,
     * taken as a parameter, in order to attain all the nodes which need to be attached to the ArmyDisplay object.
     * @param armyFile      The file containing the army, represented using a File object.
     * @throws IOException  This exception is thrown if the file stated is invalid.
     */
    public ArmyDisplay(Builder builder) {
        super(PaneSpacing.createVBoxWithSpacing(builder.hBoxList));
        this.autosize();
    }

    public static class Builder{
        private File armyFile;

        private String location;

        private Army army;

        private final List<HBox> hBoxList;

        public Builder(File armyFile) throws IOException, InstantiationException {
            this.armyFile = armyFile;
            this.hBoxList = new ArrayList<>();
            setArmyInformation();
        }

        public Builder(Army army) {
            this.army = army;
            this.hBoxList = new ArrayList<>();
        }

        /**
         * This method adds an hbox containing the army name to the hBoxList.
         * @return The builder itself, represented as a Builder object.
         */
        public Builder addArmyName(){
            this.hBoxList.add(PaneSpacing.createHBoxWithSpacing(new Text(army.getName())));
            return this;
        }

        /**
         * This method creates the file name information of the army file.
         * @return A horizontal layout with the file name information, represented using an HBox object
         */
        public Builder addFileNameInfo(){
            if(armyFile == null) return this;
            Text description = new Text("File Name: ");
            Text fileName = new Text(this.armyFile.getName());
            this.hBoxList.add(PaneSpacing.createHBoxWithSpacing(description, fileName));
            return this;
        }

        /**
         * This method creates the time saved information of the army file.
         * @return A horizontal layout with the file save information, represented using an HBox object
         */
        public Builder addTimeSavedInfo() throws IOException {
            if(armyFile == null) return this;
            Text savedText = new Text("Saved: ");
            FileTime fileTime = Files.getLastModifiedTime(this.armyFile.toPath());
            Text timeSaved = new Text(fileTime.toString().substring(0, 10));
            this.hBoxList.add(PaneSpacing.createHBoxWithSpacing(savedText, timeSaved));
            return this;
        }

        /**
         * This method creates the file location information of the army file.
         * @return A horizontal layout with the file location information,represented using an HBox object
         */
        public Builder addFileLocationInfo(){
            if(armyFile == null) return this;
            Text locationText = new Text("Location: ");
            Text pathSaved = new Text(this.location);
            this.hBoxList.add(PaneSpacing.createHBoxWithSpacing(locationText, pathSaved));
            return this;
        }

        /**
         * This method takes a unit type and finds all the units from the army that are of that type using the Unit stream
         * methods, for example for ranged the getRangedUnits is used {@link Army#getRangedUnits()}.
         * @param unitType  The type of unit, represented as a UnitType enumeration
         * @return          A horizontal layout with the unit information,represented using an HBox object
         */
        public Builder addUnitInformation(UnitType unitType){
            Text unitNameText = new Text(unitType + ": ");
            Text unitNumberText = new Text(String.valueOf(this.army.getUnitsByType(unitType).size()));
            this.hBoxList.add(PaneSpacing.createHBoxWithSpacing(unitNameText, unitNumberText));
            return this;
        }

        /**
         * This method retrieves the location and army information from the army file.
         * @throws IOException This exception is thrown if the file read is invalid.
         */
        private void setArmyInformation() throws IOException, InstantiationException{
            this.location = this.armyFile.getAbsolutePath();
            this.army = new ArmyFileHandler().readFromArmyFile(this.armyFile);
        }

        public VBox build(){
            return new ArmyDisplay(this);
        }
    }

}

//TODO: test the setArmyInformation method by checking that the location and army is correct.
