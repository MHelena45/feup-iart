package view.square;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.square.Square;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class NumberSquareView extends SquareView {
    public NumberSquareView(Square square) {
        super(square);
    }

    @Override
    public Rectangle getRectangle(){
        Rectangle rectangle = new Rectangle();
        //Setting the properties of the rectangle
        rectangle.setX(square.getX() * 27 + 90);
        rectangle.setY(square.getY() * 27 + 90);
        rectangle.setFill(Color.BLUE);
        rectangle.setWidth(25);
        rectangle.setHeight(25);
        addEventHandler(rectangle);
        return rectangle;
    }

    public void addEventHandler(Rectangle rectangle) {
        rectangle.setOnMouseClicked (new EventHandler<MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent e) {
                System.out.println("Number square pressed");
            }
        });
    }


}
