package view.square;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.square.Square;

public class GoalSquareView extends SquareView{

    public GoalSquareView(Square square) {
        super(square);
    }

    public Rectangle getRectangle(){
        Rectangle rectangle = new Rectangle();
        //Setting the properties of the rectangle
        rectangle.setX(square.getX() * 27 + 90);
        rectangle.setY(square.getY() * 27 + 90);
        rectangle.setFill(Color.GRAY);
        rectangle.setWidth(25);
        rectangle.setHeight(25);
        return rectangle;
    }

}
