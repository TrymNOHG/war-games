package edu.ntnu.trym.simulation.filehandling;

import edu.ntnu.trym.simulation.Army;
import edu.ntnu.trym.simulation.units.*;

import java.io.*;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ArmyFileHandler {
    private final Pattern validFileCharacters = Pattern.compile("^[\\w- ]*$");
    private final String DELIMITER = ",";

    //TODO: Check that all the logic in a method belongs to that method solely and not a more general method

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
        if(fileExists(file)) throw new IOException("A file with that name already exists.");
        writeToArmyFile(army, file);
    }

    //Requirement: File must already exist for

    /**
     * This method has the requirement that the given file already exists. If it does, the given army overwrites
     * the pre-existing information in the file given.
     * @param army                  The army to be saved, represented as an Army object
     * @param file                  The file that will be overwritten, represented using a File object
     * @return                      The status of overwriting. If the file doesn't already exist, then {@code false}. Else, {@code true}
     * @throws NullPointerException This exception is thrown if army is null
     */
    public boolean overwriteExistingArmyFile(Army army, File file) throws NullPointerException {
        if(!fileExists(file)) return false;
        writeToArmyFile(army, file);
        return true;
    }

    //Make sure I don't allow two armies to be saved in one

    //Does this method pose any threats?
    //Do I need to test this method or can I indirectly test it through the other two?

    /**
     * This method takes an army, given as input, and records its information in the form of a csv file (also given
     * as input). Through a writer, the army information is transcribed with the following format:
     * <pre>
     *     Army name
     *     Unit type,Unit name,Unit health,Unit attack,Unit armor
     *     Unit type,Unit name,Unit health,Unit attack,Unit armor
     *     Unit type,Unit name,Unit health,Unit attack,Unit armor
     * </pre>
     * @param army         The army that will be saved, represented using an Army object
     * @param armyFile     The file which the information will be saved, represented using a File object
     */
    private void writeToArmyFile(Army army, File armyFile) throws NullPointerException{
        Objects.requireNonNull(army, "Army cannot be null");
        try(BufferedWriter armyBufferedWriter = new BufferedWriter(new FileWriter(armyFile))){
            //Adding the army's name to the first line
            armyBufferedWriter.write(army.getName());

            army.getAllUnits().stream().forEach(unit ->{
                try {
                    armyBufferedWriter.newLine();
                    armyBufferedWriter.write(unit.getClass().getSimpleName() + DELIMITER + unit.getName() +
                            DELIMITER + unit.getHealth() + DELIMITER + unit.getAttack() + DELIMITER + unit.getArmor());
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                    //How would I send message to the User and is it ok to use two try and catch?
                }
            });
        }
        catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }

    //Create own exception for certain things, new file

    //TODO: make sure file only contains one army (throw exception if another army is trying to be added)
    //TODO: Check for duplicates (so it isn't doubled) and maybe throw specific exception


    //TODO: When reading the files maybe catch changes?

    /**
     * This method takes a given army file and initializes an Army object with the correct information. This is done
     * by taking the first line of the file, which contains the army name, and filling an army list with the appropriate
     * units. The Units are correctly initialized {@link #extractUnitFromInfo(String[])} and placed in the list.
     * @param file         The army file to be read, represented as an Army
     * @return             The army that was saved in the file, represented using an Army object
     * @throws IOException Input-output exception can be thrown by the reader
     */
    public Army readFromArmyFile(File file) throws IOException {
        String armyName = "";
        List<Unit> unitList = new ArrayList<>();

        //Maybe only throw if file contains info already
        if(!fileExists(file))
            throw new FileNotFoundException("A file with that name does not exist");

        try(BufferedReader armyBufferedReader = new BufferedReader(new FileReader(file))){
            armyName = armyBufferedReader.readLine();
            armyBufferedReader.lines().forEach(unitInfo ->{
                String[] splitUnitInfo = unitInfo.split(",");
                try {
                    unitList.add(extractUnitFromInfo(splitUnitInfo));
                } catch (Exception e) {
                    if(e.getClass() == NumberFormatException.class){
                        throw new NumberFormatException("The health, attack, or armor value is corrupted in the CSV file");
                    }
                    e.printStackTrace();
                } //Change catch
            });
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return new Army(armyName, unitList);
    }

    //Which is better this or the method below?

    //Getting warnings on this one maybe don't use
//    public Unit extractUnitFromInfo(String[] unitInfo) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {
//        Class unitConstruct = Class.forName("edu.ntnu.trym.simulation.units." + unitInfo[0]);
//        return (Unit) unitConstruct.getDeclaredConstructor(String.class, int.class, int.class, int.class)
//                .newInstance(unitInfo[1], Integer.valueOf(unitInfo[2]),
//                        Integer.valueOf(unitInfo[3]), Integer.valueOf(unitInfo[4]));
//    }

    /**
     * This method takes in all the info of a unit, given as a String array. The first index of that array contains the
     * Class name of unit. This method checks what class the unit belongs to and initializes it accordingly.
     * @param unitInfo                Information of a unit from an Army File, represented as a String array
     * @return                        The unit from extracted from the String array, given as a Unit object
     * @throws InstantiationException This exception is thrown if the unit type information is corrupt
     */
    public Unit extractUnitFromInfo(String[] unitInfo) throws InstantiationException {
        if(unitInfo[0].contains("Infantry")) return new InfantryUnit(unitInfo[1], Integer.parseInt(unitInfo[2]),
                Integer.parseInt(unitInfo[3]), Integer.parseInt(unitInfo[4]));
        else if(unitInfo[0].contains("Ranged")) return new RangedUnit(unitInfo[1], Integer.parseInt(unitInfo[2]),
                Integer.parseInt(unitInfo[3]), Integer.parseInt(unitInfo[4]));
        else if(unitInfo[0].contains("Cavalry")) return new CavalryUnit(unitInfo[1], Integer.parseInt(unitInfo[2]),
                Integer.parseInt(unitInfo[3]), Integer.parseInt(unitInfo[4]));
        else if(unitInfo[0].contains("Commander")) return new CommanderUnit(unitInfo[1], Integer.parseInt(unitInfo[2]),
                Integer.parseInt(unitInfo[3]), Integer.parseInt(unitInfo[4]));
        throw new InstantiationException("The Unit type information is corrupt");
    }

    //Is it better to just use four if's?
    //TODO: Add exception handling to the method above \
    //TODO: Check if health,name,etc. blank throw exception
    //TODO: Check if comma in unit name breaks code

    /**
     * This method takes in a file name and checks whether it is valid using a pre-set regex pattern.
     * @param fileName Name of the file to be created, represented as a String
     * @throws IOException If the file name sent in is invalid, then an IOException is thrown
     */
    public void isFileNameValid(String fileName) throws IOException{
        Matcher matcher = validFileCharacters.matcher(fileName);
        if(!matcher.matches()) throw new IOException("The file name contains illegal characters.");
    }
    //TODO: Should the exception thrown above be IllegalArgumentException

//    public File createValidFile(String fileName) throws IOException {
//        isFileNameValid(fileName);
//        return new File("src/main/resources/army-files/" + fileName + ".csv");
//    }

    //Should this method exist

    /**
     * This method checks if a file with a given directory path already exists and if it contains any information.
     * @param fileInQuestion The file that is to be checked, represented using a File object
     * @return               If the file contains no information, {@code false} is returned. Else, {@code true} is returned
     */
    public boolean fileExists(File fileInQuestion){
        return fileInQuestion.length() > 0;
    }

    /**
     * This method retrieves the file source path of a csv file with the given file name.
     * @param fileName The name of the desired file, represented as a String
     * @return         The source path to the file, represented as a String
     */
    public String getFileSourcePath(String fileName){
        return FileSystems.getDefault().getPath("src", "main", "resources", "army-files") + fileName + ".csv";
    }
}

//TODO: Make GUI, following guidelines
//TODO: Ask about formatting of information
//TODO: Read through tips
//TODO: Check that tests directly address requirements: (e.g. only one army per CSV)
