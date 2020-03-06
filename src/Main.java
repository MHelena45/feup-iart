import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Main extends Application {
    public Board board;

    int rowNum = 10;
    int colNum = 10;

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();

        Random rand = new Random();
        Color[] colors = {Color.DARKSEAGREEN, Color.GRAY, Color.HONEYDEW, Color.WHITESMOKE};

        int n = rand.nextInt(4)+1;
        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < colNum; col++) {
                n = rand.nextInt(4);
                Rectangle rec = new Rectangle();
                rec.setWidth(30);
                rec.setHeight(30);
                rec.setFill(colors[n]);
                GridPane.setRowIndex(rec, row);
                GridPane.setColumnIndex(rec, col);
                grid.getChildren().addAll(rec);
            }
        }

        Scene scene = new Scene(grid, 450, 350);

        primaryStage.setTitle("ZHED");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String args[]){
        launch(args);
    }
}