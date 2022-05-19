package edu.ntnu.trym.simulation.model.filehandling;

import edu.ntnu.trym.simulation.model.Army;
import edu.ntnu.trym.simulation.model.UnitFactory;
import edu.ntnu.trym.simulation.model.UnitType;
import edu.ntnu.trym.simulation.model.units.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class handles the storage and retrieval of Army objects from files. It handles these files through buffered
 * writers and readers. The army object is stored using the army name first and then the information of all the units
 * contained.
 * @author Trym Hamer Gudvangen
 */
public class ArmyFileHandler {
    private final String DELIMITER = ",";

    /**
     * This method checks whether a file with a given name already exists. If it does exist, then an IOException is
     * thrown. Else, a new file is created with the given name and a given army's info is written to that file.
     * {@link #writeToArmyFile(Army, File)}
     * @param army                  The army to be saved, represented as an Army object
     * @param file                  The file that the army will be saved to, represented using a File object
     * @throws IOException          If an Input-Output error occurs, for example if a file with that name already exists
     * @throws NullPointerException This exception is thrown if army is null
     */
    public void createAndWriteNewArmyFile(Army army, File file) throws IOException, NullPointerException {
        if(FileHandler.fileExists(file)) throw new IOException("A file with that name already exists.");
        writeToArmyFile(army, file);
    }

    /**
     * This method has the requirement that the given file already exists. If it does, the given army overwrites
     * the pre-existing information in the file given.
     * @param army                  The army to be saved, represented as an Army object
     * @param file                  The file that will be overwritten, represented using a File object
     * @return                      The status of overwriting. If the file doesn't already exist, then {@code false}. Else, {@code true}
     * @throws NullPointerException This exception is thrown if army is null
     */
    public boolean overwriteExistingArmyFile(Army army, File file) throws NullPointerException, IOException {
        if(!FileHandler.fileExists(file)) return false;
        writeToArmyFile(army, file);
        return true;
    }

    //Make sure I don't allow two armies to be saved in one
    //Do I need to test this method or can I indirectly test it through the other two?

    /**
     * This method takes an army, given as input, and records its information in the form of a csv file (also given
     * as input). Through a writer, the army information is transcribed with the following format:
     * <pre>
     *    Army name
     *    Unit type,Unit name,Unit health    (Default Unit)
     *    Unit type,Unit name,Unit health
     *    Unit type,Unit name,Unit health,Unit attack,Unit armor (Special Unit)
     *    Unit type,Unit name,Unit health,Unit attack,Unit armor
     * </pre>
     * @param army         The army that will be saved, represented using an Army object
     * @param armyFile     The file which the information will be saved, represented using a File object
     */
    private void writeToArmyFile(Army army, File armyFile) throws NullPointerException, IOException {
        Objects.requireNonNull(army, "Army cannot be null");
        try(BufferedWriter armyBufferedWriter = new BufferedWriter(new FileWriter(armyFile))){
            //Adding the army's name to the first line
            armyBufferedWriter.write(army.getName());

            for(Unit unit : army.getAllUnits()){
                armyBufferedWriter.newLine();
                if(unit.isDefaultUnit()){
                    armyBufferedWriter.write(unit.getClass().getSimpleName() + DELIMITER + unit.getName() +
                            DELIMITER + unit.getHealth());
                }
                else{
                    armyBufferedWriter.write(unit.getClass().getSimpleName() + DELIMITER + unit.getName() +
                            DELIMITER + unit.getHealth() + DELIMITER + unit.getAttack() + DELIMITER + unit.getArmor());
                }
            }
        }
    }

    //Create own exception for certain things, new file

    //TODO: make sure file only contains one army (throw exception if another army is trying to be added)
    //TODO: Check for duplicates (so it isn't doubled) and maybe throw specific exception


    //TODO: When reading the files maybe catch changes?

    /**
     * This method takes a given army file and initializes an Army object with the correct information. This is done
     * by taking the first line of the file, which contains the army name, and filling an army list with the appropriate
     * units. The UnitTypes are correctly chosen {@link #extractUnitTypeFromInfo(String)} and used to initialize the
     * default {@link UnitFactory#getUnit(UnitType, String, int)} and special
     * {@link UnitFactory#getUnit(UnitType, String, int, int, int)} units.
     * @param file         The army file to be read, represented as an Army
     * @return             The army that was saved in the file, represented using an Army object
     * @throws IOException Input-output exception can be thrown by the reader
     */
    public Army readFromArmyFile(File file) throws IOException, InstantiationException {
        String armyName;
        List<Unit> unitList = new ArrayList<>();

        if(!FileHandler.fileExists(file)) throw new FileNotFoundException("A file with that name does not exist");

        try(BufferedReader armyBufferedReader = new BufferedReader(new FileReader(file))){

            armyName = armyBufferedReader.readLine();

            while(armyBufferedReader.ready()){
                String[] splitUnitInfo = armyBufferedReader.readLine().split(",");
                if(splitUnitInfo.length == 3){
                    unitList.add(UnitFactory.getUnit(extractUnitTypeFromInfo(splitUnitInfo[0]), splitUnitInfo[1],
                            Integer.parseInt(splitUnitInfo[2])));
                }
                else if (splitUnitInfo.length == 5){
                    unitList.add(UnitFactory.getUnit(extractUnitTypeFromInfo(splitUnitInfo[0]), splitUnitInfo[1],
                            Integer.parseInt(splitUnitInfo[2]), Integer.parseInt(splitUnitInfo[3]),
                            Integer.parseInt(splitUnitInfo[4])));
                }
                else throw new StreamCorruptedException("Corrupt format");
            }
        }
        //TODO: add CorruptFileException here, which can be thrown further and used to remove files.
        return new Army(armyName, unitList);
    }

    //TODO: Create extra test for corrupt file with non-3 nor 5 information format
    //TODO: Ensure that the UnitFactory is used for default and special
    //TODO: Test that special units are kept special and default are kept default




    //TODO: Write test for extracting the correct UnitType
    //TODO: Check if comma in unit name breaks code
    //TODO: Add exception handling to the method below

    /**
     * This method takes in the Unit Class info, given as a String. This method checks what UnitType the information
     * belongs to.
     * @param unitTypeInfo            Information of a unit's class from an Army File, represented as a String
     * @return                        The type of unit extracted from the String, given as a UnitType enumeration
     * @throws InstantiationException This exception is thrown if the unit type information is corrupt
     */
    public UnitType extractUnitTypeFromInfo(String unitTypeInfo) throws InstantiationException{
        if(unitTypeInfo.contains("Infantry")) return UnitType.INFANTRY;
        else if(unitTypeInfo.contains("Ranged")) return UnitType.RANGED;
        else if(unitTypeInfo.contains("Cavalry")) return UnitType.CAVALRY;
        else if(unitTypeInfo.contains("Commander")) return UnitType.COMMANDER;
        throw new InstantiationException("The Unit type information is corrupt");
    }

}

//TODO: Ask about formatting of information
//TODO: Read through tips
//TODO: Check that tests directly address requirements: (e.g. only one army per CSV)
//TODO: Test for difference between default and special units
//TODO: Make tests for default units
