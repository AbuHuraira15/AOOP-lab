package com.example.loginpage;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BackgroundImageChanger extends Application {

    private int currentImageIndex = 0;

    // Array of image URLs
    private final String[] images = {
            getClass().getResource("/photo2.jpg").toExternalForm(),
            getClass().getResource("/photo_2024-10-27_18-36-19.jpg").toExternalForm(),
            getClass().getResource("/shoreham-and-tides-chicago-il-primary-photo.jpg").toExternalForm()
    };

    @Override
    public void start(Stage primaryStage) {
        // Create a Rectangle to serve as the background
        Rectangle background = new Rectangle(800, 600);

        // Initial background image
        background.setFill(new ImagePattern(new Image(images[currentImageIndex])));

        // Timeline to change the background image every 3 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            currentImageIndex = (currentImageIndex + 1) % images.length;
            background.setFill(new ImagePattern(new Image(images[currentImageIndex])));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Add background to a StackPane
        StackPane root = new StackPane(background);

        // Create the Scene
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Background Image Changer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
