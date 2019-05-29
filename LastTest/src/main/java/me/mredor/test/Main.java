package me.mredor.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Screen;
import javafx.stage.Stage;

/** Pairs game application */
public class Main extends Application {
    Controller controller;

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        var size = Integer.parseInt(getParameters().getUnnamed().get(0));
        if (size % 2 == 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Size couldn't be odd");
            alert.showAndWait();
        } else {
            var primaryScreenBounds = Screen.getPrimary().getVisualBounds();
            primaryStage.setWidth(primaryScreenBounds.getWidth());
            primaryStage.setHeight(primaryScreenBounds.getHeight());
            primaryStage.setMinHeight(size * 100);
            primaryStage.setMinWidth(size * 100);
            var buttons = new Button[size][size];
            var grid = new GridPane();
            for (int i = 0; i < size; i++) {
                var row = new RowConstraints();
                row.setVgrow(Priority.ALWAYS);
                grid.getRowConstraints().add(row);
                var column = new ColumnConstraints();
                column.setHgrow(Priority.ALWAYS);
                grid.getColumnConstraints().add(column);
            }
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    buttons[i][j] = new Button();
                    buttons[i][j].setMaxHeight(Double.MAX_VALUE);
                    buttons[i][j].setMaxWidth(Double.MAX_VALUE);
                    grid.add(buttons[i][j], i, j);
                }
            }
            grid.setMaxHeight(Double.MAX_VALUE);
            grid.setMaxWidth(Double.MAX_VALUE);
            primaryStage.setScene(new Scene(grid));
            controller = new Controller(buttons);
            primaryStage.show();
        }

    }
}
