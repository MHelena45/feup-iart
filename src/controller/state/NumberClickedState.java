package controller.state;

import controller.Controller;
import model.position.Position;

public class NumberClickedState extends ClickState {
    public NumberClickedState(Controller controller) {
        super(controller);
    }

    @Override
    public void numberClick(Position position) {
        if(position.equals(controller.previousClick)) {
            controller.setState(new IdleState(controller));
        } else {

        }
    }

    @Override
    public void regularClick(Position position) {
        // Check alignement with previous click
    }
}
