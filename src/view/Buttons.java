package view;

import controller.Controller;
import javafx.scene.control.Button;

public class Buttons {
    private Controller controller;

    Buttons(Controller controller){
        this.controller = controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Button[] getButtons() {
        //Creating menu button
        Button solutionButton = new Button("Solution");
        solutionButton.setLayoutX(40);
        solutionButton.setLayoutY(480);

        solutionButton.setOnMouseClicked((event -> controller.solve()));

        //Creating stop button
        Button hintButton = new Button("Hint");
        hintButton.setLayoutX(120);
        hintButton.setLayoutY(480);

        hintButton.setOnMouseClicked((event -> System.out.println("hint button")));

        //Creating restart button
        Button undoButton = new Button("Undo");
        undoButton.setLayoutX(200);
        undoButton.setLayoutY(480);

        undoButton.setOnMouseClicked((event -> controller.undo()));

        //Creating restart button
        Button restartButton = new Button("Restart");
        restartButton.setLayoutX(250);
        restartButton.setLayoutY(480);

        restartButton.setOnMouseClicked((event -> controller.restart()));

        //Creating previous button
        Button previousButton = new Button("previous");
        previousButton.setLayoutX(360);
        previousButton.setLayoutY(480);

        previousButton.setOnMouseClicked(event -> controller.previousLevel());

        //Creating next button
        Button nextButton = new Button("next");
        nextButton.setLayoutX(480);
        nextButton.setLayoutY(480);

        nextButton.setOnMouseClicked(event -> controller.nextLevel());

        return new Button[]{solutionButton, hintButton, undoButton, restartButton, nextButton, previousButton};
    }
}
