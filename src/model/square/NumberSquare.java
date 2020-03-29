package model.square;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import static java.lang.Math.abs;

public class NumberSquare extends Square {
    private int number;
    private boolean played;
    private boolean clicked;

    public NumberSquare(int x, int y, int number) {
        super(x, y, true);
        this.number = number;
        this.played = false;
        this.clicked = false;
    }

    public void play() { played = true; }

    public void unplay() { played = false; }

    public void click() { clicked = !clicked; }

    @Override
    public boolean isPlayed() {
        return played;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public Paint getColor() {
        if (played) return Color.BLACK;
        if (clicked) return Color.BLUE;
        return Color.LIGHTBLUE;
    };

    public double getManhattanDistance(final Square square) {
        final double dx = abs(getX() - square.getX());
        final double dy = abs(getY() - square.getY());

        return (dx + dy - this.number);
    }

}