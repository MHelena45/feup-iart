package search.aStar;

import model.state.State;
import search.NodeLW;
import search.Play;
import search.SearchAlgorithmLW;
import search.heuristics.Heuristics;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class A_STAR extends SearchAlgorithmLW {
    private static int expansions = 0;
    private PriorityQueue<NodeLW> queue;

    public A_STAR(State initialState) {
        queue = new PriorityQueue<NodeLW>(1, new Heuristics());
        NodeLW root = new NodeLW(null, null, null, 0, 0);
        this.initialState = initialState;
        playableSquares = initialState.getPlayableSquares();
        for (int sq = 0; sq < playableSquares.size(); sq++) {
            root.playedSquares.add(false);
        }
        queue.add(root);
    }

    private int evaluate(NodeLW nodeLW) {
        if(atSolution(nodeLW.playsMade)) return 100; // Solution must always be chosen

        int g = nodeLW.accCost;
        int h1 = Heuristics.fartherAway(nodeLW);
        int h2 = Heuristics.goalfrontPlay(nodeLW);
        int h3 = Heuristics.expandNowhere(nodeLW);

        if(h3 < 0) //The expansion goes nowhere
            return h3;

        return g + h1 + h2 + h3;
    }

    @Override
    public ArrayList<Play> solve() {
        expansions = 0;

        while(!queue.isEmpty()) {
            // Starts with initial state
            NodeLW v = queue.poll();
            expansions++;

            // Execute solution testing
            if(atSolution(v.playsMade)) {
                System.out.println("Number of visited nodes: " + expansions);
                // Get the path to solution from the root
                return getPath(v);
            }

            // If solution was not found, then expand the node
            // and add its children to the queue
            expand(v);
            v.children.forEach(child -> {
                child.value = evaluate(child);
                if(child.value >= 0)
                    queue.add(child);
            });
        }

        return null;
    }
}
