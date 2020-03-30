package search.aStar;

import search.Node;

import java.util.Comparator;

public class Heuristic implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {
        if (o1.value > o2.value)
            return 1;
        else if (o1.value < o2.value)
            return -1;

        return 0;
    }
}
