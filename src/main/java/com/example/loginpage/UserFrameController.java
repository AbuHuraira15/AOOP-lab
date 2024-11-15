package com.example.loginpage;

import com.example.regpage.HelloApplication;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import  com.example.regpage.*;

import static com.example.loginpage.DatabaseConnection.getConnection;

public class UserFrameController implements Initializable {
    private AnchorPane superShopPane,BuyAnchorpane;

    int userid ;
    @FXML
    private AnchorPane childPane;
    @FXML
    private AnchorPane profileSettingsAnchorpane,showMembersAnchorpane;
    @FXML
    private Button DashBoard_Admin,My_ShopButton;

    @FXML
    private ImageView Imagevoew_from_dashboard;

    @FXML
    private Button ResidentsManagement;

    @FXML
    private Label dateLabel,balanceLabel;

    @FXML
    private Label userAccId;
    @FXML
    private Label Username;

    @FXML
    private Label timeLabel;
    @FXML
    private ImageView imageviewfrom_info;
    @FXML
    private Label TotalMamberLebel;
    String email;
    private int userId;
    // Define an ArrayList to store residents
    private ArrayList<ResidentInformationSearch> IdList = new ArrayList<>();


    @FXML
    ObservableList<ResidentInformationSearch> residentInformationSearchObserveList = FXCollections.observableArrayList();

    @FXML
    private Button refreshBalanceButton;

    @FXML
    void refreshBalance(ActionEvent event) {
        fetchAndDisplayBalance(userId);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loginController login = new loginController();
        // Apply the circular shape to the ImageView
        imageView_circulerShape(Imagevoew_from_dashboard);
        // Apply the circular shape to the ImageView
        imageView_circulerShape(imageviewfrom_info);
        try{
            displayTotalMembers();
            ResidentManagementPage ();
            System.out.println(IdList);
            realTimeDate ();
            realTime ();
            fetchAndDisplayBalance(userid);
            setupAutoRefreshBalance();



        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void realTimeDate () {
        // Format the current date as DD:MM:YY
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        String formattedDate = currentDate.format(formatter);

        // Set the formatted date in the label
        dateLabel.setText(formattedDate);
    }

    public void realTime () {
        // Define the time format
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");

        // Create a Timeline to update the label every second
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            // Get the current time and format it
            LocalTime currentTime = LocalTime.now();
            String formattedTime = currentTime.format(timeFormatter);

            // Set the formatted time in the label
            timeLabel.setText(formattedTime);
        }));

        // Set the timeline to run indefinitely
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    public void imageView_circulerShape(ImageView imageView) {
        // imageView.setPreserveRatio(true);
        // Calculate the radius based on the smaller dimension of the ImageView
        double radius = Math.min(imageView.getFitWidth(), imageView.getFitHeight()) / 2;

        // Create a circular clip and center it
        Circle clip = new Circle(
                imageView.getFitWidth() / 2,
                imageView.getFitHeight() / 2,
                radius
        );

        // Apply the circular clip to the ImageView

        imageView.setClip(clip);
    }

