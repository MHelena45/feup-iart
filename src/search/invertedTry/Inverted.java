package search.invertedTry;

import model.Operator;
import model.square.GoalSquare;
import model.square.NumberSquare;
import model.square.Square;
import search.Play;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;

import static java.lang.Math.abs;
import static model.Operator.*;

public class Inverted {
    private GoalSquare goalSquare;
    private static ArrayList<INode> nodes = new ArrayList<>();
    private static ArrayList<Square> squares;

    public Inverted(GoalSquare goalSquare, ArrayList<Square> numberSquares) {
        this.goalSquare = goalSquare;
        this.squares = numberSquares;
        System.out.println(squares.size());

        search();
    }


    private void search() {
        if(squares.size() > 1)
            sortByManhattan( 0, squares.size() - 1);

        findFirstSquare();
    }

    public void sortByManhattan(int begin, int end) {
        if (end <= begin) return;
        int pivot = partition(begin, end);
        sortByManhattan(begin, pivot-1);
        sortByManhattan( pivot+1, end);
    }

    private int partition(int begin, int end) {
        int pivot = end;
        int counter = begin;

        for (int i = begin; i < end; i++) {
            if (squares.get(i).getManhattanDistance(goalSquare) <
                    squares.get(pivot).getManhattanDistance(goalSquare)) {
                Square temp = squares.get(counter);
                squares.set(counter, squares.get(i));
                squares.set(i, temp);
                counter++;
            }
        }
        Square temp = squares.get(pivot);
        squares.set(pivot, squares.get(counter));
        squares.set(counter, temp);

        return counter;
    }

    private boolean allSearch(){
        if(nodes.size() == squares.size())
            return true;

        return false;
    }

    private void findFirstSquare() {
        int x = goalSquare.getX();
        int y = goalSquare.getY();

        for(int i=0; i < squares.size(); i++) {
            NumberSquare numberSquare = (NumberSquare) squares.get(i);
            if(squares.get(i).getX() == x) {
                //found first/last square to be played
                //the goal square is above the number square
                if(numberSquare.getY() > y){
                    if(!checkInLineSquares(numberSquare.getX(), numberSquare.getY(), Operator.UP))
                        continue;

                    INode iNode = new INode(numberSquare, abs(numberSquare.getY() - y), Operator.UP, goalSquare);
                    nodes.add(iNode);

                } else if(numberSquare.getY() < y){
                    if(!checkInLineSquares(numberSquare.getX(), numberSquare.getY(), Operator.DOWN))
                        continue;
                    INode iNode = new INode(numberSquare, abs(numberSquare.getY() - y), DOWN, goalSquare);
                    nodes.add(iNode);

                } else {
                    System.out.println("number square as some position that goal square");
                }

            } else if(numberSquare.getY() == y) {
                //found first/last square to be played
                //the goal square is on the right the number square
                if(numberSquare.getX() < x){

                    if(!checkInLineSquares(numberSquare.getX(), numberSquare.getY(), RIGHT))
                        continue;

                    INode iNode = new INode(numberSquare, abs(numberSquare.getX() - x), RIGHT, goalSquare);
                    nodes.add(iNode);

                } else if(numberSquare.getX() > x){
                    if(!checkInLineSquares(numberSquare.getX(), numberSquare.getY(), LEFT))
                        continue;

                    INode iNode = new INode(numberSquare, abs(numberSquare.getX() - x), LEFT, goalSquare);
                    nodes.add(iNode);
                } else {
                    System.out.println("number square as some position that goal square");
                }
            }

        }


        System.out.println(nodes.get(0).getSquare().toString());

        for(int i= 0; i < nodes.size(); i++ ) {
            Square square = nodes.get(i).getSquare();
            if(squares.equals(square)){
                squares.remove(square);
            }
        }

        findInlineSquares(nodes.get(0).getSquare());
    }

    private Boolean checkInLineSquares(Integer x1, Integer y1, Operator operator) {
        int y2 = goalSquare.getY();
        int x2 = goalSquare.getX();

        if(squares.size() <= 3)
            return true;

        switch (operator){
            case UP:
                for(int i=0; i< squares.size(); i++) {
                    Square square = squares.get(i);
                    //positions have the same x
                    if(square.getY() < y1 && square.getY() > y2 ) {
                        return true;
                    }
                }
                break;
            case DOWN:
                for(int i=0; i< squares.size(); i++) {
                    Square square = squares.get(i);
                    //positions have the same x
                    if(square.getY() > y1 && square.getY() < y2) {
                        return true;
                    }
                }
                break;
            case RIGHT:
                for(int j=0; j< squares.size(); j++) {
                    //positions have the same y
                    Square square = squares.get(j);
                    if(square.getX() < x2 && square.getX() > x1) {
                        //playing this square can makes reach the goal
                        //the founded square is on the right the number square
                        return true;
                    }

                }
                break;
            case LEFT:
                for(int j=0; j< squares.size(); j++) {
                    //positions have the same y
                    Square square = squares.get(j);
                    if( square.getX() > x2 && square.getX() < x1) {
                        //playing this square can makes reach the goal
                        //playing this square can makes reach the goal
                        return true;
                    }
                }
                break;
            default:
                break;
        }
        return false;
    }


    public static INode getInode(Square square) {
        for(int i= 0; i < nodes.size(); i++ ) {
            if(nodes.get(i).getSquare() == square)
                return nodes.get(i);
        }
        return null;
    }

