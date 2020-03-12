package model.board;

import model.square.Square;

import java.io.*;
import java.util.ArrayList;

public class Board {
    private ArrayList<ArrayList<Square>> matrix;

    public Board(int size) {
        matrix = new ArrayList<>();

        for (int y = 0; y < size; y++) {
            ArrayList<Square> line = new ArrayList<>();
            for (int x = 0; x < size; x++) {
                line.add(new Square(x, y));
            }
            matrix.add(line);
        }
    }

    public Board(String level) throws IOException {
        InputStream stream = Board.class.getResourceAsStream("/" + level + ".txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line = null;

        matrix = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

    }

    public Square getSquare(int x, int y) {
        return matrix.get(y).get(x);
    }
}