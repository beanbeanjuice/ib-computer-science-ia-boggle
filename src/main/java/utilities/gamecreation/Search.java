package utilities.gamecreation;

import main.Main;

import java.util.ArrayList;

public class Search {

    private ArrayList<String> possibleWords;

    public Search() {
        possibleWords = new ArrayList<>();
    }

    public ArrayList<String> getPossibleWords(Game game) {

        // MAKES SURE THE RECURSIVE METHOD IS REPEATED FOR EVERY XY ON THE 2D ARRAY
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                getAllPossibleWords("", i, j, game.getBoard(), new boolean[4][4]);
            }
        }

        return possibleWords;
    }

    // RECURSIVE METHOD: CHECKS THE LETTER AT board[x][y] AND SEES ALL POSSIBLE WORDS THAT CAN BE MADE FROM IT
    public void getAllPossibleWords(String word, int x, int y, String[][] board, boolean[][] spotsTaken) {
        spotsTaken[x][y] = true; // Makes sure that those spots taken by the current letter are true.
        StringBuilder string = new StringBuilder(word); // Creates a new string builder. Basically word += board[x][y]
        word = string.append(board[x][y]).toString();

        if (word.length() >= 3 && Main.getDictionaryHandler().traverseDictionaryTree(word) && !possibleWords.contains(word)) {
            addWord(word);
        }

        // Checks all adjacent "boxes" for compatible letters.
        for (int i = x - 1; i <= x + 1 && i < 4; i++) {
            for (int j = y - 1; j <= y + 1 && j < 4; j++) {
                if (i >= 0 && j >= 0 && !spotsTaken[i][j]) {
                    if (Main.getDictionaryHandler().characterTraverseDictionaryTree(word)) { // Don't continue if no words found.
                        getAllPossibleWords(word, i, j, board, spotsTaken);
                    } else {
                        break; // No point in continuing and wasting time if it's not going to find words anyway.
                    }
                }
            }
        }

        spotsTaken[x][y] = false; // Resets it back for the use with the recursive statement later.

    }

    public void addWord(String word) {
        possibleWords.add(word);
    }

}
