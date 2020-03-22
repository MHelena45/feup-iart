import javafx.application.Application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import javafx.stage.Stage;
import model.Model;
import model.square.GoalSquare;
import model.square.Square;
import view.square.Buttons;
import view.square.GoalSquareView;
import view.square.NumberSquareView;
import view.square.SquareView;


import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        ArrayList<ArrayList<Square>> matrix;
        try {
            Model model = new Model(1);
            matrix = model.getCurrentState().getBoard().getMatrix();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        Buttons buttons = new Buttons();
        Button[] allButtons = buttons.getButtons();

        //Creating a Group object
        Group root = new Group();
        for(int i = 0; i < allButtons.length; i ++)
            root.getChildren().add(allButtons[i]);


       for(int i = 0; i < matrix.size(); i++){
           for(int j=0; j < matrix.get(i).size(); j++) {
                if(matrix.get(i).get(j).toString() == "Goal Square") {
                    root.getChildren().add(new GoalSquareView(matrix.get(i).get(j)).getRectangle());
                } else if(matrix.get(i).get(j).toString() == "Number Square") {
                    root.getChildren().add(new NumberSquareView(matrix.get(i).get(j)).getRectangle());
                } else {
                    root.getChildren().add(new SquareView(matrix.get(i).get(j)).getRectangle());
                }

           }
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

        launch(args);
    }
}