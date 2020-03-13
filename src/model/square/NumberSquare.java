package model.square;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.position.Position;

public class NumberSquare extends Square {
    private int number;

    public NumberSquare(int x, int y, int number) {
        super(x, y, true);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

//    public void addEventHandler() {
//        rectangle.setOnMouseClicked (new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(javafx.scene.input.MouseEvent e) {
//                System.out.println("Number model.square.Square pressed");
//            }
//        });
//    }
}