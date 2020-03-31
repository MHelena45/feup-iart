package model;

import model.board.Board;
import model.square.GoalSquare;
import model.square.NumberSquare;
import model.square.Square;
import model.state.State;
import search.Node;
import search.Play;
import search.aStar.A_STAR;
import search.bfs.BFS;
import search.heuristics.Heuristics;
import search.inverted.InvertedSearch;
import search.invertedTry.Inverted;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;

public class Model {
    private int level;
    private Stack<State> gameSequence;
    private Stack<Play> solvedSequence;
    private State initialState;
    private State currentState;

    public Model(int level) {
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
        this.gameSequence = new Stack<>();
        this.currentState = new State();

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
        this.initialState = currentState;
    }

    public int getLevel() {
        return level;
    }

    public ArrayList<ArrayList<Square>> getMatrix() {
        return currentState.getBoard().getMatrix();
    }

    public Square getSquare(int x, int y) { return currentState.getBoard().getSquare(x,y); };

    public Square getGoalSquare() {
        return currentState.getGoalSquare();
    }

    public void play(int x, int y, Operator dir) {
        Square playedSquare = this.currentState.getBoard().getSquare(x, y);

        if(!playedSquare.isPlayed()) {
            this.gameSequence.push(this.currentState);
            this.currentState = currentState.play(x, y, dir);
            Node node = new Node(null, currentState, new Play(playedSquare, dir), 0, 0);

//            System.out.println("Man distance: " + Heuristics.fartherAway(node));
//            System.out.println("Goalfront points: " + Heuristics.goalfrontPlay(node));
//            System.out.println("Number of interactions: " + Heuristics.expandNowhere(node));
//            if(node.isSolution()){
//                System.out.println("Is solution! -> value = 100");
//            }
        }
    }

    public void undo() {
        if(!this.gameSequence.empty()) {
            this.currentState = this.gameSequence.pop();
        } else {
            System.err.println("There are no previous plays!");
        }
    }

    public void restart() {
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

    public Stack<Play> solve() {
        this.currentState = initialState;
        System.out.println("Start solving");
//        GoalSquare goalSquare = (GoalSquare) currentState.getGoalSquare();
//        ArrayList<Square> playableSquare = currentState.getPlayableSquares();
//        InvertedSearch solver = new InvertedSearch(goalSquare, playableSquare);
        //BFS solver = new BFS(this.initialState);
        A_STAR solver = new A_STAR(this.initialState);

        this.solvedSequence = solver.solve();
        return solvedSequence;
    }

    public void nextState(Play play) {
        this.gameSequence.push(this.currentState);
        Square sqr = play.getNumberSquare();
        this.currentState = currentState.play(sqr.getX(), sqr.getY(), play.getOperator());
    }
}
