package com.example.loginpage;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ComplaintsSuggestionsController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextArea messageField;

    @FXML
    private void handleSubmit() {
        String name = nameField.getText();
        String email = emailField.getText();
        String message = messageField.getText();

        // Validation
        if (name.isEmpty() || message.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Submission Error", "Name and message are required fields.");
        } else {
            // Process the data (e.g., save to a database or send an email)
            System.out.println("Name: " + name);
            System.out.println("Email: " + email);
            System.out.println("Message: " + message);

            // Show success message
            showAlert(Alert.AlertType.INFORMATION, "Submission Successful", "Thank you for your feedback!");

            // Clear fields
            nameField.clear();
            emailField.clear();
            messageField.clear();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
