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
        InputStream stream;
        if(level <= 9)
            stream = Model.class.getResourceAsStream("/levels/00" + level + ".txt");
        else if (level < 100)
            stream = Model.class.getResourceAsStream("/levels/0" + level + ".txt");
        else {
            System.out.println("level 100!");
            stream = Model.class.getResourceAsStream("/levels/" + level + ".txt");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        String line = null;
        int y = 0;

        ArrayList<ArrayList<Square>> matrix = new ArrayList<>();
        ArrayList<Square> playableSquares = new ArrayList<>();

        while ((line = reader.readLine()) != null) {

            String[] squares = line.split("(?!^)");
            ArrayList<Square> row = new ArrayList<>();

            for (int x = 0; x < squares.length; x++) {
                String value = squares[x];
                Square square;

                switch (value) {
                    case ".":
                        square = new Square(x, y);
                        break;

                    case "X":
                        square = new GoalSquare(x, y);
                        currentState.setGoalSquare(square);
                        break;

                    default:
                        int number = Integer.parseInt(value);
                        square = new NumberSquare(x, y, number);
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

    public State getCurrentState() {
        return currentState;
    }

    public ArrayList<ArrayList<Square>> getMatrix() {
        return currentState.getBoard().getMatrix();
    }

    public void play(int x, int y) {
        this.gameSequence.push(this.currentState);

        ArrayList<ArrayList<Square>> matrix = getMatrix();
        matrix.get(y).get(x).play();
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

    public void restart(){
        try {
            loadLevel();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public int getLevel() {
        return level;
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
