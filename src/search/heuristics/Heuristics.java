package search.heuristics;

import model.Operator;
import model.board.Board;
import model.square.Square;
import search.Node;
import search.Play;

import java.util.ArrayList;

public class Heuristics {
    private static boolean isGoalfront(Square square, Square goal) {
        return goal.getX() == square.getX() || goal.getY() == square.getY();
    }

    private static int manhattanDistance(Square sqr1, Square sqr2) {
        int x1 = sqr1.getX();
        int y1 = sqr1.getY();
        int x2 = sqr2.getX();
        int y2 = sqr2.getY();

        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    /**
     * Calculate the horizontal interactions (only applicable for vertical expansions)
     * @param play Play that originated the current state
     * @param lastFilled Last square filled by the play
     * @param otherSquares List of non played squares
     * @return Number of horizontal interactions between the play and the other squares
     */
    private static int horizontalInteractions(Play play, Square lastFilled, ArrayList<Square> otherSquares) {
        Operator direction = play.getOperator();
        Square playedSquare = play.getNumberSquare();
        int result = 0;

        for(Square square : otherSquares) {
            if(direction == Operator.UP){ // played.y > lastFilled.y
                if(lastFilled.getY() <= square.getY() && square.getY() < playedSquare.getY())
                    result++;
            } else { // played.y < filledCells.y
                if(playedSquare.getY() < square.getY() && square.getY() <= lastFilled.getY())
                    result++;
            }
        }

        return result;
    }

    /**
     * Calculate the vertical interactions (only applicable for horizontal expansions)
     * @param play Play that originated the current state
     * @param lastFilled Last square filled by the play
     * @param otherSquares List of non played squares
     * @return Number of vertical interactions between the play and the other squares
     */
    private static int verticalInteractions(Play play, Square lastFilled, ArrayList<Square> otherSquares) {
        Operator direction = play.getOperator();
        Square playedSquare = play.getNumberSquare();
        int result = 0;

        for(Square square : otherSquares) {
            if(direction == Operator.LEFT){ // played.x > lastFilled.x
                if(lastFilled.getX() <= square.getX() && square.getX() < playedSquare.getX())
                    result++;
            } else { // played.x < lastFilled.x
                if(playedSquare.getX() < square.getX() && square.getX() <= lastFilled.getX())
                    result++;
            }
        }

        return result;
    }

    /**
     * Calculates the interactions between the cells filled by the last play and the
     * other non played squares.
     *
     * @param play Play that originated the current state
     * @param lastFilledSquare Last squared that was filled by the play
     * @param otherSquares List of non played squares
     * @return Number of interactions between the play and the other squares
     */
    private static int interactions(Play play, Square lastFilledSquare, ArrayList<Square> otherSquares) {
        Operator direction = play.getOperator();

        if(direction == Operator.UP || direction == Operator.DOWN)
            return horizontalInteractions(play, lastFilledSquare, otherSquares);
        else
            return verticalInteractions(play, lastFilledSquare, otherSquares);
    }

    /**
     * The farther away the played square is from the goal square the better.
     * Using manhattan distance we calculate it's distance to the goal,
     * which means that the bigger the distance, the higher is the play priority.
     *
     * @param node Node being evaluated
     *
     * @return Evaluation according to manhattan distance
     */
    public static int fartherAway(Node node) {
        Square playedSquare = node.play.getNumberSquare();
        Square goalSquare = node.state.getGoalSquare();

        return manhattanDistance(playedSquare, goalSquare);
    }

    /**
     * If the played square is in front of the goal, then it most likely will be played last.
     * This way we add a small penalty to goal front squares.
     *
     * @param node Node being evaluated
     * @return Evaluation of goal front position
     */
    public static int goalfrontPlay(Node node) {
        Square playedSquare = node.play.getNumberSquare();
        Square goalSquare = node.state.getGoalSquare();

        if(isGoalfront(playedSquare, goalSquare)) return 0;

        return 1;
    }

    /**
     * If the played square is expanded to places where there are no other squares
     * then that expansion does not help to reach the goal.
     * Therefore, these plays are highly discouraged.
     * The play is considered slightly better the more squares it interacts with.
     *
     * @param node Node being evaluated
     * @return Evaluation according to number of nodes an expansion interacts with
     */
    public static int expandNowhere(Node node) {
        ArrayList<Square> otherSquares = node.state.getPlayableSquares();
        Square lastFilledSquare = node.state.getLastFilledSquare();

        // When there are no other squares to play this does not affect the quality of the play
        if(otherSquares.isEmpty()) return 0;

        int numInteractions = interactions(node.play, lastFilledSquare, otherSquares);

        if(numInteractions == 0) return -10; // As said above, highly discouraged!

        return numInteractions;
    }
}
