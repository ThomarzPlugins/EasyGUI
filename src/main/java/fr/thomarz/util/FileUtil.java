package fr.thomarz.util;

import fr.thomarz.EasyGUI;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class FileUtil {

    /**
     * Get a list of string that is populated with each lines of a txt file,
     * if an error is occurred, it returns an empty list of string.
     *
     * @param fileName The name of the file to read
     * @return Each lines of the input fileName
     */
    public static List<String> readLines(String fileName) {
        List<String> lines = new ArrayList<>();
        try {
            InputStream inputStream = FileUtil.class.getClassLoader().getResourceAsStream(fileName);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
            }
        } catch (Exception e) {
            EasyGUI.error("Cannot read file: " + e.getMessage());
        }
        return lines;
    }
}