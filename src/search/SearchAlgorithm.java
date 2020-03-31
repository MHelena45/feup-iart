package search;

import model.Operator;
import model.square.Square;
import model.state.State;

import java.util.ArrayList;

public abstract class SearchAlgorithm {
    protected Operator[] operators = {Operator.UP, Operator.DOWN, Operator.LEFT, Operator.RIGHT};
    protected State initialState;
    protected State s;
    protected ArrayList<Square> playableSquares;

    protected boolean atSolution(ArrayList<Play> playsMade) {
        updateS(playsMade);
        return s.getGoalSquare().isFilled();
    }

    protected void updateS(ArrayList<Play> playsMade) {
        s = null; // Estou a assumir k fazendo null a memoria do s anterior é libertada
        try {
            s = (State) initialState.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        for (Play play: playsMade) {
            Square playedSquare = play.getNumberSquare();
            s.play(playedSquare.getX(), playedSquare.getY(), play.getOperator());
        }
    }

    protected ArrayList<Square> getNextPossPlaySquares(ArrayList<Play> playsMade) {
        ArrayList<Square> nextPossPlaySquares = new ArrayList<>();
        for (Square square : playableSquares) {
            nextPossPlaySquares.add(square);
        }
        for (Play play: playsMade) {
            nextPossPlaySquares.remove(play.getNumberSquare());
        }
        return nextPossPlaySquares;
    }

    protected void expand(Node node) {
        ArrayList<Play> playsMade = node.playsMade;
        ArrayList<Square> squares = getNextPossPlaySquares(playsMade);

        for (Square square : squares) {
            for(int i = 0; i < operators.length; i++) {
                Play transition = new Play(square, operators[i]);
                playsMade.add(transition);
                Node newNode = new Node(node, playsMade, node.accCost+1, node.depth+1);
                node.children.add(newNode);
            }
        }
    }

    protected ArrayList<Play> getPath(Node node) { return node.playsMade; }

    public abstract ArrayList<Play> solve();
}
