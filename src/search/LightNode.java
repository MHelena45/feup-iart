package search;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class LightNode {
        public Node parent;
        public ArrayDeque<Play> playsMade;
        public ArrayList<Node> children;
        public int accCost;
        public int depth;
        public int value;

        public LightNode(Node parent, ArrayDeque<Play> playsMade, int accCost, int depth) {
            this.parent = parent;
            this.playsMade = playsMade;
            this.accCost = accCost;
            this.depth = depth;
            this.children = new ArrayList<>();
            this.value = 0;
        }

        @Override
        public String toString() {
            Play lastPlay = playsMade.peekLast();
            String msg = lastPlay == null ? "" : (lastPlay.toString() + " with");

            return msg + " Value = " + value;
        }
}
