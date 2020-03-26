package Search;

import model.Operator;
import model.square.NumberSquare;

public class Play {
    private NumberSquare ns;
    private Operator op;

    Play(NumberSquare ns, Operator op) {
        this.ns = ns;
        this.op = op;
    }

    public NumberSquare getNumberSquare() { return ns; }
    public Operator getOperator() { return op; }
    public void setNumberSquare(NumberSquare ns) { this.ns = ns; }
    public void setOperator(Operator op) { this.op = op; }
}
