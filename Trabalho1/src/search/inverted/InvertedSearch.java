package search.inverted;

import model.Operator;
import model.position.Position;
import model.square.GoalSquare;
import model.square.NumberSquare;
import model.square.Square;
import model.state.State;
import search.Node;
import search.Play;

import java.util.ArrayList;
import java.util.Stack;

import static java.lang.Math.abs;
import static model.Operator.*;

public class InvertedSearch {

    private GoalSquare goalSquare;
    private ArrayList<MyNode> nodes = new ArrayList<>();
    private ArrayList<Operator> operators = new ArrayList<>();
    private ArrayList<Square> squares = new ArrayList<>();
    private Integer needed_squares = -1;
    private Integer last_number;

    public InvertedSearch(GoalSquare goalSquare, ArrayList<Square> numberSquares) {
        this.goalSquare = goalSquare;
        getNodes(numberSquares);
        search();
    }

    private void getNodes(ArrayList<Square> numberSquares) {
        MyNode node;
        for(int i= 0; i < numberSquares.size(); i++ ) {
            node = new MyNode((NumberSquare) numberSquares.get(i), null);
            nodes.add(node);
        }
    }

    private void search() {
        sortByManhattan( 0, nodes.size() - 1);
        findFirstSquare();

        while(!allSearch()) {
            findNextSquare();
        }

        squares.remove(0);

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
            if (nodes.get(i).getNumberSquare().getManhattanDistance(goalSquare) <
                    nodes.get(pivot).getNumberSquare().getManhattanDistance(goalSquare)) {
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

    private boolean allSearch(){
        for(int i= 0; i < nodes.size(); i++) {
            if(nodes.get(i).isVisited() == false)
                return false;
        }
        return true;
    }

    private void findFirstSquare() {

        int x = goalSquare.getX();
        int y = goalSquare.getY();
        squares.add(goalSquare);

        for(int i=0; i < nodes.size(); i++) {
            NumberSquare numberSquare = nodes.get(i).getNumberSquare();
            if(nodes.get(i).getNumberSquare().getX() == x) {
                //found first/last square to be played
                //the goal square is above the number square
                if(numberSquare.getY() > y){
                    if(!checkInLineSquares(numberSquare.getX(), numberSquare.getY(), Operator.UP))
                        continue;
                    operators.add(Operator.UP);
                } else if(numberSquare.getY() < y){
                    if(!checkInLineSquares(numberSquare.getX(), numberSquare.getY(), Operator.DOWN))
                        continue;
                    operators.add(Operator.DOWN);
                } else {
                    System.out.println("number square as some position that goal square");
                }
                last_number = numberSquare.getNumber();
                squares.add(numberSquare);
                nodes.get(i).setVisited(true);

            } else if(numberSquare.getY() == y) {
                //found first/last square to be played
                //the goal square is on the right the number square
                if(numberSquare.getX() < x){
                    if(!checkInLineSquares(numberSquare.getX(), numberSquare.getY(), RIGHT))
                        continue;
                    operators.add(RIGHT);
                } else if(numberSquare.getX() > x){
                    if(!checkInLineSquares(numberSquare.getX(), numberSquare.getY(), LEFT))
                        continue;
                    operators.add(Operator.LEFT);
                } else {
                    System.out.println("number square as some position that goal square");
                }
                last_number = numberSquare.getNumber();
                squares.add(numberSquare);
                nodes.get(i).setVisited(true);
            }

        }

    }

    private Boolean checkInLineSquares(Integer x1, Integer y1, Operator operator) {
        //y of the last square played
        int y2 = squares.get(squares.size() - 1).getY();
        int x2 = squares.get(squares.size() - 1).getX();

        if(nodes.size() <= 2)
            return true;

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
                    MyNode node = nodes.get(j);
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
        }
        return false;
    }


    private void findNextSquare(){

        //y of the last square played
        int y1 = squares.get(squares.size() - 1).getY();
        //y of the penultimate square played
        int y2 = squares.get(squares.size() - 2).getY();

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
                                operators.add(Operator.UP);
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
                                operators.add(DOWN);
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
                                operators.add(RIGHT);
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
                                operators.add(Operator.LEFT);
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
    }

    private void putNeeded_squares(Integer coord1, Integer coord2){
        this.needed_squares = abs(coord1 - coord2) - last_number;
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
    }

    public Stack<Play> solve() {
        Stack<Play> result = new Stack<>();
        for(int i=0; i < operators.size(); i++) {
            Play transition = new Play(squares.get(i), operators.get(i));
            result.push(transition);
        }

        return result;
    }

}
