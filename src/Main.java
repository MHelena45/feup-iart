import javafx.application.Application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import javafx.stage.Stage;
import model.Model;
import model.square.NumberSquare;
import model.square.Square;
import view.square.Buttons;
import view.square.SquareView;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        ArrayList<SquareView> squares = new ArrayList<>();
        for(int i = 0; i < 10; i ++) {
            for (int j = 0; j < 5; j++) {
                SquareView square = new SquareView(new NumberSquare(i, j, 3));
                squares.add(square);
            }
            for (int j = 5; j < 10; j++) {
                SquareView square = new SquareView(new Square(i, j));
                squares.add(square);
            }
        }

        //get Goal model.square.Square
//        Rectangle rectangle = board.getGoalSquare().getRectangle();
//
//        rectangle.setOnMouseClicked (new EventHandler<javafx.scene.input.MouseEvent>() {
//            @Override
//            public void handle(javafx.scene.input.MouseEvent e) {
//                System.out.println("Goal model.square.Square pressed");
//            }
//        });
//        NumberSquare sqr = new NumberSquare(1*25,1*25,2);
//        NumberSquareView view = new NumberSquareView();
//        Rectangle pane = view.draw(sqr);

        Buttons buttons = new Buttons();
        Button[] allButtons = buttons.getButtons();

        //Creating a Group object
        Group root = new Group();
        for(int i = 0; i < allButtons.length; i ++)
            root.getChildren().add(allButtons[i]);
        for(int i = 0; i <squares.size(); i++){
            root.getChildren().add(squares.get(i).getRectangle());
        }


        //Creating a scene object
        Scene scene = new Scene(root, 450, 500);
        scene.setFill(Color.LAVENDER);

        //Setting title to the Stage
        stage.setTitle("ZHED");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
    }
    public static void main(String args[]){
        try {
            Model model = new Model(1);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }
        launch(args);
    }
}