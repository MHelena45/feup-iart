package controller;

import controller.state.ClickState;
import controller.state.IdleState;
import model.Model;
import model.Operator;
import model.position.Position;
import model.state.State;
import search.Play;
import view.View;

import java.util.Stack;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

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
            nextLevel();
        }
    }

    public void clickSquare(int x, int y) {
        model.getSquare(x,y).click();
        view.display();
    }

    public void numberClick(int x, int y) {
//        System.out.println("NumberSquare(" + x + ", " + y + ")");
        state.numberClick(new Position(x,y));
    }

    public void regularClick(int x, int y) {
//        System.out.println("RegularSquare(" + x + ", " + y + ")");
        state.regularClick(new Position(x,y));
    }

    public boolean isSolved() {
        return model.getGoalSquare().isFilled();
    }

    public void undo() {
        model.undo();
        view.display();
    }

    public void restart() {
        model.restart();
        view.display();
    }

    public void nextLevel() {
        if(model.getLevel() == 100) {
            System.err.println("You are at the final level!");
            return;
        }
        model.changeLevel(model.getLevel() + 1);
        view.display();
    }

    public void previousLevel(){
        if(model.getLevel() == 1) {
            System.err.println("You are at the first level!");
            return;
        }
        model.changeLevel(model.getLevel() - 1);
        view.display();
    }

    public void solve() {
        Stack<Play> solvedSequence = model.solve();

        while (!solvedSequence.empty()) {
            Play play = solvedSequence.pop();

            System.out.println(play.toString());
            model.nextState(play);
            view.display();
        }
    }
}

