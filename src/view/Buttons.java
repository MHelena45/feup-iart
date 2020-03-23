package view;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class Buttons {
    public Button[] getButtons() {
        //Creating menu button
        Button solutionButton = new Button("Solution");
        solutionButton.setLayoutX(50);
        solutionButton.setLayoutY(450);

        solutionButton.setOnMouseClicked((event -> System.out.println("Solution button")));

        //Creating stop button
        Button hintButton = new Button("Hint");
        hintButton.setLayoutX(150);
        hintButton.setLayoutY(450);

        hintButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                System.out.println("hint button");
            }
        }));

        //Creating restart button
        Button restartButton = new Button("Restart");
        restartButton.setLayoutX(250);
        restartButton.setLayoutY(450);

        restartButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                System.out.println("Restart button");
            }
        }));

        //Creating stop button
        Button undoButton = new Button("undo");
        undoButton.setLayoutX(350);
        undoButton.setLayoutY(450);

        undoButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                System.out.println("Undo");
            }
        }));
        return new Button[]{solutionButton, hintButton, restartButton, undoButton};
    }
}
