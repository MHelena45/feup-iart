package model.state;

import model.board.Board;
import model.square.Square;
import model.Operator;

import java.util.ArrayList;

public class State {
    private Board board;
    private Square goalSquare;
    private ArrayList<Square> playableSquares;
    private Square playedSquare;
    private ArrayList<Square> lastFilledSquares = new ArrayList<>();

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
        playedSquare = newState.board.getSquare(x,y);

        playedSquare.play();
        int number = playedSquare.getNumber();
        int i = 1;

        switch (dir) {
            case UP:
                while (number > 0) {
                    Square currentSquare = board.getSquare(x,y-i);
                    if(!currentSquare.isFilled()){
                        currentSquare.fill();
                        lastFilledSquares.add(currentSquare);
                        number--;
                    }
                    i++;
                }
                break;
            case DOWN:
                while (number > 0) {
                    Square currentSquare = board.getSquare(x,y+i);
                    if(!currentSquare.isFilled()){
                        currentSquare.fill();
                        lastFilledSquares.add(currentSquare);
                        number--;
                    }
                    i++;
                }
                break;
            case LEFT:
                while (number > 0) {
                    Square currentSquare = board.getSquare(x-i,y);
                    if(!currentSquare.isFilled()){
                        currentSquare.fill();
                        lastFilledSquares.add(currentSquare);
                        number--;
                    }
                    i++;
                }
                break;
            case RIGHT:
                while (number > 0) {
                    Square currentSquare = board.getSquare(x+i,y);
                    if(!currentSquare.isFilled()){
                        currentSquare.fill();
                        lastFilledSquares.add(currentSquare);
                        number--;
                    }
                    i++;
                }
                break;
        }

        return newState;
    }

    public State undo() {
        State previousState = new State(board, goalSquare, playableSquares);
        playedSquare.unplay();

        for (Square sq : lastFilledSquares) {
            sq.clear();
        }

        return previousState;
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
