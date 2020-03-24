package view;

import controller.Controller;
import javafx.scene.control.Button;

public class Buttons {
    private static int level;
    private Controller controller;

    Buttons(int level, Controller controller){
        this.level = level;
        this.controller = controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Button[] getButtons() {
        //Creating menu button
        Button solutionButton = new Button("Solution");
        solutionButton.setLayoutX(10);
        solutionButton.setLayoutY(450);

        solutionButton.setOnMouseClicked((event -> System.out.println("Solution button")));

        //Creating stop button
        Button hintButton = new Button("Hint");
        hintButton.setLayoutX(90);
        hintButton.setLayoutY(450);

        hintButton.setOnMouseClicked((event -> System.out.println("hint button")));

        //Creating restart button
        Button restartButton = new Button("Restart");
        restartButton.setLayoutX(170);
        restartButton.setLayoutY(450);

        restartButton.setOnMouseClicked((event -> System.out.println("Restart button")));

        //Creating undo button
        Button undoButton = new Button("undo");
        undoButton.setLayoutX(250);
        undoButton.setLayoutY(450);

        undoButton.setOnMouseClicked((event -> controller.undo()));

        //Creating previous button
        Button previousButton = new Button("previous");
        previousButton.setLayoutX(310);
        previousButton.setLayoutY(450);

        previousButton.setOnMouseClicked((event -> {
            if(this.level == 1) {
                System.out.println("A previous puzzle doesn't exists");
            } else {
                this.setLevel(getLevel() - 1);
                controller.changeLevel(getLevel());
            }
        }));

        //Creating next button
        Button nextButton = new Button("next");
        nextButton.setLayoutX(380);
        nextButton.setLayoutY(450);

        nextButton.setOnMouseClicked((event -> {
            this.setLevel(getLevel() + 1);
            controller.changeLevel(getLevel());
        }));

        return new Button[]{solutionButton, hintButton, restartButton, undoButton, nextButton, previousButton};
    }
}
