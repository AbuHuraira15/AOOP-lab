package com.example.regpage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DataList implements Initializable {

    @FXML
    private Label name1, name2, name3, name4, name5, name6, name7, name8;
    @FXML
    private Label mobile1, mobile2, mobile3, mobile4, mobile5, mobile6, mobile7, mobile8;
    @FXML
    private Label type1, type2, type3, type4, type5, type6, type7, type8;
    @FXML
    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8;

    @FXML
    private Button button1,button2,button3,button4,button5,button6,button7,button8;
    @FXML
    private TextField searchdata;

    private final ArrayList<data> list = new ArrayList<>();
    private int currentPage = 0;
    private static final int RECORDS_PER_PAGE = 8;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sqlRead(); // Read data from the database
        viewPage(); // Show the first page of data
    }

    // SQL Read method to fetch data from database
    public void sqlRead() {
        String url = "jdbc:mysql://localhost:3306/registerFrom";
        String username = "root";
        String password = "Abuhuraira@15";
        String selectQuery = "SELECT ID,firstName, lastName, mobile, userType, image FROM register";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(selectQuery);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                data d = new data();
                d.id=rs.getString("ID");
                d.name = rs.getString("firstName") + " " + rs.getString("lastName"); // Combine first and last name
                d.mobile = rs.getString("mobile");
                d.type = rs.getString("userType");
                Blob imageBlob = rs.getBlob("image");
                if (imageBlob != null) {
                    InputStream imageStream = imageBlob.getBinaryStream();
                    d.image = new Image(imageStream);
                }
                // Add data object to the list
                list.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Show the current page of records
    private void viewPage() {
        int start = currentPage * RECORDS_PER_PAGE; // Calculate starting index for the current page

        // Loop through the records for the current page
        for (int i = 0; i < RECORDS_PER_PAGE; i++) {
            int index = start + i;
            if (index < list.size()) {
                switch (i) {
                    case 0:
                        setView(name1, mobile1, type1, imageView1, index);
                        break;
                    case 1:
                        setView(name2, mobile2, type2, imageView2, index);
                        break;
                    case 2:
                        setView(name3, mobile3, type3, imageView3, index);
                        break;
                    case 3:
                        setView(name4, mobile4, type4, imageView4, index);
                        break;
                    case 4:
                        setView(name5, mobile5, type5, imageView5, index);
                        break;
                    case 5:
                        setView(name6, mobile6, type6, imageView6, index);
                        break;
                    case 6:
                        setView(name7, mobile7, type7, imageView7, index);
                        break;
                    case 7:
                        setView(name8, mobile8, type8, imageView8, index);
                        break;
                }
            } else {
                // Clear the views for any remaining labels or images if there is no data
                clearView(i);
            }
        }
    }

    // Set individual record view including image
    private void setView(Label nameLabel, Label mobileLabel, Label typeLabel, ImageView imageView, int index) {
        data currentData = list.get(index);
        nameLabel.setText(currentData.name);
        mobileLabel.setText(currentData.mobile);
        typeLabel.setText(currentData.type);

        // Set the image if it exists
        if (currentData.image != null) {
            imageView.setImage(currentData.image);
        } else {
            imageView.setImage(null); // Set to null if no image is available
        }
    }

    // Clear UI components if there is no data
    private void clearView(int index) {
        switch (index) {
            case 0:
                name1.setText("");
                mobile1.setText("");
                type1.setText("");
                imageView1.setImage(null);
                button1.setVisible(false);
                break;
            case 1:
                name2.setText("");
                mobile2.setText("");
                type2.setText("");
                imageView2.setImage(null);
                button2.setVisible(false);
                break;
            case 2:
                name3.setText("");
                mobile3.setText("");
                type3.setText("");
                imageView3.setImage(null);
                button3.setVisible(false);
                break;
            case 3:
                name4.setText("");
                mobile4.setText("");
                type4.setText("");
                imageView4.setImage(null);
                button4.setVisible(false);
                break;
            case 4:
                name5.setText("");
                mobile5.setText("");
                type5.setText("");
                imageView5.setImage(null);
                button5.setVisible(false);
                break;
            case 5:
                name6.setText("");
                mobile6.setText("");
                type6.setText("");
                imageView6.setImage(null);
                button6.setVisible(false);
                break;
            case 6:
                name7.setText("");
                mobile7.setText("");
                type7.setText("");
                imageView7.setImage(null);
                button7.setVisible(false);
                break;
            case 7:
                name8.setText("");
                mobile8.setText("");
                type8.setText("");
                imageView8.setImage(null);
                button8.setVisible(false);
                break;
        }
    }




    public void Show(String id) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewPage.fxml"));
            Pane pane = fxmlLoader.load(); // Load the FXML file
            ViewPage viewPageController = fxmlLoader.getController(); // Get the controller for the FXML
            viewPageController.IdPass(id); // Pass the ID to the new controller

            // Create a new stage and set the scene
            Stage newStage = new Stage();
            Scene scene = new Scene(pane);
            newStage.setScene(scene);
            newStage.setTitle("View Page");
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void view1() { viewByIndex(0); }
    public void view2() { viewByIndex(1); }
    public void view3() { viewByIndex(2); }
    public void view4() { viewByIndex(3); }
    public void view5() { viewByIndex(4); }
    public void view6() { viewByIndex(5); }
    public void view7() { viewByIndex(6); }
    public void view8() { viewByIndex(7); }

    // Generalized method to handle view actions
    private void viewByIndex(int position) {
        int index = currentPage * RECORDS_PER_PAGE + position;
        if (index < list.size()) { // Ensure the index is within bounds
            String id = list.get(index).id;
            Show(id); // Pass the ID to the Show method
        }
    }


    // Handle Next page button click
    public void next() {
        if ((currentPage + 1) * RECORDS_PER_PAGE < list.size()) {
            currentPage++;
            viewPage(); // Update to the next page
        }
    }

    // Handle Back page button click
    public void back() {
        if (currentPage > 0) {
            currentPage--;
            viewPage(); // Update to the previous page
        }
    }

    public void search() {
        String searchQuery = searchdata.getText().trim(); // Get input from the search TextField

        if (!searchQuery.isEmpty()) {
            ArrayList<data> searchResults = new ArrayList<>();

            // Search for matching records
            for (data d : list) {
                if (d.id.equalsIgnoreCase(searchQuery)) { // Match the search query with the ID
                    searchResults.add(d);
                }
            }

            // Display the search results on a single page
            if (!searchResults.isEmpty()) {
                showOnePage(searchResults);
            } else {
                clearAllViews(); // Clear the view if no matches are found
                System.out.println("No records found for the entered ID.");
            }
        } else {
            System.out.println("Please enter a valid ID to search.");
        }
    }

    // Show records on a single page
    private void showOnePage(ArrayList<data> searchResults) {
        for (int i = 0; i < RECORDS_PER_PAGE; i++) {
            if (i < searchResults.size()) {
                data result = searchResults.get(i); // Fetch the matching data object
                switch (i) {
                    case 0 -> setView(name1, mobile1, type1, imageView1, result);
                    case 1 -> setView(name2, mobile2, type2, imageView2, result);
                    case 2 -> setView(name3, mobile3, type3, imageView3, result);
                    case 3 -> setView(name4, mobile4, type4, imageView4, result);
                    case 4 -> setView(name5, mobile5, type5, imageView5, result);
                    case 5 -> setView(name6, mobile6, type6, imageView6, result);
                    case 6 -> setView(name7, mobile7, type7, imageView7, result);
                    case 7 -> setView(name8, mobile8, type8, imageView8, result);
                }
            } else {
                clearView(i); // Clear remaining views if fewer records
            }
        }
    }

    // Update setView to accept a data object
    private void setView(Label nameLabel, Label mobileLabel, Label typeLabel, ImageView imageView, data currentData) {
        nameLabel.setText(currentData.name);
        mobileLabel.setText(currentData.mobile);
        typeLabel.setText(currentData.type);

        // Set the image if it exists
        if (currentData.image != null) {
            imageView.setImage(currentData.image);
        } else {
            imageView.setImage(null); // Set to null if no image is available
        }
    }


    // Clear all views for the search
    private void clearAllViews() {
        for (int i = 0; i < RECORDS_PER_PAGE; i++) {
            clearView(i);
        }
    }


    // Data class to hold individual record information
    class data {
        Image image;
        String name;
        String mobile;
        String type;
        String id;

        public data() {}

    }
}
