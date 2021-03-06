package model.state;

import model.board.Board;
import model.square.Square;
import model.Operator;
import search.Play;

import java.util.ArrayList;

public class State implements Cloneable {
    private Board board;
    private Square goalSquare;
    private ArrayList<Square> playableSquares;
    private Square lastFilledSquare;

    public State() {}

    public State(Board board, Square goalSquare, ArrayList<Square> playableSquares) {
        this.board = board;
        this.goalSquare = goalSquare;
        this.playableSquares = playableSquares;
        this.lastFilledSquare = null;
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

    public ArrayList<Square> getPlayableSquares() {
        return playableSquares;
    }

    public Square getLastFilledSquare() {
        return lastFilledSquare;
    }

    public State play(int x, int y, Operator dir) {
        State newState = null;
        try {
            newState = (State) this.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        return playCore(x, y, dir, newState);
    }

    private State playCore(int x, int y, Operator dir, State newState) {
        Square playedSquare = newState.board.getSquare(x,y);

        playedSquare.play();
        newState.playableSquares.remove(playedSquare);

        int number = playedSquare.getNumber();
        int i = 1;
        Square currentSquare = null;

        switch (dir) {
            case UP:
                while (number > 0 && newState.board.within(x, y-i)) {
                    currentSquare = newState.board.getSquare(x,y-i);
                    if(!currentSquare.isFilled()){
                        currentSquare.fill();
                        number--;
                    }
                    i++;
                }
                break;
            case DOWN:
                while (number > 0 && newState.board.within(x, y+i)) {
                    currentSquare = newState.board.getSquare(x,y+i);
                    if(!currentSquare.isFilled()){
                        currentSquare.fill();
                        number--;
                    }
                    i++;
                }
                break;
            case LEFT:
                while (number > 0 && newState.board.within(x-i, y)) {
                    currentSquare = newState.board.getSquare(x-i,y);
                    if(!currentSquare.isFilled()){
                        currentSquare.fill();
                        number--;
                    }
                    i++;
                }
                break;
            case RIGHT:
                while (number > 0 && newState.board.within(x+i, y)) {
                    currentSquare = newState.board.getSquare(x+i,y);
                    if(!currentSquare.isFilled()){
                        currentSquare.fill();
                        number--;
                    }
                    i++;
                }
                break;
        }

        newState.lastFilledSquare = currentSquare;

        return newState;
    }

    public State simulatePlay(Play play) {
        Square playedSquare = play.getNumberSquare();

        return playCore(playedSquare.getX(), playedSquare.getY(), play.getOperator(), this);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        State newstate = (State) super.clone();

        newstate.board = (Board) board.clone();
        newstate.playableSquares = (ArrayList<Square>) playableSquares.clone();
        newstate.goalSquare = newstate.board.getSquare(goalSquare.getX(), goalSquare.getY());

        return newstate;
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
