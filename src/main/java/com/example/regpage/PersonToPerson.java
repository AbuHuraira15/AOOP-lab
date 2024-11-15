package com.example.regpage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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

public class PersonToPerson implements Initializable {
    @FXML
    private ImageView withdrawIcon;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField agentIdField,amountField;
    @FXML
    private PasswordField passwordField;

    double balance=0.0;
    public void cashOut(){
        if(checkData()){
            // errorLabel.setText("Done");
            double x= getBalance(id);
            double y=getBalance(agentIdField.getText());
            double z=Double.parseDouble(amountField.getText());
            y+=z;
            x-=z;
            if(x>=0){
                updateBalance(id,x);
                updateBalance(agentIdField.getText(), y);
                errorLabel.setText("Successfully done");
                agentIdField.setText(null);
                amountField.setText(null);
                passwordField.setText(null);
            }
        }
    }
    private  AnchorPane anchorPane;String id;
    public void getData(AnchorPane anchorPane,String id){
        this.anchorPane=anchorPane;
        this.id=id;
    }
    public void back(){
        loadPage("BankPage.fxml");
    }


    private void loadPage(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Pane newPane = fxmlLoader.load();
            // Get the controller if needed
            BankPage bankPageController = fxmlLoader.getController();
            bankPageController.SentData(anchorPane,id);
            // Make the loaded content fill the entire frame
            AnchorPane.setTopAnchor(newPane, 0.0);
            AnchorPane.setRightAnchor(newPane, 0.0);
            AnchorPane.setBottomAnchor(newPane, 0.0);
            AnchorPane.setLeftAnchor(newPane, 0.0);

            // Clear existing content and add new content
            anchorPane.getChildren().clear();
            anchorPane.getChildren().add(newPane);

        } catch (IOException e) {
            System.err.println("Error loading page: " + fxmlFile);
            e.printStackTrace();
        }
    }
    private static final String DB_URL = "jdbc:mysql://localhost:3306/registerFrom";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Abuhuraira@15";

    public double getBalance(String id) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String selectQuery = "SELECT balance FROM register WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(selectQuery)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    balance = rs.getDouble("balance");
                } else {
                    System.out.println("No account found with ID: " + id);
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
                ps.setString(2, id);
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

    public boolean checkData(){

        if(agentIdField.getText()==null){
            errorLabel.setText("Enter Person id:");
            return false;
        }
        else if(amountField.getText()==null){
            errorLabel.setText("Enter amount");
            return false;
        }
        else if(passwordField.getText()==null){
            errorLabel.setText("Enter valid password");
            return false;
        }
        if(!idExists(agentIdField.getText())){
            errorLabel.setText("Enter valid Person id");
            return false;
        }
        if(!isValidCredentials(id,passwordField.getText())){
            errorLabel.setText("Enter valid password");
            return false;
        }
        return  true;
    }
    public boolean idExists(String id){
        String b=getUserTypeIfIdExists(id);
        return !b.equals("agent");
    }
    public boolean isValidCredentials(String id, String password) {
        boolean isValid = false;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT password FROM register WHERE id = ?";

            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, id);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    isValid = storedPassword.equals(password); // Use secure password hashing in production
                }
            }
        } catch (SQLException e) {
            System.err.println("Error validating credentials: " + e.getMessage());
            e.printStackTrace();
        }

        return isValid;
    }

    // Method to check if an ID exists in the database
    private String getUserTypeIfIdExists(String id) {
        String userType = null;
        String query = "SELECT userType FROM register WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    userType = rs.getString("userType"); // ID exists, return the userType
                } else {
                    System.out.println("ID not found in the database."); // ID doesn't exist
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking ID and user type: " + e.getMessage());
            e.printStackTrace();
        }

        return userType; // Returns userType if ID exists, or null otherwise
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            amountField.setText(null);
            agentIdField.setText(null);
            passwordField.setText(null);
            Image image=new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\java\\com\\example\\regpage\\P2P.png"));
            withdrawIcon.setImage(image);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
