package me.mredor.hw.cannon;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/** Cannon game application */
public class Main extends Application {
    private Target target;
    private Cannon cannon;
    private Background background;

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setHeight(600);
        primaryStage.setWidth(1200);
        primaryStage.setResizable(false);
        Group group = new Group();
        Scene scene = new Scene(group);
        primaryStage.setScene(scene);
        addBackground(group);
        addTarget(group);
        addCannon(group, target);
        scene.setRoot(group);
        primaryStage.getScene().setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    cannon.moveLeft();
                    break;
                case RIGHT:
                    cannon.moveRight();
                    break;
                case UP:
                    cannon.angleUp();
                    break;
                case DOWN:
                    cannon.angleDown();
                    break;
                case ENTER:
                    cannon.shoot();
                    break;
                case SPACE:
                    cannon.changeCannonball();
                    break;
                default:
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(
                            "Press RIGHT/LEFT to move cannon\n" +
                             "Press UP/DOWN to change angle\n" +
                            "Press SPACE to change cannonball type\n" +
                            "Press ENTER to shot\n");
                    alert.showAndWait();
            }
        });
        primaryStage.show();
    }

    private void addTarget(Group group) {
        target = new Target(group);
    }
    private void addCannon(Group group, Target target) {
        cannon = new Cannon(group, target);
    }

    private void addBackground(Group group) {
        background = new Background(group);
    }

}
