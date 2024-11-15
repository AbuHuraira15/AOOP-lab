package com.example.loginpage;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class loginController implements Initializable {
    public static String loggedInEmail = "";


    @FXML
    private Button cancelButton;
    @FXML
    private Label loginmassegeLabel;
    @FXML
    private ImageView fileImageView;
    @FXML
    private ImageView lockImageView;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button registerButton1;
    @FXML
    private Button loginButton;
    @FXML
    Alert alert;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        // Add a listener to monitor changes in the text property of the TextField which is related to css file
            usernameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                // Check if the TextField contains text
                if (newValue.isEmpty()) {
                    // Remove the CSS class when the text field is empty
                    usernameTextField.getStyleClass().remove("has-text");
                } else {
                    // Add the CSS class when there is text in the text field
                    if (!usernameTextField.getStyleClass().contains("has-text")) {
                        usernameTextField.getStyleClass().add("has-text");
                    }
                }
            });

        // Add a listener to monitor changes in the text property of the passwordfield which is related to css file
        passwordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Check if the TextField contains text
            if (newValue.isEmpty()) {
                // Remove the CSS class when the text field is empty
                passwordTextField.getStyleClass().remove("has-text");
            } else {
                // Add the CSS class when there is text in the text field
                if (!passwordTextField.getStyleClass().contains("has-text")) {
                    passwordTextField.getStyleClass().add("has-text");
                }
            }
        });

    }

    public void loginButtonOnAction(ActionEvent event)throws Exception{
        if(usernameTextField.getText().isBlank()==false && passwordTextField.getText().isBlank()==false){
            validateLogin();
        }else{

            if(usernameTextField.getText().isBlank()==true){
                loginmassegeLabel.setTextFill(Color.RED);
                loginmassegeLabel.setText("");


                // Create and show an alert pop-up
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Input Error");
                alert.setHeaderText("Missing Information");
                alert.setContentText("Email id cannot be blank!!");

                alertEffect();

            }

            else if(passwordTextField.getText().isBlank()==true){
                loginmassegeLabel.setTextFill(Color.RED);
                loginmassegeLabel.setText("");


                // Create and show an alert pop-up
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Input Error");
                alert.setHeaderText("Missing Information");
                alert.setContentText("Please enter your password!!");

                alertEffect();

            }
        }
    }



   /* public void validateLogin()throws Exception{
        String Email;
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM register WHERE email = ? AND password = ?";

        //Admin access
        if(usernameTextField.getText().equals("Administrator") && passwordTextField.getText().equals("1234")){
            Stage AdminFrame = new Stage();

            FXMLLoader loader = new FXMLLoader(AdminFrameApplicationClass.class.getResource("AdminFrame.fxml"));
            Scene scene = new Scene(loader.load());
            AdminFrame.setScene(scene);
            AdminFrame.show();
        }
        else{
            try{
                PreparedStatement preparedStatement = connectDB.prepareStatement(verifyLogin);
                preparedStatement.setString(1, usernameTextField.getText());
                preparedStatement.setString(2, passwordTextField.getText().trim());
                ResultSet queryResult = preparedStatement.executeQuery();

                while (queryResult.next()) {
                    loginmassegeLabel.setTextFill(Color.RED);
                    if ((queryResult.getInt(1)==1)) {

                        // If login is successful, store the email globally
                        loggedInEmail = usernameTextField.getText();
                        System.out.println(loggedInEmail);

                        loginmassegeLabel.setTextFill(Color.GREEN);
                        loginmassegeLabel.setTextFill(Color.RED);
                        loginmassegeLabel.setText("");


                        // Create and show an alert pop-up
                        alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("matched!");
                        alert.setContentText("congratulation login successful.");




                        alertEffect();

                        Stage stage = (Stage) loginButton.getScene().getWindow();
                        stage.close();

                        FXMLLoader fxmlLoader = new FXMLLoader(loginApplication.class.getResource("UserFrame.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());
                        UserFrameController userFrameController = fxmlLoader.getController();
                        userFrameController.loadData(loggedInEmail);


                        stage.setScene(scene);
                        stage.show();


                    } else {
                        //loginmassegeLabel.setText("Invalid Login. Please try again!");
                        loginmassegeLabel.setTextFill(Color.RED);
                        loginmassegeLabel.setText("");


                        // Create and show an alert pop-up
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Not matched!");
                        alert.setHeaderText("Did you do any mistake?");
                        alert.setContentText("Oops! please try again");

                        alertEffect();
                    }


                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }*/

    public void validateLogin() throws Exception {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        // SQL query to fetch id along with validation
        String verifyLogin = "SELECT id FROM register WHERE email = ? AND password = ?";

        // Admin access
        if (usernameTextField.getText().equals("Administrator") && passwordTextField.getText().equals("1234")) {
            Stage AdminFrame = new Stage();

            FXMLLoader loader = new FXMLLoader(AdminFrameApplicationClass.class.getResource("AdminFrame.fxml"));
            Scene scene = new Scene(loader.load());
            AdminFrame.setScene(scene);
            AdminFrame.show();
        } else {
            try {
                PreparedStatement preparedStatement = connectDB.prepareStatement(verifyLogin);
                preparedStatement.setString(1, usernameTextField.getText());
                preparedStatement.setString(2, passwordTextField.getText().trim());
                ResultSet queryResult = preparedStatement.executeQuery();

                if (queryResult.next()) {
                    // Retrieve the user's id
                    int userId = queryResult.getInt("id");
                    loggedInEmail = usernameTextField.getText();

                    System.out.println("User ID: " + userId);
                    System.out.println("Email: " + loggedInEmail);

                    // Set success message
                    loginmassegeLabel.setTextFill(Color.GREEN);
                    loginmassegeLabel.setText("Login successful!");

                    // Create and show an alert pop-up
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Matched!");
                    alert.setContentText("Congratulations, login successful.");
                    alertEffect();

                    // Close current stage and open user frame
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.close();

                    FXMLLoader fxmlLoader = new FXMLLoader(loginApplication.class.getResource("UserFrame.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    UserFrameController userFrameController = fxmlLoader.getController();
                   // SuperShopController superShopController = fxmlLoader.getController();

                    // Pass user data (email and id) to the next controller
                    userFrameController.loadData(loggedInEmail,
                            userId);
                    userFrameController.fetchAndDisplayBalance(userId);
                    System.out.println("Set user id: "+userId);
                    SessionManager.setUserId(userId);
                    /*superShopController.menuPayBtn(userId);*/

                    stage.setScene(scene);
                    stage.show();
                } else {
                    // Handle invalid login
                    loginmassegeLabel.setTextFill(Color.RED);
                    loginmassegeLabel.setText("Invalid Login. Please try again!");

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Not Matched!");
                    alert.setHeaderText("Did you make a mistake?");
                    alert.setContentText("Oops! Please try again.");
                    alertEffect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public  void clickRegisterButton(ActionEvent event){
        try{
            Stage registerStage1 = new Stage();

            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("RegistrationFrom.fxml"));
            Scene scene = new Scene(loader.load());
            //adding image icon
            Image realstate = new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\realstateicon.png"));
            registerStage1.getIcons().add(realstate);
            registerStage1.setScene(scene);
            registerStage1.show();

            Stage stage = (Stage) registerButton1.getScene().getWindow();
            stage.close();


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void alertEffect(){
        alert.getDialogPane().getStylesheets().add(getClass().getResource("Alert.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("alert");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

        // Add a fade-in transition
        FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), stage.getScene().getRoot());
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setCycleCount(1);
        fadeIn.play();

        // Show the Alert
        alert.showAndWait();


    }



}
