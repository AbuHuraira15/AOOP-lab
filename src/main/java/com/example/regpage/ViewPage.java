package com.example.regpage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ViewPage implements Initializable {

    @FXML
    private Label name, id, fatherName, dateOfBirth, nid, mobile, type, email, address;
    @FXML
    private ImageView imageView;

    private String ID;

    // Method to set the ID passed from another controller
    public void IdPass(String ID) {
        this.ID = ID;searchById();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         // Perform the search when the view is initialized
    }

    public void back() {
        // Logic to navigate back, e.g., close the current window
        imageView.getScene().getWindow().hide(); // Close the current stage
    }

    public void searchById() {
        if (ID == null || ID.isEmpty()) {
            System.out.println("No ID provided.");
            return;
        }

        String dbUrl = "jdbc:mysql://localhost:3306/registerFrom";
        String username = "root";
        String password = "Abuhuraira@15";
        String selectQuery = "SELECT ID, userType, firstName, lastName, FatherName, Nid, mobile, email, dob, address, image FROM register WHERE ID = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement ps = connection.prepareStatement(selectQuery)) {

            ps.setString(1, ID); // Set the search ID
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Extract and display the data
                    id.setText(rs.getString("ID"));
                    type.setText(rs.getString("userType"));
                    fatherName.setText(rs.getString("FatherName"));
                    name.setText(rs.getString("firstName") + " " + rs.getString("lastName"));
                    nid.setText(rs.getString("Nid"));
                    mobile.setText(rs.getString("mobile"));
                    email.setText(rs.getString("email"));
                    dateOfBirth.setText(rs.getString("dob"));
                    address.setText(rs.getString("address"));

                    // Handle the image if present
                    Blob imageBlob = rs.getBlob("image");
                    if (imageBlob != null) {
                        try (InputStream imageStream = imageBlob.getBinaryStream()) {
                            Image image = new Image(imageStream);
                            imageView.setImage(image);
                        }
                    } else {
                        imageView.setImage(null); // Clear the image view if no image exists
                    }
                } else {
                    System.out.println("No record found for ID: " + ID);
                    clearFields(); // Clear fields if no record found
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    // Clear all fields in case of no data
    private void clearFields() {
        id.setText("");
        type.setText("");
        fatherName.setText("");
        name.setText("");
        nid.setText("");
        mobile.setText("");
        email.setText("");
        dateOfBirth.setText("");
        address.setText("");
        imageView.setImage(null);
    }
}
