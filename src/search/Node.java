package search;

import java.util.ArrayList;

public class Node {
    public Node parent;
    public ArrayList<Play> playsMade;
    public ArrayList<Node> children;
    public int accCost;
    public int depth;
    public int value;

    public Node(Node parent, ArrayList<Play> playsMade, int accCost, int depth) {
        this.parent = parent;
        this.playsMade = playsMade;
        this.accCost = accCost;
        this.depth = depth;
        this.children = new ArrayList<>();
        this.value = 0;
    }

    @Override
    public String toString() {
        Play lastPlay = playsMade.get(playsMade.size() - 1);
        String msg = lastPlay == null ? "" : (lastPlay.toString() + " with");

        return msg + " Value = " + value;
    }
}
