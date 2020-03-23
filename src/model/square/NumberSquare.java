package model.square;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class NumberSquare extends Square {
    private int number;
    private boolean played;

    public NumberSquare(int x, int y, int number) {
        super(x, y, true);
        this.number = number;
        this.played = false;
    }

    public void play() { played = true; }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public Paint getColor() { return played ? Color.BLACK : Color.BLUE; };

    @Override
    public String toString() {
        return "Number Square";
    }

}