package search;

import model.Operator;
import model.state.State;

import java.util.ArrayList;

public class MyNode {
    public MyNode parent;
    public Play play;
    public ArrayList<MyNode> children;
    public State state;
    public int accCost;
    public int depth;

    public MyNode(MyNode parent, State state, Play play, int accCost, int depth) {
        this.parent = parent;
        this.state = state;
        this.play = play;
        this.accCost = accCost;
        this.depth = depth;
        this.children = new ArrayList<>();
    }

    public boolean isSolution() {
        return state.getGoalSquare().isFilled();
    }
}
