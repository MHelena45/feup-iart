package view.event;

import controller.Controller;
import javafx.scene.input.MouseEvent;

public class RegularSquareClick extends SquareClick {
    public RegularSquareClick(Controller controller, int x, int y) {
        super(controller, x, y);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        this.controller.regularClick(x, y);
    }
}
