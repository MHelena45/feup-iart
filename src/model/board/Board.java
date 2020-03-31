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

    public int getRows() {
        return matrix.size();
    }

    public int getColumns() {
        return matrix.get(0).size();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Board newBoard = (Board) super.clone();

        newBoard.matrix = cloneList();

        return newBoard;
    }

    private ArrayList<ArrayList<Square>> cloneList() throws CloneNotSupportedException {
        ArrayList<ArrayList<Square>> newMatrix = new ArrayList<>();

        for(ArrayList<Square> row : matrix) {
            ArrayList<Square> newRow = new ArrayList<>();
            for(Square sqr : row) {
                Square newSquare = (Square) sqr.clone();
                newRow.add(newSquare);
            }
            newMatrix.add(newRow);
        }

        return newMatrix;
    }

    public boolean within(int x, int y) {
        return (x > -1 && x < matrix.get(0).size()) && (y > -1 && y < matrix.size());
    }
}