package com.example.regpage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.scene.image.PixelReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DataStore implements Initializable {

    @FXML
    private ImageView imageView1, imageView2, imageView3, imageView4;
    @FXML
    private Label wrong;
    @FXML
    private MenuButton type, bedRoom, bathRoom, block;
    @FXML
    private TextField price, area,houseNo,roadNo;
    @FXML
    private MenuItem bd1, bd2, A, B, C, D, bt1, bt2, bt3, S1, S2;

    private final File[] files = new File[4]; // Array to store image files

    // Method to set photo for imageView1
    public void setPhoto1() {
        files[0] = chooseFile(imageView1); // Update files[0] with the selected file
    }

    // Method to set photo for imageView2
    public void setPhoto2() {
        files[1] = chooseFile(imageView2); // Update files[1] with the selected file
    }

    // Method to set photo for imageView3
    public void setPhoto3() {
        files[2] = chooseFile(imageView3); // Update files[2] with the selected file
    }

    // Method to set photo for imageView4
    public void setPhoto4() {
        files[3] = chooseFile(imageView4); // Update files[3] with the selected file
    }

    // Generic method to open a file chooser and set an image to an ImageView
    private File chooseFile(ImageView imageView) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            if (selectedFile.length() > 64 * 1024) { // Check file size (64 KB limit)
                wrong.setText("File size exceeds 64 KB.");
                return null;
            }

            Image image = new Image(selectedFile.toURI().toString());
            if (isValidImage(image)) {
                imageView.setImage(image);
                return selectedFile; // Return the file if valid
            } else {
                wrong.setText("Invalid image dimensions. Must be 450x350.");
                return null;
            }
        }
        return null; // Return null if no file was selected
    }


    // Validate image dimensions
    private boolean isValidImage(Image image) {
        if (image != null) {
            PixelReader reader = image.getPixelReader();
            return image.getWidth() == 400 && image.getHeight() == 300 && reader != null;
        }
        return false;
    }

    // Post data method
    public void post() {
        if (checkData()) {
            System.out.println("Posting data...");
            // Logic to post data to a database or API
            insertDataWithImagesToDatabase();
        }
    }

    // Check required data before posting
    public boolean checkData() {
        if (price.getText().isEmpty()) { // Check if price is empty
            wrong.setText("Set price");
            return false;
        } else if (area.getText().isEmpty()) { // Check if area is empty
            wrong.setText("Set area");
            return false;
        }  else if (houseNo.getText().isEmpty()) { // Check if area is empty
            wrong.setText("Set House no");
            return false;
        } else if (roadNo.getText().isEmpty()) { // Check if area is empty
            wrong.setText("Set Road no");
            return false;
        }
        else if (bedRoom.getText().equals("Select Bedrooms")) { // Check if bedroom is not selected
            wrong.setText("Select bedroom count");
            return false;
        } else if (bathRoom.getText().equals("Select Bathrooms")) { // Check if bathroom is not selected
            wrong.setText("Select bathroom count");
            return false;
        } else if (block.getText().equals("Select Block")) { // Check if block is not selected
            wrong.setText("Select block");
            return false;
        }
        else if (type.getText().equals("Select Type")) { // Check if block is not selected
            wrong.setText("Select Type");
            return false;
        }
        for (File file : files) {
            if (file == null) {
                wrong.setText("Set all images");
                return false;
            }
        }
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set default text for menus and add listeners
        block.setText("Select Block");
        bedRoom.setText("Select Bedrooms");
        bathRoom.setText("Select Bathrooms");
        type.setText("Select Type");

        A.setOnAction(event -> block.setText(A.getText()));
        B.setOnAction(event -> block.setText(B.getText()));
        C.setOnAction(event -> block.setText(C.getText()));
        D.setOnAction(event -> block.setText(D.getText()));

        bt1.setOnAction(event -> bathRoom.setText(bt1.getText()));
        bt2.setOnAction(event -> bathRoom.setText(bt2.getText()));
        bt3.setOnAction(event -> bathRoom.setText(bt3.getText()));

        bd1.setOnAction(event -> bedRoom.setText(bd1.getText()));
        bd2.setOnAction(event -> bedRoom.setText(bd2.getText()));

        S1.setOnAction(event -> type.setText(S1.getText()));
        S2.setOnAction(event -> type.setText(S2.getText()));
    }


    public void insertDataWithImagesToDatabase() {
        String dbUrl = "jdbc:mysql://localhost:3306/dataStore";
        String dbUser = "root";
        String dbPassword = "Abuhuraira@15";

        String sql = "INSERT INTO properties (type1,roadNo,houseNo, bedroom_count, bathroom_count, block, price, area, image1, image2, image3, image4) "
                + "VALUES (?, ?,?,?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, type.getText().trim());
            ps.setString(2,roadNo.getText());
            ps.setString(3,houseNo.getText());
            ps.setString(4, bedRoom.getText().trim());
            ps.setString(5, bathRoom.getText().trim());
            ps.setString(6, block.getText().trim());
            ps.setString(7, price.getText().trim());
            ps.setString(8, area.getText().trim());

            for (int i = 0; i < files.length; i++) {
                if (files[i] != null) {
                    FileInputStream fis = new FileInputStream(files[i]);
                    ps.setBinaryStream(9 + i, fis, (int) files[i].length());
                    // Do not close fis here. JDBC driver will read it completely.
                } else {
                    ps.setNull(9 + i, java.sql.Types.BLOB);
                }
            }


            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                wrong.setText("Data inserted successfully.");
            } else {
                wrong.setText("No rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            wrong.setText("Database error: " + e.getMessage());
        } catch (NumberFormatException e) {
            wrong.setText("Invalid number format.");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



}
