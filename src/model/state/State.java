package model.state;

import model.board.Board;
import model.square.Square;

import java.util.ArrayList;

public class State {
    private Board board;
    private Square goalSquare;
    private ArrayList<Square> playableSquares;

    public State() {}

    public State(Board board, Square goalSquare, ArrayList<Square> playableSquares) {
        this.board = board;
        this.goalSquare = goalSquare;
        this.playableSquares = playableSquares;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setGoalSquare(Square goalSquare) {
        this.goalSquare = goalSquare;
    }

    public void setPlayableSquares(ArrayList<Square> playableSquares) {
        this.playableSquares = playableSquares;
    }

    @Override
    public String toString() {
        String msg = "";

        for(Square square : playableSquares) {
            msg += square.toString() + "\n";
        }

        msg += goalSquare.toString();

        return msg;
    }
}
