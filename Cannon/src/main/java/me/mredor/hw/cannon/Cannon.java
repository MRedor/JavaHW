package me.mredor.hw.cannon;

import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.QuadCurve;
import javafx.util.Duration;

import java.util.HashSet;

import static java.lang.Math.*;

/** Cannon class -- can move and shoot */
public class Cannon {
    private final int RADIUS = 15;
    private final int HEIGHT_ABOVE_GROUND = -10;
    private final int START_X = 0;
    private final int START_Y = 560;
    private double centerX;
    private double centerY;
    private Circle viewCannon;
    private int angle = 90;
    private Label viewAngle;
    private Cannonball cannonball;
    private Target target;
    private Group group;
    private Background background;

    /** Creates cannon and add it to view as a circle */
    public Cannon(Group group, Target target, Background background) {
        this.group = group;
        this.target = target;
        this.background = background;
        centerX = START_X;
        centerY = START_Y;
        viewCannon = new Circle(centerX, centerY + HEIGHT_ABOVE_GROUND, RADIUS);
        viewCannon.setFill(Color.BLUEVIOLET);
        group.getChildren().add(viewCannon);
        viewAngle = new Label("Angle: " + String.valueOf(angle));
        viewAngle.setLayoutX(100);
        viewAngle.setLayoutY(180);
        viewAngle.setStyle("-fx-font-weight: bold");
        viewAngle.setTextFill(Color.BLUEVIOLET);
        group.getChildren().add(viewAngle);
        cannonball = new Cannonball();
    }

    /** Moves cannon to left */
    public void moveLeft() {
        centerX -= 10;
        centerX = max(0, centerX);
        centerY = background.getYByX(centerX);
        viewCannon.setCenterX(centerX);
        viewCannon.setCenterY(centerY + HEIGHT_ABOVE_GROUND);
        cannonball.updateCoordinates();
    }

    /** Moves cannon to right */
    public void moveRight() {
        centerX += 10;
        centerX = min(1200, centerX);
        centerY = background.getYByX(centerX);
        viewCannon.setCenterX(centerX);
        viewCannon.setCenterY(centerY + HEIGHT_ABOVE_GROUND);
        cannonball.updateCoordinates();
    }

    /** Increases angle value */
    public void angleUp() {
        angle = min(140, angle + 1);
        viewAngle.setText("Angle: " + String.valueOf(angle));
    }

    /** Decreases angle value */
    public void angleDown() {
        angle = max(40, angle - 1);
        viewAngle.setText("Angle: " + String.valueOf(angle));
    }

    /** Shoots a cannon */
    public void shoot() {
        if (cannonball.shoot()) {
            group.getChildren().removeAll(viewCannon, viewAngle, cannonball.view, target.getView());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("WIN");
            alert.showAndWait();
        }
    }

    /** Changes type of cannonball */
    public void changeCannonball() {
        cannonball.changeType();
    }

    private class Cannonball{
        private final int radiusSmall = 5;
        private final int radiusBig = 9;
        private CannonballSize size;
        private Circle view;
        private double x;
        private double y;
        private double radius;
        private boolean notThrown;

        private Cannonball() {
            x = centerX;
            y = centerY;
            radius = radiusSmall;
            //isSmall = true;
            size = CannonballSize.SMALL;
            notThrown = true;
            view = new Circle(x, y + HEIGHT_ABOVE_GROUND, radius);
            view.setFill(Color.BLUE);
            group.getChildren().add(view);
        }

        private void updateCoordinates() {
            if (notThrown) {
                x = centerX;
                y = centerY;
                view.setCenterX(x);
                view.setCenterY(y + HEIGHT_ABOVE_GROUND);
            }
        }

        private void changeType() {
            if (size == CannonballSize.SMALL) {
                size = CannonballSize.BIG;
                radius = radiusBig;
            } else {
                size = CannonballSize.SMALL;
                radius = radiusSmall;
            }
            view.setRadius(radius);
        }

        private boolean shoot() {
            notThrown = false;
            double topY = y + HEIGHT_ABOVE_GROUND;
            double topX = x;
            double endX = x;
            double endY = y + HEIGHT_ABOVE_GROUND;
            double time = 0;
            double speed = 3 + (5 / (radius*radius));
            while (endY < background.getYByX(endX)) {
                time += 0.001;
                endX += speed * time * cos(Math.toRadians(angle));
                endY -= speed * time * sin(Math.toRadians(angle)) - 5 * time * time;
                if (endY < topY) {
                    topY = endY;
                    topX = endX;
                }
            }
            var result = isClose(endX, endY);
            QuadCurve quadcurve = new QuadCurve(x, y + HEIGHT_ABOVE_GROUND, topX, topY, endX, endY);
            quadcurve.setStroke(Color.GREEN);
            var pathTransition = new PathTransition();
            pathTransition.setNode(view);
            pathTransition.setDuration(Duration.seconds(2));
            pathTransition.setPath(quadcurve);
            pathTransition.setOnFinished(event -> {
                if (!result) {
                    notThrown = true;
                    //view.setCenterX(centerX);
                    //view.setCenterY(centerY);
                    group.getChildren().remove(view);
                    view = new Circle(centerX, centerY + HEIGHT_ABOVE_GROUND, radius);
                    view.setFill(Color.BLUE);
                    x = centerX;
                    y = centerY;
                    group.getChildren().add(view);
                }
            });
            pathTransition.play();
            return result;
        }

        private boolean isClose(double x, double y) {
            var distance = target.distance(x, y);
            if (size == CannonballSize.SMALL) {
                return (distance <= target.getRadius() + radius * 1.5);
            } else {
                return (distance <= target.getRadius() * 2 + radius * 2);
            }
        }
    }

    private enum CannonballSize {
        SMALL,
        BIG
    }
}
