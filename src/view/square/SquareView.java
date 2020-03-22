package view.square;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.square.Square;

public class SquareView extends Node {
    Square square;
    public SquareView(Square square) {
        this.square = square;
    }

    public Rectangle getRectangle(){
        Rectangle rectangle = new Rectangle();
        //Setting the properties of the rectangle
        rectangle.setX(square.getX() * 27 + 90);
        rectangle.setY(square.getY() * 27 + 90);
        if(square.isFilled())
            rectangle.setFill(Color.BLACK);
        else rectangle.setFill(Color.GREEN);
        rectangle.setWidth(25);
        rectangle.setHeight(25);
        return rectangle;
    }

}
