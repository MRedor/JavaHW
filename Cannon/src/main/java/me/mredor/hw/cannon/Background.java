package me.mredor.hw.cannon;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.Random;


/** Background of app */
public class Background {
    Random random;
    private final double MAX_X = 1200;
    private final double MAX_Y = 560;
    private final double HEIGHT = 110;
    private double[] bottomPoints;


    /** Creates background with triangles and add it to view */
    public Background(Group group) {
        random = new Random();
        bottomPoints = new double[4];
        bottomPoints[0] = 0;
        bottomPoints[1] = random.nextInt((int) (MAX_X/2)) + 200;
        bottomPoints[2] = random.nextInt((int) (MAX_X - bottomPoints[1])) + bottomPoints[1];
        bottomPoints[3] = MAX_X;

        var polygon = new Polygon();
        polygon.getPoints().addAll(new Double[]{
                bottomPoints[0], MAX_Y,
                (bottomPoints[1] + bottomPoints[0]) / 2, MAX_Y - HEIGHT,
                bottomPoints[1], MAX_Y,
                (bottomPoints[2] + bottomPoints[1]) / 2, MAX_Y - HEIGHT,
                bottomPoints[2], MAX_Y,
                (bottomPoints[3] + bottomPoints[2]) / 2, MAX_Y - HEIGHT,
                bottomPoints[3], MAX_Y
        });
        polygon.setFill(Color.GREEN);

        group.getChildren().add(polygon);
    }

    /** Gets Y by X in borderline */
    public double getYByX(double x) {
        for (int i = 0; i < 3; i++) {
            if (x < bottomPoints[i] || x > bottomPoints[i + 1]) {
                continue;
            }
            if (x < (bottomPoints[i + 1] + bottomPoints[i]) / 2) {
                return MAX_Y - (x - bottomPoints[i]) * 2 * HEIGHT / (bottomPoints[i + 1] - bottomPoints[i]);
            } else {
                return MAX_Y - (bottomPoints[i + 1] - x) * 2 * HEIGHT / (bottomPoints[i + 1] - bottomPoints[i]);
            }
        }
        return -1;
    }
}
