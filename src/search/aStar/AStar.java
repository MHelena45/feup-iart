package search.aStar;

import model.square.Square;
import model.state.State;
import search.Node;
import search.Play;
import search.SearchAlgorithm;
import search.heuristics.Heuristics;

import javax.swing.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

public class AStar extends SearchAlgorithm {
    private static int expansions = 0;
    private PriorityQueue<Node> queue;

    public AStar(State firstState) {
        queue = new PriorityQueue<Node>(1, new Heuristics());
        Node root = new Node(null, firstState, null, 0, 0);
        queue.add(root);
    }

    private int evaluate(Node node) {
        if(node.state.getGoalSquare().isFilled()) return 100; // Solution must always be chosen

        int g = node.accCost;
        int h1 = Heuristics.fartherAway(node.play, node.state);
        int h2 = Heuristics.goalfrontPlay(node.play, node.state);
        int h3 = Heuristics.expandNowhere(node.play, node.state);
        // Note that only nodes that expand to useful places are evaluated
        return h1 + h2 + h3;
    }

    @Override
    protected void expand(Node node) {
        ArrayList<Square> squares = node.state.getPlayableSquares();

        for (Square square : squares) {
            for(int i = 0; i < operators.length; i++) {
                State newState = node.state.play(square.getX(), square.getY(), operators[i]);
                Play transition = new Play(square, operators[i]);
                int useful = Heuristics.expandNowhere(transition, newState);

                if(useful >= 0) {
                    Node newNode = new Node(node, newState, transition, node.accCost+1, node.depth+1);
                    node.children.add(newNode);
                }
            }
        }
    }

    @Override
    public Stack<Play> solve() {
        expansions = 0;

        while(!queue.isEmpty()) {
            // Starts with initial state
            Node v = queue.poll();
            expansions++;

            // Execute solution testing
            if(v.isSolution()) {
                System.out.println("Number of visited nodes: " + expansions);
                // Get the path to solution from the root
                return getPath(v);
            }

            // If solution was not found, then expand the node
            // and add its children to the queue
            expand(v);
            v.children.forEach(child -> {
                child.value = evaluate(child); // This way, value is f = g + h
                if(child.value >= 0)
                    queue.add(child);
            });
        }

        return null;
    }
}
