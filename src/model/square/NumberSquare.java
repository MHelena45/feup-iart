package model.square;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class NumberSquare extends Square {
    private int number;

    public NumberSquare(int x, int y, int number) {
        super(x, y, true);
        this.number = number;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public Paint getColor() { return isFilled() ? Color.BLACK : Color.BLUE; };

    @Override
    public String toString() {
        return "Number Square";
    }

}