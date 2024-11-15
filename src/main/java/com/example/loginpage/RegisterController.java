package com.example.loginpage;

import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.xml.stream.FactoryConfigurationError;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ResourceBundle;

import static com.example.loginpage.DatabaseConnection.getConnection;

public class RegisterController implements Initializable {

    @FXML
    private ImageView sheildImageView;
    @FXML
    private Button backButton;
    @FXML
    private Label registrationMassegeLabel;
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private TextField firstNameTextField, lastNameTextField, usernameTextField;
    @FXML
    private Button eyeButton,registerButton;
    @FXML
    private ToggleButton togglebutton;
    @FXML
    private TextField shownPassword;
    @FXML
    private ImageView lbl_closeeye,lbl_openEye;
    @FXML
    private Label checkFirstNameEntered,checkLastnameNameEntered,checkUsernameNameEntered,checkPasswordNameEntered;

    String Password;

    Alert alert;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        shownPassword.setVisible(false);
        lbl_openEye.setVisible(false);

        //green border color for firstname textfeild
        firstNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Check if the TextField contains text
            if (newValue.isEmpty()) {
                // Remove the CSS class when the text field is empty
                firstNameTextField.getStyleClass().remove("has-text");
            } else {
                // Add the CSS class when there is text in the text field
                if (!firstNameTextField.getStyleClass().contains("has-text")) {
                    firstNameTextField.getStyleClass().add("has-text");
                }
            }
        });

        //green color for lastnametextfield
        lastNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Check if the TextField contains text
            if (newValue.isEmpty()) {
                // Remove the CSS class when the text field is empty
                lastNameTextField.getStyleClass().remove("has-text");
            } else {
                // Add the CSS class when there is text in the text field
                if (!lastNameTextField.getStyleClass().contains("has-text")) {
                    lastNameTextField.getStyleClass().add("has-text");
                }
            }
        });

        //green color for lastnametextfield
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

        setPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Check if the TextField contains text
            if (newValue.isEmpty()) {
                // Remove the CSS class when the text field is empty
                setPasswordField.getStyleClass().remove("has-text");
            } else {
                // Add the CSS class when there is text in the text field
                if (!setPasswordField.getStyleClass().contains("has-text")) {
                    setPasswordField.getStyleClass().add("has-text");
                }
            }
        });

        shownPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            // Check if the TextField contains text
            if (newValue.isEmpty()) {
                // Remove the CSS class when the text field is empty
                shownPassword.getStyleClass().remove("has-text");
            } else {
                // Add the CSS class when there is text in the text field
                if (!shownPassword.getStyleClass().contains("has-text")) {
                    shownPassword.getStyleClass().add("has-text");
                }
            }
        });

        confirmPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Check if the TextField contains text
            if (newValue.isEmpty()) {
                // Remove the CSS class when the text field is empty
                confirmPasswordField.getStyleClass().remove("has-text");
            } else {
                // Add the CSS class when there is text in the text field
                if (!confirmPasswordField.getStyleClass().contains("has-text")) {
                    confirmPasswordField.getStyleClass().add("has-text");
                }
            }
        });



    }

    //Back action button to login page
    public void BackButtonOnAction(ActionEvent event) throws Exception{
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        //setting up image icon from here if we use in controller then it will be vanish after some click
        Image realstate = new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\realstateicon.png"));
        stage.getIcons().add(realstate);
        FXMLLoader fxmlLoader = new FXMLLoader(loginApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    //password doesn't match action button
    public void registrationActionButton(ActionEvent event) {

        if (setPasswordField.getText().equals(confirmPasswordField.getText())) {
            confirmPasswordLabel.setText("");
            registeredUser();
        } else{
            confirmPasswordLabel.setText("Password doesn't match!");
            confirmPasswordLabel.setTextFill(Color.web("#fa0025"));
        }
        if(firstNameTextField.getText().isBlank()==true){
            registrationMassegeLabel.setTextFill(Color.RED);
            registrationMassegeLabel.setText("");


            // Create and show an alert pop-up
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText("Missing Information");
            alert.setContentText("First name cannot be blank.Please enter first name.");

            alertEffect();

        }
        else if(lastNameTextField.getText().isBlank()==true){
            registrationMassegeLabel.setTextFill(Color.RED);
            registrationMassegeLabel.setText("");

            // Create the Alert
           alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Last name cannot be blank. Please enter your last name.");
            alertEffect();

        }
       else if(usernameTextField.getText().isBlank()==true){
            registrationMassegeLabel.setTextFill(Color.RED);
            registrationMassegeLabel.setText("");

            // Create and show an alert pop-up
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Username cannot be blank. Please enter your user name.");
            alertEffect(); // Show the alert and wait for user to dismiss

        }
        else if(shownPassword.getText().isBlank()==true){
            registrationMassegeLabel.setTextFill(Color.RED);
            registrationMassegeLabel.setText("");

            // Create and show an alert pop-up
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Please enter password!");
            alertEffect();// Show the alert and wait for user to dismiss

        }

    }


    //setting up query to database and connection to database .
    public void registeredUser() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = getConnection();

        String insertQuery = "INSERT INTO new_table (firstname, phone, email, password) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(insertQuery);
            preparedStatement.setString(1, firstNameTextField.getText());
            preparedStatement.setString(2, lastNameTextField.getText());
            preparedStatement.setString(3, usernameTextField.getText());
            preparedStatement.setString(4, setPasswordField.getText());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                registrationMassegeLabel.setTextFill(Color.RED);
                registrationMassegeLabel.setText("");

                // Create and show an alert pop-up
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Create account");
                alert.setHeaderText("");
                alert.setContentText("Hurrah!!You have created an account.");
                alertEffect();
            } else {
                registrationMassegeLabel.setTextFill(Color.RED);
                registrationMassegeLabel.setText("");

                // Create and show an alert pop-up
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Create account");
                alert.setHeaderText("");
                alert.setContentText("Account didn't create please try again.");
                alertEffect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //hide password Action method
    public void hidePasswordOnAction(KeyEvent keyEvent) {
        Password=confirmPasswordField.getText();
        shownPassword.setText(Password);
    }

    public void confirmPasswordButton(ActionEvent event) {

    }

   //showPassword action method
    public void showPasswordOnAction(KeyEvent keyEvent) {
        Password=shownPassword.getText();
        confirmPasswordField.setText(Password);

    }

    //close eye mouse click action event
    public void Close_eye_clickOnAction(MouseEvent mouseEvent) {
       shownPassword.setVisible(true);
       lbl_openEye.setVisible(true);
       lbl_closeeye.setVisible(false);
       confirmPasswordField.setVisible(false);
    }

    //open eye mouse click action event
    public void Open_eye_clickOnAction(MouseEvent mouseEvent) {
        shownPassword.setVisible(false);
        lbl_openEye.setVisible(false);
        lbl_closeeye.setVisible(true);
        confirmPasswordField.setVisible(true);
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
