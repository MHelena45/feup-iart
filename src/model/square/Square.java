package model.square;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.position.Position;

import java.util.Comparator;

import static java.lang.Math.abs;

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

    public void unplay() {}

    public void click() {}

    public boolean isPlayed() { return true; }

    public void fill() {
        filled = true;
    }

    public void clear() {
        filled = false;
    }

    public Paint getColor() {
        return filled ? Color.BLACK : Color.GREEN;
    }

    public int getNumber() { return 0; }

    @Override
    public String toString() {
        return "Square: (" + position.getX() + ", " + position.getY() + ") -> " + (filled ? "filled" : "unfilled");
    }
}
