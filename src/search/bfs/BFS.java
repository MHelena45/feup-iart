package search.bfs;

import model.state.State;
import search.NodeLW;
import search.Play;
import search.SearchAlgorithmLW;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class BFS extends SearchAlgorithmLW {
    private ArrayDeque<NodeLW> queue = new ArrayDeque<>();

    public BFS(State initialState) {
        NodeLW root = new NodeLW(null, null, null, 0, 0);
        this.initialState = initialState;
        s = initialState;
        playableSquares = s.getPlayableSquares();
        for (int sq = 0; sq < playableSquares.size(); sq++) {
            root.playedSquares.add(false);
        }
        queue.add(root);
    }

    @Override
    public ArrayList<Play> solve() {
        while(!queue.isEmpty()) {
            // Starts with initial state
            NodeLW v = queue.removeFirst();

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
