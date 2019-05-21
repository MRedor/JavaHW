package me.mredor.hw.cannon;

import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.QuadCurve;
import javafx.util.Duration;

import static java.lang.Math.*;
import static me.mredor.hw.cannon.Background.getYByX;

/** Cannon class -- can move and shoot */
public class Cannon {
    private final int upY = -10;
    private double centerX;
    private double centerY;
    private Circle viewCannon;
    private int angle = 90;
    private Label viewAngle;
    private Cannonball cannonball;
    private Target target;
    private Group group;

    /** Creates cannon and add it to view as a circle */
    public Cannon(Group group, Target target) {
        this.group = group;
        this.target = target;
        centerX = 100;
        centerY = 550;
        viewCannon = new Circle(centerX, centerY + upY, 15);
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
        centerY = getYByX(centerX);
        viewCannon.setCenterX(centerX);
        viewCannon.setCenterY(centerY + upY);
        cannonball.updateCoordinates();
    }

    /** Moves cannon to right */
    public void moveRight() {
        centerX += 10;
        centerX = min(1200, centerX);
        centerY = getYByX(centerX);
        viewCannon.setCenterX(centerX);
        viewCannon.setCenterY(centerY + upY);
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
        private boolean isSmall;
        private Circle view;
        private double x;
        private double y;
        private double radius;
        private boolean isAsleep;

        private Cannonball() {
            x = centerX;
            y = centerY;
            radius = radiusSmall;
            isSmall = true;
            isAsleep = true;
            view = new Circle(x, y + upY, radius);
            view.setFill(Color.BLUE);
            group.getChildren().add(view);
        }

        private void updateCoordinates() {
            if (isAsleep) {
                x = centerX;
                y = centerY;
                view.setCenterX(x);
                view.setCenterY(y + upY);
            }
        }

        private void changeType() {
            isSmall = !isSmall;
            if (isSmall) {
                radius = radiusSmall;
            } else {
                radius = radiusBig;
            }
            view.setRadius(radius);
        }

        private boolean shoot() {
            isAsleep = false;
            double topY = y + upY;
            double topX = x;
            double endX = x;
            double endY = y + upY;
            double time = 0;
            double speed = 3 + (5 / (radius*radius));
            while (endY < getYByX(endX)) {
                time += 0.001;
                endX += speed * time * cos(Math.toRadians(angle));
                endY -= speed * time * sin(Math.toRadians(angle)) - 5 * time * time;
                if (endY < topY) {
                    topY = endY;
                    topX = endX;
                }
            }
            var result = isClose(endX, endY);
            QuadCurve quadcurve = new QuadCurve(x, y + upY, topX, topY, endX, endY);
            quadcurve.setStroke(Color.GREEN);
            var pathTransition = new PathTransition();
            pathTransition.setNode(view);
            pathTransition.setDuration(Duration.seconds(3));
            pathTransition.setPath(quadcurve);
            pathTransition.setOnFinished(event -> {
                if (!result) {
                    isAsleep = true;
                    view.setCenterX(centerX);
                    view.setCenterY(centerY);
                    group.getChildren().remove(view);
                    view = new Circle(centerX, centerY + upY, radius);
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
            if (isSmall) {
                return (distance <= target.getRadius() + radius * 1.5);
            } else {
                return (distance <= target.getRadius() * 2 + radius * 2);
            }
        }
    }
}
