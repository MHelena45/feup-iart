package model.square;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.position.Position;

public class GoalSquare extends Square {
    public GoalSquare(int x, int y) {
        super(x, y);
    }

    /* Returns the string representation of the GoalSquare*/
    @Override
    public String toString() {
        return "Goal Square";
    }



}
