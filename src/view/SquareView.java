package view;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.square.Square;

import java.awt.*;

public class SquareView {
    protected final int offset = 80;
    protected final int square_size = 30;
    private Group group;

    public SquareView(Group group) {
        this.group = group;
    }

    public Rectangle drawRectangle(Square square){
        Rectangle rectangle = new Rectangle();

        //Setting the properties of the rectangle
        rectangle.setX(square.getX() * square_size + offset);
        rectangle.setY(square.getY() * square_size + offset/2);

        rectangle.setFill(square.getColor());

        rectangle.setWidth(square_size - 1);
        rectangle.setHeight(square_size - 1);

        group.getChildren().add(rectangle);

        if(square.toString() == "Number Square"){
            Text t = new Text(square.getX() * square_size + offset + 15, square.getY() * square_size + offset/2 + 15,  String.valueOf(square.getNumber()));
            t.setFont(Font.font ("Verdana", square_size/3));
            group.getChildren().add(t);
        }

        return rectangle;
    }

}
