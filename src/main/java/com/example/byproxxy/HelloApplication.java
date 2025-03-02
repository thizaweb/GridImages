package com.example.byproxxy;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {

    private List<Image> images = new ArrayList<>(); // List to store images
    private FlowPane flowPane = new FlowPane(); // FlowPane to hold image thumbnails
    private ImageView fullImageView = new ImageView(); // ImageView for displaying full-size images
    private Stage fullImageStage; // Stage for displaying full-size images
    private int currentIndex = 0; // Keeps track of the currently displayed image index

    @Override
    public void start(Stage stage) {

        // The label as the title
        var label = new Label("PrivateThrifts Ty");

        // Buttons for different image categories
        var btn1 = new Button("Delela");
        var btn2 = new Button("Hoodies");
        var btn3 = new Button("Shirts");
        var btn4 = new Button("Shorts");

        // HBox for category buttons
        HBox buttons = new HBox(15, btn1, btn2, btn3, btn4);
        buttons.setAlignment(Pos.CENTER);

        // VBox layout to arrange label, buttons, and image grid
        VBox layout = new VBox(10, label, buttons, flowPane);
        layout.setStyle("-fx-background-color: #00FFFF;");
        layout.setAlignment(Pos.CENTER);

        // Button Actions - Load images when clicking the buttons
        btn1.setOnAction(e -> {
            loadDelelaImages();
            displayGridImages();
        });
        btn2.setOnAction(e -> {
            loadHoodiesImages();
            displayGridImages();
        });
        btn3.setOnAction(e -> {
            loadShirtsImages();
            displayGridImages();
        });
        btn4.setOnAction(e -> {
            loadShortsImages();
            displayGridImages();
        });

        // Scene setup
        var scene = new Scene(layout, 600, 600);
        stage.setScene(scene);
        stage.setTitle("Rich Internet Image Gallery");
        stage.show();

        // Full Image Stage Setup
        fullImageStage = new Stage();
        fullImageStage.setTitle("Full-Size Image");

        // Navigation Buttons
        Button prevButton = new Button("Previous");
        Button nextButton = new Button("Next");
        Button backButton = new Button("Back");

        // Event Listeners for Navigation
        prevButton.setOnAction(e -> navigateImages(-1)); // Show previous image
        nextButton.setOnAction(e -> navigateImages(1)); // Show next image
        backButton.setOnAction(e -> fullImageStage.hide()); // Close full-screen view

        // HBox for navigation buttons
        HBox navigationBox = new HBox(10, prevButton, nextButton, backButton);
        navigationBox.setAlignment(Pos.CENTER);

        // VBox layout for full image display
        VBox fullImageLayout = new VBox(10, fullImageView, navigationBox);
        fullImageLayout.setAlignment(Pos.CENTER);
        fullImageLayout.setStyle("-fx-background-color: #008080;");

        // Scene setup for full image stage
        Scene fullImageScene = new Scene(fullImageLayout, 600, 600);
        fullImageStage.setScene(fullImageScene);
    }

    // Method to load delela images
    private void loadDelelaImages() {
        images.clear();
        loadImages("/images/Delela/IMG", "jpg", 10);
    }

    // Method to load hoodies images
    private void loadHoodiesImages() {
        images.clear();
        loadImages("/images/Hoodies/hoodie", "jpg", 10);
    }

    // Method to load shirts images
    private void loadShirtsImages() {
        images.clear();
        loadImages("/images/Shirts/shirt", "jpg", 10);
    }

    // Method to load shorts images
    private void loadShortsImages() {
        images.clear();
        loadImages("/images/Shorts/short", "jpg", 10);
    }

    // Loads images from specified folders
    private void loadImages(String basePath, String extension, int count) {
        for (int i = 1; i <= count; i++) {
            String imagePath = basePath + i + "." + extension;
            InputStream imageStream = getClass().getResourceAsStream(imagePath);
            if (imageStream != null) {
                images.add(new Image(imageStream));
            } else {
                System.err.println("Image not found: " + imagePath);
            }
        }
        currentIndex = 0;
    }

    // Displays grid images in FlowPane
    private void displayGridImages() {
        flowPane.getChildren().clear();
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setStyle("-fx-padding: 10px; -fx-background-color: #008080; -fx-alignment: center;");

        for (int i = 0; i < images.size(); i++) {
            ImageView imageView = new ImageView(images.get(i));
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            imageView.setStyle("-fx-border-width: 2px; -fx-border-radius: 5px; -fx-padding: 5px;");

            // Hover effect (No color change)
            imageView.setOnMouseEntered(e -> imageView.setStyle(
                    "-fx-border-width: 2px; -fx-border-radius: 5px; -fx-padding: 5px; -fx-opacity: 0.7;"
            ));
            imageView.setOnMouseExited(e -> imageView.setStyle(
                    "-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-padding: 5px;"
            ));

            int finalI = i;
            imageView.setOnMouseClicked(e -> showFullImage(finalI));
            flowPane.getChildren().add(imageView);
        }
    }

    // Shows full image in a separate stage
    private void showFullImage(int index) {
        if (index >= 0 && index < images.size()) {
            currentIndex = index;
            fullImageView.setFitWidth(500);
            fullImageView.setFitHeight(500);
            fullImageView.setImage(images.get(index));
            fullImageStage.sizeToScene();
            fullImageStage.show();
        }
    }

    // Navigates between images in full-screen mode
    private void navigateImages(int step) {
        if (images.isEmpty()) return;

        currentIndex += step;
        if (currentIndex < 0) currentIndex = 0;
        if (currentIndex >= images.size()) currentIndex = images.size() - 1;

        showFullImage(currentIndex);
    }

    public static void main(String[] args) {
        launch();
    }
}
