package view.square;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.square.NumberSquare;
import model.square.Square;
import org.w3c.dom.Text;

public class SquareView {
    Square square;
    public SquareView(Square square) {
        this.square = square;
    }

    public Rectangle getRectangle(){
        Rectangle rectangle = new Rectangle();
        //Setting the properties of the rectangle
        rectangle.setX(square.getX() * 27 + 90);
        rectangle.setY(square.getY() * 27 + 90);
        if(square.toString() == "Goal Square")
            rectangle.setFill(Color.GRAY);
        else if(square.toString() == "Number Square"){
            rectangle.setFill(Color.CORAL);
        }
        else rectangle.setFill(Color.BLACK);
        rectangle.setWidth(25);
        rectangle.setHeight(25);

        return rectangle;
    }
}
