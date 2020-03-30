package search;

import model.Operator;
import model.square.NumberSquare;
import model.square.Square;

public class Play {
    private Square ns;
    private Operator op;

    public Play(Square ns, Operator op) {
        this.ns = ns;
        this.op = op;
    }

    public Square getNumberSquare() { return ns; }
    public Operator getOperator() { return op; }
    public void setNumberSquare(Square ns) { this.ns = ns; }
    public void setOperator(Operator op) { this.op = op; }

    @Override
    public String toString() {
        return "Play : Square(" + ns.getX() + ", " + ns.getY() + ") to " + op;
    }
}
