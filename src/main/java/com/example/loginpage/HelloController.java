package com.example.loginpage;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.*;
import java.net.Socket;

public class HelloController {
    @FXML
    private ListView<String> residentsListView;
    @FXML
    private VBox chatVBox;
    @FXML
    private TextField messageTextField;
    @FXML
    private Button sendButton;
    @FXML
    private ScrollPane chatScrollPane;

    private Socket socket;
    private BufferedReader serverReader;
    private PrintWriter serverWriter;

    public void initialize() {

        try {
            // Connect to the server
            socket = new Socket("localhost", 8);
            serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            serverWriter = new PrintWriter(socket.getOutputStream(), true);

            // Start a thread to listen for server messages
            new Thread(() -> {
                try {
                    String message;
                    while ((message = serverReader.readLine()) != null) {
                        String finalMessage = message;
                        Platform.runLater(() -> {
                            if (finalMessage.contains("joined the chat") || finalMessage.contains("left the chat")) {
                                updateResidentsList(finalMessage);
                            }
                            displayMessage(finalMessage);
                        });
                    }
                } catch (IOException e) {
                    Platform.runLater(() -> displayMessage("Disconnected from the server."));
                }
            }).start();

        } catch (IOException e) {
            displayMessage("Unable to connect to the server.");
            e.printStackTrace();
        }

        // Set action for send button
        sendButton.setOnAction(event -> sendMessage());

        // Highlight the selected resident
        residentsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                highlightSelectedResident(newValue);
            }
        });
    }

    private void sendMessage() {
        String message = messageTextField.getText();
        if (!message.isEmpty()) {
            displayMessage("You: " + message); // Show the message instantly
            serverWriter.println(message);     // Send the message to the server
            messageTextField.clear();
        }
    }

    private void displayMessage(String message) {
        Text text = new Text(message);

        // Apply styles based on message content
        if (message.startsWith("You: ")) {
            // Style for the user's messages
            text.setStyle("-fx-padding: 10; -fx-background-color: #c9f7d7; -fx-border-radius: 10; -fx-background-radius: 10; -fx-font-size: 14; -fx-font-family: Arial;");
        } else if (message.contains("joined the chat") || message.contains("left the chat") || message.startsWith("Welcome")) {
            // Style for system messages
            text.setStyle("-fx-padding: 10; -fx-background-color: #e3e3e3; -fx-border-radius: 10; -fx-background-radius: 10; -fx-font-size: 14; -fx-font-family: Arial; -fx-text-fill: #6c757d;");
        } else {
            // Style for other users' messages
            text.setStyle("-fx-padding: 10; -fx-background-color: #f1f1f1; -fx-border-radius: 10; -fx-background-radius: 10; -fx-font-size: 14; -fx-font-family: Arial;");
        }

        // Add the styled message to the VBox
        chatVBox.getChildren().add(text);

        // Auto-scroll to the latest message
        Platform.runLater(() -> chatScrollPane.setVvalue(1.0));
    }


    private void updateResidentsList(String message) {
        if (message.contains("joined the chat")) {
            String residentName = message.split(" ")[0];
            if (!residentsListView.getItems().contains(residentName)) {
                residentsListView.getItems().add(residentName);
            }
        } else if (message.contains("left the chat")) {
            String residentName = message.split(" ")[0];
            residentsListView.getItems().remove(residentName);
        }
    }

    private void highlightSelectedResident(String residentName) {
        residentsListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(String resident, boolean empty) {
                super.updateItem(resident, empty);

                if (empty || resident == null) {
                    setText(null);
                    setStyle(""); // Default style for non-selected cells
                } else {
                    setText(resident);
                    if (resident.equals(residentName)) {
                        setStyle("-fx-background-color: lightblue; -fx-text-fill: black;"); // Highlight selected resident
                    } else {
                        setStyle(""); // Reset style for other cells
                    }
                }
            }
        });
    }

    public void closeConnection() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
