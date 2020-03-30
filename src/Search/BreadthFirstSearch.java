package Search;

import model.Operator;
import model.square.NumberSquare;
import model.state.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BreadthFirstSearch {

    private State s; // current state
    private ArrayList<NumberSquare> numberSquares;
    private ArrayList<Operator> operators = new ArrayList<>(List.of(Operator.UP, Operator.DOWN, Operator.LEFT, Operator.RIGHT));
    private Stack<Play> playsMade = new Stack<>();

    BreadthFirstSearch(State s, ArrayList<NumberSquare> numberSquares) {
        this.s = s;
        this.numberSquares = numberSquares;
    }

    public boolean goalAchieved() { return s.getGoalSquare().isFilled(); }

    public ArrayList<NumberSquare> getNextPossPlaySquares() {
        ArrayList<NumberSquare> nextPossPlaySquares = new ArrayList<>();
        for (NumberSquare ns : numberSquares) {
            nextPossPlaySquares.add(ns);
        }
        for (Play play: playsMade) {
            nextPossPlaySquares.remove(play.getNumberSquare());
        }
        return nextPossPlaySquares;
    }

    public int switchIndex(int play_nr, int remaining_nr) {
        int switchIndex = 1;
        for (int p = 0; p < play_nr; p++) {
            switchIndex *= remaining_nr + play_nr;
        }
        return (int)(switchIndex * (Math.pow(4, play_nr)));
    }

    public State bfs() {
        int nr_of_numberSquares = numberSquares.size(); // Pretty self explanatory
        int k = 1; // Number of possible states to choose from in making the last play
        ArrayList<NumberSquare> remainingSquares = numberSquares; // Number Squares that remain to be played
        Stack<Integer> playSquareIndexes = new Stack<>(); // Index of the square of each play made
        playSquareIndexes.push(0);

        // Runs through every play(level)
        for (int play_nr = 0; play_nr < nr_of_numberSquares; play_nr++) {
            int remaining_nr = remainingSquares.size(); // Again, pretty self explanatory
            int pp_total = k * ((play_nr > 0) ? 4 : 1) * remaining_nr;
            // Runs through every possible play(pp) for all possible previous plays (runs through same level nodes)
            for (int pp = 0; pp < pp_total; pp++) {
                for (int op = 0; op < 4; op++) {
                    // check if current play fills the goalSquare
                    playsMade.push(new Play(remainingSquares.get(playSquareIndexes.peek()), operators.get(op)));
                    s = s.play(remainingSquares.get(playSquareIndexes.peek()).getX(), remainingSquares.get(playSquareIndexes.peek()).getY(), operators.get(op));
                    if (goalAchieved()) return s;
                    s.undo();
                    playsMade.pop();
                }

                // if last operator was not DOWN(because that is the last of them)
                if ((pp % 4) != 3) {
                    // switch previous play operator
                    if (play_nr > 0) {
                        for (int i = play_nr; i > 0 ; i--) {
                            if (pp + 1 == Math.pow(4, play_nr)) {
                                for (int p = 1; p < play_nr; p++) {
                                    playsMade.pop();
                                    s.undo();
                                }
                                Play previousPlay = playsMade.pop();
                                NumberSquare playedSquare = previousPlay.getNumberSquare();
                                Operator nextOperator = previousPlay.getOperator().getNextOperator();
                                s.undo();
                                playsMade.push(new Play(playedSquare, nextOperator));
                                s = s.play(playedSquare.getX(), playedSquare.getY(), nextOperator);
                                for (int p = 1; p < play_nr; p++) {
                                    remainingSquares = getNextPossPlaySquares();
                                    playsMade.push(new Play(remainingSquares.get(0), operators.get(0)));
                                    s = s.play(remainingSquares.get(0).getX(), remainingSquares.get(0).getY(), operators.get(0));
                                }
                                break;
                            }
                            remainingSquares = getNextPossPlaySquares();
                        }
                    } // switch square being played first
                    else playSquareIndexes.push(playSquareIndexes.pop() + 1);
                } else {
                    // Entered at the last pp of each play
                    if (pp + 1 == pp_total) {
                        // make first possible play
                        playsMade.push(new Play(remainingSquares.get(0), operators.get(0)));
                        s = s.play(remainingSquares.get(0).getX(), remainingSquares.get(0).getY(), operators.get(0));
                        remainingSquares = getNextPossPlaySquares();
                        playSquareIndexes.push(0);
                    } else if (play_nr > 0) {
                        // Entered when all possible squares following a certain play have been tested
                        if (Math.floor(pp / (4 * remaining_nr))!= Math.floor((pp+1) / (4 * remaining_nr))) {
                            for (int i = play_nr; i > 0 ; i--) {
                                // Switch play branch
                                if (pp + 1 == switchIndex(play_nr, remaining_nr)) {
                                    // backtrack previous plays
                                    for (int p = 1; p < play_nr; p++) {
                                        playsMade.pop();
                                        s.undo();
                                        playSquareIndexes.pop();
                                    }
                                    // make play with different square
                                    playsMade.pop();
                                    s.undo();
                                    remainingSquares = getNextPossPlaySquares();
                                    int index = playSquareIndexes.push(playSquareIndexes.pop() + 1);
                                    playsMade.push(new Play(remainingSquares.get(index), operators.get(0)));
                                    s = s.play(remainingSquares.get(index).getX(), remainingSquares.get(index).getY(), operators.get(0));
                                    // reset plays made afterwards
                                    for (int p = 1; p < play_nr; p++) {
                                        remainingSquares = getNextPossPlaySquares();
                                        playsMade.push(new Play(remainingSquares.get(0), operators.get(0)));
                                        s = s.play(remainingSquares.get(0).getX(), remainingSquares.get(0).getY(), operators.get(0));
                                        playSquareIndexes.push(0);
                                    }
                                    break;
                                }
                                remainingSquares = getNextPossPlaySquares();
                            }
                        }
                        else if (Math.floor(pp / 4)!= Math.floor((pp+1) / 4)) {
                            // switch last square played
                            playSquareIndexes.push(playSquareIndexes.pop() + 1);
                            remainingSquares = getNextPossPlaySquares();
                        }
                    }
                }
            }
            k *= remaining_nr;
        }
        return s;
    }
}
