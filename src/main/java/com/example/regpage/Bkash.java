package com.example.regpage;
import  com.example.loginpage.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Bkash implements Initializable {
    @FXML
    private AnchorPane frame;
    @FXML
    private TextField mobileNumber, amount, pin, otp;
    @FXML
    private Button button, payButton;
    @FXML
    private Label wrong;
    @FXML
    private ImageView bkashIcon;

    private AnchorPane anchorPane;
    private String id;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bankmanagementsystem";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "shamim";
    private double balance = 0.0;

    public void getData(AnchorPane anchorPane, String id,int x) {
        this.anchorPane = anchorPane;
        this.id = id;
        this.x=x;
    }

    int x;
    public void back() {
        if(x==1){
            loadPage("Withdraw.fxml");
        }else{
            loadPage("addMoney.fxml");
        }

    }

    public double getBalance(String id) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String selectQuery = "SELECT balance FROM register WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(selectQuery)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    balance = rs.getDouble("balance");
                } else {
                    System.out.println("No account found with ID: " + SessionManager.getUserId());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error reading balance: " + e.getMessage());
        }
        return balance;
    }

    public void updateBalance(String id, double newBalance) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String updateQuery = "UPDATE register SET balance = ? WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(updateQuery)) {
                ps.setDouble(1, newBalance);
                ps.setInt(2,SessionManager.getUserId());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Balance updated successfully");
                } else {
                    System.out.println("No account found with ID: " + id);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error updating balance: " + e.getMessage());
        }
    }

    private void loadPage(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Pane newPane = fxmlLoader.load();

            if(x==2){
                addMoney addMoneyController = fxmlLoader.getController();
                addMoneyController.getData(anchorPane, id);
            }
            else {
                Withdraw addMoneyController = fxmlLoader.getController();
                addMoneyController.getData(anchorPane, id);
            }

            AnchorPane.setTopAnchor(newPane, 0.0);
            AnchorPane.setRightAnchor(newPane, 0.0);
            AnchorPane.setBottomAnchor(newPane, 0.0);
            AnchorPane.setLeftAnchor(newPane, 0.0);

            anchorPane.getChildren().clear();
            anchorPane.getChildren().add(newPane);

        } catch (IOException e) {
            System.err.println("Error loading page: " + fxmlFile);
            e.printStackTrace();
        }
    }

    public void getOtp() {
        if (dataCheck()) {
            frame.setDisable(false);
            button.setDisable(true);
            mobileNumber.setDisable(true);
            pin.setDisable(true);
            amount.setDisable(true);
        }
    }

    public boolean dataCheck() {
        if (mobileNumber.getText().isEmpty()) {
            wrong.setText("Enter mobile number");
            return false;
        } else if (amount.getText().isEmpty()) {
            wrong.setText("Enter amount");
            return false;
        } else if (pin.getText().isEmpty()) {
            wrong.setText("Enter Pin");
            return false;
        }

        try {
            Double.parseDouble(amount.getText());
        } catch (NumberFormatException e) {
            wrong.setText("Enter a valid amount");
            return false;
        }

        return true;
    }

    public void payment() {
        if (otp.getText().equals("123")) {
            if(x==1){
                double currentBalance= getBalance(id);
                double newBalance=Double.parseDouble(amount.getText());
                double upDate=currentBalance-newBalance;
                if(upDate>=0){
                    updateBalance(id, upDate);
                    wrong.setText("Payment Successful");
                }
                else{
                    wrong.setText("Not sufficient balance");
                }
            }else{
                double currentBalance = getBalance(id);
                double newBalance = currentBalance + Double.parseDouble(amount.getText());
                updateBalance(id, newBalance);
                wrong.setText("Payment Successful");
            }

        } else {
            wrong.setText("Invalid OTP");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Image image = new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\java\\com\\example\\regpage\\Bkash.png"));
            bkashIcon.setImage(image);
        } catch (FileNotFoundException e) {
            System.err.println("Bkash icon not found: " + e.getMessage());
        }
        frame.setDisable(true);
    }
}
