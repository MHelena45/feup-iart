package search.aStar;

import model.state.State;
import search.Node;
import search.Play;
import search.SearchAlgorithm;
import search.heuristics.Heuristics;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class A_STAR extends SearchAlgorithm {
    private static int expansions = 0;
    private PriorityQueue<Node> queue;

    public A_STAR(State initialState) {
        queue = new PriorityQueue<Node>(1, new Heuristics());
        Node root = new Node(null, null, 0, 0);
        queue.add(root);
        this.initialState = initialState;
        s = initialState;
        playableSquares = s.getPlayableSquares();
    }

    private int evaluate(Node node) {
        if(atSolution(node.playsMade)) return 100; // Solution must always be chosen

        int g = node.accCost;
        int h1 = Heuristics.fartherAway(node);
        int h2 = Heuristics.goalfrontPlay(node);
        int h3 = Heuristics.expandNowhere(node);

        if(h3 < 0) //The expansion goes nowhere
            return h3;

        return g + h1 + h2 + h3;
    }

    @Override
    public ArrayList<Play> solve() {
        expansions = 0;

        while(!queue.isEmpty()) {
            // Starts with initial state
            Node v = queue.poll();
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
