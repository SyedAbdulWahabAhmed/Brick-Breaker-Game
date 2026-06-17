package com.example.brickbreaker;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Paddle {
    private final Rectangle shape;
    private final double speed = 7.0;

    public Paddle(double startX, double startY) {
        shape = new Rectangle(startX, startY, 120, 15);
        shape.setFill(Color.LIGHTBLUE);
    }

    public Rectangle getShape() { return shape; }

    public void moveLeft() {
        if (shape.getX() > 0) {
            shape.setX(shape.getX() - speed);
        }
    }

    public void moveRight(double screenWidth) {
        if (shape.getX() + shape.getWidth() < screenWidth) {
            shape.setX(shape.getX() - speed + (speed * 2)); // Simple cross-boundary safety move
        }
    }
}
