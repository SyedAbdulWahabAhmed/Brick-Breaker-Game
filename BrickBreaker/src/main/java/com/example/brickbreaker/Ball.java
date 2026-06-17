package com.example.brickbreaker;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball {
    private final Circle shape;
    private double dx = 4;
    private double dy = -4;
    private double screenHeight;

    public Ball(double startX, double startY) {
        shape = new Circle(startX, startY, 8);
        shape.setFill(Color.ORANGE);
    }

    public Circle getShape() { return shape; }

    public void reverseY() { dy = -dy; }

    
    public void resetSpeed() {
        dx = 4;
        dy = -4; 
    }

    // 🔥 NAYA METHOD: Har dafa speed thori tez karne ke liye
    public void increaseSpeed() {
        // Agar ball right ja rahi hai (dx positive) toh +1 karo, warna left ja rahi hai toh -1 karo
        if (dx > 0) dx += 1;
        else dx -= 1;

        // Agar ball neeche ja rahi hai (dy positive) toh +1 karo, warna upar ja rahi hai toh -1 karo
        if (dy > 0) dy += 1;
        else dy -= 1;
    }

    public void update(double screenWidth, double screenHeight) {
        this.screenHeight = screenHeight;
        shape.setCenterX(shape.getCenterX() + dx);
        shape.setCenterY(shape.getCenterY() + dy);

        
        if (shape.getCenterX() - shape.getRadius() <= 0 || shape.getCenterX() + shape.getRadius() >= screenWidth) {
            dx = -dx;
        }
        
        if (shape.getCenterY() - shape.getRadius() <= 0) {
            dy = -dy;
        }
    }
}