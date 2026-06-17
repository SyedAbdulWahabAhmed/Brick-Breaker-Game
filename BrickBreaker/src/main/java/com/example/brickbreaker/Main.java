package com.example.brickbreaker;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Main extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private Paddle paddle;
    private Ball ball;


    private final ArrayList<Brick> bricks = new ArrayList<>();
    private Pane root;
    private int score = 0;
    private int bricksDestroyed = 0;
    private Text scoreText;
    private Text gameOverText;
    private Text gameWinText; // Variable declared
    private boolean isGameOver = false;

    private boolean leftPressed = false;
    private boolean rightPressed = false;

    @Override
    public void start(Stage primaryStage) {
        root = new Pane();
        root.setStyle("-fx-background-color: #1a1a1a;");
        Scene scene = new Scene(root, WIDTH, HEIGHT);


        paddle = new Paddle(WIDTH / 2.0 - 60, HEIGHT - 50);
        ball = new Ball(WIDTH / 2.0, HEIGHT - 100);

        root.getChildren().addAll(paddle.getShape(), ball.getShape());


        scoreText = new Text("Score: 0");
        scoreText.setFill(Color.WHITE);
        scoreText.setStyle("-fx-font-size: 20px; -fx-font-family: 'Arial'; -fx-font-weight: bold;");
        scoreText.setX(20);
        scoreText.setY(35);


        gameOverText = new Text("GAME OVER\nPress 'R' to Restart");
        gameOverText.setFill(Color.RED);
        gameOverText.setStyle("-fx-font-size: 40px; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-alignment: center;");
        gameOverText.setX(WIDTH / 2.0 - 160);
        gameOverText.setY(HEIGHT / 2.0);
        gameOverText.setVisible(false);


        gameWinText = new Text("");
        gameWinText.setFill(Color.GREEN);
        gameWinText.setStyle("-fx-font-size: 50px; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-alignment: center;");
        gameWinText.setX(WIDTH / 2.0 - 130);
        gameWinText.setY(HEIGHT / 2.0);
        gameWinText.setVisible(false);


        root.getChildren().addAll(scoreText, gameOverText, gameWinText);

        createBricks();


        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A) leftPressed = true;
            if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D) rightPressed = true;


            if (e.getCode() == KeyCode.R && gameOverText.isVisible()) {
                restartGame();
            }
        });

        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A) leftPressed = false;
            if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D) rightPressed = false;
        });


        scene.setOnMouseMoved(e -> {
            double mouseX = e.getX() - (paddle.getShape().getWidth() / 2.0);
            if (mouseX >= 0 && mouseX + paddle.getShape().getWidth() <= WIDTH) {
                paddle.getShape().setX(mouseX);
            }
        });


        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {

                if (isGameOver) return;

                if (leftPressed) paddle.moveLeft();
                if (rightPressed) paddle.moveRight(WIDTH);

                ball.update(WIDTH, HEIGHT);


                /*for (Brick brick : bricks) {
                    brick.update(WIDTH);
                }*/


                if (ball.getShape().getBoundsInParent().intersects(paddle.getShape().getBoundsInParent())) {
                    ball.reverseY();
                }


                for (int i = 0; i < bricks.size(); i++) {
                    Brick brick = bricks.get(i);
                    if (ball.getShape().getBoundsInParent().intersects(brick.getShape().getBoundsInParent())) {
                        ball.reverseY();
                        root.getChildren().remove(brick.getShape());
                        bricks.remove(i);

                        score += 10;
                        scoreText.setText("Score: " + score);

                        // 🔥 NAYA LOGIC: Eentein gino aur speed badhao
                        bricksDestroyed++;
                        if (bricksDestroyed % 10 == 0) {
                            ball.increaseSpeed(); // Ball ki speed bump karo!
                        }

                        break;
                    }
                }


                if (ball.getShape().getCenterY() >= HEIGHT - 20) {
                    isGameOver = true;
                    gameOverText.setVisible(true);
                }


                if (bricks.isEmpty() && !isGameOver) {
                    isGameOver = true;
                    gameWinText.setText("YOU WIN!");
                    gameWinText.setVisible(true);
                }
            }
        };

        gameLoop.start();

        primaryStage.setTitle("JavaFX Brick Breaker (Fixed & Complete)");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    private void createBricks() {
        int rows = 4;
        int columns = 10;
        int brickWidth = 70;
        int brickHeight = 25;
        int padding = 8;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                double x = j * (brickWidth + padding) + 15;
                double y = i * (brickHeight + padding) + 60;

                Brick brick = new Brick(x, y, brickWidth, brickHeight);
                bricks.add(brick);
                root.getChildren().add(brick.getShape());
            }
        }
    }


    private void restartGame() {
        score = 0;
        bricksDestroyed = 0; // 🔥 Nayi line: Counter reset
        scoreText.setText("Score: 0");
        isGameOver = false;
        gameOverText.setVisible(false);
        gameWinText.setVisible(false);

        ball.getShape().setCenterX(WIDTH / 2.0);
        ball.getShape().setCenterY(HEIGHT - 100);
        ball.resetSpeed();

        for (Brick b : bricks) {
            root.getChildren().remove(b.getShape());
        }
        bricks.clear();

        createBricks();
    }

    public static void main(String[] args) {
        launch(args);
    }
}