public class OrangeSquare extends Square {

    private int number;

    public OrangeSquare(int x, int y, int number) {
        super(x, y);
        this.number = number;
    }

    public OrangeSquare(Position position, int number) {
        super(position);
        this.number = number;
    }
}
