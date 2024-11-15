package com.example.regpage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.regex.Pattern;

public class RegistrationFrom implements Initializable {
 @FXML
 private TextField firstName, lastName, fatherName, nid, mobileNumber, emailAddress, getOtp;
 @FXML
 private DatePicker dateOfBirth;
 @FXML
 private TextArea address;
 @FXML
 private PasswordField pass, confirmPass;
 @FXML
 private Label wrong, otp;
 @FXML
 private ImageView imageView;
 @FXML
 private Button otpButton;
 private Stage stage;
 private File file;

 ArrayList<person>list=new ArrayList<>();
 @Override
 public void initialize(URL url, ResourceBundle resourceBundle) {
  otp.setVisible(false);
  getOtp.setVisible(false);
  otpButton.setVisible(false);
  sqlRead();

  try {
   Image image = new Image(new FileInputStream("D:\\RegPage\\src\\main\\java\\org\\example\\regpage\\blankPhoto.png"));
   imageView.setImage(image);
  } catch (Exception e) {
   wrong.setText("Error loading default image");
  }
 }


 public void sqlRead() {
  String url = "jdbc:mysql://localhost:3306/registerFrom";
  String username = "root";
  String password = "Abuhuraira@15";
  String selectQuery = "SELECT * FROM register";

  try (Connection connection = DriverManager.getConnection(url, username, password);
       PreparedStatement ps = connection.prepareStatement(selectQuery);
       ResultSet rs = ps.executeQuery()) {

   while (rs.next()) {
    String id = rs.getString("ID");
    String mobile = rs.getString("mobile");
    String email = rs.getString("email");
    list.add(new person(Integer.parseInt(id),mobile,email));
   }
  } catch (SQLException e) {
   e.printStackTrace();
  }
 }



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

 private boolean validateImage(File file) throws IOException {
  if (file.length() > 65 * 1024) {
   return false;
  }

  try (FileInputStream fis = new FileInputStream(file)) {
   Image image = new Image(fis);
   int width = (int) image.getWidth();
   int height = (int) image.getHeight();

   // Ensure PNG format, dimensions in the range, and file size
   return "png".equalsIgnoreCase(file.getName().substring(file.getName().lastIndexOf('.') + 1))
           && width >= 290 && width <= 300
           && height >= 290 && height <= 300;
  }
 }

 public boolean checkAllData() {
  StringBuilder errors = new StringBuilder();

  // Validate individual fields
  if (firstName == null || firstName.getText().trim().isEmpty()) errors.append("Enter first name.\n");
  if (lastName == null || lastName.getText().trim().isEmpty()) errors.append("Enter last name.\n");
  if (fatherName == null || fatherName.getText().trim().isEmpty()) errors.append("Enter father's name.\n");
  if (nid == null || nid.getText().trim().isEmpty()) errors.append("Enter NID number.\n");
  if (mobileNumber == null || mobileNumber.getText() == null || !Pattern.matches("^\\d{11}$", mobileNumber.getText())) {
   errors.append("Enter a valid 11-digit mobile number.\n");
  }
  // Validate duplicate mobile number
  if (mobileCheck()) {
   errors.append("This mobile number is already used.\n");
  }
  if (emailAddress == null || emailAddress.getText() == null || !Pattern.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", emailAddress.getText())) {
   errors.append("Enter a valid email address.\n");
  }
  // Validate duplicate email
  if (emailCheck()) {
   errors.append("This email is already used.\n");
  }
  if (dateOfBirth == null || dateOfBirth.getValue() == null) errors.append("Enter date of birth.\n");
  if (address == null || address.getText().trim().isEmpty()) errors.append("Enter address.\n");
  if (pass == null || pass.getText().trim().isEmpty()) errors.append("Enter password.\n");
  if (confirmPass == null || confirmPass.getText().trim().isEmpty()) errors.append("Enter confirm password.\n");
  if (pass != null && confirmPass != null && !pass.getText().equals(confirmPass.getText())) {
   errors.append("Passwords do not match.\n");
  }
  if (file == null) {
   errors.append("Set an image meeting requirements.\n");
  }

  // Check if there are any errors
  if (errors.length() > 0) {
   wrong.setText(errors.toString());
   return false;
  }

  return true;
 }
 public boolean emailCheck(){
  for(person p:list){
   if(p.email.equals(emailAddress.getText())){
    return true;
   }
  }
  return false;
 }
 public boolean mobileCheck(){
  for(person p:list){
   if(p.mobile.equals(mobileNumber.getText())){
    return true;
   }
  }
  return false;
 }


