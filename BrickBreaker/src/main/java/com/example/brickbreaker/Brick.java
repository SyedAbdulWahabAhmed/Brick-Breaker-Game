package com.example.brickbreaker;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Brick {
    private final Rectangle shape;
    private double dx = 2.0;

    public Brick(double x, double y, double width, double height) {
        shape = new Rectangle(x, y, width, height);
        shape.setFill(Color.RED);
        shape.setStroke(Color.BLACK);
    }

    public Rectangle getShape() { return shape; }


    public void update(double screenWidth) {
        shape.setX(shape.getX() + dx);


        if (shape.getX() <= 0 || shape.getX() + shape.getWidth() >= screenWidth) {
            dx = -dx;
        }
    }
}