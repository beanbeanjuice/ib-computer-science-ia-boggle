package utilities.handlers;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class DistributionHandler {

    private ArrayList<String> distribution;

    // Constructor. Runs when class is created.
    public DistributionHandler() {
        distribution = new ArrayList<>();
        makeDistribution();
    }

    private void makeDistribution() {
        try (Scanner scanner = new Scanner(new File("src/main/resources/distribution.csv"))) {
            while (scanner.hasNextLine()) { // Goes through every line of the distribution
                distribution.add(getWord(scanner.nextLine())); // Adds the next line to the ArrayList
            }
        } catch (Exception e) {
            System.out.println("ERROR: No distributions found."); // Error message if file is missing.
        }
    }

    private String getWord(String word) {
        try (Scanner rowScanner = new Scanner(word)) {
            rowScanner.useDelimiter(","); // Removes the comma
            return word.toUpperCase(); // Capitalizes every character.
        }
    }

    public ArrayList<String> getDistribution() {
        return distribution;
    }
}
