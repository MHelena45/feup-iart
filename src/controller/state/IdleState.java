package controller.state;

import controller.Controller;
import model.position.Position;

public class IdleState extends ClickState {
    public IdleState(Controller controller) {
        super(controller);
    }

    @Override
    public void numberClick(Position position) {
        controller.previousClick = position;
        controller.setState(new NumberClickedState(controller));
    }

    @Override
    public void regularClick(Position position) {
        // Ignore click
    }
}
