package search.bfs;

import model.state.State;
import search.Node;
import search.Play;
import search.SearchAlgorithm;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class BFS extends SearchAlgorithm {
    private ArrayDeque<Node> queue = new ArrayDeque<>();

    public BFS(State initialState) {
        Node root = new Node(null, null, 0, 0);
        queue.add(root);
        this.initialState = initialState;
        s = initialState;
        playableSquares = s.getPlayableSquares();
    }

    @Override
    public ArrayList<Play> solve() {
        while(!queue.isEmpty()) {
            // Starts with initial state
            Node v = queue.removeFirst();

            // Execute solution testing
            if(atSolution(v.playsMade)) {
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
