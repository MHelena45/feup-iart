import controller.Controller;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.paint.Color;

import javafx.stage.Stage;
import model.Model;
import view.View;

public class Zhed extends Application {
    @Override
    public void start(Stage stage) {
        Model model = new Model(1);
        View view = new View(model);
        Controller controller = new Controller(model, view);

        view.setController(controller);
        view.display();

        //Creating a scene object
        Scene scene = new Scene(view.getRoot(), 580, 520);
        scene.setFill(Color.WHITE);

        //Setting title to the Stage
        stage.setTitle("ZHED");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
}