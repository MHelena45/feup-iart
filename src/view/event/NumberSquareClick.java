package view.event;

import controller.Controller;
import javafx.scene.input.MouseEvent;

public class NumberSquareClick extends SquareClick {
    public NumberSquareClick(Controller controller, int x, int y) {
        super(controller, x, y);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        this.controller.numberClick(x, y);
    }
}
