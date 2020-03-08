import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Number_Square extends Square {
    private int number;
    private Boolean played;
    Rectangle rectangle = new Rectangle();

    public Number_Square(int x, int y, int number) {
        super(x, y);
        this.number = number;
        this.played = false;
        addEventHandler();
    }

    public Number_Square(Position position, int number) {
        super(position);
        this.number = number;
        this.played = false;
        addEventHandler();
    }

    public void addEventHandler() {
        rectangle.setOnMouseClicked (new EventHandler<MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent e) {
                System.out.println("Number Square pressed");
                played = true;
            }
        });
    }

    public Rectangle getRectangle(){
        //Setting the properties of the rectangle
        rectangle.setX(this.position.getX());
        rectangle.setY(this.position.getY());
        rectangle.setFill(Color.ORANGE);
        rectangle.setWidth(25);
        rectangle.setHeight(25);
        return rectangle;
    }


}