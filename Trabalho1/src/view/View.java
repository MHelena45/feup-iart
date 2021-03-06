package view;

import controller.Controller;
import javafx.scene.Group;;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Model;
import model.square.Square;
import view.event.RegularSquareClick;
import view.event.NumberSquareClick;

import java.util.ArrayList;

public class View {
    private Model model;
    private Controller controller;
    private Group root;
    private Group game;
    private SquareView squareView;
    private Buttons buttons;

    public View() {}

    public View(Model model) {
        this(model, null);
    }

    public View(Model model, Controller controller) {
        this.model = model;
        this.controller = controller;
        this.root = new Group();
        this.game = new Group();
        this.squareView = new SquareView(game);
        this.buttons = new Buttons(controller);

        for(int i = 0; i < buttons.getButtons().length; i ++)
            root.getChildren().add(buttons.getButtons()[i]);

        root.getChildren().add(game);
    }

    public void setController(Controller controller) {
        this.controller = controller;
        this.buttons.setController(controller);
    }

    public Group getRoot() {
        return root;
    }

    public void display() {
        ArrayList<ArrayList<Square>> matrix = model.getMatrix();

        //Clearing screen
        game.getChildren().clear();

        Text t = new Text(225, 20, "Level " + model.getLevel());
        t.setFont(Font.font ("Verdana", 15));
        game.getChildren().add(t);

        //Drawing the matrix
        for(int i = 0; i < matrix.size(); i++){
            for(int j = 0; j < matrix.get(i).size(); j++) {
                Square square = matrix.get(i).get(j);
                Group rectangle = squareView.drawRectangle(matrix.get(i).get(j));

                if(square.getNumber() > 0)
                    rectangle.setOnMouseClicked(new NumberSquareClick(controller, j, i));
                else
                    rectangle.setOnMouseClicked(new RegularSquareClick(controller, j, i));
            }
        }
    }
}
