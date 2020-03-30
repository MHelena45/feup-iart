package search.aStar;

import model.state.State;
import search.Node;
import search.Play;
import search.SearchAlgorithm;
import search.heuristics.Heuristics;

import java.util.PriorityQueue;
import java.util.Stack;

public class A_STAR extends SearchAlgorithm {
    private PriorityQueue<Node> queue;

    public A_STAR(State firstState) {
        queue = new PriorityQueue<Node>(1, new Heuristics());
        Node root = new Node(null, firstState, null, 0, 0);
        queue.add(root);
    }

    private int evaluate(Node node) {
        if(node.isSolution()) return 100; // Solution must always be chosen

        int g = node.accCost;
        int h1 = Heuristics.fartherAway(node);
        int h2 = Heuristics.goalfrontPlay(node);
        int h3 = Heuristics.expandNowhere(node);

        return g + h1 + h2 + h3;
    }

    @Override
    public Stack<Play> solve() {
        while(!queue.isEmpty()) {
            // Starts with initial state
            Node v = queue.poll();

            // Execute solution testing
            if(v.isSolution()) {
                // Get the path to solution from the root
                return getPath(v);
            }

            // If solution was not found, then expand the node
            // and add its children to the queue
            expand(v);
            v.children.forEach(child -> {
                child.value = evaluate(child);
                queue.add(child);
            });
        }

        return null;
    }
}
