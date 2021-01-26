package utilities.handlers;

import main.Main;

import java.util.ArrayList;
import java.util.Random;

public class BoardHandler {

    private String[][] board;
    private DistributionHandler distributionHandler;

    public BoardHandler(DistributionHandler distributionHandler) {
        this.distributionHandler = distributionHandler;
    }

    public String[][] randomize() {
        String[][] board = new String[4][4];
        ArrayList<String> letters = new ArrayList<>();
        Random rand = new Random();
        int random;
        int count = 0;

        // CHOOSES A RANDOM LETTER FROM EACH LETTER DISTRIBUTION FOUND IN THE .CSV FILE
        while (count < 16) {
            random = rand.nextInt(6);

            // Splits the letter distribution into its individual letters.
            String letter = distributionHandler.getDistribution().get(count++).split("")[random];

            if (letter.equals("Q")) { // Case for when the letter is "Q" to add a "u" at the end.
                letter += "U";
            }

            letters.add(letter);
        }

        ArrayList<String> tmp = new ArrayList<>();

        // LETTERS ARE PARTIALLY RANDOMISED. THIS PLACES THE LETTERS "FULLY" RANDOMLY IN A NEW ARRAYLIST.
        while (letters.size() != 0) {
            random = rand.nextInt(letters.size());
            tmp.add(letters.get(random));
            letters.remove(random); // Removes the letters from the original list.
        }

        count = 0; // Resets the "count" parameter to be re-used.

        // PLACES THE RANDOM LETTERS FROM THE NEW ARRAYLIST INTO A 2D ARRAY.
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = tmp.get(count++);
            }
        }
        return board;
    }

    public String[][] getBoard() {
        return board;
    }

}