    public void loadData(String email, int userId) throws Exception{
        this.email = email;
        this.userId = userId; // Assign user ID
        String query = "SELECT * FROM register WHERE email = ?"; // Modify query to select the appropriate record

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, String.valueOf(email)); // Replace with the actual NID or another identifier

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {

                System.out.println("Userrrr : "+userId);
                // Set the data to the labels
                userAccId.setText(resultSet.getString("id"));
                Username.setText(resultSet.getString("firstname"));
                // Retrieve the image as a byte array
                byte[] imageBytes = resultSet.getBytes("image"); // Adjust column name if needed
                if (imageBytes != null) {
                    // Convert byte array to Image
                    InputStream inputStream = new ByteArrayInputStream(imageBytes);
                    Image image = new Image(inputStream);

                    // Set the image to an ImageView
                    imageviewfrom_info.setImage(image);// Assuming you have an ImageView named profileImageView
                    Imagevoew_from_dashboard.setImage(image);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // Method to display image from DB
    public static void displayImageFromDB(ImageView imageView, int imageId) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Get the connection from the DatabaseConnection class
            connection = getConnection();

            // Retrieve the image as a BLOB
            String query = "SELECT image FROM register WHERE id = ?";
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, imageId);
            rs = stmt.executeQuery();

            // If an image is found, load it into the ImageView
            if (rs.next()){
                byte[] imgData = rs.getBytes("image");
                try{
                    ByteArrayInputStream bis = new ByteArrayInputStream(imgData);
                    Image img = new Image(bis);

                    // Set the image to the ImageView
                    imageView.setImage(img);
                }catch(Exception e){

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the result set, statement, and connection to free resources
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void ResidentManagementPage () {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = getConnection();
        String InformationQuery = "SELECT * FROM register";

        try {
            Statement Statement = connectDB.createStatement();
            ResultSet queryOutput = Statement.executeQuery(InformationQuery);

            while ((queryOutput.next())) {
                int queryId = queryOutput.getInt("id");
                //id = queryOutput.getInt("id");
                ResidentInformationSearch resident = new ResidentInformationSearch(queryId);
                IdList.add(resident);
            }
        }catch(Exception e){

        }
    }
    public void displayTotalMembers() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = getConnection();

        String countQuery = "SELECT COUNT(*) AS total FROM register"; // Adjust table name if needed

        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(countQuery);

            if (resultSet.next()) {
                int totalMembers = resultSet.getInt("total");
                // Run the animation on the JavaFX Application Thread
                Platform.runLater(() -> animateNumber(totalMembers));
                TotalMamberLebel.setText(String.valueOf(totalMembers)); // Update the label

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void animateNumber(int targetNumber) {
        final int[] currentNumber = {0}; // Start from 0

        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(100), e -> {
            currentNumber[0]++;
            TotalMamberLebel.setText(String.valueOf(currentNumber[0]));
            if (currentNumber[0] >= targetNumber) {
                timeline.stop(); // Stop the animation when the target is reached
            }
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(targetNumber); // Set the total cycles to the target number
        timeline.play(); // Start the animation
    }


    public void profilesettingsClickButtonOnAction(ActionEvent event){
        profileSettingsAnchorpane.setVisible(true);
        showMembersAnchorpane.setVisible(false);
        MyshopClose();


    }

    public void HandleUpdateProductOnAction(ActionEvent event){

    }

    public void UpdateInformation_cancelButton(ActionEvent event){

    }
    public void DashBoardButtonClickOnAction(ActionEvent event){
        showMembersAnchorpane.setVisible(true);
        profileSettingsAnchorpane.setVisible(false);
        // Remove the SuperShop pane if it is loaded
        MyshopClose();
        /*Stage stage = (Stage) DashBoard_Admin.getScene().getWindow(); // Replace 'myButton' with your actual button or node
        stage.close();*/

    }

    public void MyshopActionOnbutton(ActionEvent event) throws Exception {
        // Check if the SuperShop pane is already loaded
        profileSettingsAnchorpane.setVisible(false);
        if (superShopPane == null) {
            // Load the child FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SuperShop.fxml"));
            superShopPane = loader.load();

            // Add AnchorPane constraints to the child
            AnchorPane.setTopAnchor(superShopPane, 0.0);
            AnchorPane.setBottomAnchor(superShopPane, 0.0);
            AnchorPane.setLeftAnchor(superShopPane, 0.0);
            AnchorPane.setRightAnchor(superShopPane, 0.0);

            // Add the child FXML to the parent AnchorPane
            childPane.getChildren().add(superShopPane);
        }
    }
    public void PaymentsActionButton(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainFrame.fxml")); // Load the FXML file
        Scene scene = new Scene(fxmlLoader.load()); // Create a new scene

        Stage newStage = new Stage(); // Create a new stage (window)
        newStage.setScene(scene); // Set the new scene on the new stage
        newStage.setTitle("Main Frame"); // Optional: Set a title for the new window
        newStage.initModality(Modality.APPLICATION_MODAL); // Optional: Make it modal (blocks interaction with other windows)
        newStage.show(); // Show the new window
    }



    public  void ChatButtonOnAction(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("nw1.fxml"));
            Parent root = loader.load();

            // Optionally, you can set the controller if needed.
             HelloController controller = loader.getController();
            // Perform any necessary setup with the controller here.

            Stage stage = new Stage();
            stage.setTitle("Chat Window");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void MyshopClose(){
        if (superShopPane != null) {
            childPane.getChildren().remove(superShopPane);
            superShopPane = null; // Reset the reference
        }
    }

    private void setupAutoRefreshBalance() {
        Timeline refreshTimeline = new Timeline(new KeyFrame(Duration.seconds(30), event -> {
            fetchAndDisplayBalance(userId); // Refresh balance every 30 seconds
        }));
        refreshTimeline.setCycleCount(Timeline.INDEFINITE); // Run indefinitely
        refreshTimeline.play(); // Start the timeline
    }

    


    public void fetchAndDisplayBalance(int userid) {
        new Thread(() -> {
            try {
                // Database connection
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/bankmanagementsystem", // Update with your DB URL
                        "root", // Replace with your username
                        "shamim"  // Replace with your password
                );

                // SQL Query to fetch the balance
                String query = "SELECT balance FROM register WHERE id = ?";
                System.out.println("User id : "+userid);// Modify query as needed
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, userid); // Use the correct id from your application
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    double balance = resultSet.getDouble("balance");
                    // Run the animation on the JavaFX Application Thread
                    Platform.runLater(() -> animateNumber1(balance)); // Pass balance as double
                    balanceLabel.setText(String.valueOf(balance));// Update the label immediately (optional)
                }

                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    balanceLabel.setText("Error fetching balance.");
                });
            }
        }).start();
    }

    // Animate the balance number gradually using double for accuracy
    private void animateNumber1(double targetNumber) {
        final double[] currentNumber = {0.0}; // Start from 0.0

        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(50), e -> {
            currentNumber[0] += targetNumber / 100; // Increment by a fraction of the target
            if (currentNumber[0] >= targetNumber) {
                currentNumber[0] = targetNumber; // Ensure it doesn't exceed the target
                timeline.stop(); // Stop the animation when the target is reached
            }
            balanceLabel.setText(String.format("%.2f", currentNumber[0])); // Update label with two decimals
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely until the target is reached
        timeline.play(); // Start the animation
    }





}
