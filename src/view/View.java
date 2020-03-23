package view;

import javafx.scene.Group;;
import model.Model;
import model.square.Square;
import view.square.Buttons;
import view.square.NumberSquareView;
import view.square.SquareView;

import java.util.ArrayList;

public class View {
    private Model model;
    private Group root;
    private SquareView squareView;
    private Buttons buttons;

    public View(Model model) {
        this.model = model;
        this.root = new Group();
        this.squareView = new SquareView(root);
        this.buttons = new Buttons();

        for(int i = 0; i < buttons.getButtons().length; i ++)
            root.getChildren().add(buttons.getButtons()[i]);
    }

    public Group getRoot() {
        return root;
    }

    public void display() {
        ArrayList<ArrayList<Square>> matrix = model.getCurrentState().getBoard().getMatrix();

        for(int i = 0; i < matrix.size(); i++){
            for(int j = 0; j < matrix.get(i).size(); j++) {
                squareView.drawRectangle(matrix.get(i).get(j));
            }
        }
    }
}
