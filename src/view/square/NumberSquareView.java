package view.square;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import view.SquareView;

public class NumberSquareView extends SquareView {
    public NumberSquareView(Group group) {
        super(group);
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
