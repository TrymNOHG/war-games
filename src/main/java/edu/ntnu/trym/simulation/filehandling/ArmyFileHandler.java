package edu.ntnu.trym.simulation.filehandling;

import edu.ntnu.trym.simulation.Army;
import edu.ntnu.trym.simulation.units.Unit;

import java.io.*;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ArmyFileHandler {
    private Pattern validFileCharacters = Pattern.compile("^[\\w- ]*$");
    private final String DELIMITER = ",";

    //TODO: Check that all the logic in a method belongs to that method solely and not a more general method

    //Makes sure no file exists with that name, creates a new file, and writes out the army to that file
    public void createAndWriteNewArmyFile(Army army, File file) throws IOException {
        if(fileExists(file)) throw new IOException("A file with that name already exists.");
        writeToArmyFile(army, file);
    }

    //Requirement: File must already exist for
    public boolean overwriteExistingArmyFile(Army army, File file) throws IOException {
        if(fileExists(file)) return false;
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
     * @throws IOException Input-Output exception from the writer, in case there is something wrong with the file
     */
    private void writeToArmyFile(Army army, File armyFile) throws IOException{
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
                    Class unitConstruct = Class.forName("edu.ntnu.trym.simulation.units." + splitUnitInfo[0]);
                    unitList.add((Unit) unitConstruct
                            .getDeclaredConstructor(String.class, int.class, int.class, int.class)
                            .newInstance(splitUnitInfo[1], Integer.valueOf(splitUnitInfo[2]),
                                    Integer.valueOf(splitUnitInfo[3]), Integer.valueOf(splitUnitInfo[4])));

                } catch (Exception e) {
                    e.printStackTrace();
                } //Change catch
            });
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return new Army(armyName, unitList);
    }
    //Is it better to just use four if's?
    //TODO: Add exception handling to the method above


    public void isFileNameValid(String fileName) throws IOException{
        Matcher matcher = validFileCharacters.matcher(fileName);
        if(!matcher.matches()) throw new IOException("The file name contains illegal characters.");
    }

//    public File createValidFile(String fileName) throws IOException {
//        isFileNameValid(fileName);
//        return new File("src/main/resources/army-files/" + fileName + ".csv");
//    }

    //Should this method exist
    public boolean fileExists(File fileInQuestion){
        return fileInQuestion.length() > 0;
    }

    public String getFileSourcePath(String fileName){
        return FileSystems.getDefault().getPath("src", "main", "resources", "army-files") + fileName + ".csv";
    }
}


//TODO: JavaDoc