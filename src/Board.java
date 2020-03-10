public class Board {
    private Number_Square[] number_squares;
    private GoalSquare goalSquare;
    private Square[][] board;
    private int size;

    public Board(int size) {
        this.size = size;
        goalSquare = new GoalSquare(10, 10);
        board = new Square[size][size];
    }


    public Number_Square[] getNumber_squares() {
        return number_squares;
    }

    public void setNumber_squares(Number_Square[] number_squares) {
        this.number_squares = number_squares;
    }

    public GoalSquare getGoalSquare() {
        return goalSquare;
    }

    public void setGoalSquare(GoalSquare goalSquare) {
        this.goalSquare = goalSquare;
    }
}
