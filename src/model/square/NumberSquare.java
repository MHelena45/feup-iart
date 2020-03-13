package model.square;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.position.Position;

public class NumberSquare extends Square {
    private int number;

//    Rectangle rectangle = new Rectangle();

    public NumberSquare(int x, int y, int number) {
        super(x, y, true);
        this.number = number;
//        addEventHandler();
    }

//    public void addEventHandler() {
//        rectangle.setOnMouseClicked (new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(javafx.scene.input.MouseEvent e) {
//                System.out.println("Number model.square.Square pressed");
//            }
//        });
//    }

//    public Rectangle getRectangle(){
//        //Setting the properties of the rectangle
//        rectangle.setX(this.position.getX());
//        rectangle.setY(this.position.getY());
//        rectangle.setFill(Color.ORANGE);
//        rectangle.setWidth(25);
//        rectangle.setHeight(25);
//        return rectangle;
//    }


}