package search.lightAstar;

import model.square.Square;
import search.Play;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class LightNode {
        public ArrayDeque<Play> playsMade;
        public ArrayList<LightNode> children;
        public int cost;
        public int value;
        //Basic state information
        public Square goalSquare;
        public ArrayList<Square> remainingSquares;
        public Square lastFilledSquare;

        public LightNode(ArrayDeque<Play> playsMade, int cost, Square goal, ArrayList<Square> remaining) {
            this.playsMade = playsMade;
            this.cost = cost;
            this.goalSquare = goal;
            this.remainingSquares = remaining;
            this.children = new ArrayList<>();
            this.value = 0;
            this.lastFilledSquare = null;
        }

        public Play getLastPlay() {
            return playsMade.peekLast();
        }

        @Override
        public String toString() {
            Play lastPlay = playsMade.peekLast();
            String msg = lastPlay == null ? "" : (lastPlay.toString() + " with");

            return msg + " Value = " + value;
        }
}
