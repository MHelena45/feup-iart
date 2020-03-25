package model.square;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GoalSquare extends Square {
    public GoalSquare(int x, int y) {
        super(x, y);
    }

    @Override
    public Paint getColor () {
        return isFilled() ? Color.BLACK : Color.GRAY;
    }
}
