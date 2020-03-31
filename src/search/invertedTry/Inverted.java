package search.invertedTry;

import model.Operator;
import model.square.GoalSquare;
import model.square.NumberSquare;
import model.square.Square;
import search.Play;

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
            INode iNode;
            if(squares.get(i).getX() == x) {
                //found first/last square to be played
                //the goal square is above the number square
                if(numberSquare.getY() > y){
                   /* if(!checkInLineSquares(numberSquare.getX(), numberSquare.getY(), Operator.UP))
                        continue;*/

                    iNode = new INode(numberSquare, abs(numberSquare.getY() - y), Operator.UP);
                    nodes.add(iNode);

                } else if(numberSquare.getY() < y){
                   /* if(!checkInLineSquares(numberSquare.getX(), numberSquare.getY(), Operator.DOWN))
                        continue; */
                    iNode = new INode(numberSquare, abs(numberSquare.getY() - y), DOWN);
                    nodes.add(iNode);

                } else {
                    System.out.println("number square as some position that goal square");
                }

            } else if(numberSquare.getY() == y) {
                //found first/last square to be played
                //the goal square is on the right the number square
                if(numberSquare.getX() < x){
                  /*  if(!checkInLineSquares(numberSquare.getX(), numberSquare.getY(), RIGHT))
                        continue; */
                  System.out.println("here");
                    iNode = new INode(numberSquare, abs(numberSquare.getY() - y), RIGHT);
                    nodes.add(iNode);
                } else if(numberSquare.getX() > x){
                   /* if(!checkInLineSquares(numberSquare.getX(), numberSquare.getY(), LEFT))
                        continue; */
                    iNode = new INode(numberSquare, abs(numberSquare.getY() - y), LEFT);
                    nodes.add(iNode);
                } else {
                    System.out.println("number square as some position that goal square");
                }
            }

        }

    }

    private Boolean checkInLineSquares(Integer x1, Integer y1, Operator operator) {
        //y of the last square played
        int y2 = squares.get(squares.size() - 1).getY();
        int x2 = squares.get(squares.size() - 1).getX();

        if(nodes.size() <= 2)
            return true;
/*
        switch (operator){
            case UP:
                for(int i=0; i< nodes.size(); i++) {
                    MyNode node = nodes.get(i);
                    //positions have the same x
                    if(node.isVisited() == false) {
                        if(node.getNumberSquare().getY() < y1 && node.getNumberSquare().getY() > y2 ) {
                            return true;
                        }
                    }
                }
                break;
            case DOWN:
                for(int i=0; i< nodes.size(); i++) {
                    MyNode node = nodes.get(i);
                    //positions have the same x
                    if(node.isVisited() == false) {
                        if(node.getNumberSquare().getY() > y1 && node.getNumberSquare().getY() < y2) {
                            return true;
                        }
                    }
                }
                break;
            case RIGHT:
                for(int j=0; j< nodes.size(); j++) {
                    //positions have the same y
                    MyNode node = nodes.get(j);
                    if(node.isVisited() == false) {
                        if(node.getNumberSquare().getX() < x2 && node.getNumberSquare().getX() > x1) {
                            //playing this square can makes reach the goal
                            //the founded square is on the right the number square
                            return true;
                        }
                    }
                }
                break;
            case LEFT:
                for(int j=0; j< nodes.size(); j++) {
                    INode node = nodes.get(j);
                    //positions have the same y
                    if(node.isVisited() == false) {
                        if( node.getNumberSquare().getX() > x2 && node.getNumberSquare().getX() < x1) {
                            //playing this square can makes reach the goal
                            //playing this square can makes reach the goal
                            return true;
                        }
                    }
                }
                break;
            default:
                break;
        } */
        return false;
    }


    public static INode getInode(Square square) {
        for(int i= 0; i < nodes.size(); i++ ) {
            if(nodes.get(i).getSquare() == square)
                return nodes.get(i);
        }
        return null;
    }
    /**
     *
     * @param square1
     */
    public static void findInlineSquares(Square square1) {

        INode iNode = getInode(square1);
        Square square2 = iNode.getParentSquare();  //in first time is goal square

        int y2 = square2.getY();
        int y1 = square1.getY();

        int x1 = square1.getX();
        int x2 = square2.getX();

        switch (iNode.getOperator()) {
            case UP:
                for (int i = 0; i < squares.size(); i++) {
                    NumberSquare numberSquare = (NumberSquare) squares.get(i);
                    //positions have the same x
                    if (numberSquare.getY() < y1 && numberSquare.getY() > y2) {
                        //playing this square can makes reach the goal
                        //the founded square is on the right the number square
                        if (numberSquare.getX() < x1) {
                            iNode = new INode(numberSquare, abs(numberSquare.getX() - x1), RIGHT, iNode);
                            nodes.add(iNode);
                        } else if (squares.get(i).getX() > x1) {
                            iNode = new INode(numberSquare, abs(numberSquare.getX() - x1), LEFT, iNode);
                            nodes.add(iNode);
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
                            iNode = new INode(numberSquare, abs(numberSquare.getX() - x1), RIGHT, iNode);
                            nodes.add(iNode);
                        } else if (numberSquare.getX() > x1) {
                            iNode = new INode(numberSquare, abs(numberSquare.getX() - x1), LEFT, iNode);
                            nodes.add(iNode);
                        } else {
                            System.out.println("same number square is being played");
                        }

                    }
                }
                break;
            case RIGHT:
                for (int j = 0; j < nodes.size(); j++) {
                    NumberSquare numberSquare = (NumberSquare) squares.get(j);
                    //positions have the same y
                    if (numberSquare.getX() < x2 && numberSquare.getX() > x1) {
                        //playing this square can makes reach the goal
                        //the founded square is on the right the number square
                        if (numberSquare.getY() > y1) {
                            iNode = new INode(numberSquare, abs(numberSquare.getX() - x1), UP, iNode);
                            nodes.add(iNode);
                        } else if (numberSquare.getY() < y1) {
                            iNode = new INode(numberSquare, abs(numberSquare.getX() - x1), DOWN, iNode);
                            nodes.add(iNode);
                        } else {
                            System.out.println("same number square is being played");
                        }
                    }
                }
                break;
            case LEFT:
                for (int j = 0; j < nodes.size(); j++) {
                    NumberSquare numberSquare = (NumberSquare) squares.get(j);
                    //positions have the same y
                    if (numberSquare.getX() > x2 && numberSquare.getX() < x1) {
                        //playing this square can makes reach the goal
                        //the founded square is on the right the number square
                        if (numberSquare.getY() > y1) {
                            iNode = new INode(numberSquare, abs(numberSquare.getX() - x1), UP, iNode);
                            nodes.add(iNode);
                        } else if (numberSquare.getY() < y1) {
                            iNode = new INode(numberSquare, abs(numberSquare.getX() - x1), DOWN, iNode);
                            nodes.add(iNode);
                        } else {
                            System.out.println("same number square is being played");
                        }
                    }
                }
                break;
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


 /*   public void sortByX(int begin, int end) {
        if (end <= begin) return;
        int pivot = partitionX(begin, end);
        sortByX(begin, pivot-1);
        sortByX( pivot+1, end);
    }

    private int partitionX(int begin, int end) {
        int pivot = end;
        int counter = begin;

        for (int i = begin; i < end; i++) {
            if (nodes.get(i).getNumberSquare().getXDistance(goalSquare) <
                    nodes.get(pivot).getNumberSquare().getXDistance(goalSquare)) {
                MyNode temp = nodes.get(counter);
                nodes.set(counter, nodes.get(i));
                nodes.set(i, temp);
                counter++;
            }
        }
        MyNode temp =nodes.get(pivot);
        nodes.set(pivot, nodes.get(counter));
        nodes.set(counter, temp);

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
            if (nodes.get(i).getNumberSquare().getYDistance(goalSquare) <
                    nodes.get(pivot).getNumberSquare().getYDistance(goalSquare)) {
                MyNode temp = nodes.get(counter);
                nodes.set(counter, nodes.get(i));
                nodes.set(i, temp);
                counter++;
            }
        }
        MyNode temp =nodes.get(pivot);
        nodes.set(pivot, nodes.get(counter));
        nodes.set(counter, temp);

        return counter;
    } */

    public Stack<Play> solve() {
        Stack<Play> result = new Stack<>();

        Square lastSquare = nodes.get(0).getSquare();
        //play first
        Play transition = new Play(nodes.get(0).getSquare(), nodes.get(0).getOperator());
        result.push(transition);


        for(int i= 0; i < nodes.size(); i++ ){
            if(nodes.get(i).isParent(lastSquare)){
                transition = new Play(lastSquare, nodes.get(0).getOperator());
                result.push(transition);
            }

        }

        nodes = new ArrayList<>();

        return result;
    }
}
