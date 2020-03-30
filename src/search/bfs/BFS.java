package search.bfs;

import model.Operator;
import model.square.Square;
import model.state.State;
import search.Node;
import search.Play;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Stack;

public class BFS {
    private ArrayDeque<Node> queue = new ArrayDeque<>();
    private Operator[] operators = {Operator.UP, Operator.DOWN, Operator.LEFT, Operator.RIGHT};

    public BFS(State firstState) {
        Node root = new Node(null, firstState, null, 0, 0);
        queue.add(root);
    }

    private void expand(Node node) {
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

    public Stack<Play> solve() {
        while(!queue.isEmpty()) {
            // Starts with initial state
            Node v = queue.removeFirst();

            // Execute solution testing
            if(v.isSolution()) {
                // Get the path to solution from the root
                return getPath(v);
            }

            // If solution was not found, then expand the node
            // and add its children to the queue
            expand(v);
            v.children.forEach(child -> queue.addLast(child));
        }

        return null;
    }

    private Stack<Play> getPath(Node node) {
        Stack<Play> result = new Stack<>();

        do {
            result.push(node.play);
            node = node.parent;
        } while (node.play != null);

        return result;
    }

}
