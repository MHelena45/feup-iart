public class Board {
    private Number_Square[] number_squares;
    private GoalSquare goalSquare;
    private int Number_Rows;
    private int Number_Columns;

    public Board(int Number_Rows, int Number_Columns) {
        this.Number_Rows = Number_Rows;
        this.Number_Columns = Number_Columns;
        goalSquare = new GoalSquare(10, 10);
    }

    public int getNumber_Rows() {
        return Number_Rows;
    }

    public void setNumber_Rows(int number_Rows) {
        Number_Rows = number_Rows;
    }

    public int getNumber_Columns() {
        return Number_Columns;
    }

    public void setNumber_Columns(int number_Columns) {
        Number_Columns = number_Columns;
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
