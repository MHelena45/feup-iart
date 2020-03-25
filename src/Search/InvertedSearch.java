package Search;

import model.Operator;
import model.position.Position;
import model.square.GoalSquare;
import model.square.NumberSquare;

import java.util.ArrayList;

import static model.Operator.RIGHT;
import static model.Operator.UP;

public class InvertedSearch {

    private GoalSquare goalSquare;
    private ArrayList<NumberSquare> numberSquares;
    private ArrayList<Operator> operators = new ArrayList<>();
    private ArrayList<Position> positions = new ArrayList<>();

    InvertedSearch(GoalSquare goalSquare, ArrayList<NumberSquare> numberSquares) {
        this.goalSquare = goalSquare;
        this.numberSquares = numberSquares;
    }

    private void search() {
        boolean notAllSearch = true;
        findFirstSquare();
        while(notAllSearch) {
            findNextSquare();
        }
    }

    private void findFirstSquare() {
        int x = goalSquare.getX();
        int y = goalSquare.getY();
        positions.add(new Position(x, y ));

        for(int i=0; i < numberSquares.size(); i++) {
            if(numberSquares.get(i).getX() == x) {
                //found first/last square to be played
                //the goal square is above the number square
                if(numberSquares.get(i).getY() > y){
                    operators.add(Operator.UP);
                } else if(numberSquares.get(i).getY() < y){
                    operators.add(Operator.DOWN);
                } else {
                    System.out.println("number square as some position that goal square");
                }
                positions.add(new Position(numberSquares.get(i).getX(), numberSquares.get(i).getY() ));
                numberSquares.get(i).play();
            } else if(numberSquares.get(i).getY() == y) {
                //found first/last square to be played

                //the goal square is on the right the number square
                if(numberSquares.get(i).getX() > x){
                    operators.add(RIGHT);
                } else if(numberSquares.get(i).getX() < x){
                    operators.add(Operator.LEFT);
                } else {
                    System.out.println("number square as some position that goal square");
                }
                positions.add(new Position(numberSquares.get(i).getX(), numberSquares.get(i).getY() ));
                numberSquares.get(i).play();
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
                for(int i=0; i<numberSquares.size(); i++) {
                    //positions have the same x
                    if(numberSquares.get(i).isPlayed() == false) {
                        if(numberSquares.get(i).getY() < y1 && numberSquares.get(i).getY() > y2) {
                            //playing this square can makes reach the goal
                            //the founded square is on the right the number square
                            if(numberSquares.get(i).getX() < x1){
                                operators.add(RIGHT);
                            } else if(numberSquares.get(i).getX() > x1){
                                operators.add(Operator.LEFT);
                            } else {
                                System.out.println("same number square is being played");
                            }
                            positions.add(new Position(numberSquares.get(i).getX(), numberSquares.get(i).getY() ));
                            numberSquares.get(i).play();
                        }
                    }
                }
                break;
            case DOWN:
                for(int i=0; i<numberSquares.size(); i++) {
                    //positions have the same x
                    if(numberSquares.get(i).isPlayed() == false) {
                        if(numberSquares.get(i).getY() > y1 && numberSquares.get(i).getY() < y2) {
                            //playing this square can makes reach the goal
                            //the founded square is on the right the number square
                            if(numberSquares.get(i).getX() < x1){
                                operators.add(RIGHT);
                            } else if(numberSquares.get(i).getX() > x1){
                                operators.add(Operator.LEFT);
                            } else {
                                System.out.println("same number square is being played");
                            }
                            positions.add(new Position(numberSquares.get(i).getX(), numberSquares.get(i).getY() ));
                            numberSquares.get(i).play();
                        }
                    }
                }
                break;
            case RIGHT:
                for(int j=0; j<numberSquares.size(); j++) {
                    //positions have the same y
                    if(numberSquares.get(j).isPlayed() == false) {
                        if(numberSquares.get(j).getX() < x1 && numberSquares.get(j).getX() > x2) {
                            //playing this square can makes reach the goal
                            //the founded square is on the right the number square
                            if(numberSquares.get(j).getY() < y1){
                                operators.add(UP);
                            } else if(numberSquares.get(j).getY() > y1){
                                operators.add(Operator.DOWN);
                            } else {
                                System.out.println("same number square is being played");
                            }
                            positions.add(new Position(numberSquares.get(j).getX(), numberSquares.get(j).getY() ));
                            numberSquares.get(j).play();
                        }
                    }
                }
                break;
            case LEFT:
                for(int j=0; j<numberSquares.size(); j++) {
                    //positions have the same y
                    if(numberSquares.get(j).isPlayed() == false) {
                        if(numberSquares.get(j).getX() > x1 && numberSquares.get(j).getX() < x2) {
                            //playing this square can makes reach the goal
                            //the founded square is on the right the number square
                            if(numberSquares.get(j).getY() < y1){
                                operators.add(UP);
                            } else if(numberSquares.get(j).getY() > y1){
                                operators.add(Operator.DOWN);
                            } else {
                                System.out.println("same number square is being played");
                            }
                            positions.add(new Position(numberSquares.get(j).getX(), numberSquares.get(j).getY() ));
                            numberSquares.get(j).play();
                        }
                    }
                }
                break;
            default:
                break;
        }
    }
}
