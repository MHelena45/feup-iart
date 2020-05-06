package search.inverted;

import model.Operator;
import model.square.NumberSquare;

import java.util.ArrayList;

public class MyNode {
    private NumberSquare ns;
    private Operator op;
    private boolean visited;

    MyNode(NumberSquare ns, Operator op) {
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