package search;

import model.state.State;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Node {
    public ArrayDeque<Play> playsMade;
    public ArrayList<Node> children;
    public State state;
    public int accCost;
    public int depth;
    public int value;

    public Node(State state, ArrayDeque<Play> play, int accCost, int depth) {
        this.state = state;
        this.playsMade = play;
        this.accCost = accCost;
        this.depth = depth;
        this.children = new ArrayList<>();
        this.value = 0;
    }

    public Play getLastPlay() {
        return playsMade.peekLast();
    }

    public boolean isSolution() {
        return state.getGoalSquare().isFilled();
    }

    @Override
    public String toString() {
        Play play = getLastPlay();

        String msg = play == null ? "" : (play.toString() + " with");

        return msg + " Value = " + value;
    }
}
