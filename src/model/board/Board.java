package model.board;

import model.square.Square;

import java.util.ArrayList;

public class Board implements Cloneable {
    private ArrayList<ArrayList<Square>> matrix;
    private int size;

    public Board(ArrayList<ArrayList<Square>> matrix) {
        this.matrix = matrix;
        this.size = matrix.size();
    }

    public Square getSquare(int x, int y) {
        return matrix.get(y).get(x);
    }

    public ArrayList<ArrayList<Square>> getMatrix(){
        return matrix;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Board newBoard = (Board) super.clone();

        newBoard.matrix = (ArrayList<ArrayList<Square>>) matrix.clone();

        return newBoard;
    }
}