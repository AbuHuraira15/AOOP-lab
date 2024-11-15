package com.example.regpage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class UploadBill implements Initializable {
    @FXML
    private ImageView imageView;
    @FXML
    private TextField inputUseId,homeRent,gasBill,EcBill,wifiBill,helpingHandBill,utilityBill;
    @FXML
    private DatePicker billMonth;
    @FXML
    private Label name,wrong;
    @FXML
    private GridPane loadPage;
    @FXML
    private Button button;

    public void load(){
        if(inputUseId.getText()==null){
            name.setText("Enter use id !!!");
        }
        else if(inputUseId.getText()!=null){
            String a=getUserDetailsById(inputUseId.getText());
            if(a==null){
                name.setText("Enter valid uer id!!!");
                return;
            }
            String []b=a.split(" ");
            ArrayList<String>list=new ArrayList<>();
            list.addAll(Arrays.asList(b).subList(1, b.length));
            String d=list.toString();
            name.setText(d);
            list.clear();
            loadPage.setDisable(false);
            button.setDisable(false);
            if(b[0].equals("none")){
                homeRent.setDisable(false);
            }
            else {
                homeRent.setDisable(true);
            }
        }
    }


    public boolean checkAllData(){
        if(homeRent.getText()==null){
            wrong.setText("enter home rent");
            return false;
        }
        else if(gasBill.getText()==null){
            wrong.setText("Fill gas bill");
            return false;
        }
        else if(EcBill.getText()==null){
            wrong.setText("Fill Electricity bill");
            return false;
        }
        else if(wifiBill.getText()==null){
            wrong.setText("");
        }
        return true;
    }

    public void saveChange(){}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setIcon();
            loadPage.setDisable(true);
            button.setDisable(true);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public String getUserDetailsById(String id) {
        String url = "jdbc:mysql://localhost:3306/registerFrom";
        String username = "root";
        String password = "Abuhuraira@15";
        String query = "SELECT userType, firstName, lastName FROM register WHERE ID = ?";
        String result = "";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, id);  // Set the ID parameter

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {  // If a record is found
                    String userType = rs.getString("userType");
                    String firstName = rs.getString("firstName");
                    String lastName = rs.getString("lastName");
                    result =  userType + " " + firstName + lastName;  // Format the result
                } else {
                    result = null;  // Handle case where no record is found
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log exception
        }

        return result;  // Return the result
    }



    public void setIcon() throws FileNotFoundException {
        imageView.setImage(new Image(new FileInputStream("D:\\RegPage\\src\\main\\java\\org\\example\\regpage\\Photo\\Utility.png")));
    }
}
