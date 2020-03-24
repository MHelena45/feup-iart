package model;

import model.board.Board;
import model.square.GoalSquare;
import model.square.NumberSquare;
import model.square.Square;
import model.state.State;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;

public class Model {
    private int level;
    private Stack<State> gameSequence;
    private State currentState;

    public Model(int level) {
        this.gameSequence = new Stack<>();
        this.currentState = new State();
        this.level = level;

        try {
            loadLevel();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void loadLevel() throws IOException {
        InputStream stream = Model.class.getResourceAsStream("/levels/" + level + ".txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        String line = null;
        int y = 0;

        ArrayList<ArrayList<Square>> matrix = new ArrayList<>();
        ArrayList<Square> playableSquares = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            String content = line.substring(1, line.length() - 2);
            String[] squares = content.split(",");
            ArrayList<Square> row = new ArrayList<>();

            for (int x = 0; x < squares.length; x++) {
                int value = Integer.parseInt(squares[x]);
                Square square;

                switch (value) {
                    case 0:
                        square = new Square(x, y);
                        break;

                    case -1:
                        square = new GoalSquare(x, y);
                        currentState.setGoalSquare(square);
                        break;

                    default:
                        square = new NumberSquare(x, y, value);
                        playableSquares.add(square);
                        break;
                }

                row.add(square);
            }
            matrix.add(row);

            y++;
        }

        currentState.setPlayableSquares(playableSquares);
        currentState.setBoard(new Board(matrix));
    }

    public int getLevel() {
        return level;
    }

    public ArrayList<ArrayList<Square>> getMatrix() {
        return currentState.getBoard().getMatrix();
    }

    public Square getGoalSquare() {
        return currentState.getGoalSquare();
    }

    public void play(int x, int y, Operator dir) {
        Square playedSquare = this.currentState.getBoard().getSquare(x, y);

        if(!playedSquare.isPlayed()) {
            this.gameSequence.push(this.currentState);
            this.currentState = currentState.play(x, y, dir);

            System.out.println("Played square (" + x + ", " + y + ")");
        }
    }

    public State undo() {
        State removedState = this.currentState;
        this.currentState = this.gameSequence.pop();

        return removedState;
    }

    public void restart(){
        try {
            loadLevel();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void changeLevel(int level) {
        if(level < 0)
            return;
        else {
            try {
                this.level = level;
                loadLevel();
            } catch (Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

}
