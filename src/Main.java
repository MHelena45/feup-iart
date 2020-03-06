import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;

public class Main extends Application {
    public Board board;
    @Override
    public void start(Stage stage) {
        Square square = new Square(150,70);
        Rectangle rectangle = square.setRectangle();

        //Creating a Group object
        Group root = new Group(rectangle);

        //Creating a scene object
        Scene scene = new Scene(root, 600, 300);

        //Setting title to the Stage
        stage.setTitle("Zhed");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
}