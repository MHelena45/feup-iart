package model;

public enum Operator {
    UP, DOWN, LEFT, RIGHT;

    public Operator getNextOperator() {
        Operator nextOperator = UP;

        switch (this) {
            case UP:
                nextOperator = DOWN;
                break;
            case DOWN:
                nextOperator = LEFT;
            break;
            case LEFT:
                nextOperator = RIGHT;
            break;
            case RIGHT:
                nextOperator = UP;
            break;
        }

        return nextOperator;
    }
}
