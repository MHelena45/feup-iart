package Search;

import model.Operator;
import model.square.NumberSquare;

import java.util.ArrayList;

public class Node {
    private ArrayList<Node> successors = new ArrayList<>();
    private ArrayList<Node> processors = new ArrayList<>();
    private NumberSquare ns;
    private Operator op;
    private boolean visited;

    Node(NumberSquare ns, Operator op) {
        this.ns = ns;
        this.op = op;
        visited = false;
    }

    public NumberSquare getNumberSquare() { return ns; }
    public Operator getOperator() { return op; }
    public void setNumberSquare(NumberSquare ns) { this.ns = ns; }
    public void setOperator(Operator op) { this.op = op; }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public ArrayList<Node> getSuccessors() {
        return successors;
    }

    public void addSuccessors(Node node) {
        successors.add(node);
    }

    public void setSuccessors(ArrayList<Node> successors) {
        this.successors = successors;
    }

    public ArrayList<Node> getProcessors() {
        return processors;
    }

    public void addProcessors(Node node) {
        processors.add(node);
    }

    public void setProcessors(ArrayList<Node> processors) {
        this.processors = processors;
    }
}
