package controller.state;

import controller.Controller;
import model.Operator;
import model.position.Position;

public abstract class ClickState {
    protected Controller controller;

    protected ClickState(Controller controller) {
        this.controller = controller;
    }

    /**
     * checkAlignment compares the positions between two clicks to check
     * which operation was issued by the user
     *
     * @return Operator if the click was not in a diagonal; null otherwise.
     */
    protected Operator checkAlignment() {
        Position prev = controller.previousClick;
        Position curr = controller.currentClick;
        Operator result = null;

        // Note that this condition is only called when prev != curr
        if(curr.getY() == prev.getY()) { //both clicks on the same line
            if (curr.getX() < prev.getX()) result = Operator.LEFT;
            else result = Operator.RIGHT;
        } else if(curr.getX() == prev.getX()) { //both clicks on the same column
            if (curr.getY() < prev.getY()) result = Operator.UP;
            else result = Operator.DOWN;
        }

        return result;
    }

    public abstract void numberClick(Position position);

    public abstract void regularClick(Position position);
}
