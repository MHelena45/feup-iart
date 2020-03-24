package controller;

import controller.state.ClickState;
import controller.state.IdleState;
import model.Model;
import model.Operator;
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
        this.state = new IdleState(this);
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

    public void playSquare(Operator op) {
        model.play(previousClick.getX(), previousClick.getY(), op);
        view.display();

        if(isSolved()){
            changeLevel(model.getLevel() + 1);
            System.out.println("SOLVED!!!");
        }
    }

    public void clickSquare(int x, int y) {
        model.getMatrix().get(y).get(x).click();
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

    public boolean isSolved() {
        return model.getGoalSquare().isFilled();
    }

    public void restart() {
        System.out.println("restart");
        model.restart();
        view.display();
    }

    public void undo() {
        System.out.println("undo");
        model.undo();
        view.display();
    }

    public void changeLevel(int level){
        System.out.println("change level");
        model.changeLevel(level);
        view.display();
    }
}
