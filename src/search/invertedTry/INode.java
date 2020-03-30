package search.invertedTry;

import model.Operator;
import model.square.NumberSquare;
import model.square.Square;

import java.util.ArrayList;

public class INode {
    private INode parent = null;
    private Square parentSquare;
    private NumberSquare square;
    private ArrayList<Square> inLineSquare = new ArrayList<>();
    private Integer needSquares;
    private Operator operator;

    INode(NumberSquare square, Integer needSquares, Operator operator) {
        this.square = square;
        this.needSquares = needSquares - square.getNumber();
        this.operator = operator;

        search();
    }

    public void search() {
        if(needSquares > 0)
            Inverted.findInlineSquares(square);
    }

    INode(NumberSquare square, Integer needSquares, Operator operator, INode parent) {
        this.square = square;
        this.needSquares = needSquares - square.getNumber();
        this.operator = operator;
        this.parent = parent;

        search();
    }

    public boolean isParent(Square square){
        if(square == parentSquare)
            return true;
        return false;
    }

    public Square getSquare(){
        return square;
    }


    public Square getParentSquare(){
        return parentSquare;
    }

    public Integer canRemoveAny() {
        return inLineSquare.size() - this.needSquares;
    }

    public void addInLineSquare(Square square) {
        inLineSquare.add(square);
    }

    public void removeSquare(Square square) {
        inLineSquare.remove(square);
    }


    public Operator getOperator(){
        return operator;
    }
}
