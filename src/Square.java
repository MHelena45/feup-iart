import javafx.scene.shape.Rectangle;

public class Square {
    private Position position;
    public Square(int x, int y) {
        position = new Position(x, y);
    }

    public Square(Position position) {
        this.position = position;
    }

    public Rectangle setRectangle(){
        //Drawing a Rectangle
        Rectangle rectangle = new Rectangle();

        //Setting the properties of the rectangle
        rectangle.setX(this.position.getX());
        rectangle.setY(this.position.getY());
        rectangle.setWidth(25);
        rectangle.setHeight(25);
        return rectangle;
    }
}
