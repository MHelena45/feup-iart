package search;

import model.Operator;
import model.square.GoalSquare;
import model.square.NumberSquare;
import model.state.State;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class BreadthFirstSearch {

    private GoalSquare goalSquare;
    private ArrayList<NumberSquare> numberSquares;
    private ArrayList<Operator> operators = new ArrayList<>(List.of(Operator.UP, Operator.DOWN, Operator.LEFT, Operator.RIGHT));
    private PriorityQueue<Node> playsMade = new PriorityQueue<>();

    BreadthFirstSearch(GoalSquare goalSquare, ArrayList<NumberSquare> numberSquares) {
        this.goalSquare = goalSquare;
        this.numberSquares = numberSquares;
    }

    public boolean goalAchieved() { return goalSquare.isFilled(); }

    public ArrayList<NumberSquare> getNextPossPlaySquares() {
        ArrayList<NumberSquare> nextPossPlaySquares = numberSquares;
        for (Node play: playsMade) {
            nextPossPlaySquares.remove(play.getNumberSquare());
        }
        return nextPossPlaySquares;
    }

    public void bfs(State s) { // Initial state passed as argument
        int k = 1; // Number of possible states to choose from in making the last play
        ArrayList<NumberSquare> remainingSquares = numberSquares; // Number Squares that remain to be played

        for (int play_nr = 0; play_nr < numberSquares.size(); play_nr++) {
            for (int sq = 0; sq < remainingSquares.size() * k ; sq++) {
                for (int op = 0; op < 4; op++) {
                    // check if next play fills the goalSquare
                    playsMade.add(new Node(numberSquares.get(sq), operators.get(op)));
                    s = s.play(remainingSquares.get(sq).getX(), remainingSquares.get(sq).getY(), operators.get(op));
                    if (goalAchieved()) break;
                    s = s.undo();
                    playsMade.poll();
                    // switch previous play operator
                    playsMade.poll();
                    s = s.undo();
                    playsMade.add(new Node(numberSquares.get(sq), operators.get((op+1) % 4)));
                    s = s.play(remainingSquares.get(sq).getX(), remainingSquares.get(sq).getY(), operators.get((op+1) % 4));
                }
                if (Math.floor(sq / remainingSquares.size()) != Math.floor((sq+1) / remainingSquares.size())) {
                    if (sq + 1 == remainingSquares.size() * k) { // make play
                        playsMade.add(new Node(remainingSquares.get(0), operators.get(0)));
                        s = s.play(remainingSquares.get(0).getX(), remainingSquares.get(0).getY(), operators.get(0));
                    } else { // switch previous play square
                        playsMade.poll();
                        s = s.undo();
                        playsMade.add(new Node(numberSquares.get(sq + 1), operators.get(0)));
                        s = s.play(remainingSquares.get(sq + 1).getX(), remainingSquares.get(sq + 1).getY(), operators.get(0));
                    }
                    remainingSquares = getNextPossPlaySquares();
                }
            }
            k = remainingSquares.size() * k;
        }
    }
}
