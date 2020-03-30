package search.bfs;

import model.Operator;
import model.square.Square;
import model.state.State;
import search.MyNode;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class MyBFS {
    private ArrayDeque<MyNode> queue = new ArrayDeque<>();

    public MyBFS(State firstState) {
        MyNode root = new MyNode(null, firstState, null, 0, 0);
        queue.add(root);
    }

    private void expand(MyNode node) {
        ArrayList<Square> squares = node.state.getPlayableSquares();

        for (Square square : squares) {
            State left = node.state.play(square.getX(), square.getY(), Operator.LEFT);
            MyNode leftNode = new MyNode(node, left, Operator.LEFT, node.accCost+1, node.depth+1);
            node.children.add(leftNode);

            State right = node.state.play(square.getX(), square.getY(), Operator.RIGHT);
            MyNode rightNode = new MyNode(node, right, Operator.RIGHT, node.accCost+1, node.depth+1);
            node.children.add(rightNode);

            State up = node.state.play(square.getX(), square.getY(), Operator.UP);
            MyNode upNode = new MyNode(node, up, Operator.UP, node.accCost+1, node.depth+1);
            node.children.add(upNode);

            State down = node.state.play(square.getX(), square.getY(), Operator.DOWN);
            MyNode downNode = new MyNode(node, down, Operator.DOWN, node.accCost+1, node.depth+1);
            node.children.add(downNode);
        }
    }

    public ArrayDeque<State> solve() {
        while(!queue.isEmpty()) {
            // Starts with initial state
            MyNode v = queue.removeFirst();

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

    private ArrayDeque<State> getPath(MyNode node) {
        ArrayDeque<State> result = new ArrayDeque<>();

        do {
            result.push(node.state);
            node = node.parent;
        } while (node != null);

        return result;
    }

}
