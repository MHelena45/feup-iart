package model.square;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

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

    @Override
    public String toString() {
        return "Number Square";
    }

}