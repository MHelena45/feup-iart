package model.square;

public class NumberSquare extends Square {
    private int number;
    private boolean played;
    private boolean clicked;

    public NumberSquare(int x, int y, int number) {
        super(x, y, true);
        this.number = number;
        this.played = false;
        this.clicked = false;
    }

    public void play() { played = true; }

    public void undo() { played = false; }

    public void click() { clicked = !clicked; }

    @Override
    public boolean isPlayed() {
        return played;
    }

    @Override
    public int getNumber() {
        return number;
    }
}