package view.square;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.square.NumberSquare;

public class NumberSquareView {

    public Rectangle draw(NumberSquare square){
        Rectangle rectangle = new Rectangle();
        //Setting the properties of the rectangle
        rectangle.setX(square.getX());
        rectangle.setY(square.getY());
        rectangle.setFill(Color.BLACK);
        rectangle.setWidth(25);
        rectangle.setHeight(25);

//        Label number = new Label(Integer.toString(square.getNumber()));
//        StackPane stack = new StackPane();
//        stack.getChildren().addAll(rectangle, number);

        return rectangle;
    }
}
