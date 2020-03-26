package Search;

import model.Operator;
import model.position.Position;
import model.square.GoalSquare;
import model.square.NumberSquare;
import model.square.Square;

import java.util.ArrayList;

import static model.Operator.RIGHT;
import static model.Operator.UP;

public class InvertedSearch {

    private GoalSquare goalSquare;
    private ArrayList<Node> nodes = new ArrayList<>();
    private ArrayList<Operator> operators = new ArrayList<>();
    private ArrayList<Position> positions = new ArrayList<>();

    public InvertedSearch(GoalSquare goalSquare, ArrayList<Square> numberSquares) {
        this.goalSquare = goalSquare;
        getNodes(numberSquares);
        search();
    }

    public ArrayList<Position> getPositions(){
        return positions;
    }

    public ArrayList<Operator> getOperators(){
        return operators;
    }

    private void getNodes(ArrayList<Square> numberSquares) {
        Node node;
        for(int i= 0; i < numberSquares.size(); i++ ) {
            node = new Node((NumberSquare) numberSquares.get(i), null);
            nodes.add(node);
        }
    }
    private void search() {

        findFirstSquare();
        while(!allSearch()) {
            findNextSquare();
        }

        reverseOperators();
        reversePositions();
    }

    private void reversePositions()
    {
        //remove goal square
        positions.remove(0);
        int n = this.positions.size();

        Position position;
        for (int i = 0; i < n / 2; i++) {
            position = positions.get(i);
            this.positions.set(i, positions.get(n - i - 1));
            positions.set(n - i - 1, position);
        }

        return;
    }

    private void reverseOperators()
    {
        int n = this.operators.size();
        Operator op;
        for (int i = 0; i < n / 2; i++) {
            op = this.operators.get(i);
            this.operators.set(i, operators.get(n - i - 1));
            operators.set(n - i - 1, op);
        }

        return;
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
        positions.add(new Position(x, y ));

        for(int i=0; i < nodes.size(); i++) {
            NumberSquare numberSquare = nodes.get(i).getNumberSquare();

            if(nodes.get(i).getNumberSquare().getX() == x) {
                //found first/last square to be played
                //the goal square is above the number square
                if(numberSquare.getY() > y){
                    operators.add(Operator.UP);
                } else if(numberSquare.getY() < y){
                    operators.add(Operator.DOWN);
                } else {
                    System.out.println("number square as some position that goal square");
                }
                positions.add(new Position(numberSquare.getX(), numberSquare.getY() ));
                nodes.get(i).setVisited(true);
                return;

            } else if(numberSquare.getY() == y) {
                //found first/last square to be played

                //the goal square is on the right the number square
                if(numberSquare.getX() < x){
                    operators.add(RIGHT);
                } else if(numberSquare.getX() > x){
                    operators.add(Operator.LEFT);
                } else {
                    System.out.println("number square as some position that goal square");
                }
                positions.add(new Position(numberSquare.getX(), numberSquare.getY() ));
                nodes.get(i).setVisited(true);
                return;

            }

        }
    }

    private void findNextSquare(){

        //y of the last square played
        int y1 = positions.get(positions.size() - 1).getY();
        //y of the penultimate square played
        int y2 = positions.get(positions.size() - 2).getY();

        int x1 = positions.get(positions.size() - 1).getX();
        int x2 = positions.get(positions.size() - 2).getX();

        switch (operators.get(operators.size() - 1)){

            case UP:
                for(int i=0; i< nodes.size(); i++) {
                    Node node = nodes.get(i);
                    //positions have the same x
                    if(node.isVisited() == false) {
                        if(node.getNumberSquare().getY() < y1 && node.getNumberSquare().getY() > y2) {
                            //playing this square can makes reach the goal
                            //the founded square is on the right the number square
                            if(node.getNumberSquare().getX() < x1){
                                operators.add(RIGHT);
                            } else if(node.getNumberSquare().getX() > x1){
                                operators.add(Operator.LEFT);
                            } else {
                                System.out.println("same number square is being played");
                            }
                            positions.add(new Position(node.getNumberSquare().getX(), node.getNumberSquare().getY() ));
                            node.setVisited(true);
                            return;
                        }
                    }
                }
                break;
            case DOWN:
                for(int i=0; i< nodes.size(); i++) {
                    Node node = nodes.get(i);
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
                            positions.add(new Position(node.getNumberSquare().getX(), node.getNumberSquare().getY() ));
                            node.setVisited(true);
                            return;
                        }
                    }
                }
                break;
            case RIGHT:
                for(int j=0; j< nodes.size(); j++) {
                    //positions have the same y
                    Node node = nodes.get(j);
                    if(node.isVisited() == false) {
                        if(node.getNumberSquare().getX() < x1 && node.getNumberSquare().getX() > x2) {
                            //playing this square can makes reach the goal
                            //the founded square is on the right the number square
                            if(node.getNumberSquare().getY() < y1){
                                operators.add(UP);
                            } else if(node.getNumberSquare().getY() > y1){
                                operators.add(Operator.DOWN);
                            } else {
                                System.out.println("same number square is being played");
                            }
                            positions.add(new Position(node.getNumberSquare().getX(), node.getNumberSquare().getY() ));
                            node.setVisited(true);
                            return;
                        }
                    }
                }
                break;
            case LEFT:
                for(int j=0; j< nodes.size(); j++) {
                    Node node = nodes.get(j);
                    //positions have the same y
                    if(node.isVisited() == false) {
                        if( node.getNumberSquare().getX() > x1 && node.getNumberSquare().getX() < x2) {
                            //playing this square can makes reach the goal
                            //the founded square is on the right the number square
                            if(node.getNumberSquare().getY() < y1){
                                operators.add(UP);
                            } else if( node.getNumberSquare().getY() > y1){
                                operators.add(Operator.DOWN);
                            } else {
                                System.out.println("same number square is being played");
                            }
                            positions.add(new Position( node.getNumberSquare().getX(), node.getNumberSquare().getY() ));
                            node.isVisited();
                            return;
                        }
                    }
                }
                break;
            default:
                break;
        }
    }
}
