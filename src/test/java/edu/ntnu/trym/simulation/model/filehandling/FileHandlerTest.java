package edu.ntnu.trym.simulation.model.filehandling;

import edu.ntnu.trym.simulation.model.Army;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.fail;

public class FileHandlerTest {

    public File getValidFile(String fileName) {
        return new File(FileSystems.getDefault()
                .getPath("src", "test", "resources", "armytestfiles", fileName) + ".csv");
    }

    @Nested
    public class The_FileHandler_makes_sure_a_file_name {

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

    @Nested
    public class The_FileHandler_can_check {
        @Test
        void if_a_file_exists(){
            //Given/Arrange
            boolean expectedStatus = true;

            //When/Act
            boolean actualStatusOfFile = FileHandler.fileExists(getValidFile("Tryms Army"));

            //Then/Assert
            Assertions.assertEquals(expectedStatus, actualStatusOfFile);
        }

        @Test
        void how_many_CSV_files_exist_in_a_directory() throws IOException {
            //Given/Arrange
            int expectedNumberOfFiles = 5;

            //When/Act
            int actualNumberOfFiles = getNumberOfCSVFilesTest();

            //Then/Assert
            Assertions.assertEquals(expectedNumberOfFiles, actualNumberOfFiles);

        }
    }

   /*
   Since the method in FileHandler is used to find the number for the dynamic army-files directory in src/main/java,
    this method was implemented to demonstrate the functionality.
    */

    int getNumberOfCSVFilesTest() throws IOException {
        AtomicInteger counter = new AtomicInteger();
        Stream<Path> fileWalk = Files.walk(FileSystems.getDefault().getPath("src", "test", "resources",
                "armytestfiles"));
        try(fileWalk){
            fileWalk.forEach(file -> {
                String fileName = file.getFileName().toString();
                String extensionText = fileName.substring(fileName.length() - 4);
                if(extensionText.equals(".csv")) counter.getAndIncrement();
            });
        }
        return counter.get();
    }

}
