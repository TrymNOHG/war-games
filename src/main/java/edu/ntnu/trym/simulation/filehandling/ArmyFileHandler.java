package edu.ntnu.trym.simulation.filehandling;

import edu.ntnu.trym.simulation.Army;
import edu.ntnu.trym.simulation.units.Unit;

import java.io.*;
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
    public void createAndWriteNewArmyFile(Army army, String fileName) throws IOException {
        if(statusOnFileExistence(createValidFile(fileName))) throw new IOException("A file with that name already exists.");
        writeToArmyFile(army, createValidFile(fileName));
    }

    //Requirement: File must already exist for
    public boolean overwriteExistingArmyFile(Army army, String fileName) throws IOException {
        if(!statusOnFileExistence(createValidFile(fileName))) return false;
        writeToArmyFile(army, createValidFile(fileName));
        return true;
    }

    //Make sure I don't allow two armies to be saved in one

    //Does this method pose any threats?
    //Do I need to test this method or can I indirectly test it through the other two?
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
    //Split up methods into smaller methods, perhaps

    //TODO: make sure file only contains one army (throw exception if another army is trying to be added)
    //TODO: Update file method?
    //TODO: Check for duplicates (so it isn't doubled) and maybe throw specific exception


    //TODO: When reading the files maybe catch changes?

    public Army readFromArmyFile(String fileName) throws IOException {
        String armyName = "";
        List<Unit> unitList = new ArrayList<>();

        //Maybe only throw if file contains info already
        if(!statusOnFileExistence(createValidFile(fileName)))
            throw new FileNotFoundException("A file with the name " + fileName + " does not exist");

        try(BufferedReader armyBufferedReader = new BufferedReader(new FileReader(getFileSourcePath(fileName)))){
            armyName = armyBufferedReader.readLine();
            armyBufferedReader.lines().skip(1).forEach(unitInfo ->{
                String[] splitUnitInfo = unitInfo.split(",");
                try {
                    Class unitConstruct = Class.forName("edu.ntnu.trym.simulation." + splitUnitInfo[0]);
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

    public File createValidFile(String fileName) throws IOException {
        isFileNameValid(fileName);
        return new File("src/main/resources/army-files/" + fileName + ".csv");
    }

    //Should this method exist
    //Change inputs to be more general
    public boolean statusOnFileExistence(File fileInQuestion){
        return fileInQuestion.length() > 0;
    }

    public String getFileSourcePath(String fileName){
        return "src/main/resources/army-files/" + fileName + ".csv";
    }
}


//TODO: JavaDoc