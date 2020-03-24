package controller.state;

import controller.Controller;
import model.Operator;
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
            regularClick(position);
        }
    }

    @Override
    public void regularClick(Position position) {
        controller.currentClick = position;
        Operator operator = checkAlignment();

        if(operator == null) {
            controller.setState(new IdleState(controller));
            return;
        }

        controller.playSquare(operator);
    }
}
