package search.lightAstar;

import model.Operator;
import model.square.GoalSquare;
import model.square.Square;
import model.state.State;
import search.Play;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

public class LightAStar {
    private Operator[] operators = {Operator.UP, Operator.DOWN, Operator.LEFT, Operator.RIGHT};
    private PriorityQueue<LightNode> queue;
    private State initalState;
    private State currentState;

    public LightAStar(State initialState) {
        this.initalState = initialState;
        this.currentState = null;
        GoalSquare goal = new GoalSquare(initialState.getGoalSquare().getX(), initialState.getGoalSquare().getY());
        LightNode root = new LightNode(new ArrayDeque<>(), 0, goal, initialState.getPlayableSquares());
        queue = new PriorityQueue<LightNode>(1, new LightHeuristics());
        queue.add(root);
    }

    protected void expand(LightNode node) {
        ArrayList<Square> squares = node.remainingSquares;

        for (Square square : squares) {
            for(int i = 0; i < operators.length; i++) {
//                State newState = node.state.play(square.getX(), square.getY(), operators[i]);
//                int useful = LightHeuristics.expandNowhere(transition, newState);
                Play play = new Play(square, operators[i]);

//                if(useful >= 0) {
                LightNode newNode = newLightNode(node, play);
                node.children.add(newNode);
//                }
            }
        }
    }

    private LightNode newLightNode(LightNode node, Play play) {
        ArrayDeque<Play> newPlays = new ArrayDeque<>();
        node.playsMade.forEach(oldPlay -> newPlays.push(oldPlay));
        newPlays.push(play);

        ArrayList<Square> newSquares = new ArrayList<>();
        node.remainingSquares.forEach(square -> {
            if(!square.equals(play.getNumberSquare()))
                newSquares.add(square);
        });

        GoalSquare goal = new GoalSquare(node.goalSquare.getX(), node.goalSquare.getY());

        return new LightNode(newPlays, node.cost + 1, goal, newSquares);
    }

    private boolean isSolution(LightNode node) {
        if(node.goalSquare.isFilled()) return true; //Just in case the root is already a solution

        boolean result = false;
        try {
            this.currentState = (State) this.initalState.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        ArrayDeque<Play> aux = new ArrayDeque<>();

        while(!node.playsMade.isEmpty()) {
            Play currentPlay = node.playsMade.pop();
            aux.push(currentPlay);

            this.currentState.simulatePlay(currentPlay);
            if(this.currentState.getGoalSquare().isFilled()){
                node.goalSquare.fill();
                node.lastFilledSquare = this.currentState.getLastFilledSquare();
                result = true;
            }
        }

        while(!aux.isEmpty()) {
            node.playsMade.push(aux.pop());
        }
        node.lastFilledSquare = this.currentState.getLastFilledSquare();
        this.currentState = null;
        return result;
    }

    private int evaluate(LightNode node) {
        if(isSolution(node)) return 100; // Solution must always be chosen

        int g = node.cost;
        int h1 = LightHeuristics.fartherAway(node.getLastPlay(), node.goalSquare);
        int h2 = LightHeuristics.goalfrontPlay(node.getLastPlay(), node.goalSquare);
        int h3 = LightHeuristics.expandNowhere(node);
        // Note that only nodes that expand to useful places are evaluated
        return h1 + h2 + h3 - g;
    }

    private Stack<Play> getPath(LightNode node) {
        Stack<Play> result = new Stack<>();

        while(!node.playsMade.isEmpty()) {
            result.push(node.playsMade.removeLast());
        }

        return result;
    }

    public Stack<Play> solve() {
        while(!queue.isEmpty()) {
            // Starts with initial state
            LightNode v = queue.poll();

            // Execute solution testing
            if(v.goalSquare.isFilled()) {
                // Get the path to solution from the root
                return getPath(v);
            }

            // If solution was not found, then expand the node
            // and add its children to the queue
            expand(v);
            v.children.forEach(child -> {
                child.value = evaluate(child); // This way, value is f = g + h1 + h2
                if(child.value >= 0)
                    queue.add(child);
            });
        }

        System.err.println("No solution");
        return null;
    }
}
