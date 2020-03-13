import javafx.application.Application;
import javafx.event.EventHandler;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import javafx.scene.shape.*;
import javafx.stage.Stage;
import model.Model;
import model.board.Board;
import model.square.NumberSquare;
import model.square.Square;
import model.state.State;
import view.square.NumberSquareView;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        //get Goal model.square.Square
//        Rectangle rectangle = board.getGoalSquare().getRectangle();
//
//        rectangle.setOnMouseClicked (new EventHandler<javafx.scene.input.MouseEvent>() {
//            @Override
//            public void handle(javafx.scene.input.MouseEvent e) {
//                System.out.println("Goal model.square.Square pressed");
//            }
//        });
        NumberSquare sqr = new NumberSquare(1*25,1*25,2);
        NumberSquareView view = new NumberSquareView();

        Rectangle pane = view.draw(sqr);

        //Creating menu button
        Button menuButton = new Button("Menu");
        menuButton.setLayoutX(50);
        menuButton.setLayoutY(250);

        menuButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                System.out.println("Menu button");
            }
        }));

        //Creating stop button
        Button hintButton = new Button("Hint");
        hintButton.setLayoutX(200);
        hintButton.setLayoutY(250);

        hintButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                System.out.println("hint button");
            }
        }));

        //Creating restart button
        Button restartButton = new Button("Restart");
        restartButton.setLayoutX(350);
        restartButton.setLayoutY(250);

        restartButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                System.out.println("Restart button");
            }
        }));

        //Creating stop button
        Button undoButton = new Button("undo");
        undoButton.setLayoutX(500);
        undoButton.setLayoutY(250);

        undoButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                System.out.println("Undo");
            }
        }));

        //Creating a Group object
        Group root = new Group( pane, menuButton, hintButton, restartButton, undoButton);

        //Creating a scene object
        Scene scene = new Scene(root, 600, 300);
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