 public void registration() {
  if (checkAllData()) {
   sendEmail();
   otp.setVisible(true);
   otpButton.setVisible(true);
   getOtp.setVisible(true);
  }
 }

 private int y=0;

 public void sendEmail(){
  // Sender's email credentials
  final String fromEmail = "abuhuraira01924@gmail.com";  // Your Gmail address
  final String appPassword = "igzy pgtk yehb xdos";   // Your Gmail App Password (generated in Google Account)

  // Recipient's email
  String toEmail = emailAddress.getText();  // The recipient's email address

  // SMTP server configuration for Gmail
  Properties properties = new Properties();
  properties.put("mail.smtp.host", "smtp.gmail.com"); // Gmail's SMTP server
  properties.put("mail.smtp.port", "587"); // TLS port (use port 465 for SSL)
  properties.put("mail.smtp.auth", "true"); // Enable authentication
  properties.put("mail.smtp.starttls.enable", "true"); // Enable STARTTLS

  // Create a session with authentication using the App Password
  Session session = Session.getInstance(properties, new Authenticator() {
   @Override
   protected PasswordAuthentication getPasswordAuthentication() {
    return new PasswordAuthentication(fromEmail, appPassword); // Use App Password
   }
  });

  try {
   // Create a new email message
   MimeMessage message = new MimeMessage(session);
   message.setFrom(new InternetAddress(fromEmail)); // Set sender's email address
   message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail)); // Set recipient's email address
   message.setSubject("Test Email from Java"); // Subject of the email
   Random random=new Random();
   y= random.nextInt(9000);
   y=y+1000;
   message.setText(y+""); // Email body text

   // Send the email
   Transport.send(message); // Send the message

   System.out.println("Email sent successfully!");
  } catch (MessagingException e) {
   e.printStackTrace();  // Print any errors that occur during sending the email
   System.err.println("Failed to send email: " + e.getMessage());
  }
 }


 public void sqlSetup() {
  String url = "jdbc:mysql://localhost:3306/registerFrom";
  String username = "root";
  String password = "Abuhuraira@15";

  try (Connection connection = DriverManager.getConnection(url, username, password)) {
   String insertQuery = "INSERT INTO register (ID,userType,firstName, lastName, FatherName, Nid, mobile, email, password, dob, image, address,balance) VALUES (?,?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

   try (PreparedStatement ps = connection.prepareStatement(insertQuery)) {
    int p=list.getLast().id+1;
    ps.setString(1,String.valueOf(p));
    ps.setString(2,"none");
    ps.setString(3, firstName.getText());
    ps.setString(4, lastName.getText());
    ps.setString(5, fatherName.getText());
    ps.setString(6, nid.getText());
    ps.setString(7, mobileNumber.getText());
    ps.setString(8, emailAddress.getText());
    ps.setString(9, confirmPass.getText());
    ps.setString(10, dateOfBirth.getValue().toString());
    ps.setString(12, address.getText());
    ps.setDouble(13,0.0);

    try (FileInputStream fileInputStream = new FileInputStream(file)) {
     ps.setBinaryStream(11, fileInputStream, (int) file.length());
     ps.executeUpdate();
    }
   }
  } catch (SQLException | IOException e) {
   //logger.log(Level.SEVERE, "Database operation failed", e);
   e.printStackTrace();
  }
 }
 public void OtpChecker() {
  if (getOtp.getText().equals(String.valueOf(y))) {
   sqlSetup();
   wrong.setText("Registration successful!");
  } else {
   wrong.setText("Invalid OTP");
  }
 }
}
class person{
 public person(int id,String mobile,String email){
  this.id=id;
  this.email=email;
  this.mobile=mobile;
 }
 int id;
 String email;
 String mobile;
}