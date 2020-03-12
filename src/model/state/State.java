package model.state;

import model.board.Board;
import model.square.Square;

import java.util.ArrayList;

public class State {
    private Board board;
    private Square goalSquare;
    private ArrayList<Square> playableSquares;
}
