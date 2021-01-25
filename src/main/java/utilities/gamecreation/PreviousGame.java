package utilities.gamecreation;

public class PreviousGame {

    private final int gameID;
    private String board = "";
    private String[][] boardArray;
    private final int score;
    private final double timeTaken;
    private final double totalTime;
    private final int wordsFound;
    private final int totalWords;

    public PreviousGame(int gameID, String[][] board, int score,
                        double timeTaken, double totalTime,
                        int wordsFound, int totalWords) {

        this.gameID = gameID;
        this.boardArray = board;
        arrayToString(boardArray);
        this.score = score;
        this.timeTaken = timeTaken;
        this.totalTime = totalTime;
        this.wordsFound = wordsFound;
        this.totalWords = totalWords;
    }

    public PreviousGame(int gameID, String board, int score,
                        double timeTaken, double totalTime,
                        int wordsFound, int totalWords) {

        this.gameID = gameID;
        this.board = board;
        stringToArray(board);
        this.score = score;
        this.timeTaken = timeTaken;
        this.totalTime = totalTime;
        this.wordsFound = wordsFound;
        this.totalWords = totalWords;
    }

    private void stringToArray(String board) {
        boardArray = new String[4][4];
        int count = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                boardArray[i][j] = String.valueOf(board.toCharArray()[count++]);
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

    public int getGameID() {
        return gameID;
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
