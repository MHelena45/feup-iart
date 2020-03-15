package view.square;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.BufferedInputStream;
import java.util.ArrayList;

public class Buttons {
    public Button[] getButtons() {
        //Creating menu button
        Button menuButton = new Button("Menu");
        menuButton.setLayoutX(50);
        menuButton.setLayoutY(450);

        menuButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                System.out.println("Menu button");
            }
        }));

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
        return new Button[]{menuButton, hintButton, restartButton, undoButton};
    }
}
