package edu.ntnu.trym.simulation.filehandling;

import edu.ntnu.trym.simulation.Army;
import edu.ntnu.trym.simulation.units.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArmyFileHandlerTest{

    ArmyFileHandler armyFileHandler = new ArmyFileHandler();

//    @Test
//    void presetClasses() throws IOException {
//        Army army = new Army("Trym's Army", fillArmyList());
//        armyFileHandler.createAndWriteNewArmyFile(army, createValidFile("Tryms Army"));
//    }

    //TODO: fix the regex file error
    //Make try catch here so I dont need throws every!!!
    //Do I need to check that the first line has no commas and that every other line has 4 commas?
    public File createValidFile(String fileName) throws IOException {
        return new File(FileSystems.getDefault()
                .getPath("src", "test", "resources", "armytestfiles", fileName) + ".csv");
    }

    static List<Unit> fillArmyList(){
        List<Unit> filledArmyList = new ArrayList<>();
        filledArmyList.add(new CommanderUnit("King", 10));
        filledArmyList.add(new CavalryUnit("Knight", 10));
        filledArmyList.add(new InfantryUnit("Pikeman", 10));
        filledArmyList.add(new RangedUnit("Crossbowman", 10));
        return filledArmyList;
    }

    @Nested
    public class An_Armys_files_work_if{

        //What if army is null
        //Check if file is closed, check if it can be renamed
        //Sending in a path which is not valid as a CSV but is a file
        //Check that file is saved to right directory
        //Do not make ArmyFileHandler static

        @ParameterizedTest
        @ValueSource(strings = {"$123test", "50%Off", "***army***", "Army?", "Orc Army!",
                "Human Army > Orc Army", "Army/military", "[ArmyFile]", "{ArmyFile}", "Trym's : Army"})
        void names_with_special_characters_cannot_be_used_in_file_name(String fileName){
            //Given/Arrange
            Army army = new Army("Trym's Army", fillArmyList());
            String expectedExceptionMessage = "The file name contains illegal characters.";

            //When/Act
            try{
                armyFileHandler.isFileNameValid(fileName);
            }catch (IOException e){
                //Then/Assert
                Assertions.assertEquals("The file name contains illegal characters.", e.getMessage());
            }
        }

        @ParameterizedTest
        @ValueSource (strings = {"Sarahs army", "Army", "Human-Army", "OrcArmy123"})
        void names_with_valid_characters_can_be_used_as_file_name(String fileName) throws IOException {
            //Given/Arrange
            Army army = new Army("Trym's Army", fillArmyList());

            //When/Act
            try{
                armyFileHandler.createAndWriteNewArmyFile(army, createValidFile(fileName));
            }catch (Exception e){
                fail("An exception was thrown when it shouldn't have.");
            }

            File expectedFileCreated = createValidFile(fileName);

            //Then/Assert
            Assertions.assertTrue(expectedFileCreated.isFile());
            expectedFileCreated.delete();

        }

        //TODO: Use this:
        @ParameterizedTest (name = "Create army with name {0}")
        @ValueSource (strings = {"Sarahs army", "Army", "Human-Army", "OrcArmy123"})
        void valid_files_created_can_be_accessed_and_read(String fileName) throws IOException {
            //Given/Arrange
            Army army = new Army("Trym's Army", fillArmyList());

            //When/Act
            try{
                armyFileHandler.createAndWriteNewArmyFile(army, createValidFile(fileName));
            }catch (Exception e){
                fail("An exception was thrown when it shouldn't have.");
            }

            File expectedFileCreated = createValidFile(fileName);

            //Then/Assert
            Assertions.assertTrue(expectedFileCreated.canRead());
            expectedFileCreated.delete();
        }

        @ParameterizedTest
        @ValueSource (strings = {"Sarahs army", "Army", "Human-Army", "OrcArmy123"})
        void the_pathing_is_correctly_set(String fileName) throws IOException {
            //Given/Arrange
            Army army = new Army("Trym's Army", fillArmyList());
            boolean fileDoesNotExistAtStart = !createValidFile(fileName).exists();

            //When/Act
            try{
                armyFileHandler.createAndWriteNewArmyFile(army, createValidFile(fileName));
            }catch (Exception e){
                fail("An exception was thrown when it shouldn't have.");
            }

            File expectedFileCreated = createValidFile(fileName);
            boolean fileDoesExistAfterWrite = expectedFileCreated.exists();

            //Then/Assert
            Assertions.assertTrue(fileDoesNotExistAtStart);
            Assertions.assertTrue(fileDoesExistAfterWrite);
            expectedFileCreated.delete();
        }

        @Test
        void cant_create_new_file_with_preexisting_file_name() throws IOException {
            //Given/Arrange
            Army army = new Army("Trym's Army", fillArmyList());

            File preexistingFile = createValidFile("Tryms Army");
            if(createValidFile("Tryms Army").isFile()) {

                Assertions.assertThrows(IOException.class, () -> {
                    //When/Act
                    armyFileHandler.createAndWriteNewArmyFile(army, preexistingFile);
                });//Then/Assert
            }
            else fail("The file check for doesn't exist, so this test is invalid");
        }


        @Test
        void saves_the_correct_information() throws IOException {
            //Given/Arrange
            List<Unit> oneUnitList = new ArrayList<>();
            oneUnitList.add(new CommanderUnit("Queen", 50, 20, 10));
            Army army = new Army("Sarah's Army", oneUnitList);
            String expectedFirstLine = "Sarah's Army";
            String expectedSecondLine = "CommanderUnit,Queen,50,20,10";

            File newFile = new File("Sarah Army");

            //When/Act
            armyFileHandler.createAndWriteNewArmyFile(army, newFile);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(newFile));
            String actualFirstLine = bufferedReader.readLine();
            String actualSecondLine = bufferedReader.readLine();

            //Then/Assert
            Assertions.assertEquals(expectedFirstLine, actualFirstLine);
            Assertions.assertEquals(expectedSecondLine, actualSecondLine);

            newFile.delete();
        }

        @Test
        void constructs_an_Army_correctly_when_read() throws IOException {
            //Given/Arrange
            Army expectedArmy = new Army("Trym's Army", fillArmyList());

            //When/Act
            Army actualArmy = armyFileHandler.readFromArmyFile(createValidFile("Tryms Army"));

            //Then/Assert
            Assertions.assertTrue(actualArmy.equals(expectedArmy));
        }

        @Test
        void overwriting_doesnt_add_on_to_but_changes_a_file_completely() throws IOException {
            //Given/Arrange
            Army firstFileArmy = armyFileHandler.readFromArmyFile(createValidFile("Tryms Army"));
            long initialFileLength = createValidFile("Tryms Army").length();
            String initialFirstLine = "Trym's Army";

            List<Unit> changedArmyList = fillArmyList();
            changedArmyList.add(new CommanderUnit("Queen", 1000));
            Army changedArmy = new Army("Sarahs Army", changedArmyList);

            //When/Act
            armyFileHandler.overwriteExistingArmyFile(changedArmy, createValidFile("Tryms Army"));
            String expectedChangedName = "Sarahs Army";
            Army secondFileArmy = armyFileHandler.readFromArmyFile(createValidFile("Tryms Army"));

            BufferedReader bufferedReader = new BufferedReader(new FileReader(createValidFile("Tryms Army")));
            String overwrittenFirstLine = bufferedReader.readLine();

            long overwrittenFileLength = createValidFile("Tryms Army").length();

            //Then/Assert
            Assertions.assertNotEquals(initialFirstLine, overwrittenFirstLine);
            Assertions.assertNotEquals(initialFileLength, overwrittenFileLength);

            //Resetting the start info
            armyFileHandler.overwriteExistingArmyFile(firstFileArmy, createValidFile("Tryms Army"));
        }

        @Test
        void overwriting_successfully_writes_a_new_armys_info() throws IOException {
            //Given/Arrange
            Army firstFileArmy = armyFileHandler.readFromArmyFile(createValidFile("Tryms Army"));
            List<Unit> changedArmyList = fillArmyList();
            changedArmyList.add(new CommanderUnit("Queen", 1000));
            Army changedArmy = new Army("Sarahs Army", changedArmyList);

            //When/Act
            armyFileHandler.overwriteExistingArmyFile(changedArmy, createValidFile("Tryms Army"));
            Army secondFileArmy = armyFileHandler.readFromArmyFile(createValidFile("Tryms Army"));

            //Then/Assert
            Assertions.assertTrue(changedArmy.equals(secondFileArmy));
            Assertions.assertFalse(secondFileArmy.equals(firstFileArmy));
            Assertions.assertNotEquals(firstFileArmy.getName(), secondFileArmy.getName());

            //Resetting the start info
            armyFileHandler.overwriteExistingArmyFile(firstFileArmy, createValidFile("Tryms Army"));
        }

        @Test
        void an_overwritten_file_can_be_read_successfully() throws IOException {
            //Given/Arrange
            Army firstFileArmy = armyFileHandler.readFromArmyFile(createValidFile("Tryms Army"));

            List<Unit> expectedOverwrittenArmyList = fillArmyList();
            expectedOverwrittenArmyList.add(new CommanderUnit("Queen", 1000));
            Army expectedArmy = new Army("Sarahs Army", expectedOverwrittenArmyList);
            armyFileHandler.overwriteExistingArmyFile(expectedArmy, createValidFile("Tryms Army"));

            //When/Act
            Army overwrittenArmy = armyFileHandler.readFromArmyFile(createValidFile("Tryms Army"));

            //Then/Assert
            Assertions.assertTrue(overwrittenArmy.equals(expectedArmy));

            //Resetting the start info
            armyFileHandler.overwriteExistingArmyFile(firstFileArmy, createValidFile("Tryms Army"));
        }

        @Test
        void an_empty_army_will_write_only_name() throws IOException {
            //Given/Arrange
            Army army = new Army("Human Army");
            File file = createValidFile("Empty Army Test");

            //When/Act
            armyFileHandler.createAndWriteNewArmyFile(army, file);
            Army expectedArmyWithoutUnits = armyFileHandler.readFromArmyFile(file);

            //Then/Assert
            Assertions.assertTrue(expectedArmyWithoutUnits.getAllUnits().isEmpty());
            file.delete();
        }

        @Test
        void a_null_army_when_creating_new_file_will_throw_NullPointerException(){
            //Given/Arrange
            Army army = null;

            Assertions.assertThrows(NullPointerException.class, () ->{
                //When/Act
                armyFileHandler.createAndWriteNewArmyFile(army, createValidFile("Null Army Test"));
            }); //Then/Assert
        }

        @Test
        void a_null_army_when_overwriting_file_will_throw_NullPointerException(){
            //Given/Arrange
            Army army = null;

            Assertions.assertThrows(NullPointerException.class, () ->{
                //When/Act
                armyFileHandler.overwriteExistingArmyFile(army, createValidFile("Tryms army"));
            }); //Then/Assert
        }

        @Test
        void cant_overwrite_a_non_existent_file() throws IOException {
            //Given/Arrange
            Army changedArmy = new Army("Sarahs Army", fillArmyList());

            //When/Act
            boolean canOverwrite = armyFileHandler.overwriteExistingArmyFile(changedArmy, createValidFile("Tryms Army"));

            //Then/Assert
            Assertions.assertFalse(canOverwrite);

        }

        @Test
        void formats_the_information_using_CSV() throws IOException {
            //Given/Arrange
            Army army = new Army("Sarah's Army", fillArmyList());
            File file = createValidFile("Sarahs Army");
            armyFileHandler.createAndWriteNewArmyFile(army,file);


            //When/Act
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            bufferedReader.lines().skip(1).forEach(line -> {
                //Check that each line, other than the first, has four commas
                if(line.split(",").length != 5){
                    //Then/Assert
                    fail("There aren't five attributes saved and separated by commas");
                }
            });

            file.delete();
        }

        @Test
        void can_handle_external_changes() {

        }

        //This is kind of to be expected?
//        @Test
//        void it_saves_to_the_correct_location(){
//
//        }

        //TODO: Organize better
        //Check it contains the correct information
        //Corrupt data
        //Missing formatting?
        //If something is changed in the csv, check that everything isn't messed up completely
    }

}