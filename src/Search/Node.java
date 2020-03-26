package Search;

import model.Operator;
import model.square.NumberSquare;

public class Node {
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
}
