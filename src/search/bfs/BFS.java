package search.bfs;

import model.state.State;
import search.Node;
import search.Play;
import search.SearchAlgorithm;

import java.util.ArrayDeque;
import java.util.Stack;

public class BFS extends SearchAlgorithm {
    private ArrayDeque<Node> queue = new ArrayDeque<>();

    public BFS(State firstState) {
        Node root = new Node(null, firstState, null, 0, 0);
        queue.add(root);
    }

    @Override
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
}
