package me.mredor.hw.cannon;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;


/** Background of app */
public class Background {

    /** Creates background with triangles and add it to view */
    public Background(Group group) {
        var polygon1 = new Polygon();
        polygon1.getPoints().addAll(new Double[]{
                400.0, 500.0,
                600.0, 600.0,
                -200.0, 600.0 });
        polygon1.setFill(Color.GREEN);
        //g.getChildren().add(polygon1);

        var polygon2 = new Polygon();
        polygon2.getPoints().addAll(new Double[]{
                700.0, 420.0,
                500.0, 600.0,
                850.0, 600.0 });
        polygon2.setFill(Color.GREEN);

        var polygon3 = new Polygon();
        polygon3.getPoints().addAll(new Double[]{
                950.0, 420.0,
                600.0, 600.0,
                1300.0, 600.0 });
        polygon3.setFill(Color.GREEN);

        group.getChildren().addAll(polygon1, polygon2, polygon3);
    }

    /** Gets Y by X in borderline */
    public static double getYByX(double x) {
        if (x < 400) {
            return (3400 - x) / 6;
        }
        if (x < 535.7) {
            return (x + 600)/2;
        }
        if (x < 700) {
            return (21000 - 18*x) / 20;
        }
        if (x < 775) {
            return (18 * x - 6300) / 15;
        }
        if (x < 950) {
            return (31800 - 18 * x) / 35;
        }
        return (18 * x - 2400) / 35;
    }

}
