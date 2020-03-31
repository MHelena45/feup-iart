package search.dfs;

import model.state.State;
import search.Node;
import search.SearchAlgorithm;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class DFS extends SearchAlgorithm {
    private ArrayDeque<Node> queue = new ArrayDeque<>();

    public DFS(State firstState) {
        Node root = new Node(firstState, new ArrayDeque<>(), 0, 0);
        queue.add(root);
    }

    @Override
    protected boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    protected Node getNextNode() {
        return queue.removeFirst();
    }

    @Override
    protected void addChildren(ArrayList<Node> children) {
        children.forEach(child -> queue.addFirst(child));
    }
}
