package edu.ntnu.trym.simulation.model.filehandling;

import edu.ntnu.trym.simulation.model.Army;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;

import static org.junit.jupiter.api.Assertions.fail;

public class FileHandlerTest {

    @Nested
    public class A_file_name {

        //TODO: Check if file is closed, check if it can be renamed
        //Sending in a path which is not valid as a CSV but is a file
        //Check that file is saved to right directory
        //Do not make ArmyFileHandler static

        @ParameterizedTest(name = "{index}. File was named: {0}")
        @ValueSource(strings = {"$123test", "50%Off", "***army***", "Army?", "Orc Army!",
                "Human Army > Orc Army", "Army/military", "[ArmyFile]", "{ArmyFile}", "Trym's : Army"})
        void does_not_contain_special_characters(String fileName) {
            //Given/Arrange
            String expectedExceptionMessage = "The file name contains illegal characters.";

            //When/Act
            try {
                FileHandler.isFileNameValid(fileName);
            } catch (IllegalArgumentException e) {
                //Then/Assert
                Assertions.assertEquals(expectedExceptionMessage, e.getMessage());
            }
        }

        @ParameterizedTest(name = "{index}. File was named: {0}")
        @ValueSource(strings = {"", "  "})
        void is_not_empty_or_blank(String fileName){
            //Given/Arrange
            String expectedExceptionMessage = "The file name is blank/empty.";

            //When/Act
            try {
                FileHandler.isFileNameValid(fileName);
            } catch (IllegalArgumentException e) {
                //Then/Assert
                Assertions.assertEquals(expectedExceptionMessage, e.getMessage());
            }
        }

        @ParameterizedTest(name = "{index}. File was named: {0}")
        @ValueSource(strings = {"Sarahs army", "Army", "Human-Army", "OrcArmy123"})
        void only_contains_valid_characters(String fileName) {
            //Given/Arrange
            boolean expectedStatus = true;

            //When/Act
            boolean actualStatusOfFile = FileHandler.isFileNameValid(fileName);

            //Then/Assert
            Assertions.assertEquals(expectedStatus, actualStatusOfFile);
        }
    }




}