    public void removeSquares( INode node) {
        //remove unneeded squares
        node.remove();

        ArrayList<Square> removeSquares = node.getInLineSquares();
        ArrayList<Operator> operators = node.getOperators();
        for(int i=0; i < removeSquares.size(); i++){
            Square numberSquare = removeSquares.get(i);
            INode iNode1;
            if(operators.get(i) == UP || operators.get(i) == DOWN)
                iNode1 = new INode((NumberSquare) numberSquare, abs(numberSquare.getY() - node.getSquare().getY()), operators.get(i), node);
            else {
                iNode1 = new INode((NumberSquare) numberSquare, abs(numberSquare.getX() - node.getSquare().getX()), operators.get(i), node);
            }
            nodes.add(iNode1);
        }

    }

    /**
     *
     * @param square1
     */
    public void findInlineSquares(Square square1) {

        INode iNode = getInode(square1);

        //Final square
        if(iNode.getNeedSquares() <= 0){
            return;
        }

        Square square2 = iNode.getParentSquare();  //in first time is goal square

        int y1 = square1.getY();
        int x1 = square1.getX();

        int x2, y2;
        if(square2 == null) {
            x2 = goalSquare.getX();
            y2 = goalSquare.getY();

        } else {
            y2 = square2.getY();
            x2 = square2.getX();
        }

        switch (iNode.getOperator()) {
            case UP:
                for (int i = 0; i < squares.size(); i++) {
                    NumberSquare numberSquare = (NumberSquare) squares.get(i);
                    //positions have the same x
                    if (numberSquare.getY() < y1 && numberSquare.getY() > y2) {
                        //playing this square can makes reach the goal
                        //the founded square is on the right the number square
                        if (numberSquare.getX() < x1) {
                            if(!iNode.isParent(numberSquare)) {
                                iNode.addInLineSquare(numberSquare);
                                iNode.addOperator(RIGHT);
                            }
                        } else if (squares.get(i).getX() > x1) {
                            if(!iNode.isParent(numberSquare)) {
                                iNode.addInLineSquare(numberSquare);
                                iNode.addOperator(LEFT);
                            }
                        } else {
                            if(!iNode.isParent(numberSquare)) {
                                iNode.addInLineSquare(numberSquare);
                                iNode.addOperator(UP);
                            }
                        }
                    }
                }
                break;
            case DOWN:
                for (int i = 0; i < squares.size(); i++) {
                    NumberSquare numberSquare = (NumberSquare) squares.get(i);
                    //positions have the same x
                    if (numberSquare.getY() > y1 && numberSquare.getY() < y2) {
                        //playing this square can makes reach the goal
                        //the founded square is on the right the number square
                        if (numberSquare.getX() < x1) {
                            if(!iNode.isParent(numberSquare)) {
                                iNode.addInLineSquare(numberSquare);
                                iNode.addOperator(RIGHT);
                            }
                        } else if (numberSquare.getX() > x1) {
                            if(!iNode.isParent(numberSquare)) {
                                iNode.addInLineSquare(numberSquare);
                                iNode.addOperator(LEFT);
                            }
                        } else {
                            if(!iNode.isParent(numberSquare)) {
                                iNode.addInLineSquare(numberSquare);
                                iNode.addOperator(DOWN);
                            }
                        }

                    }
                }
                break;
            case RIGHT:
                for (int j = 0; j < squares.size(); j++) {
                    NumberSquare numberSquare = (NumberSquare) squares.get(j);
                    //positions have the same y
                    if (numberSquare.getX() < x2 && numberSquare.getX() > x1) {
                        //playing this square can makes reach the goal
                        //the founded square is on the right the number square
                        if (numberSquare.getY() > y1) {
                            if(!iNode.isParent(numberSquare)) {
                                iNode.addInLineSquare(numberSquare);
                                iNode.addOperator(UP);
                            }

                        } else if (numberSquare.getY() < y1) {
                            if(!iNode.isParent(numberSquare)) {
                                iNode.addInLineSquare(numberSquare);
                                iNode.addOperator(DOWN);
                            }

                        } else {
                            if(!iNode.isParent(numberSquare)) {
                                iNode.addInLineSquare(numberSquare);
                                iNode.addOperator(RIGHT);
                            }
                        }
                    }
                }
                break;
            case LEFT:
                for (int j = 0; j < squares.size(); j++) {
                    NumberSquare numberSquare = (NumberSquare) squares.get(j);
                    //positions have the same y
                    if (numberSquare.getX() > x2 && numberSquare.getX() < x1) {
                        //playing this square can makes reach the goal
                        //the founded square is on the right the number square
                        if (numberSquare.getY() > y1) {
                            if(!iNode.isParent(numberSquare)) {
                                iNode.addInLineSquare(numberSquare);
                                iNode.addOperator(UP);
                            }
                        } else if (numberSquare.getY() < y1) {
                            if(!iNode.isParent(numberSquare)) {
                                iNode.addInLineSquare(numberSquare);
                                iNode.addOperator(DOWN);
                            }
                        } else {
                            if(!iNode.isParent(numberSquare)) {
                                iNode.addInLineSquare(numberSquare);
                                iNode.addOperator(LEFT);
                            }
                        }
                    }
                }
                break;
        }

        removeSquares(iNode);

        ArrayList<NumberSquare> inLineSquares = iNode.getInLineSquares();
        for(int i = 0; i < inLineSquares.size(); i++) {
            findInlineSquares(inLineSquares.get(i));
        }
    }

    public Stack<Play> solve() {
        Stack<Play> result = new Stack<>();

        for(int i= 0; i < nodes.size(); i++ ){
                Play transition = new Play(nodes.get(i).getSquare(), nodes.get(i).getOperator());
                result.push(transition);

        }

        nodes = new ArrayList<>();

        return result;
    }
}
