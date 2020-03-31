package search.bfs;

import model.Operator;
import model.square.GoalSquare;
import model.square.NumberSquare;
import model.square.Square;
import model.state.State;
import search.Node;
import search.Play;
import search.inverted.MyNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Stack;

public class ImproveBFS {
    private ArrayList<Node> nodes = new ArrayList<>();
    private Operator[] operators = {Operator.UP, Operator.DOWN, Operator.LEFT, Operator.RIGHT};
    private ArrayList<Square> squares;
    private int[] limits;
    private GoalSquare goalSquare;

    public ImproveBFS(State firstState) {
        Node root = new Node(firstState, new ArrayDeque<>(), 0, 0);
        nodes.add(root);

        squares = root.state.getPlayableSquares();
        goalSquare = (GoalSquare) root.state.getGoalSquare();

        sortByManhattan(0, squares.size() - 1);
        getLimits();
    }

    public void sortByManhattan(int begin, int end) {
        if (end <= begin) return;
        int pivot = partition(begin, end);
        sortByManhattan(begin, pivot-1);
        sortByManhattan( pivot+1, end);
    }

    private int partition(int begin, int end) {
        int pivot = end;
        int counter = begin;

        for (int i = begin; i < end; i++) {
            if (squares.get(i).getManhattanDistance(goalSquare) >
                    squares.get(pivot).getManhattanDistance(goalSquare)) {
                Square temp = squares.get(counter);
                squares.set(counter, squares.get(i));
                squares.set(i, temp);
                counter++;
            }
        }
        Square temp = squares.get(pivot);
        squares.set(pivot, squares.get(counter));
        squares.set(counter, temp);

        return counter;
    }

    private void getLimits() {
        int maxX = 0, minX = 0, maxY = 0, minY= 0;
        for (Square square : squares) {
            Integer y = square.getY();
            Integer x = square.getX();

            if(y > maxY)
                maxY = y;
            else if(y < minY)
                minY = y;

            if(x > maxX)
                maxX = x;
            else if(x < minX)
                minX = x;
        }
        limits = new int[]{maxX, minX, maxY, minY};
    }

    private boolean reduceNodes(Square square, Operator operator) {
        switch (operator){
            case RIGHT:
                if(square.getY() == goalSquare.getY())
                    return true;
                if(square.getX() == limits[0])
                    return false;
                break;
            case LEFT:
                if(square.getY() == goalSquare.getY())
                    return true;
                if(square.getX() == limits[1])
                    return false;
                break;
            case DOWN:
                if(square.getX() == goalSquare.getX())
                    return true;
                if(square.getY() == limits[2])
                    return false;
                break;
            case UP:
                if(square.getX() == goalSquare.getX())
                    return true;
                if(square.getY() == limits[3])
                    return false;
                break;

            default:
                break;
        }

        return true;
    }

    private void expand(Node node) {
        ArrayList<Square> squares = node.state.getPlayableSquares();

        for (Square square : squares) {
            for(int i = 0; i < operators.length; i++) {
                if(reduceNodes(square, operators[i])) {
                    State newState = node.state.play(square.getX(), square.getY(), operators[i]);

                    ArrayDeque<Play> newPlays = new ArrayDeque<>();
                    node.playsMade.forEach(oldPlay -> newPlays.addLast(oldPlay));
                    newPlays.addLast(new Play(square, operators[i]));

                    Node newNode = new Node(newState, newPlays, node.accCost+1, node.depth+1);
                    node.children.add(newNode);
                }
            }
        }
    }

    public Stack<Play> solve() {
        while(!nodes.isEmpty()) {
            // Starts with initial state
            Node v = nodes.get(0);
            nodes.remove(0);

            // Execute solution testing
            if(v.isSolution()) {
                // Get the path to solution from the root
                return getPath(v);
            }

            // If solution was not found, then expand the node
            // and add its children to the queue
            expand(v);
            v.children.forEach(child -> nodes.add(child));
        }

        return null;
    }

    private Stack<Play>  getPath(Node node) {
        Stack<Play> result = new Stack<>();

        while(!node.playsMade.isEmpty()) {
            result.push(node.playsMade.removeLast());
        }

        return result;
    }
}
