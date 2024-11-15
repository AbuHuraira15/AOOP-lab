package com.example.regpage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Withdraw implements Initializable {
    @FXML
    private ImageView dbblIcon,bkashIcon,nogodIcon,sonaliBankIcon,mainAddMoneyIcon;

    public void DBBL(){

    }
    public void Bkash() {
        loadPage("Bkash.fxml",2);
    }
    public void Nogod(){

    }
    public void SonaliBank(){

    }
    private AnchorPane anchorPane;
    public void getData(AnchorPane anchorPane,String id){
        this.anchorPane=anchorPane;
        this.id=id;
    }
    public void back(){
        loadPage("BankPage.fxml",1);
    }

    String id;
    private void loadPage(String fxmlFile,int x) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Pane newPane = fxmlLoader.load();

            // Get the controller if needed
            if(x==1){
                BankPage bankPageController = fxmlLoader.getController();
                bankPageController.SentData(anchorPane,id);
            }
            else if(x==2){
                Bkash bankPageController = fxmlLoader.getController();
                bankPageController.getData(anchorPane,id,1);
            }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            SetImage();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void SetImage() throws FileNotFoundException {
        Image image=new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\java\\com\\example\\regpage\\sentMoney.png"));
        mainAddMoneyIcon.setImage(image);
        image=new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\java\\com\\example\\regpage\\Dbbl.jpeg"));
        dbblIcon.setImage(image);
        image=new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\java\\com\\example\\regpage\\SonaliBank.png"));
        sonaliBankIcon.setImage(image);
        image=new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\java\\com\\example\\regpage\\Bkash.png"));
        bkashIcon.setImage(image);
        image=new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\java\\com\\example\\regpage\\Nogod.png"));
        nogodIcon.setImage(image);
    }
}
