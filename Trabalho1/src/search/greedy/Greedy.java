package search.greedy;

import model.square.Square;
import model.state.State;
import search.Node;
import search.Play;
import search.SearchAlgorithm;
import search.heuristics.Heuristics;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Greedy extends SearchAlgorithm {
    private PriorityQueue<Node> queue;

    public Greedy(State firstState) {
        queue = new PriorityQueue<Node>(1, new Heuristics());
        Node root = new Node(firstState, new ArrayDeque<>(), 0, 0);
        queue.add(root);
    }

    private int evaluate(Node node) {
        if(node.state.getGoalSquare().isFilled()) return 100; // Solution must always be chosen

        int h1 = Heuristics.fartherAway(node.getLastPlay(), node.state);
        int h2 = Heuristics.goalfrontPlay(node.getLastPlay(), node.state);
        int h3 = Heuristics.expandNowhere(node.getLastPlay(), node.state);
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
                    ArrayDeque<Play> newPlays = new ArrayDeque<>();
                    node.playsMade.forEach(oldPlay -> newPlays.addLast(oldPlay));
                    newPlays.addLast(transition);

                    Node newNode = new Node(newState, newPlays, node.accCost+1, node.depth+1);
                    node.children.add(newNode);
                }
            }
        }
    }

    @Override
    protected boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    protected Node getNextNode() {
        return queue.poll();
    }

    @Override
    protected void addChildren(ArrayList<Node> children) {
        children.forEach(child -> {
            child.value = evaluate(child); // This way, value is f = g + h
            if(child.value >= 0)
                queue.add(child);
        });
    }
}
