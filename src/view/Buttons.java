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
        solutionButton.setLayoutX(40);
        solutionButton.setLayoutY(480);

        solutionButton.setOnMouseClicked((event -> System.out.println("Solution button")));

        //Creating stop button
        Button hintButton = new Button("Hint");
        hintButton.setLayoutX(120);
        hintButton.setLayoutY(480);

        hintButton.setOnMouseClicked((event -> System.out.println("hint button")));

        //Creating restart button
        Button restartButton = new Button("Restart");
        restartButton.setLayoutX(250);
        restartButton.setLayoutY(480);

        restartButton.setOnMouseClicked((event -> controller.restart()));

        //Creating previous button
        Button previousButton = new Button("previous");
        previousButton.setLayoutX(360);
        previousButton.setLayoutY(480);

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
        nextButton.setLayoutX(480);
        nextButton.setLayoutY(480);

        nextButton.setOnMouseClicked((event -> {
            if(this.level == 100) {
                System.out.println("There aren't more puzzles");
            } else {
                this.setLevel(getLevel() + 1);
                controller.changeLevel(getLevel());
            }
        }));

        return new Button[]{solutionButton, hintButton, restartButton, nextButton, previousButton};
    }
}
