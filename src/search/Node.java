package search;

import model.state.State;

import java.util.ArrayList;

public class Node {
    public Node parent;
    public Play play;
    public ArrayList<Node> children;
    public State state;
    public int accCost;
    public int depth;

    public Node(Node parent, State state, Play play, int accCost, int depth) {
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

    public State getState() {
        return state;
    }
}
