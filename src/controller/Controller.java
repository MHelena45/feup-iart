package controller;

import controller.state.ClickState;
import model.Model;
import model.position.Position;
import view.View;

public class Controller {
    private Model model;
    private View view;
    private ClickState state;
    // Click information public to be acessed by State
    public Position previousClick;
    public Position currentClick;

    public Controller() {}

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setState(ClickState state) {
        this.state = state;
    }

    public void playSquare(int x, int y) {
        model.play(x, y);
        view.display();
    }

    public void numberClick(int x, int y) {
        System.out.println("NumberSquare(" + x + ", " + y + ")");
        state.numberClick(new Position(x,y));
    }

    public void regularClick(int x, int y) {
        System.out.println("RegularSquare(" + x + ", " + y + ")");
        state.regularClick(new Position(x,y));
    }
}
