package view;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import model.square.Square;

public class SquareView {
    protected final int offset = 90;
    private Group group;

    public SquareView(Group group) {
        this.group = group;
    }

    public Rectangle drawRectangle(Square square){
        Rectangle rectangle = new Rectangle();

        //Setting the properties of the rectangle
        rectangle.setX(square.getX() * 27 + offset);
        rectangle.setY(square.getY() * 27 + offset);

        rectangle.setFill(square.getColor());

        rectangle.setWidth(25);
        rectangle.setHeight(25);

        group.getChildren().add(rectangle);
        return rectangle;
    }

}
