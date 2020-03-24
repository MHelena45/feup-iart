package model.state;

import model.board.Board;
import model.square.Square;
import model.Operator;

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

    public Board getBoard() {
        return board;
    }

    public Square getGoalSquare() {
        return goalSquare;
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

    public State play(int x, int y, Operator dir) {
        State newState = new State(board, goalSquare, playableSquares);
        Square playedSquare = newState.board.getSquare(x,y);

        playedSquare.play();
        int number = playedSquare.getNumber();

        switch (dir) {
            case UP:
                for (int i = 1; i <= number; i++) {
                    board.getSquare(x,y-i).fill();
                }
                break;
            case DOWN:
                for (int i = 1; i <= number; i++) {
                    board.getSquare(x,y+i).fill();
                }
                break;
            case LEFT:
                for (int i = 1; i <= number; i++) {
                    board.getSquare(x-i,y).fill();
                }
                break;
            case RIGHT:
                for (int i = 1; i <= number; i++) {
                    board.getSquare(x+i,y).fill();
                }
                break;
        }

        return newState;
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
