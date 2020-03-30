package model.state;

import model.Operator;
import model.board.Board;
import model.square.GoalSquare;
import model.square.NumberSquare;
import model.square.Square;

import java.util.ArrayList;
import java.util.Stack;

public class State {
    private Board board;
    private GoalSquare goalSquare;
    private ArrayList<NumberSquare> playableSquares;
    private Stack<NumberSquare> playedSquares = new Stack<>();
    private Stack<Square> lastFilledSquares = new Stack<>();
    private Stack<Integer> nr_of_lastFilledSquares = new Stack();

    public State() {}

    public State(Board board, GoalSquare goalSquare, ArrayList<NumberSquare> playableSquares) {
        this.board = board;
        this.goalSquare = goalSquare;
        this.playableSquares = playableSquares;
    }

    public State(Board board, GoalSquare goalSquare, ArrayList<NumberSquare> playableSquares, Stack<NumberSquare> playedSquares, Stack<Square> lastFilledSquares, Stack<Integer> nr_of_lastFilledSquares) {
        this.board = board;
        this.goalSquare = goalSquare;
        this.playableSquares = playableSquares;
        this.playedSquares = playedSquares;
        this.lastFilledSquares = lastFilledSquares;
        this.nr_of_lastFilledSquares = nr_of_lastFilledSquares;
    }

    public Board getBoard() {
        return board;
    }

    public GoalSquare getGoalSquare() {
        return goalSquare;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setGoalSquare(GoalSquare goalSquare) {
        this.goalSquare = goalSquare;
    }

    public void setPlayableSquares(ArrayList<NumberSquare> playableSquares) {
        this.playableSquares = playableSquares;
    }

    public ArrayList<NumberSquare> getPlayableSquares() {
        return playableSquares;
    }

    public State play(int x, int y, Operator dir) {
        State newState = new State(board, goalSquare, playableSquares, playedSquares, lastFilledSquares, nr_of_lastFilledSquares);
        newState.playedSquares.push((NumberSquare) newState.board.getSquare(x,y));

        newState.playedSquares.peek().play();
        int number = newState.playedSquares.peek().getNumber();
        int i = 1;
        int height = board.getSize();
        int width = board.getMatrix().get(0).size();
        int nr_of_filledSquares = 0;

        switch (dir) {
            case UP:
                while (number > 0) {
                    if (y - i < 0)
                        break;
                    Square currentSquare = board.getSquare(x,y-i);
                    if(!currentSquare.isFilled()){
                        currentSquare.fill();
                        newState.lastFilledSquares.push(currentSquare);
                        nr_of_filledSquares++;
                        number--;
                    }
                    i++;
                }
                break;
            case DOWN:
                while (number > 0) {
                    if (y + i == height)
                        break;
                    Square currentSquare = board.getSquare(x,y+i);
                    if(!currentSquare.isFilled()){
                        currentSquare.fill();
                        newState.lastFilledSquares.push(currentSquare);
                        nr_of_filledSquares++;
                        number--;
                    }
                    i++;
                }
                break;
            case LEFT:
                while (number > 0) {
                    if (x - i < 0)
                        break;
                    Square currentSquare = board.getSquare(x-i,y);
                    if(!currentSquare.isFilled()){
                        currentSquare.fill();
                        newState.lastFilledSquares.push(currentSquare);
                        nr_of_filledSquares++;
                        number--;
                    }
                    i++;
                }
                break;
            case RIGHT:
                while (number > 0) {
                    if (x + i == width)
                        break;
                    Square currentSquare = board.getSquare(x+i,y);
                    if(!currentSquare.isFilled()){
                        currentSquare.fill();
                        newState.lastFilledSquares.push(currentSquare);
                        nr_of_filledSquares++;
                        number--;
                    }
                    i++;
                }
                break;
        }
        newState.nr_of_lastFilledSquares.push(nr_of_filledSquares);

        return newState;
    }

    public void undo() {
        NumberSquare playedSquare = playedSquares.pop();
        playedSquare.undo();
        int nr_of_squares2clear = nr_of_lastFilledSquares.pop();
        for (int sq = 0; sq < nr_of_squares2clear; sq++) {
            Square square = lastFilledSquares.pop();
            square.clear();
        }
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
