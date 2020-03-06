public class Square {
    private Position position;
    public Square(int x, int y) {
        position = new Position(x, y);
    }

    public Square(Position position) {
        this.position = position;
    }
}
