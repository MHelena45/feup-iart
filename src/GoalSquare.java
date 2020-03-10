import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GoalSquare extends Square {
    public GoalSquare(int x, int y) {
        super(x, y);
    }

    public GoalSquare(Position position) {
        super(position);
    }

    public Boolean getFill() {
        return fill;
    }

    public Rectangle getRectangle(){
        //Drawing a Rectangle
        Rectangle rectangle = new Rectangle();

        //Setting the properties of the rectangle
        rectangle.setX(this.position.getX());
        rectangle.setY(this.position.getY());
        if(fill)
            rectangle.setFill(Color.BLACK);
        else rectangle.setFill(Color.WHITE);
        rectangle.setWidth(25);
        rectangle.setHeight(25);
        return rectangle;
    }


}
