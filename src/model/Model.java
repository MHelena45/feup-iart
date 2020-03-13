package model;

import model.board.Board;
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
    private final int level;
    private Stack<State> gameSequence;
    private State currentState;

    private void loadLevel() throws IOException {
        InputStream stream = Model.class.getResourceAsStream("/levels/" + level + ".txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        String line = null;
        int y = 0;

        ArrayList<ArrayList<Square>> matrix = new ArrayList<>();
        ArrayList<Square> playableSquares = new ArrayList<>();
        ArrayList<Square> row = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            String content = line.substring(1, line.length() - 2);
            String[] squares = content.split(",");

            for (int x = 0; x < squares.length; x++) {
                int value = Integer.parseInt(squares[x]);
                Square square;

                switch (value) {
                    case 0:
                        square = new Square(x, y);
                        break;

                    case -1:
                        square = new Square(x, y);
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
            row.clear();
            y++;
        }

        currentState.setPlayableSquares(playableSquares);
        currentState.setBoard(new Board(matrix));
    }

    public Model(int level) throws IOException {
        this.gameSequence = new Stack<>();
        this.currentState = new State();
        this.level = level;

        loadLevel();
    }

    public State getCurrentState() {
        return currentState;
    }

    public void move(State state) {
        this.gameSequence.push(this.currentState);
        this.currentState = state;
    }

    public State undo() {
        State removedState = this.currentState;
        this.currentState = this.gameSequence.pop();

        return removedState;
    }
}
