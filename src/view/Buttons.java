package view;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class Buttons {
    public Button[] getButtons() {
        //Creating menu button
        Button solutionButton = new Button("Solution");
        solutionButton.setLayoutX(20);
        solutionButton.setLayoutY(450);

        solutionButton.setOnMouseClicked((event -> System.out.println("Solution button")));

        //Creating stop button
        Button hintButton = new Button("Hint");
        hintButton.setLayoutX(120);
        hintButton.setLayoutY(450);

        hintButton.setOnMouseClicked((event -> System.out.println("hint button")));

        //Creating restart button
        Button restartButton = new Button("Restart");
        restartButton.setLayoutX(200);
        restartButton.setLayoutY(450);

        restartButton.setOnMouseClicked((event -> System.out.println("Restart button")));

        //Creating previousButton button
        Button previousButton = new Button("previous");
        previousButton.setLayoutX(290);
        previousButton.setLayoutY(450);

        previousButton.setOnMouseClicked((event -> System.out.println("previous puzzle")));

        //Creating next button
        Button nextButton = new Button("next");
        nextButton.setLayoutX(380);
        nextButton.setLayoutY(450);

        nextButton.setOnMouseClicked((event -> System.out.println("Undo")));

        return new Button[]{solutionButton, hintButton, restartButton, nextButton, previousButton};
    }
}