package view;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.square.Square;

import java.awt.*;

public class SquareView {
    private final int offset = 80;
    private Group group;

    public SquareView(Group group) {
        this.group = group;
    }

    public Group drawRectangle(Square square){
        Group cell = new Group();
        Rectangle rectangle = new Rectangle();

        //Setting the properties of the rectangle
        rectangle.setX(square.getX() * 30 + offset);
        rectangle.setY(square.getY() * 30 + offset/2);

        rectangle.setFill(square.getColor());

        rectangle.setWidth(29);
        rectangle.setHeight(29);

        cell.getChildren().add(rectangle);

        if(square.getNumber() > 0){
            Text t = new Text(square.getX() * 30 + offset + 10, square.getY() * 30 + offset/2 + 19,  String.valueOf(square.getNumber()));
            t.setFont(Font.font ("Verdana", 17));
            cell.getChildren().add(t);
        }

        group.getChildren().add(cell);
        return cell;
    }

}