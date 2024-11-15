package com.example.regpage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class BankPage implements Initializable {
    @FXML
    private Label balanceAmount;
    @FXML
    private ImageView addMoneyIcon,withdrawIcon,sendMoneyIcon,superShopIcon,bankIcon;

    private AnchorPane frame;String id;
    public void handleWithdrawClick() throws IOException {
        loadPage("Withdraw.fxml",1);
    }


    public void handleSendMoneyClick() throws IOException {
        loadPage("PersonToPerson.fxml",2);
    }
    public void handleSuperShopClick(){
        loadPage("PayBill.fxml",4);
    }
    public void handleAddMoneyClick() throws IOException {
        loadPage("addMoney.fxml",3);
    }
    private void loadPage(String fxmlFile, int x) {
        try {
            // Load the new FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Pane newPane = fxmlLoader.load();

            // Pass data to the appropriate controller based on 'x'
            switch (x) {
                case 1 -> {
                    Withdraw controller = fxmlLoader.getController();
                    controller.getData(frame, id);
                }
                case 2 -> {
                    PersonToPerson controller = fxmlLoader.getController();
                    controller.getData(frame, id);
                }
                case 3 -> {
                    addMoney controller = fxmlLoader.getController();
                    controller.getData(frame, id);
                }
                case 4 -> {
                    PayBill controller = fxmlLoader.getController();
                    controller.setPage(frame, id);
                }
                default -> System.err.println("Invalid option for 'x': " + x);
            }

            // Ensure the newPane is dynamically resized
            newPane.prefWidthProperty().bind(frame.widthProperty());
            newPane.prefHeightProperty().bind(frame.heightProperty());

            // Replace the old content of the frame with the new pane
            frame.getChildren().clear(); // Remove old content
            frame.getChildren().add(newPane); // Add new content

            // Set anchors for the new pane to fill the frame
            AnchorPane.setTopAnchor(newPane, 0.0);
            AnchorPane.setRightAnchor(newPane, 0.0);
            AnchorPane.setBottomAnchor(newPane, 0.0);
            AnchorPane.setLeftAnchor(newPane, 0.0);

            // Apply layout and force scene to resize
            frame.applyCss();
            frame.layout();

            // Get the stage associated with the frame (current window)
            Stage currentStage = (Stage) frame.getScene().getWindow();

            // Adjust the stage size to the new content's preferred size
            currentStage.sizeToScene(); // Automatically adjust the window size to fit the new scene
            currentStage.centerOnScreen(); // Optionally, center the window on the screen

            // Debugging: Log new dimensions
            System.out.println("Updated Stage Dimensions: Width = " + currentStage.getWidth() + ", Height = " + currentStage.getHeight());

        } catch (IOException e) {
            System.err.println("Error loading page: " + fxmlFile);
            e.printStackTrace();
        }
    }




    public void SentData(AnchorPane frame, String id) {
        this.frame = frame;
        this.id = id;

        // Update balance label dynamically
        if (balanceAmount != null) {
            balanceAmount.setText(String.format("%.2f", getBalance(id)));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setIcons();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private double getBalance(String id) {
        double balance = 0.0;
        System.out.println(id);
        String url = "jdbc:mysql://localhost:3306/bankmanagementsystem";
        String username = "root";
        String password = "shamim";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String selectQuery = "SELECT balance FROM register WHERE id = ?";

            try (PreparedStatement ps = connection.prepareStatement(selectQuery)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    balance = rs.getDouble("balance");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    private void setIcons() throws FileNotFoundException {
        bankIcon.setImage(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\RegPage\\src\\main\\java\\org\\example\\regpage\\Bank.jpeg")));
        sendMoneyIcon.setImage(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\RegPage\\src\\main\\java\\org\\example\\regpage\\P2P.png")));
        superShopIcon.setImage(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\RegPage\\src\\main\\java\\org\\example\\regpage\\shop.jpg")));
        addMoneyIcon.setImage(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\RegPage\\src\\main\\java\\org\\example\\regpage\\AddMoney.png")));
        withdrawIcon.setImage(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\RegPage\\src\\main\\java\\org\\example\\regpage\\sentMoney.png")));
    }
}
