package edu.ntnu.trym.simulation.model.armydisplay;

import edu.ntnu.trym.simulation.model.Army;
import edu.ntnu.trym.simulation.model.filehandling.ArmyFileHandler;
import edu.ntnu.trym.simulation.model.units.UnitType;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a depiction of an army. This army can come in both the form of an Army file as well as just an
 * army object. From there, a builder, {@link Builder}, allows the user to choose what information concerning the army
 * file and/or army. The file and the army's information will be instantiated as text and displayed in the GUI.
 * Additionally, this class extends the {@link VBox} class, which means an object of ArmyDisplay can be treated the
 * same as a VBox.
 *
 * @author Trym Hamer Gudvangen
 */
public class ArmyDisplay extends VBox{

    /**
     * This constructor represents a display filled with a given army's information. It utilizes a builder object,
     * {@link Builder#Builder(Army)} taken as a parameter, in order to attain all the nodes which need to be attached
     * to the ArmyDisplay object.
     */
    public ArmyDisplay(Builder builder) {
        super(PaneSpacing.createVBoxWithSpacing(builder.hBoxList));
        this.autosize();
    }

    /**
     * This static class employs the builder design pattern. It allows for a person to construct a display by adding
     * on the desired parts of information. It, therefore, includes multiple methods for adding army information.
     */
    public static class Builder{
        private File armyFile;

        private String location;

        private Army army;

        private final List<HBox> hBoxList;

        /**
         * This is the constructor for the builder class which takes in a file as input. With this constructor,
         * information not only surrounding the army in the file but the file itself can be extracted. This information
         * is set during the initialization phase using the method {@link #setArmyInformation()}.
         * @param armyFile                  The file containing the army, represented using a File object.
         * @throws IOException              This exception is thrown if the file stated is invalid.
         * @throws InstantiationException   This exception is thrown if the file being read is
         */
        public Builder(File armyFile) throws IOException, InstantiationException {
            this.armyFile = armyFile;
            this.hBoxList = new ArrayList<>();
            setArmyInformation();
        }

        /**
         * This is the constructor for the builder class which takes in an Army object as input. With this constructor,
         * information only information surrounding the given army can be used.
         * @param army  The army whose information will be displayed, represented using an Army object.
         */
        public Builder(Army army) {
            this.army = army;
            this.hBoxList = new ArrayList<>();
        }

        /**
         * This method adds an HBox containing the army name to the hBoxList.
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
            pathSaved.setWrappingWidth(200);
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

        /**
         * This method finalizes all the information that has been added and creates a new ArmyDisplay object,
         * {@link ArmyDisplay#ArmyDisplay(Builder)}.
         * @return  An army display with all the information added, represented as a VBox.
         */
        public VBox build(){
            return new ArmyDisplay(this);
        }
    }

}