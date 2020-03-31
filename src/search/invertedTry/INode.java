package search.invertedTry;

import model.Operator;
import model.square.NumberSquare;
import model.square.Square;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class INode {
    private INode parent = null;
    private Square parentSquare;
    private NumberSquare square;
    private ArrayList<NumberSquare> inLineSquare = new ArrayList<>();
    private ArrayList<Operator> operators = new ArrayList<>();
    private Integer needSquares;
    private Operator operator;

    INode(NumberSquare square, Integer needSquares, Operator operator) {
        this.square = square;
        this.needSquares = needSquares - square.getNumber();
        this.operator = operator;
        this.parent = null;
    }

    INode(NumberSquare square, Integer needSquares, Operator operator, Square parent) {
        this.square = square;
        this.needSquares = needSquares - square.getNumber();
        this.operator = operator;
        this.parentSquare = parent;

    }

    INode(NumberSquare square, Integer needSquares, Operator operator, INode parent) {
        this.square = square;
        this.needSquares = needSquares - square.getNumber();
        this.operator = operator;
        this.parent = parent;
        this.parentSquare = parent.getSquare();
    }

    public Integer getNeedSquares() {
        return needSquares;
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

    public void remove() {

        if(needSquares == inLineSquare.size())
            return;

        if(operator.equals(Operator.RIGHT) || operator.equals(Operator.LEFT)) {
            sortByY(0, inLineSquare.size() - 1);
            System.out.println("Sorting by Y");
            System.out.println("Inline: " + inLineSquare.size());
        }
        sortByX(0, inLineSquare.size() -1);

        for(int i =  0; i < (inLineSquare.size() - needSquares); i++){
            inLineSquare.remove(i);
            operators.remove(i);
            System.out.println("Remove in INODE");
        }
        return;
    }

    public void addInLineSquare(NumberSquare square) {
        inLineSquare.add(square);
    }

    public void addOperator(Operator operator) {
        operators.add(operator);
    }

    public void removeSquare(Square square) {
        inLineSquare.remove(square);
    }

    public ArrayList getInLineSquares() {
        return inLineSquare;
    }

    public Operator getOperator(){
        return operator;
    }

    public void sortByX(int begin, int end) {
        if (end <= begin) return;
        int pivot = partitionX(begin, end);
        sortByX(begin, pivot-1);
        sortByX( pivot+1, end);
    }

    private int partitionX(int begin, int end) {
        int pivot = end;
        int counter = begin;

        for (int i = begin; i < end; i++) {
            if (inLineSquare.get(i).getXDistance(parentSquare) >
                    inLineSquare.get(pivot).getXDistance(parentSquare)) {
                //sort squares
                NumberSquare temp = inLineSquare.get(counter);
                inLineSquare.set(counter, inLineSquare.get(i));
                inLineSquare.set(i, temp);

                Operator tem = operators.get(counter);
                operators.set(counter, operators.get(i));
                operators.set(i, tem);

                counter++;
            }
        }

        NumberSquare temp = inLineSquare.get(pivot);
        inLineSquare.set(pivot, inLineSquare.get(counter));
        inLineSquare.set(counter, temp);

        Operator tem = operators.get(pivot);
        operators.set(pivot, operators.get(counter));
        operators.set(counter, tem);

        return counter;
    }

    public void sortByY(int begin, int end) {
        if (end <= begin) return;
        int pivot = partitionY(begin, end);
        sortByY(begin, pivot-1);
        sortByY( pivot+1, end);
    }

    private int partitionY(int begin, int end) {
        int pivot = end;
        int counter = begin;

        for (int i = begin; i < end; i++) {
            if (inLineSquare.get(i).getYDistance(parentSquare) >
                    inLineSquare.get(pivot).getYDistance(parentSquare)) {
                NumberSquare temp = inLineSquare.get(counter);
                inLineSquare.set(counter, inLineSquare.get(i));
                inLineSquare.set(i, temp);

                Operator tem = operators.get(counter);
                operators.set(counter, operators.get(i));
                operators.set(i, tem);

                counter++;
            }
        }
        NumberSquare temp = inLineSquare.get(pivot);
        inLineSquare.set(pivot, inLineSquare.get(counter));
        inLineSquare.set(counter, temp);

        Operator tem = operators.get(pivot);
        operators.set(pivot, operators.get(counter));
        operators.set(counter, tem);

        return counter;
    }

    public ArrayList<Operator> getOperators() {
        return operators;
    }

    public void setOperators(ArrayList<Operator> operators) {
        this.operators = operators;
    }
}
