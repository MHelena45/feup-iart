import javafx.scene.shape.Rectangle;

public class Square {
    public Position position;
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
