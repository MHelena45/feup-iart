public class Square {
    public Position position;
    Boolean fill = false;
    public Square(int x, int y) {
        position = new Position(x, y);
    }

    public Square(Position position) {
        this.position = position;
    }


    public Position getPosition(){
        return position;
    }
}
