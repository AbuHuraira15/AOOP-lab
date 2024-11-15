package com.example.loginpage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BuyControllerClass {



    @FXML
    private Button AddressButton;

    @FXML
    private Button AddressButton2;

    @FXML
    private Button AddressButton21;

    @FXML
    private Button AddressButton3;

    @FXML
    private Button AddressButton4;

    @FXML
    private Button AddressButton41;

    @FXML
    private Button DetailsFlat;

    @FXML
    private Button DetailsFlat2;

    @FXML
    private Button DetailsFlat21;

    @FXML
    private Button DetailsFlat3;

    @FXML
    private Button DetailsFlat4;

    @FXML
    private Button DetailsFlat41;

    @FXML
    private ImageView ImageInancorpane;

    @FXML
    private ImageView ImageInancorpane2;

    @FXML
    private ImageView ImageInancorpane3;

    @FXML
    private ImageView ImageInancorpane4;

    @FXML
    private ImageView ImageInancorpane5;

    @FXML
    private ImageView ImageInancorpane51;

    @FXML
    private Button LeftArrowButton1;

    @FXML
    private Button LeftArrowButton12;

    @FXML
    private Button LeftArrowButton13;

    @FXML
    private Button LeftArrowButton14;

    @FXML
    private Button LeftArrowButton141;

    @FXML
    private Button LeftArrowButton4;

    @FXML
    private Button MoneyButton;

    @FXML
    private Button MoneyButton2;

    @FXML
    private Button MoneyButton21;

    @FXML
    private Button MoneyButton3;

    @FXML
    private Button MoneyButton4;

    @FXML
    private Button MoneyButton41;

    @FXML
    private Button RightArrowButton1;

    @FXML
    private Button RightArrowButton12;

    @FXML
    private Button RightArrowButton13;

    @FXML
    private Button RightArrowButton14;

    @FXML
    private Button RightArrowButton141;

    @FXML
    private Button RightArrowButton4;

    @FXML
    private Button threeDotButton;

    @FXML
    private Button threeDotButton2;

    @FXML
    private Button threeDotButton21;

    @FXML
    private Button threeDotButton3;

    @FXML
    private Button threeDotButton4;

    @FXML
    private Button threeDotButton41;

    //for homepage
    private int currentIndex = 0; // To track the current image index
    private List<Image> images = new ArrayList<>(); // List to store images

    //for frame 1
    private int currentIndexForBuy = 0; // To track the current image index
    private List<Image> imagesBuy = new ArrayList<>(); // List to store images

    //for frame2
    private int currentIndexForBuy1 = 0; // To track the current image index
    private List<Image> imagesBuy1 = new ArrayList<>(); // List to store images

    //for frame3
    private int currentIndexForBuy3 = 0; // To track the current image index
    private List<Image> imagesBuy3 = new ArrayList<>(); // List to store images

    //for frame4
    private int currentIndexForBuy4 = 0; // To track the current image index
    private List<Image> imagesBuy4 = new ArrayList<>(); // List to store images


    public void initialize(URL url, ResourceBundle resourceBundle) {

        ///Methode for arrow signs
        try{
            ArrowMethod();

            BuyFrame1PhotoChanger();
            BuyFrame2PhotoChanger();
            BuyFrame3PhotoChanger();
            BuyFrame4PhotoChanger();

        }catch(Exception e){

        }
    }


    @FXML
    public void BuyFrame1PhotoChanger()throws  Exception{
        // Load images into the list
        imagesBuy.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\frame3\\IMG-20241225-WA0050.jpg")));
        imagesBuy.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\frame3\\IMG-20241225-WA0049.jpg")));
        imagesBuy.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\frame3\\IMG-20241225-WA0052.jpg")));
        imagesBuy.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\frame3\\IMG-20241225-WA0048.jpg")));
        imagesBuy.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\frame3\\IMG-20241225-WA0047.jpg")));
        // Set the initial image in the ImageView
        ImageInancorpane.setImage(imagesBuy.get(currentIndexForBuy));
    }
    @FXML
    public void ClickActionOnLeftArrow1(ActionEvent event) {
        currentIndexForBuy = (currentIndexForBuy - 1 + imagesBuy.size()) % imagesBuy.size();
        ImageInancorpane.setImage(imagesBuy.get(currentIndexForBuy));
    }

    public void ClickActionOnRightArrow1(ActionEvent event) {
        currentIndexForBuy = (currentIndexForBuy + 1) % imagesBuy.size();
        ImageInancorpane.setImage(imagesBuy.get(currentIndexForBuy));
    }

    @FXML
    public void BuyFrame2PhotoChanger()throws Exception{
        // Load images into the list
        imagesBuy1.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\IMG-20241225-WA0029.jpg")));
        imagesBuy1.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\IMG-20241225-WA0030.jpg")));
        imagesBuy1.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\IMG-20241225-WA0042.jpg")));
        imagesBuy1.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\IMG-20241225-WA0043.jpg")));
        imagesBuy1.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\IMG-20241225-WA0044.jpg")));

        // Set the initial image in the ImageView
        ImageInancorpane3.setImage(imagesBuy1.get(currentIndexForBuy1));
    }
    @FXML
    public void ClickActionOnLeftArrow2(ActionEvent event) {
        currentIndexForBuy1 = (currentIndexForBuy1 - 1 + imagesBuy1.size()) % imagesBuy1.size();
        ImageInancorpane3.setImage(imagesBuy1.get(currentIndexForBuy1));
    }

    public void ClickActionOnRightArrow2(ActionEvent event) {
        currentIndexForBuy1 = (currentIndexForBuy1 + 1) % imagesBuy1.size();
        ImageInancorpane3.setImage(imagesBuy1.get(currentIndexForBuy1));
    }

    @FXML
    public void BuyFrame3PhotoChanger()throws Exception{
        // Load images into the list
        imagesBuy3.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\Frame31\\IMG-20241227-WA0022.jpg")));
        imagesBuy3.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\Frame31\\IMG-20241227-WA0023.jpg")));
        imagesBuy3.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\Frame31\\IMG-20241227-WA0024.jpg")));
        imagesBuy3.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\Frame31\\IMG-20241227-WA0025.jpg")));
        imagesBuy3.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\Frame31\\IMG-20241227-WA0026.jpg")));
        imagesBuy3.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\Frame31\\IMG-20241227-WA0027.jpg")));
        imagesBuy3.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\Frame31\\IMG-20241227-WA0021.jpg")));


        // Set the initial image in the ImageView
        ImageInancorpane2.setImage(imagesBuy3.get(currentIndexForBuy3));
    }
    @FXML
    public void ClickActionOnLeftArrow3(ActionEvent event) {
        currentIndexForBuy3 = (currentIndexForBuy3 - 1 + imagesBuy3.size()) % imagesBuy3.size();
        ImageInancorpane2.setImage(imagesBuy3.get(currentIndexForBuy3));
    }

    public void ClickActionOnRightArrow3(ActionEvent event) {
        currentIndexForBuy3 = (currentIndexForBuy3 + 1) % imagesBuy3.size();
        ImageInancorpane2.setImage(imagesBuy3.get(currentIndexForBuy3));
    }

    //Frame 4
    @FXML
    public void BuyFrame4PhotoChanger()throws Exception{
        // Load images into the list
        imagesBuy4.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\frame4\\photo3.jpg")));
        imagesBuy4.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\frame4\\photo1.jpg")));
        imagesBuy4.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\frame4\\photo2.jpg")));
        imagesBuy4.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\frame4\\photo4.jpg")));
        imagesBuy4.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\frame4\\photo5.jpg")));
        imagesBuy4.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\frame4\\photo6.jpg")));
        //imagesBuy4.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\frame4\\photo3.jpg")));


        // Set the initial image in the ImageView
        ImageInancorpane4.setImage(imagesBuy4.get(currentIndexForBuy4));
    }
    @FXML
    public void ClickActionOnLeftArrow4(ActionEvent event) {
        currentIndexForBuy4 = (currentIndexForBuy4 - 1 + imagesBuy4.size()) % imagesBuy4.size();
        ImageInancorpane4.setImage(imagesBuy4.get(currentIndexForBuy4));
    }

    public void ClickActionOnRightArrow4(ActionEvent event) {
        currentIndexForBuy4 = (currentIndexForBuy4 + 1) % imagesBuy4.size();
        ImageInancorpane4.setImage(imagesBuy4.get(currentIndexForBuy4));
    }

    public void ArrowMethod(){


        LeftArrowButton1.setStyle("-fx-background-color: transparent; -fx-opacity: 0;");

        // Add hover effect to make the button visible on mouse hover
        LeftArrowButton1.setOnMouseEntered(e -> LeftArrowButton1.setStyle("-fx-background-color: transparent; -fx-opacity: 1;"));
        LeftArrowButton1.setOnMouseExited(e -> LeftArrowButton1.setStyle("-fx-background-color: transparent; -fx-opacity: 0;"));



        RightArrowButton1.setStyle("-fx-background-color: transparent; -fx-opacity: 0;");

        // Add hover effect to make the button visible on mouse hover
        RightArrowButton1.setOnMouseEntered(e -> RightArrowButton1.setStyle("-fx-background-color: transparent; -fx-opacity: 1;"));
        RightArrowButton1.setOnMouseExited(e -> RightArrowButton1.setStyle("-fx-background-color: transparent; -fx-opacity: 0;"));


        LeftArrowButton13.setStyle("-fx-background-color: transparent; -fx-opacity: 0;");

        // Add hover effect to make the button visible on mouse hover
        LeftArrowButton13.setOnMouseEntered(e -> LeftArrowButton13.setStyle("-fx-background-color: transparent; -fx-opacity: 1;"));
        LeftArrowButton13.setOnMouseExited(e -> LeftArrowButton13.setStyle("-fx-background-color: transparent; -fx-opacity: 0;"));

        RightArrowButton13.setStyle("-fx-background-color: transparent; -fx-opacity: 0;");

        // Add hover effect to make the button visible on mouse hover
        RightArrowButton13.setOnMouseEntered(e -> RightArrowButton13.setStyle("-fx-background-color: transparent; -fx-opacity: 1;"));
        RightArrowButton13.setOnMouseExited(e -> RightArrowButton13.setStyle("-fx-background-color: transparent; -fx-opacity: 0;"));

        LeftArrowButton12.setStyle("-fx-background-color: transparent; -fx-opacity: 0;");

        // Add hover effect to make the button visible on mouse hover
        LeftArrowButton12.setOnMouseEntered(e -> LeftArrowButton12.setStyle("-fx-background-color: transparent; -fx-opacity: 1;"));
        LeftArrowButton12.setOnMouseExited(e -> LeftArrowButton12.setStyle("-fx-background-color: transparent; -fx-opacity: 0;"));

        RightArrowButton12.setStyle("-fx-background-color: transparent; -fx-opacity: 0;");

        // Add hover effect to make the button visible on mouse hover
        RightArrowButton12.setOnMouseEntered(e -> RightArrowButton12.setStyle("-fx-background-color: transparent; -fx-opacity: 1;"));
        RightArrowButton12.setOnMouseExited(e -> RightArrowButton12.setStyle("-fx-background-color: transparent; -fx-opacity: 0;"));


        LeftArrowButton4.setStyle("-fx-background-color: transparent; -fx-opacity: 0;");

        // Add hover effect to make the button visible on mouse hover
        LeftArrowButton4.setOnMouseEntered(e -> LeftArrowButton12.setStyle("-fx-background-color: transparent; -fx-opacity: 1;"));
        LeftArrowButton4.setOnMouseExited(e -> LeftArrowButton12.setStyle("-fx-background-color: transparent; -fx-opacity: 0;"));

        RightArrowButton4.setStyle("-fx-background-color: transparent; -fx-opacity: 0;");

        // Add hover effect to make the button visible on mouse hover
        RightArrowButton4.setOnMouseEntered(e -> RightArrowButton4.setStyle("-fx-background-color: transparent; -fx-opacity: 1;"));
        RightArrowButton4.setOnMouseExited(e -> RightArrowButton4.setStyle("-fx-background-color: transparent; -fx-opacity: 0;"));


    }
}
