package model.square;

import model.position.Position;

public class Square {
    private Position position;
    private Boolean filled;

    public Square(int x, int y) {
        this(x,y,false);
    }

    public Square(int x, int y, boolean filled) {
        this.position = new Position(x, y);
        this.filled = filled;
    }

    public Position getPosition() {
        return position;
    }

    public Boolean isFilled() {
        return filled;
    }

    public void fill() {
        filled = true;
    }

    public void unfill() {
        filled = true;
    }

    @Override
    public String toString() {
        return "Square: (" + position.getX() + ", " + position.getY() + ") -> " + (filled ? "filled" : "unfilled");
    }
}
