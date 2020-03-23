package controller;

import model.Model;
import view.View;

public class Controller {
    private Model model;
    private View view;

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

    public void numberClick(int x, int y) {

    }

    public void regularClick(int x, int y) {

    }
}
