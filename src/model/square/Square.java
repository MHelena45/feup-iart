package model.square;

import model.position.Position;

public class Square {
    private Position position;
    private Boolean filled;

    public Square(int x, int y) {
        this(x, y, false);
    }

    public Square(int x, int y, boolean filled) {
        this.position = new Position(x, y);
        this.filled = filled;
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public Boolean isFilled() {
        return filled;
    }

    public void play() {}

    public void undo() {}

    public void click() {}

    public boolean isPlayed() { return true; }

    public void fill() {
        filled = true;
    }

    public void clear() {
        filled = false;
    }

    public int getNumber() { return 0; }

    @Override
    public String toString() {
        return "Square: (" + position.getX() + ", " + position.getY() + ") -> " + (filled ? "filled" : "unfilled");
    }
}
