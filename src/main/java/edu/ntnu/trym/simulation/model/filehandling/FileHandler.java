package edu.ntnu.trym.simulation.model.filehandling;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * This class contains methods that are use generally through all file handling such as getting a file source path,
 * check if a name of a file is valid, and calculating the number of CSV files in a directory. This class provides
 * general utility for other more specific file handlers.
 */
public class FileHandler {

    private static final Pattern validFileCharacters = Pattern.compile("^[\\w- ]*$");

    /**
     * This method takes in a file name and checks whether it is valid using a pre-set regex pattern.
     * @param fileName                  Name of the file to be created, represented as a String
     * @return                          A boolean representing whether the file name is valid {@code true} if it is.
     * @throws IllegalArgumentException If the file name sent in is invalid, then an IllegalArgumentException is thrown
     */
    public static boolean isFileNameValid(String fileName) throws IllegalArgumentException{
        Matcher matcher = validFileCharacters.matcher(fileName);
        if(!matcher.matches()) throw new IllegalArgumentException("The file name contains illegal characters.");
        else if(fileName.isEmpty() || fileName.isBlank()) throw new IllegalArgumentException("The file name is blank/empty.");
        return true;
    }

    /**
     * This method checks if a file with a given directory path already exists and if it contains any information.
     * @param fileInQuestion The file that is to be checked, represented using a File object
     * @return               If the file contains no information, {@code false} is returned. Else, {@code true} is returned
     */
    public static boolean fileExists(File fileInQuestion){
        return fileInQuestion.length() > 0;
    }

    /**
     * This method retrieves the file source path of a csv file with the given file name.
     * @param fileName The name of the desired file, represented as a String
     * @return         The source path to the file, represented as a String
     */
    public static String getFileSourcePath(String fileName){
        return FileSystems.getDefault().getPath("src", "main", "resources", "army-files", fileName) + ".csv";
    }

    /**
     * This method finds counts all the CSV files present in the directory called army-files.
     * @return Number of CSV files, represented as an int
     */
    public static int getNumberOfCSVFiles() throws IOException {
        AtomicInteger counter = new AtomicInteger();
        Stream<Path> fileWalk = Files.walk(FileSystems.getDefault().getPath("src", "main", "resources", "army-files"));
        try(fileWalk){
            fileWalk.forEach(file -> {
                if(file.endsWith(".csv")) counter.getAndIncrement();
            });
        }
        return counter.get();
    }
    //TODO: test that the number returned is correct and that it only counts CSV Files


//    /**
//     * This method checks if a given File's path ends with .csv, therefore making it a CSV file.
//     * @param file  The file to be checked, represented as a File object.
//     * @return      A boolean stating if the path contains .csv {@code true} or if it does not {@code false}.
//     */
//    public boolean endsWithCSV(File file){
//        String fileName = file.getName();
//    }
    //TODO: Test that this actually returns the correct booleans.


}
