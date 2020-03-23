package view.event;

import controller.Controller;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public abstract class SquareClick implements EventHandler<MouseEvent> {
    protected Controller controller;
    protected int x;
    protected int y;

    protected SquareClick(Controller controller, int x, int y) {
        this.controller = controller;
        this.x = x;
        this.y = y;
    }
}
