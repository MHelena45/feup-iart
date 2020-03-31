package search.bfs;

import model.Operator;
import model.square.GoalSquare;
import model.square.Square;
import model.state.State;
import search.NodeLW;
import search.Play;

import java.util.ArrayList;
import java.util.Stack;

public class ImproveBFS {
    private ArrayList<NodeLW> nodeLWS = new ArrayList<>();
    private Operator[] operators = {Operator.UP, Operator.DOWN, Operator.LEFT, Operator.RIGHT};
    private ArrayList<Square> squares;
    private int[] limits;
    private GoalSquare goalSquare;

    public ImproveBFS(State firstState) {
        NodeLW root = new NodeLW(null, firstState, null, 0, 0);
        nodeLWS.add(root);

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

    private void expand(NodeLW nodeLW) {
        ArrayList<Square> squares = nodeLW.state.getPlayableSquares();

        for (Square square : squares) {
            for(int i = 0; i < operators.length; i++) {
                if(reduceNodes(square, operators[i])) {
                    State newState = nodeLW.state.play(square.getX(), square.getY(), operators[i]);
                    Play transition = new Play(square, operators[i]);
                    NodeLW newNodeLW = new NodeLW(nodeLW, newState, transition, nodeLW.accCost + 1, nodeLW.depth + 1);
                    nodeLW.children.add(newNodeLW);
                }
            }
        }
    }

    public Stack<Play> solve() {
        while(!nodeLWS.isEmpty()) {
            // Starts with initial state
            NodeLW v = nodeLWS.get(0);
            nodeLWS.remove(0);

            // Execute solution testing
            if(v.isSolution()) {
                // Get the path to solution from the root
                return getPath(v);
            }

            // If solution was not found, then expand the node
            // and add its children to the queue
            expand(v);
            v.children.forEach(child -> nodeLWS.add(child));
        }

        return null;
    }

    private Stack<Play>  getPath(NodeLW nodeLW) {
        Stack<Play> result = new Stack<>();

        do {
            result.add(nodeLW.play);
            nodeLW = nodeLW.parent;
        } while (nodeLW.play != null);

        return result;
    }
}
