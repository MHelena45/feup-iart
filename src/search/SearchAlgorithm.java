package search;

import model.Operator;
import model.square.Square;
import model.state.State;

import java.util.ArrayList;
import java.util.Stack;

public abstract class SearchAlgorithm {
    protected Operator[] operators = {Operator.UP, Operator.DOWN, Operator.LEFT, Operator.RIGHT};

    protected void expand(Node node) {
        ArrayList<Square> squares = node.state.getPlayableSquares();

        for (Square square : squares) {
            for(int i = 0; i < operators.length; i++) {
                State newState = node.state.play(square.getX(), square.getY(), operators[i]);
                Play transition = new Play(square, operators[i]);
                Node newNode = new Node(node, newState, transition, node.accCost+1, node.depth+1);
                node.children.add(newNode);
            }
        }
    }

    protected Stack<Play> getPath(Node node) {
        Stack<Play> result = new Stack<>();

        do {
            result.push(node.play);
            node = node.parent;
        } while (node.play != null);

        return result;
    }

    public Stack<Play> solve() {
        while(!isEmpty()) {
            // Starts with initial state
            Node v = getNextNode();

            // Execute solution testing
            if(v.isSolution()) {
                // Get the path to solution from the root
                return getPath(v);
            }

            // If solution was not found, then expand the node
            // and add its children to the queue
            expand(v);
            addChildren(v.children);
        }

        return null;
    }

    //Methods each algorithm has to define
    protected abstract boolean isEmpty();

    protected abstract Node getNextNode();

    protected abstract void addChildren(ArrayList<Node> node);
}
