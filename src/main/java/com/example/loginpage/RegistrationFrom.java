package com.example.loginpage;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.regex.Pattern;

public class RegistrationFrom implements Initializable {

    // FXML elements
    @FXML private TextField firstName, lastName, fatherName, nid, mobileNumber, emailAddress, getOtp;
    @FXML private DatePicker dateOfBirth;
    @FXML private TextArea address;
    @FXML private PasswordField pass, confirmPass;
    @FXML private Label wrong, otp;
    @FXML private ImageView imageView;
    @FXML private Button otpButton;
    @FXML
    Alert alert;
    // Instance variables
    private Stage stage;
    private File file;
    private int y = 100000 + new Random().nextInt(900000); // OTP variable

    // Initialization
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfEffect(); // Add text field effects
        otp.setVisible(false);
        getOtp.setVisible(false);
        otpButton.setVisible(false);

        try {
            Image image = new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\java\\com\\example\\loginpage\\blankPhoto.png"));
            imageView.setImage(image);
        } catch (Exception e) {
            wrong.setText("Error loading default image");
        }
    }

    // Set image
    public void setImage() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PNG Images", "*.png")
        );

        file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            if (validateImage(file)) {
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
            } else {
                wrong.setText("Image must be PNG format, 290x300px, and under 65KB");
                file = null;
            }
        }
    }

    // Validate image
    private boolean validateImage(File file) throws IOException {
        if (file.length() > 65 * 1024) {
            return false;
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            Image image = new Image(fis);
            int width = (int) image.getWidth();
            int height = (int) image.getHeight();

            return "png".equalsIgnoreCase(file.getName().substring(file.getName().lastIndexOf('.') + 1))
                    && width >= 290 && width <= 300
                    && height >= 290 && height <= 300;
        }
    }

    // Check all data validation
    public boolean checkAllData() {
        StringBuilder errors = new StringBuilder();

        if (firstName == null || firstName.getText().trim().isEmpty())
        {
            // Create and show an alert pop-up
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText("Missing Information");
            alert.setContentText("First name cannot be blank.Please enter first name.");

            alertEffect();
        }
        else if (lastName == null || lastName.getText().trim().isEmpty()) {
            // Create and show an alert pop-up
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Enter lastname");

            alertEffect();
        }

        else if (fatherName == null || fatherName.getText().trim().isEmpty()){
            // Create and show an alert pop-up
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Enter fathers name");

            alertEffect();
        }
       else  if (nid == null || nid.getText().trim().isEmpty()){
            // Create and show an alert pop-up
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Enter nid");

            alertEffect();
        }
        else if (mobileNumber == null || !Pattern.matches("^\\d{11}$", mobileNumber.getText())){
            // Create and show an alert pop-up
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Enter mobile number");

            alertEffect();
        }
        else if (emailAddress == null || !Pattern.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", emailAddress.getText())){
            // Create and show an alert pop-up
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Enter email");

            alertEffect();
        }
        else if (dateOfBirth == null || dateOfBirth.getValue() == null){
            // Create and show an alert pop-up
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Enter date of birth");

            alertEffect();
        }
        else if (address == null || address.getText().trim().isEmpty()){
            // Create and show an alert pop-up
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Enter address");

            alertEffect();
        }
        else if (pass == null || pass.getText().trim().isEmpty()){
            // Create and show an alert pop-up
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Please enter pass");

            alertEffect();
        }
        else if (confirmPass == null || confirmPass.getText().trim().isEmpty()) {
            // Create and show an alert pop-up
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText("Missing Information");
            alert.setContentText("please confirm pass");

            alertEffect();
        }
        else if (pass != null && confirmPass != null && !pass.getText().equals(confirmPass.getText())) {
            // Create and show an alert pop-up
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Please enter password");

            alertEffect();
        }
        else if (file == null) {
            // Create and show an alert pop-up
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Image input Error");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Set image meeting require");

            alertEffect();
        }

        if (errors.length() > 0) {
            wrong.setText(errors.toString());
            return false;
        }
        return true;
    }

    // Registration process
    public void registration() {
        if (checkAllData()) {
            sendEmail();
            otp.setVisible(true);
            otpButton.setVisible(true);
            getOtp.setVisible(true);
        }
    }

    // Send OTP via email
    public void sendEmail() {
        final String fromEmail = "sreza2330720@bscse.uiu.ac.bd";  // Sender's email
        final String appPassword = "ozay tcwt ivxe hkbu";         // App password
        String toEmail = emailAddress.getText();                 // Recipient's email

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, appPassword);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Registration OTP");
            // Generate a 6-digit OTP
            Random random = new Random();
            y = 100000 + random.nextInt(900000); // 6-digit OTP
            message.setText("Your OTP is: " + y);

            Transport.send(message); // Send the email
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }

    // Save data to database
    public void sqlSetup() {
        String url = "jdbc:mysql://localhost:3306/bankmanagementsystem";
        String username = "root";
        String password = "shamim";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String insertQuery = "INSERT INTO register (firstName, lastName, FatherName, Nid, mobile, email, password, dob, image, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement ps = connection.prepareStatement(insertQuery)) {
                ps.setString(1, firstName.getText());
                ps.setString(2, lastName.getText());
                ps.setString(3, fatherName.getText());
                ps.setString(4, nid.getText());
                ps.setString(5, mobileNumber.getText());
                ps.setString(6, emailAddress.getText());
                ps.setString(7, confirmPass.getText());
                ps.setString(8, dateOfBirth.getValue().toString());
                ps.setString(10, address.getText());

                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    ps.setBinaryStream(9, fileInputStream, (int) file.length());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    // Check OTP
    public void OtpChecker() throws Exception{
        if (getOtp.getText().equals(String.valueOf(y))) {
            sqlSetup();
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Input Error");
            alert.setContentText("Registration successful");
            alertEffect();

            Stage currentStage = (Stage) getOtp.getScene().getWindow(); // Get the current stage
            currentStage.close();

            Stage registerStage2 = new Stage();

            FXMLLoader loader1 = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
            Scene scene1 = new Scene(loader1.load());
            registerStage2.setScene(scene1);
            registerStage2.show();
        } else {
            wrong.setText("Invalid OTP");
        }
    }

    // Add text field effects
    public void tfEffect() {
        firstName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                firstName.getStyleClass().remove("has-text");
            } else {
                if (!firstName.getStyleClass().contains("has-text")) {
                    firstName.getStyleClass().add("has-text");
                }
            }
        });


        lastName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                lastName.getStyleClass().remove("has-text");
            } else {
                if (!lastName.getStyleClass().contains("has-text")) {
                    lastName.getStyleClass().add("has-text");
                }
            }
        });

        fatherName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                fatherName.getStyleClass().remove("has-text");
            } else {
                if (!fatherName.getStyleClass().contains("has-text")) {
                    fatherName.getStyleClass().add("has-text");
                }
            }
        });

        nid.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                nid.getStyleClass().remove("has-text");
            } else {
                if (!nid.getStyleClass().contains("has-text")) {
                    nid.getStyleClass().add("has-text");
                }
            }
        });

        mobileNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                mobileNumber.getStyleClass().remove("has-text");
            } else {
                if (!mobileNumber.getStyleClass().contains("has-text")) {
                    mobileNumber.getStyleClass().add("has-text");
                }
            }
        });

        emailAddress.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                emailAddress.getStyleClass().remove("has-text");
            } else {
                if (!emailAddress.getStyleClass().contains("has-text")) {
                    emailAddress.getStyleClass().add("has-text");
                }
            }
        });

        address.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                address.getStyleClass().remove("has-text");
            } else {
                if (!address.getStyleClass().contains("has-text")) {
                    address.getStyleClass().add("has-text");
                }
            }
        });



        address.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                address.getStyleClass().remove("has-text");
            } else {
                if (!address.getStyleClass().contains("has-text")) {
                    address.getStyleClass().add("has-text");
                }
            }
        });

        confirmPass.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                confirmPass.getStyleClass().remove("has-text");
            } else {
                if (!confirmPass.getStyleClass().contains("has-text")) {
                    confirmPass.getStyleClass().add("has-text");
                }
            }
        });

        pass.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                pass.getStyleClass().remove("has-text");
            } else {
                if (!pass.getStyleClass().contains("has-text")) {
                    pass.getStyleClass().add("has-text");
                }
            }
        });
        getOtp.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                getOtp.getStyleClass().remove("has-text");
            } else {
                if (!getOtp.getStyleClass().contains("has-text")) {
                    getOtp.getStyleClass().add("has-text");
                }
            }
        });
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
