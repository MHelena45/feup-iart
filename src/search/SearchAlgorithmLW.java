package search;

import model.Operator;
import model.square.Square;
import model.state.State;

import java.util.ArrayList;

public abstract class SearchAlgorithmLW {
    protected Operator[] operators = {Operator.UP, Operator.DOWN, Operator.LEFT, Operator.RIGHT};
    protected State initialState;
    protected State s = new State();
    protected ArrayList<Square> playableSquares;

    protected boolean atSolution(ArrayList<Play> playsMade) {
        updateS(playsMade);
        return s.getGoalSquare().isFilled();
    }

    protected void updateS(ArrayList<Play> playsMade) {
        s = s.copy(initialState);
        for (Play play: playsMade) {
            Square playedSquare = play.getNumberSquare();
            s.play(playedSquare.getX(), playedSquare.getY(), play.getOperator());
        }
    }

    protected void expand(NodeLW nodeLW) {
        ArrayList<Boolean> squares = nodeLW.playedSquares;

        for (int sq = 0; sq < squares.size(); sq++) {
            if (!squares.get(sq)) {
                for (int op = 0; op < operators.length; op++) {
                    ArrayList<Boolean> playedSquares = squares;
                    playedSquares.add(sq, true);
                    ArrayList<Play>playsMade = nodeLW.playsMade;
                    Play transition = new Play(playableSquares.get(sq), operators[op]);
                    playsMade.add(transition);
                    NodeLW newNodeLW = new NodeLW(nodeLW, playedSquares, playsMade, nodeLW.accCost + 1, nodeLW.depth + 1);
                    nodeLW.children.add(newNodeLW);
                }
            }
        }
    }

    protected ArrayList<Play> getPath(NodeLW nodeLW) { return nodeLW.playsMade; }

    public abstract ArrayList<Play> solve();
}
