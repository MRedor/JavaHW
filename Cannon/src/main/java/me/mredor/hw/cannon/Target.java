package me.mredor.hw.cannon;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

import static me.mredor.hw.cannon.Background.getYByX;

/** Class of game target */
public class Target {
    private final int MAX_X = 1200;
    private final int TARGET_RADIUS = 8;
    private double centerX;
    private double centerY;
    private Circle view;

    /** Randomly generating target and adds it to the view as red circle */
    public Target(Group group) {
        var random = new Random();
        centerX = random.nextInt(MAX_X);
        centerY = getYByX(centerX);
        view = new Circle(centerX, centerY, TARGET_RADIUS);
        view.setFill(Color.RED);
        group.getChildren().add(view);
    }

    /** Gets radius of target */
    public int getRadius() {
        return TARGET_RADIUS;
    }

    /** Gets Circle -- view of target */
    public Circle getView() {
        return view;
    }

    /** Calculates distanse between point (x, y) with given coordinates and target */
    public double distance(double x, double y) {
        return (x - centerX) * (x - centerX) + (y - centerY) * (y - centerY);
    }
}
