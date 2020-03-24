package controller.state;

import controller.Controller;
import model.position.Position;

public abstract class ClickState {
    protected Controller controller;

    protected ClickState(Controller controller) {
        this.controller = controller;
    }

    public abstract void numberClick(Position position);

    public abstract void regularClick(Position position);
}
