package model.board;

import model.square.Square;

import java.util.ArrayList;

public class Board {
    private ArrayList<ArrayList<Square>> matrix;
    private int size;

    public Board(ArrayList<ArrayList<Square>> matrix) {
        this.matrix = matrix;
        this.size = matrix.size();
    }

    public Square getSquare(int x, int y) {
        return matrix.get(y).get(x);
    }
}