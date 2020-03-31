package search.heuristics;

import model.Operator;
import model.square.Square;
import model.state.State;
import search.Play;

import java.util.ArrayList;

public class EdgeHeuristics {
    private static int horizontalInteractions(Play play, Square lastFilled, ArrayList<Square> otherSquares) {
        Operator direction = play.getOperator();
        Square playedSquare = play.getNumberSquare();
        int result = 0;

        if(lastFilled != null) { // lastFilled will be null if the expansion was completely out of bounds
            for(Square square : otherSquares) {
                if(direction == Operator.UP){ // played.y > lastFilled.y
                    if(lastFilled.getY() <= square.getY() && square.getY() < playedSquare.getY())
                        result++;
                } else { // played.y < filledCells.y
                    if(playedSquare.getY() < square.getY() && square.getY() <= lastFilled.getY())
                        result++;
                }
            }
        }

        return result;
    }

    private static int verticalInteractions(Play play, Square lastFilled, ArrayList<Square> otherSquares) {
        Operator direction = play.getOperator();
        Square playedSquare = play.getNumberSquare();
        int result = 0;

        if(lastFilled != null) { // lastFilled will be null if the expansion was completely out of bounds
            for(Square square : otherSquares) {
                if(direction == Operator.LEFT){ // played.x > lastFilled.x
                    if(lastFilled.getX() <= square.getX() && square.getX() < playedSquare.getX())
                        result++;
                } else { // played.x < lastFilled.x
                    if(playedSquare.getX() < square.getX() && square.getX() <= lastFilled.getX())
                        result++;
                }
            }
        }

        return result;
    }

    private static int interactions(Play play, Square lastFilledSquare, ArrayList<Square> otherSquares) {
        Operator direction = play.getOperator();

        if(direction == Operator.UP || direction == Operator.DOWN)
            return horizontalInteractions(play, lastFilledSquare, otherSquares);
        else
            return verticalInteractions(play, lastFilledSquare, otherSquares);
    }

    public static int expandNowhere(Play play, State state) {
        ArrayList<Square> otherSquares = state.getPlayableSquares();
        Square lastFilledSquare = state.getLastFilledSquare();

        // When there are no other squares to play this does not affect the quality of the play
        if(otherSquares.isEmpty()) return 0;

        int numInteractions = interactions(play, lastFilledSquare, otherSquares);

        if(numInteractions == 0) return -10; // As said above, highly discouraged!

        return numInteractions;
    }
}
