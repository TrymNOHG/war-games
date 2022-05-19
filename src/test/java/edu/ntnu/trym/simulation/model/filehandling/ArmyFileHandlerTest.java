package edu.ntnu.trym.simulation.model.filehandling;

import edu.ntnu.trym.simulation.model.Army;
import edu.ntnu.trym.simulation.model.units.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.*;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class ArmyFileHandlerTest{

    ArmyFileHandler armyFileHandler = new ArmyFileHandler();

    //TODO: Remove helper method below
//    @Test
//    void presetClasses() throws IOException {
//        Army army = new Army("Trym's Army", fillArmyList());
//        armyFileHandler.createAndWriteNewArmyFile(army, getValidFile("Corrupt CSV Format"));
//    }

    public File getValidFile(String fileName) {
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
    public class An_ArmyFile_is_valid_if {

        @ParameterizedTest(name = "{index}. File was named: {0}")
        @ValueSource(strings = {"Sarahs army", "Army", "Human-Army", "OrcArmy123"})
        void a_file_has_a_valid_name(String fileName) {
            //Given/Arrange
            Army army = new Army("Trym's Army", fillArmyList());

            //When/Act
            try {
                armyFileHandler.createAndWriteNewArmyFile(army, getValidFile(fileName));
            } catch (Exception e) {
                fail("An exception was thrown when it shouldn't have.");
            }

            File expectedFileCreated = getValidFile(fileName);

            //Then/Assert
            Assertions.assertTrue(expectedFileCreated.isFile());
            expectedFileCreated.delete();
        }

        //TODO: Use this:
        @ParameterizedTest (name = "{index}. File was named: {0}")
        @ValueSource (strings = {"Sarahs army", "Army", "Human-Army", "OrcArmy123"})
        void files_created_can_be_accessed_and_read(String fileName) {
            //Given/Arrange
            Army army = new Army("Trym's Army", fillArmyList());

            //When/Act
            try{
                armyFileHandler.createAndWriteNewArmyFile(army, getValidFile(fileName));
            }catch (Exception e){
                fail("An exception was thrown when it shouldn't have.");
            }

            File expectedFileCreated = getValidFile(fileName);

            //Then/Assert
            Assertions.assertTrue(expectedFileCreated.canRead());
            expectedFileCreated.delete();
        }

        @ParameterizedTest (name = "{index}. File was named: {0}")
        @ValueSource (strings = {"Sarahs army", "Army", "Human-Army", "OrcArmy123"})
        void the_pathing_is_correctly_set(String fileName) {
            //Given/Arrange
            Army army = new Army("Trym's Army", fillArmyList());
            boolean fileDoesNotExistAtStart = !getValidFile(fileName).exists();

            //When/Act
            try{
                armyFileHandler.createAndWriteNewArmyFile(army, getValidFile(fileName));
            }catch (Exception e){
                fail("An exception was thrown when it shouldn't have.");
            }

            File expectedFileCreated = getValidFile(fileName);
            boolean fileDoesExistAfterWrite = expectedFileCreated.exists();

            //Then/Assert
            Assertions.assertTrue(fileDoesNotExistAtStart);
            Assertions.assertTrue(fileDoesExistAfterWrite);
            expectedFileCreated.delete();
        }

        @Test
        void it_cant_create_new_file_with_preexisting_file_name() {
            //Given/Arrange
            Army army = new Army("Trym's Army", fillArmyList());

            File preexistingFile = getValidFile("Tryms Army");
            if(getValidFile("Tryms Army").isFile()) {

                Assertions.assertThrows(IOException.class, () -> {
                    //When/Act
                    armyFileHandler.createAndWriteNewArmyFile(army, preexistingFile);
                });//Then/Assert
            }
            else fail("The file check for doesn't exist, so this test is invalid");
        }

    }

    @Nested
    public class An_ArmyFile_properly_writes_an_army_to_new_file_if_it {
        @Test
        void saves_the_correct_information_for_a_special_unit() throws IOException {
            //Given/Arrange
            List<Unit> oneUnitList = new ArrayList<>();
            oneUnitList.add(new CommanderUnit("Queen", 50, 20, 10));
            Army army = new Army("Sarah's Army", oneUnitList);
            String expectedSecondLine = "CommanderUnit,Queen,50,20,10";

            File newFile = new File("Sarah Army");

            //When/Act
            armyFileHandler.createAndWriteNewArmyFile(army, newFile);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(newFile));
            String firstLine = bufferedReader.readLine();
            String actualSecondLine = bufferedReader.readLine();
            bufferedReader.close();

            //Then/Assert
            Assertions.assertEquals(expectedSecondLine, actualSecondLine);

            newFile.delete();
        }

        @Test
        void saves_the_correct_information_for_default_units() throws IOException {
            //Given/Arrange
            List<Unit> oneUnitList = new ArrayList<>();
            oneUnitList.add(new CommanderUnit("Queen", 50));
            Army army = new Army("Sarah's Army", oneUnitList);
            String expectedFirstLine = "Sarah's Army";
            String expectedSecondLine = "CommanderUnit,Queen,50";

            File newFile = new File("Sarah Army");

            //When/Act
            armyFileHandler.createAndWriteNewArmyFile(army, newFile);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(newFile));
            String firstLine = bufferedReader.readLine();
            String actualSecondLine = bufferedReader.readLine();
            bufferedReader.close();

            //Then/Assert
            Assertions.assertEquals(expectedSecondLine, actualSecondLine);

            newFile.delete();
        }

        @Test
        void saves_the_army_name_correctly() throws IOException {
            //Given/Arrange
            List<Unit> oneUnitList = new ArrayList<>();
            Army army = new Army("Sarah's Army", oneUnitList);
            String expectedFirstLine = "Sarah's Army";

            File newFile = new File("Sarah Army");

            //When/Act
            armyFileHandler.createAndWriteNewArmyFile(army, newFile);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(newFile));
            String actualFirstLine = bufferedReader.readLine();
            bufferedReader.close();

            //Then/Assert
            Assertions.assertEquals(expectedFirstLine, actualFirstLine);

            newFile.delete();
        }

        @Test
        void formats_the_information_using_CSV() throws IOException {
            //Given/Arrange
            List<Unit> twoUnitList = new ArrayList<>();
            twoUnitList.add(new CommanderUnit("Queen", 50, 20, 10));
            twoUnitList.add(new RangedUnit("Archer", 25));

            Army army = new Army("Sarah's Army", twoUnitList);
            String expectedFirstLine = "Sarah's Army";
            String expectedSecondLine = "CommanderUnit,Queen,50,20,10";
            String expectedThirdLine = "RangedUnit,Archer,25";

            File newFile = new File("Sarah Army");
            armyFileHandler.createAndWriteNewArmyFile(army, newFile);

            //When/Act
            BufferedReader bufferedReader = new BufferedReader(new FileReader(newFile));
            String actualFirstLine = bufferedReader.readLine();
            String actualSecondLine = bufferedReader.readLine();
            String actualThirdLine = bufferedReader.readLine();
            bufferedReader.close();


            //Then/Assert
            Assertions.assertEquals(expectedFirstLine, actualFirstLine);
            Assertions.assertEquals(expectedSecondLine, actualSecondLine);
            Assertions.assertEquals(expectedThirdLine, actualThirdLine);

            newFile.delete();
        }

    }

    @Nested
    public class An_ArmyFile_properly_overwrites_an_army_if {

        @Test
        void it_doesnt_add_on_to_but_changes_a_file_completely() throws IOException, InstantiationException {
            //Given/Arrange
            Army firstFileArmy = armyFileHandler.readFromArmyFile(getValidFile("Tryms Army"));
            long initialFileLength = getValidFile("Tryms Army").length();
            String initialFirstLine = "Trym's Army";

            List<Unit> changedArmyList = fillArmyList();
            changedArmyList.add(new CommanderUnit("Queen", 1000));
            Army changedArmy = new Army("Sarahs Army", changedArmyList);

            //When/Act
            armyFileHandler.overwriteExistingArmyFile(changedArmy, getValidFile("Tryms Army"));
            String expectedChangedName = "Sarahs Army";
            Army secondFileArmy = armyFileHandler.readFromArmyFile(getValidFile("Tryms Army"));

            BufferedReader bufferedReader = new BufferedReader(new FileReader(getValidFile("Tryms Army")));
            String overwrittenFirstLine = bufferedReader.readLine();
            bufferedReader.close();

            long overwrittenFileLength = getValidFile("Tryms Army").length();

            //Then/Assert
            Assertions.assertEquals(expectedChangedName, overwrittenFirstLine);
            assertNotEquals(firstFileArmy, secondFileArmy);
            Assertions.assertNotEquals(initialFirstLine, overwrittenFirstLine);
            Assertions.assertNotEquals(initialFileLength, overwrittenFileLength);

            //Resetting the start info
            armyFileHandler.overwriteExistingArmyFile(firstFileArmy, getValidFile("Tryms Army"));
        }

        @Test
        void it_successfully_writes_a_new_armys_info() throws IOException, InstantiationException {
            //Given/Arrange
            Army firstFileArmy = armyFileHandler.readFromArmyFile(getValidFile("Tryms Army"));
            List<Unit> changedArmyList = fillArmyList();
            changedArmyList.add(new CommanderUnit("Queen", 1000));
            Army changedArmy = new Army("Sarahs Army", changedArmyList);

            //When/Act
            armyFileHandler.overwriteExistingArmyFile(changedArmy, getValidFile("Tryms Army"));
            Army secondFileArmy = armyFileHandler.readFromArmyFile(getValidFile("Tryms Army"));

            //Then/Assert
            Assertions.assertTrue(changedArmy.equals(secondFileArmy));
            Assertions.assertFalse(secondFileArmy.equals(firstFileArmy));
            Assertions.assertNotEquals(firstFileArmy.getName(), secondFileArmy.getName());

            //Resetting the start info
            armyFileHandler.overwriteExistingArmyFile(firstFileArmy, getValidFile("Tryms Army"));
        }

        @Test
        void an_empty_army_will_write_only_name() throws IOException, InstantiationException {
            //Given/Arrange
            Army army = new Army("Human Army");
            File file = getValidFile("Empty Army Test");

            //When/Act
            armyFileHandler.createAndWriteNewArmyFile(army, file);
            Army expectedArmyWithoutUnits = armyFileHandler.readFromArmyFile(file);

            //Then/Assert
            Assertions.assertTrue(expectedArmyWithoutUnits.getAllUnits().isEmpty());
            file.delete();
        }

        @Test
        void it_cant_overwrite_a_non_existent_file() throws IOException {
            //Given/Arrange
            Army changedArmy = new Army("Sarahs Army", fillArmyList());

            //When/Act
            boolean canOverwrite = armyFileHandler.overwriteExistingArmyFile(changedArmy, getValidFile("Non Existent"));
            //Then/Assert
            Assertions.assertFalse(canOverwrite);

        }
    }

    @Nested
    public class An_ArmyFile_properly_reads_an_army_if_it {
        @Test
        void constructs_an_Army_correctly_when_read() throws IOException, InstantiationException {
            //Given/Arrange
            Army expectedArmy = new Army("Trym's Army", fillArmyList());

            //When/Act
            Army actualArmy = armyFileHandler.readFromArmyFile(getValidFile("Tryms Army"));

            //Then/Assert
            Assertions.assertTrue(actualArmy.equals(expectedArmy));
        }

        @Test
        void an_overwritten_file_can_be_read_successfully() throws IOException, InstantiationException {
            //Given/Arrange
            Army firstFileArmy = armyFileHandler.readFromArmyFile(getValidFile("Tryms Army"));

            List<Unit> expectedOverwrittenArmyList = fillArmyList();
            expectedOverwrittenArmyList.add(new CommanderUnit("Queen", 1000));
            Army expectedArmy = new Army("Sarahs Army", expectedOverwrittenArmyList);
            armyFileHandler.overwriteExistingArmyFile(expectedArmy, getValidFile("Tryms Army"));

            //When/Act
            Army overwrittenArmy = armyFileHandler.readFromArmyFile(getValidFile("Tryms Army"));

            //Then/Assert
            Assertions.assertTrue(overwrittenArmy.equals(expectedArmy));

            //Resetting the start info
            armyFileHandler.overwriteExistingArmyFile(firstFileArmy, getValidFile("Tryms Army"));
        }
    }

    @Nested
    public class An_ArmyFile_with_invalid_information_such_as {
        @Test
        void a_null_army_when_creating_new_file_will_throw_NullPointerException(){
            //Given/Arrange
            Army army = null;

            Assertions.assertThrows(NullPointerException.class, () ->{
                //When/Act
                armyFileHandler.createAndWriteNewArmyFile(army, getValidFile("Null Army Test"));
            }); //Then/Assert
        }

        @Test
        void a_null_army_when_overwriting_file_will_throw_NullPointerException() {
            //Given/Arrange
            Army army = null;

            Assertions.assertThrows(NullPointerException.class, () ->{
                //When/Act
                armyFileHandler.overwriteExistingArmyFile(army, getValidFile("Tryms Army"));
            }); //Then/Assert
        }

        @Test
        void corrupt_attribute_info_throws_NumberFormatException_when_read(){
            //Given/Arrange
            Army expectedArmy = new Army("Trym's Army", fillArmyList());

            Assertions.assertThrows(NumberFormatException.class, () -> {
                //When/Act
                Army actualArmy = armyFileHandler.readFromArmyFile(getValidFile("Corrupt Attributes"));
                assertNotEquals(expectedArmy, actualArmy);
            });//Then/Assert

        }

        @Test
        void file_with_corrupt_unit_class_throws_InstantiationException() {
            //Given/Arrange
            Army expectedArmy = new Army("Trym's Army", fillArmyList());

            //When/Act
            Assertions.assertThrows(InstantiationException.class, () ->{
                Army actualArmy = armyFileHandler.readFromArmyFile(getValidFile("Corrupt Unit Class"));
            }); //Then/Assert

            //TODO: Complete this test, when my own CorruptFileException is made
            //This test may be completed by asking asserting CorruptFileException was thrown
        }

        //TODO: change name to my exception
        @Test
        void file_with_improper_format_throws_StreamCorruptedException() throws IOException {
            //Given/Arrange
            Army expectedArmy = new Army("Trym's Army", fillArmyList());

            //When/Act
            Assertions.assertThrows(StreamCorruptedException.class, () ->{
                Army actualArmy = armyFileHandler.readFromArmyFile(getValidFile("Corrupt CSV Format"));
            }); //Then/Assert

            //TODO: Complete this test, when my own CorruptFileException is made
            //This test may be completed by asking asserting CorruptFileException was thrown
        }

    }

        //TODO: Test for all attributes and for when commas are used. What if a comma is used in the name?
        //TODO: Go over structure and make sure it is organized

}

//TODO: Make CorruptFileException, which can then be used to remove the corrupted file