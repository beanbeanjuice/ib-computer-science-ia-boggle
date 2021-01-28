package utilities.handlers;

public class BoardCharacter {

    private int x; // X Position
    private int y; // Y Position
    String character; // Character Name

    public BoardCharacter(int x, int y, String character) {
        this.x = x;
        this.y = y;
        this.character = character;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getCharacter() {
        return character;
    }

}
