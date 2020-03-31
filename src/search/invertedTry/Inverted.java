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

     /*   while(!allSearch()) {
            findNextSquare();
        }*/

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

        for(int i= 0; i < nodes.size(); i++ ) {
            if(squares.equals(nodes.get(i)))
                squares.remove(i);

           // findInlineSquares(nodes.get(i).getSquare());
        }

        System.out.println("Squares: " + squares.size());
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
       // node.remove();

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

        for(int i=0; i < removeSquares.size(); i++){
            squares.remove(removeSquares.get(i));
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
            System.out.println("Leave");
            return;
        }

        Square square2 = iNode.getParentSquare();  //in first time is goal square

        int y1 = square1.getY();
        int x1 = square1.getX();

        int x2, y2;
        if(square2 == null) {
            x2 = goalSquare.getX();
            y2 = goalSquare.getY();
            squares.remove(square1);

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
                            System.out.println("same number square is being played");
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
                            System.out.println("same number square is being played");
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
                            System.out.println("same number square is being played");
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
                            System.out.println("same number square is being played");
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


    /*private void findNextSquare(){
        int y2;
        if(nodes.size() == 1) {
            y2 = goalSquare.getY();
        } else {
            //y of the penultimate square played
            y2 = squares.get(squares.size() - 2).getY();
        }
        //y of the last square played
        int y1 = nodes.get(nodes.size() - 1).getY();


        int x1 = squares.get(squares.size() - 1).getX();
        int x2 = squares.get(squares.size() - 2).getX();

        switch (operators.get(operators.size() - 1)){
            case UP:
                putNeeded_squares(y1, y2);
                sortByX(0, nodes.size() - 1);
                for(int i=0; i< nodes.size(); i++) {
                    MyNode node = nodes.get(i);
                    if(needed_squares == 0)
                        break;
                    //positions have the same x
                    if(node.isVisited() == false) {
                        if(node.getNumberSquare().getY() < y1 && node.getNumberSquare().getY() > y2 ) {
                            //playing this square can makes reach the goal
                            //the founded square is on the right the number square
                            if(node.getNumberSquare().getX() < x1){
                                operators.add(RIGHT);
                            } else if(node.getNumberSquare().getX() > x1){
                                operators.add(Operator.LEFT);
                            } else {
                                System.out.println("same number square is being played");
                            }
                            squares.add(node.getNumberSquare());
                            needed_squares--;
                            last_number = node.getNumberSquare().getNumber();
                            nodes.get(i).setVisited(true);
                        }
                    }
                }
                break;
            case DOWN:
                putNeeded_squares(y1, y2);
                sortByX(0, nodes.size() - 1);
                for(int i=0; i< nodes.size(); i++) {
                    MyNode node = nodes.get(i);
                    if(needed_squares == 0)
                        break;
                    //positions have the same x
                    if(node.isVisited() == false) {
                        if(node.getNumberSquare().getY() > y1 && node.getNumberSquare().getY() < y2) {
                            //playing this square can makes reach the goal
                            //the founded square is on the right the number square
                            if(node.getNumberSquare().getX() < x1){
                                operators.add(RIGHT);
                            } else if(node.getNumberSquare().getX() > x1){
                                operators.add(Operator.LEFT);
                            } else {
                                System.out.println("same number square is being played");
                            }
                            squares.add(node.getNumberSquare());
                            needed_squares--;
                            last_number = node.getNumberSquare().getNumber();
                            nodes.get(i).setVisited(true);
                        }
                    }
                }
                break;
            case RIGHT:
                putNeeded_squares(x1, x2);
                sortByY(0, nodes.size() - 1);
                for(int j=0; j< nodes.size(); j++) {
                    //positions have the same y
                    MyNode node = nodes.get(j);
                    if(needed_squares == 0)
                        break;
                    if(node.isVisited() == false) {
                        if(node.getNumberSquare().getX() < x2 && node.getNumberSquare().getX() > x1) {
                            //playing this square can makes reach the goal
                            //the founded square is on the right the number square
                            if(node.getNumberSquare().getY() > y1){
                                operators.add(UP);
                            } else if(node.getNumberSquare().getY() < y1){
                                operators.add(Operator.DOWN);
                            } else {
                                System.out.println("same number square is being played");
                            }
                            squares.add(node.getNumberSquare());
                            needed_squares--;
                            last_number = node.getNumberSquare().getNumber();
                            nodes.get(j).setVisited(true);
                        }
                    }
                }
                break;
            case LEFT:
                putNeeded_squares(x1, x2);
                sortByY(0, nodes.size() - 1);
                for(int j=0; j< nodes.size(); j++) {
                    MyNode node = nodes.get(j);
                    if(needed_squares == 0)
                        break;
                    //positions have the same y
                    if(node.isVisited() == false) {
                        if( node.getNumberSquare().getX() > x2 && node.getNumberSquare().getX() < x1) {
                            //playing this square can makes reach the goal
                            //the founded square is on the right the number square
                            if(node.getNumberSquare().getY() > y1){
                                operators.add(Operator.UP);
                            } else if( node.getNumberSquare().getY() < y1){
                                operators.add(Operator.DOWN);
                            } else {
                                System.out.println("same number square is being played");
                            }
                            squares.add(node.getNumberSquare());
                            needed_squares--;
                            last_number = node.getNumberSquare().getNumber();
                            nodes.get(j).setVisited(true);
                        }
                    }
                }
                break;
            default:
                break;
        }
    } */


    public Stack<Play> solve() {
        Stack<Play> result = new Stack<>();


        for(int i= 0; i < nodes.size(); i++ ){
           // if(nodes.get(i).getNeedSquares() == nodes.get(i).getInLineSquares().size()) {
                Play transition = new Play(nodes.get(i).getSquare(), nodes.get(i).getOperator());
                result.push(transition);
          //  }

        }

        System.out.println("Number of nodes: " + nodes.size());

        nodes = new ArrayList<>();

        return result;
    }
}
