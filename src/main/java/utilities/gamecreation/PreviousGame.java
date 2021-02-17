package utilities.gamecreation;

import java.util.ArrayList;
import java.util.Arrays;

public class PreviousGame {

    private final int gameID;
    private String board = "";
    private String[][] boardArray;
    private final int score;
    private final double timeTaken;
    private final double totalTime;
    private final int wordsFound;
    private final int totalWords;
    private final ArrayList<String> wordsFoundArrayList;
    private final ArrayList<String> totalWordsArrayList;
    private final String wordsFoundStringList;
    private final String totalWordsStringList;

    // Creating a previous game from app
    public PreviousGame(int gameID, String[][] board, int score,
                        double timeTaken, double totalTime,
                        int wordsFound, int totalWords,
                        ArrayList<String> wordsFoundArrayList, ArrayList<String> totalWordsArrayList) {

        this.gameID = gameID;
        this.boardArray = board;
        arrayToString(boardArray);
        this.score = score;
        this.timeTaken = timeTaken;
        this.totalTime = totalTime;
        this.wordsFound = wordsFound;
        this.totalWords = totalWords;
        this.wordsFoundArrayList = wordsFoundArrayList;
        this.totalWordsArrayList = totalWordsArrayList;

        wordsFoundStringList = arrayListToString(wordsFoundArrayList);
        totalWordsStringList = arrayListToString(totalWordsArrayList);
    }

    // Creating a previous game from database
    public PreviousGame(int gameID, String board, int score,
                        double timeTaken, double totalTime,
                        int wordsFound, int totalWords,
                        String wordsFoundStringList, String totalWordsStringList) {

        this.gameID = gameID;
        this.board = board;
        stringToArray(board);
        this.score = score;
        this.timeTaken = Math.round((timeTaken/60) * 100.0) / 100.0;
        this.totalTime = Math.round((totalTime/60) * 100.0) / 100.0;
        this.wordsFound = wordsFound;
        this.totalWords = totalWords;
        this.wordsFoundStringList = wordsFoundStringList;
        this.totalWordsStringList = totalWordsStringList;
        wordsFoundArrayList = stringListToArray(wordsFoundStringList);
        totalWordsArrayList = stringListToArray(totalWordsStringList);
    }

    private ArrayList<String> stringListToArray(String string) {
        return new ArrayList<>(Arrays.asList(string.split(",")));
    }

    private String arrayListToString(ArrayList<String> arrayList) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String string : arrayList) {
            stringBuilder.append(string);
            stringBuilder.append(",");
        }

        return stringBuilder.toString();
    }

    private void stringToArray(String board) {
        boardArray = new String[4][4];
        int count = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                // Base case to check that, if it is "Q" always add a "U"
                if (String.valueOf(board.charAt(count)).equalsIgnoreCase("Q") && String.valueOf(board.charAt(count+1)).equals("U")) {
                    boardArray[i][j] = String.valueOf(board.charAt(count)) + board.charAt(count + 1);
                    count += 2;
                } else {
                    boardArray[i][j] = String.valueOf(board.toCharArray()[count++]);
                }
            }
        }
    }

    private void arrayToString(String[][] array) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board += array[i][j];
            }
        }
    }

    public String getFoundWordsStringList() {
        return wordsFoundStringList;
    }

    public ArrayList<String> getFoundWordsArrayList() {
        return wordsFoundArrayList;
    }

    public String getTotalWordsStringList() {
        return totalWordsStringList;
    }

    public ArrayList<String> getTotalWordsArrayList() {
        return totalWordsArrayList;
    }

    public int getGameID() {
        return gameID;
    }

    public String[][] getBoardArray() {
        return boardArray;
    }

    public String getBoard() {
        return board;
    }

    public int getScore() {
        return score;
    }

    public double getTimeTaken() {
        return timeTaken;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public int getWordsFound() {
        return wordsFound;
    }

    public int getTotalWords() {
        return totalWords;
    }

}
