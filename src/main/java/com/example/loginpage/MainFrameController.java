package com.example.loginpage;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.naming.PartialResultException;
import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.example.loginpage.DatabaseConnection.getConnection;


public class MainFrameController implements Initializable {

    @FXML
    private AnchorPane HomePafeAnchorpane,filterAnchorpanesetVisible,BuyAnchorpane1;
    @FXML
    private TextField keywordTextField;
    @FXML
    private Label bed0,bed1,bed3,bed4,bed5,bed6;

    @FXML
    private Button AddressButton1;

    @FXML
    private Button AddressButton11;

    @FXML
    private Button AddressButton2;

    @FXML
    private Button AddressButton3;

    @FXML
    private Button AddressButton31;
    @FXML
    private Button HomePageSignInButton;

    @FXML
    private ImageView ImageInancorpane5,ImageInancorpane4,ImageInancorpane51;
    @FXML
    private AnchorPane AnchorpaneVisible2;

    @FXML
    private AnchorPane Anchorpanevisible1;

    @FXML
    private MenuItem BedMax1;

    @FXML
    private MenuItem BedMax2;

    @FXML
    private MenuItem BedMax3;

    @FXML
    private MenuItem BedMin1;

    @FXML
    private MenuItem BedMin2;

    @FXML
    private MenuItem BedMin3;


    @FXML
    private FlowPane FlowPane;

    @FXML
    private Button ContuctUsButton;

    @FXML
    private Button DetailsFlat;

    @FXML
    private Button DetailsFlat1;

    @FXML
    private Button DetailsFlat11;

    @FXML
    private Button DetailsFlat2;

    @FXML
    private Button DetailsFlat3;

    @FXML
    private Button DetailsFlat31;

    @FXML
    private Button HomeButton;

    @FXML
    private ImageView ImageInancorpane;

    @FXML
    private ImageView ImageInancorpane1;

    @FXML
    private ImageView ImageInancorpane11;

    @FXML
    private ImageView ImageInancorpane2;

    @FXML
    private ImageView ImageInancorpane3;

    @FXML
    private ImageView ImageInancorpane31;

    @FXML
    private Label price1;

    @FXML
    private Button LeftArrowButton;

    @FXML
    private Button LeftArrowButton1;

    @FXML
    private AnchorPane postAnchorpane2;
    @FXML
    private Button searchButton;

    @FXML
    private Button LeftArrowButton111;

    @FXML
    private Button LeftArrowButton12;
    @FXML
    private Label addr,addr1,addr2,addr3,addr4,addr5;

    @FXML
    private Button LeftArrowButton13;

    @FXML
    private Button LeftArrowButton131;



    @FXML
    private Button MoneyButton1;

    @FXML
    private Button MoneyButton11;



    @FXML
    private Button MoneyButton3;

    @FXML
    private Button MoneyButton31;

    @FXML
    private MenuButton PriceMaxButton;

    @FXML
    private MenuButton PriceMinButton;

    @FXML
    private MenuButton MaxBed;
    @FXML
    private MenuButton MinBed;
    @FXML
    private MenuButton BathRoom;

    @FXML
    private Button RentButton;
    @FXML
    private AnchorPane BuyAnchorpane,RentAnchorpane;

    @FXML
    private Button RightArrowButton;

    @FXML
    private Button RightArrowButton1;

    @FXML
    private Button RightArrowButton11;

    @FXML
    private Button RightArrowButton111;

    @FXML
    private Button RightArrowButton12;

    @FXML
    private Button RightArrowButton13;
    @FXML
    private Button RightArrowButton4;
    @FXML
    private Button LeftArrowButton4;
    @FXML
    private Label  MoneyButton2, MoneyButton41, MoneyButton, MoneyButton4, MoneyButton21;

    @FXML
    private Button RightArrowButton131;

    @FXML
    private MenuItem bath1;

    @FXML
    private MenuItem bath2;

    @FXML
    private MenuItem bath3;

    @FXML
    private ImageView imageView1;

    @FXML
    private MenuItem priceMaxItem1;
    @FXML
    private MenuItem priceMaxItem2;
    @FXML
    private MenuItem priceMaxItem3;

    @FXML
    private MenuItem priceMinItem1;

    @FXML
    private MenuItem priceMinItem2;

    @FXML
    private MenuItem priceMinItem3;

    @FXML
    private Button threeDotButton;

    @FXML
    private Button threeDotButton1;

    @FXML
    private Button threeDotButton11;

    @FXML
    private Button threeDotButton2;

    @FXML
    private Button threeDotButton3;


    @FXML
    private Button threeDotButton31;
    //private final ArrayList<data> list = new ArrayList<>();

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

    //for homepage
    private int currentIndex5 = 0; // To track the current image index
    private List<Image> imagesBuy5 = new ArrayList<>(); // List to store images

    //for frame4
    private int currentIndexForBuy6 = 0; // To track the current image index
    private List<Image> imagesBuy6 = new ArrayList<>(); // List to store images


    @FXML
    private AnchorPane childContainer;
    @FXML
    private ResidentInformationSearch selectedResident;

    @FXML
    private Button RightArrowButton141,LeftArrowButton141,RightArrowButton14,LeftArrowButton14;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        imageView1.getStyleClass().add("image-view-effect");
        // Create effects for hover
        DropShadow normalEffect = new DropShadow(10, Color.BLACK);
        DropShadow hoverEffect = new DropShadow(20, Color.DARKGRAY);
        hoverEffect.setSpread(0.3);

        // Set default effect
        imageView1.setEffect(normalEffect);

        // Add hover effect using event handlers
        imageView1.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            imageView1.setEffect(hoverEffect); // Apply hover effect
            imageView1.setScaleX(1.1); // Slight zoom in
            imageView1.setScaleY(1.1); // Slight zoom in
        });

        imageView1.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            imageView1.setEffect(normalEffect); // Revert to default effect
            imageView1.setScaleX(1.0); // Reset scale
            imageView1.setScaleY(1.0); // Reset scale
        });


        try {
            tfEffect();
            loadPrices1();
            ArrowMethod();
            HomePagePhotoChange();
            BuyFrame1PhotoChanger();
            BuyFrame2PhotoChanger();
            BuyFrame3PhotoChanger();
            BuyFrame4PhotoChanger();
            BuyFrame5PhotoChanger();
            BuyFrame6PhotoChanger();

        } catch (Exception e) {

        }

    }


    @FXML
    public void HomePageSignupButtonOnAction(ActionEvent event) throws Exception {
        Stage LoginPage = new Stage();

        FXMLLoader loader = new FXMLLoader(loginApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(loader.load());
        LoginPage.setScene(scene);
        LoginPage.show();
    }

    @FXML
    public void HomePagePhotoChange() {
        // Load images into the list
        images.add(new Image(getClass().getResource("/United-Cityphoto1.jpg").toExternalForm())); // Replace with actual file paths
        images.add(new Image(getClass().getResource("/352.jpg").toExternalForm())); // Replace with actual file path
        images.add(new Image(getClass().getResource("/jason-dent-w3eFhqXjkZE-unsplash.jpg").toExternalForm())); // Replace with actual file path
        // Set the initial image in the ImageView
        imageView1.setImage(images.get(currentIndex));
    }

    @FXML
    public void ClickActionOnLeftArrow(ActionEvent event) {
        currentIndex = (currentIndex - 1 + images.size()) % images.size();
        imageView1.setImage(images.get(currentIndex));
    }

    public void ClickActionOnRightArrow(ActionEvent event) {
        currentIndex = (currentIndex + 1) % images.size();
        imageView1.setImage(images.get(currentIndex));
    }

    @FXML
    public void BuyFrame1PhotoChanger() throws Exception {
        // Load images into the list
        imagesBuy.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\frame3\\IMG-20241225-WA0050.jpg")));
        imagesBuy.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\frame3\\IMG-20241225-WA0049.jpg")));
        imagesBuy.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\frame3\\IMG-20241225-WA0052.jpg")));
        imagesBuy.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\frame3\\IMG-20241225-WA0048.jpg")));
        imagesBuy.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\frame3\\IMG-20241225-WA0047.jpg")));
        // Set the initial image in the ImageView
        ImageInancorpane.setImage(imagesBuy.get(currentIndexForBuy));
    }

    public void SearchButtonOnAction(ActionEvent event) {
        try {
            double minPrice = Double.parseDouble(PriceMinButton.getText());
            double maxPrice = Double.parseDouble(PriceMaxButton.getText());
            int minBeds = Integer.parseInt(MinBed.getText().trim());
            int maxBeds = Integer.parseInt(MaxBed.getText().trim());

            System.out.println(minPrice);
            System.out.println(maxPrice);
            System.out.println(minBeds);
            System.out.println(maxBeds);

            loadFilteredAndUnfilteredPrices(minPrice, maxPrice, minBeds, maxBeds);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Invalid input. Please ensure all fields are correctly filled.");
        }
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
    public void BuyFrame2PhotoChanger() throws Exception {
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
    public void BuyFrame3PhotoChanger() throws Exception {
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
    public void BuyFrame4PhotoChanger() throws Exception {
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

    @FXML
    public void ClickActionOnLeftArrow5(ActionEvent event) {
        currentIndex5 = (currentIndex5 - 1 + imagesBuy5.size()) % imagesBuy5.size();
        ImageInancorpane51.setImage(imagesBuy5.get(currentIndex5));
    }

    public void ClickActionOnRightArrow5(ActionEvent event) {
        currentIndex5 = (currentIndex5 + 1) % imagesBuy5.size();
        ImageInancorpane51.setImage(imagesBuy5.get(currentIndex5));
    }

    @FXML
    public void BuyFrame5PhotoChanger() throws Exception {
        // Load images into the list
        imagesBuy5.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\frame5\\WhatsApp Image 2024-12-25 at 19.50.21_6b91df02.jpg")));
        imagesBuy5.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\frame5\\WhatsApp Image 2024-12-25 at 19.50.22_9857a19a.jpg")));
        imagesBuy5.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\frame5\\WhatsApp Image 2024-12-25 at 19.55.13_28dcd2e7.jpg")));
        imagesBuy5.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\frame5\\WhatsApp Image 2024-12-25 at 19.55.13_d6a504d2.jpg")));

        // Set the initial image in the ImageView
        ImageInancorpane51.setImage(imagesBuy5.get(currentIndex5));
    }

    @FXML
    public void ClickActionOnLeftArrow6(ActionEvent event) {
        currentIndexForBuy6 = (currentIndexForBuy6 - 1 + imagesBuy6.size()) % imagesBuy6.size();
        ImageInancorpane5.setImage(imagesBuy6.get(currentIndexForBuy6));
    }

    public void ClickActionOnRightArrow6(ActionEvent event) {
        currentIndexForBuy6 = (currentIndexForBuy6 + 1) % imagesBuy6.size();
        ImageInancorpane5.setImage(imagesBuy6.get(currentIndexForBuy6));
    }

    @FXML
    public void BuyFrame6PhotoChanger() throws Exception {
        // Load images into the list
        imagesBuy6.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\frame6\\WhatsApp Image 2024-12-25 at 19.39.02_1379513a.jpg")));
        imagesBuy6.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\frame6\\WhatsApp Image 2024-12-25 at 19.50.20_22272402.jpg")));
        imagesBuy6.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\frame6\\WhatsApp Image 2024-12-25 at 19.50.20_aeb676d4.jpg")));
        imagesBuy6.add(new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\frame6\\WhatsApp Image 2024-12-25 at 19.50.21_bfdda220.jpg")));

        // Set the initial image in the ImageView
        ImageInancorpane5.setImage(imagesBuy6.get(currentIndexForBuy6));
    }


    @FXML
    public void ActionOnHomeButton(ActionEvent event) throws Exception {
        Anchorpanevisible1.setVisible(true);
        AnchorpaneVisible2.setVisible(false);
        HomePafeAnchorpane.setVisible(true);
    }

    @FXML
    public void ActionOnBuyButton(ActionEvent event) {
        AnchorpaneVisible2.setVisible(true);
        HomePafeAnchorpane.setVisible(false);
        RentAnchorpane.setVisible(false);
        BuyAnchorpane.setVisible(true);
        filterAnchorpanesetVisible.setVisible(true);

    }

    @FXML
    public void ActionOnRentButton(ActionEvent event) {
        Anchorpanevisible1.setVisible(false);
        AnchorpaneVisible2.setVisible(true);
        BuyAnchorpane.setVisible(false);
        RentAnchorpane.setVisible(true);


    }

    @FXML
    public void ClickActionOnAboutUsButton(ActionEvent event) {

    }


    @FXML
    public void BuyFlat(ActionEvent event) {
        // Get the price from the label
        String flatPrice = price1.getText();

        // Show a confirmation dialog
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Purchase");
        confirmationAlert.setHeaderText("You are about to purchase this flat.");
        confirmationAlert.setContentText("Price: " + flatPrice + "\n\nDo you want to proceed?");

        // Apply CSS to the dialog
        DialogPane dialogPane = confirmationAlert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/com/example/loginpage/cssColors/dialog.css").toExternalForm());
        dialogPane.getStyleClass().add("confirmation-dialog");

        // Wait for user response
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Perform the purchase logic (e.g., update database)
            System.out.println("Flat purchased successfully!");

            // Show a success dialog
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Purchase Successful");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Congratulations! You have purchased the flat.");

            // Apply CSS to the success dialog
            DialogPane successDialogPane = successAlert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/com/example/loginpage/cssColors/dialog.css").toExternalForm());
            successDialogPane.getStyleClass().add("success-dialog");

            successAlert.showAndWait();
        } else {
            // If canceled
            System.out.println("Purchase canceled.");
        }
    }
    @FXML
    public void BuyFlat1(ActionEvent event) {
        // Get the price from the label
        String flatPrice = MoneyButton2.getText();

        // Show a confirmation dialog
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Purchase");
        confirmationAlert.setHeaderText("You are about to purchase this flat.");
        confirmationAlert.setContentText("Price: " + flatPrice + "\n\nDo you want to proceed?");

        // Apply CSS to the dialog
        DialogPane dialogPane = confirmationAlert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/com/example/loginpage/cssColors/dialog.css").toExternalForm());
        dialogPane.getStyleClass().add("confirmation-dialog");

        // Wait for user response
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Perform the purchase logic (e.g., update database)
            System.out.println("Flat purchased successfully!");

            // Show a success dialog
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Purchase Successful");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Congratulations! You have purchased the flat.");

            // Apply CSS to the success dialog
            DialogPane successDialogPane = successAlert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/com/example/loginpage/cssColors/dialog.css").toExternalForm());
            successDialogPane.getStyleClass().add("success-dialog");

            successAlert.showAndWait();
        } else {
            // If canceled
            System.out.println("Purchase canceled.");
        }
    }
    @FXML
    public void BuyFlat2(ActionEvent event) {
        // Get the price from the label
        String flatPrice = MoneyButton41.getText();

        // Show a confirmation dialog
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Purchase");
        confirmationAlert.setHeaderText("You are about to purchase this flat.");
        confirmationAlert.setContentText("Price: " + flatPrice + "\n\nDo you want to proceed?");

        // Apply CSS to the dialog
        DialogPane dialogPane = confirmationAlert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/com/example/loginpage/cssColors/dialog.css").toExternalForm());
        dialogPane.getStyleClass().add("confirmation-dialog");

        // Wait for user response
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Perform the purchase logic (e.g., update database)
            System.out.println("Flat purchased successfully!");

            // Show a success dialog
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Purchase Successful");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Congratulations! You have purchased the flat.");

            // Apply CSS to the success dialog
            DialogPane successDialogPane = successAlert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/com/example/loginpage/cssColors/dialog.css").toExternalForm());
            successDialogPane.getStyleClass().add("success-dialog");

            successAlert.showAndWait();
        } else {
            // If canceled
            System.out.println("Purchase canceled.");
        }
    }
    @FXML
    public void BuyFlat3(ActionEvent event) {
        // Get the price from the label
        String flatPrice = MoneyButton21.getText();

        // Show a confirmation dialog
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Purchase");
        confirmationAlert.setHeaderText("You are about to purchase this flat.");
        confirmationAlert.setContentText("Price: " + flatPrice + "\n\nDo you want to proceed?");

        // Apply CSS to the dialog
        DialogPane dialogPane = confirmationAlert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/com/example/loginpage/cssColors/dialog.css").toExternalForm());
        dialogPane.getStyleClass().add("confirmation-dialog");

        // Wait for user response
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Perform the purchase logic (e.g., update database)
            System.out.println("Flat purchased successfully!");

            // Show a success dialog
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Purchase Successful");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Congratulations! You have purchased the flat.");

            // Apply CSS to the success dialog
            DialogPane successDialogPane = successAlert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/com/example/loginpage/cssColors/dialog.css").toExternalForm());
            successDialogPane.getStyleClass().add("success-dialog");

            successAlert.showAndWait();
        } else {
            // If canceled
            System.out.println("Purchase canceled.");
        }
    }
    @FXML
    public void BuyFlat4(ActionEvent event) {
        // Get the price from the label
        String flatPrice = MoneyButton4.getText();

        // Show a confirmation dialog
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Purchase");
        confirmationAlert.setHeaderText("You are about to purchase this flat.");
        confirmationAlert.setContentText("Price: " + flatPrice + "\n\nDo you want to proceed?");

        // Apply CSS to the dialog
        DialogPane dialogPane = confirmationAlert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/com/example/loginpage/cssColors/dialog.css").toExternalForm());
        dialogPane.getStyleClass().add("confirmation-dialog");

        // Wait for user response
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Perform the purchase logic (e.g., update database)
            System.out.println("Flat purchased successfully!");

            // Show a success dialog
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Purchase Successful");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Congratulations! You have purchased the flat.");

            // Apply CSS to the success dialog
            DialogPane successDialogPane = successAlert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/com/example/loginpage/cssColors/dialog.css").toExternalForm());
            successDialogPane.getStyleClass().add("success-dialog");

            successAlert.showAndWait();
        } else {
            // If canceled
            System.out.println("Purchase canceled.");
        }
    }
    @FXML
    public void BuyFlat5(ActionEvent event) {
        // Get the price from the label
        String flatPrice = MoneyButton.getText();

        // Show a confirmation dialog
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Purchase");
        confirmationAlert.setHeaderText("You are about to purchase this flat.");
        confirmationAlert.setContentText("Price: " + flatPrice + "\n\nDo you want to proceed?");

        // Apply CSS to the dialog
        DialogPane dialogPane = confirmationAlert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/com/example/loginpage/cssColors/dialog.css").toExternalForm());
        dialogPane.getStyleClass().add("confirmation-dialog");

        // Wait for user response
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Perform the purchase logic (e.g., update database)
            System.out.println("Flat purchased successfully!");

            // Show a success dialog
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Purchase Successful");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Congratulations! You have purchased the flat.");

            // Apply CSS to the success dialog
            DialogPane successDialogPane = successAlert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/com/example/loginpage/cssColors/dialog.css").toExternalForm());
            successDialogPane.getStyleClass().add("success-dialog");

            successAlert.showAndWait();
        } else {
            // If canceled
            System.out.println("Purchase canceled.");
        }
    }
    //Method for arrow sign
    public void ArrowMethod() {
        LeftArrowButton.setStyle("-fx-background-color: transparent; -fx-opacity: 0;");

        // Add hover effect to make the button visible on mouse hover
        LeftArrowButton.setOnMouseEntered(e -> LeftArrowButton.setStyle("-fx-background-color: transparent; -fx-opacity: 1;"));
        LeftArrowButton.setOnMouseExited(e -> LeftArrowButton.setStyle("-fx-background-color: transparent; -fx-opacity: 0;"));


        LeftArrowButton1.setStyle("-fx-background-color: transparent; -fx-opacity: 0;");

        // Add hover effect to make the button visible on mouse hover
        LeftArrowButton1.setOnMouseEntered(e -> LeftArrowButton1.setStyle("-fx-background-color: transparent; -fx-opacity: 1;"));
        LeftArrowButton1.setOnMouseExited(e -> LeftArrowButton1.setStyle("-fx-background-color: transparent; -fx-opacity: 0;"));

        RightArrowButton.setStyle("-fx-background-color: transparent; -fx-opacity: 0;");

        // Add hover effect to make the button visible on mouse hover
        RightArrowButton.setOnMouseEntered(e -> RightArrowButton.setStyle("-fx-background-color: transparent; -fx-opacity: 1;"));
        RightArrowButton.setOnMouseExited(e -> RightArrowButton.setStyle("-fx-background-color: transparent; -fx-opacity: 0;"));


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
        LeftArrowButton4.setOnMouseEntered(e -> LeftArrowButton4.setStyle("-fx-background-color: transparent; -fx-opacity: 1;"));
        LeftArrowButton4.setOnMouseExited(e -> LeftArrowButton4.setStyle("-fx-background-color: transparent; -fx-opacity: 0;"));

        RightArrowButton4.setStyle("-fx-background-color: transparent; -fx-opacity: 0;");

        // Add hover effect to make the button visible on mouse hover
        RightArrowButton4.setOnMouseEntered(e -> RightArrowButton4.setStyle("-fx-background-color: transparent; -fx-opacity: 1;"));
        RightArrowButton4.setOnMouseExited(e -> RightArrowButton4.setStyle("-fx-background-color: transparent; -fx-opacity: 0;"));

        LeftArrowButton141.setStyle("-fx-background-color: transparent; -fx-opacity: 0;");

        // Add hover effect to make the button visible on mouse hover
        LeftArrowButton141.setOnMouseEntered(e -> LeftArrowButton141.setStyle("-fx-background-color: transparent; -fx-opacity: 1;"));
        LeftArrowButton141.setOnMouseExited(e -> LeftArrowButton141.setStyle("-fx-background-color: transparent; -fx-opacity: 0;"));

        RightArrowButton141.setStyle("-fx-background-color: transparent; -fx-opacity: 0;");

        // Add hover effect to make the button visible on mouse hover
        RightArrowButton141.setOnMouseEntered(e -> RightArrowButton141.setStyle("-fx-background-color: transparent; -fx-opacity: 1;"));
        RightArrowButton141.setOnMouseExited(e -> RightArrowButton141.setStyle("-fx-background-color: transparent; -fx-opacity: 0;"));

        LeftArrowButton14.setStyle("-fx-background-color: transparent; -fx-opacity: 0;");

        // Add hover effect to make the button visible on mouse hover
        LeftArrowButton14.setOnMouseEntered(e -> LeftArrowButton14.setStyle("-fx-background-color: transparent; -fx-opacity: 1;"));
        LeftArrowButton14.setOnMouseExited(e -> LeftArrowButton14.setStyle("-fx-background-color: transparent; -fx-opacity: 0;"));

        RightArrowButton14.setStyle("-fx-background-color: transparent; -fx-opacity: 0;");

        // Add hover effect to make the button visible on mouse hover
        RightArrowButton14.setOnMouseEntered(e -> RightArrowButton14.setStyle("-fx-background-color: transparent; -fx-opacity: 1;"));
        RightArrowButton14.setOnMouseExited(e -> RightArrowButton14.setStyle("-fx-background-color: transparent; -fx-opacity: 0;"));




    }

    ///For Filtering

    @FXML
    public void MinPriceShow1(ActionEvent event) {
        PriceMinButton.setText(priceMinItem1.getText());
    }

    @FXML
    public void MinPriceShow2(ActionEvent event) {
        PriceMinButton.setText(priceMinItem2.getText());
    }

    @FXML
    public void MinPriceShow3(ActionEvent event) {
        PriceMinButton.setText(priceMinItem3.getText());
    }


    public void MaxPriceShow1(ActionEvent event) {
        PriceMaxButton.setText(priceMaxItem1.getText());
    }

    public void MaxPriceShow2(ActionEvent event) {
        PriceMaxButton.setText(priceMaxItem2.getText());
    }

    public void MaxPriceShow3(ActionEvent event) {
        PriceMaxButton.setText(priceMaxItem3.getText());
    }

    public void MaxBed1(ActionEvent event) {
        MaxBed.setText(BedMax1.getText());
    }

    public void MaxBed2(ActionEvent event) {
        MaxBed.setText(BedMax2.getText());
    }

    public void MaxBed3(ActionEvent event) {
        MaxBed.setText(BedMax3.getText());
    }

    public void MinBed1(ActionEvent event) {
        MinBed.setText(BedMin1.getText());
    }

    public void MinBed2(ActionEvent event) {
        MinBed.setText(BedMin2.getText());
    }

    public void MinBed3(ActionEvent event) {
        MinBed.setText(BedMin3.getText());
    }

    public void Bathroom1(ActionEvent event) {
        BathRoom.setText(bath1.getText());
    }

    public void Bathroom2(ActionEvent event) {
        BathRoom.setText(bath2.getText());
    }

    public void Bathroom3(ActionEvent event) {
        BathRoom.setText(bath3.getText());
    }


   public void loadPrices1() throws Exception {
       // SQL query to fetch the prices, addresses, bedroom counts, and images (stored as BLOBs)
       String query = "SELECT price, address, bedroom_count, image1 FROM properties LIMIT 7";

       try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

           ResultSet resultSet = stmt.executeQuery();

           // Reset all labels and image views to clear old data
           // resetLabels();

           // Iterate through the result set and set prices, addresses, bedrooms, and images
           int index = 0;
           while (resultSet.next() && index < 7) {
               String price = resultSet.getString("price"); // Fetch price
               String address = resultSet.getString("address"); // Fetch address
               String bed = resultSet.getString("bedroom_count"); // Fetch bedroom count

               // Fetch image as binary stream
               InputStream imageStream = resultSet.getBinaryStream("image1");

               // Convert binary stream to JavaFX Image
               Image image = null;
               if (imageStream != null) {
                   image = new Image(imageStream);
               }

               // Set data to corresponding labels and image views
               switch (index) {
                   case 0 -> {
                       price1.setText(price);
                       addr.setText(address);
                       bed0.setText(bed);
                       if (image != null) ImageInancorpane3.setImage(image);
                   }
                   case 1 -> {
                       MoneyButton2.setText(price);
                       addr1.setText(address);
                       bed1.setText(bed);
                       if (image != null) ImageInancorpane2.setImage(image);
                   }
                   case 2 -> {
                       MoneyButton41.setText(price);
                       addr2.setText(address);
                       bed3.setText(bed);
                       if (image != null) ImageInancorpane51.setImage(image);
                   }
                   case 3 -> {
                       MoneyButton.setText(price);
                       addr3.setText(address);
                       bed4.setText(bed);
                       if (image != null) ImageInancorpane4.setImage(image);
                   }
                   case 4 -> {
                       MoneyButton4.setText(price);
                       addr4.setText(address);
                       bed5.setText(bed);
                       if (image != null) ImageInancorpane5.setImage(image);
                   }
                   case 5 -> {
                       MoneyButton21.setText(price);
                       addr5.setText(address);
                       bed6.setText(bed);
                       if (image != null) ImageInancorpane.setImage(image);
                   }
               }
               index++;
           }
       } catch (SQLException e) {
           e.printStackTrace();
           // Optional: Show an error dialog or log the error
       }
   }



    public void loadFilteredAndUnfilteredPrices(double minPrice, double maxPrice, int minBeds, int maxBeds) throws Exception {
        // Queries for filtered and unfiltered data
        String filteredQuery = "SELECT price, address, bedroom_count, image1 FROM properties WHERE price BETWEEN ? AND ? AND bedroom_count BETWEEN ? AND ? ORDER BY price ASC";
        String unfilteredQuery = "SELECT price, address, bedroom_count, image1 FROM properties WHERE (price NOT BETWEEN ? AND ? OR bedroom_count NOT BETWEEN ? AND ?) ORDER BY price ASC";

        try (Connection conn = getConnection();
             PreparedStatement filteredStmt = conn.prepareStatement(filteredQuery);
             PreparedStatement unfilteredStmt = conn.prepareStatement(unfilteredQuery)) {

            // Set parameters for the filtered query
            filteredStmt.setDouble(1, minPrice);
            filteredStmt.setDouble(2, maxPrice);
            filteredStmt.setInt(3, minBeds);
            filteredStmt.setInt(4, maxBeds);

            ResultSet filteredResultSet = filteredStmt.executeQuery();

            // Reset all labels and image views before setting new data
            resetLabelsAndImages();

            int index = 0;

            // Fill labels and images with filtered data
            while (filteredResultSet.next() && index < 7) {
                String price = filteredResultSet.getString("price");
                String address = filteredResultSet.getString("address");
                int bed = filteredResultSet.getInt("bedroom_count");
                InputStream imageStream = filteredResultSet.getBinaryStream("image1");

                Image image = null;
                if (imageStream != null) {
                    image = new Image(imageStream);
                }

                setLabelAndImage(index++, price, address, bed, image);
            }

            // If not all labels are filled, fetch unfiltered data
            if (index < 7) {
                unfilteredStmt.setDouble(1, minPrice);
                unfilteredStmt.setDouble(2, maxPrice);
                unfilteredStmt.setInt(3, minBeds);
                unfilteredStmt.setInt(4, maxBeds);

                ResultSet unfilteredResultSet = unfilteredStmt.executeQuery();

                while (unfilteredResultSet.next() && index < 7) {
                    String price = unfilteredResultSet.getString("price");
                    String address = unfilteredResultSet.getString("address");
                    int bed = unfilteredResultSet.getInt("bedroom_count");
                    InputStream imageStream = unfilteredResultSet.getBinaryStream("image1");

                    Image image = null;
                    if (imageStream != null) {
                        image = new Image(imageStream);
                    }

                    setLabelAndImage(index++, price, address, bed, image);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Optional: Show an error dialog or log the error
        }
    }
    // Helper method to reset all labels
    private void resetLabels() {
        price1.setText("");
        MoneyButton2.setText("");
        MoneyButton41.setText("");
        MoneyButton.setText("");
        MoneyButton4.setText("");
        MoneyButton21.setText("");

        addr1.setText("");
        addr2.setText("");
        addr3.setText("");
        addr4.setText("");
        addr5.setText("");

        bed0.setText("");
        bed1.setText("");
        bed3.setText("");
        bed4.setText("");
        bed5.setText("");
        bed6.setText("");
    }


    // Helper method to reset all labels and image views
    private void resetLabelsAndImages() {
        resetLabels();

        ImageView[] imageViews = {ImageInancorpane3, ImageInancorpane2, ImageInancorpane51, ImageInancorpane4, ImageInancorpane5, ImageInancorpane};
        for (ImageView imageView : imageViews) {
            if (imageView != null) {
                imageView.setImage(null);
            }
        }
    }

    // Helper method to set label and image
    private void setLabelAndImage(int index, String price, String address, int bed, Image image) {
        switch (index) {
            case 0 -> {
                price1.setText(price);
                addr.setText(address);
                bed0.setText(String.valueOf(bed));
                if (image != null) ImageInancorpane3.setImage(image);
            }
            case 1 -> {
                MoneyButton2.setText(price);
                addr1.setText(address);
                bed1.setText(String.valueOf(bed));
                if (image != null) ImageInancorpane2.setImage(image);
            }
            case 2 -> {
                MoneyButton41.setText(price);
                addr2.setText(address);
                bed3.setText(String.valueOf(bed));
                if (image != null) ImageInancorpane51.setImage(image);
            }
            case 3 -> {
                MoneyButton21.setText(price);
                addr3.setText(address);
                bed4.setText(String.valueOf(bed));
                if (image != null) ImageInancorpane4.setImage(image);
            }
            case 4 -> {
                MoneyButton4.setText(price);
                addr4.setText(address);
                bed5.setText(String.valueOf(bed));
                if (image != null) ImageInancorpane5.setImage(image);
            }
            case 5 -> {
                MoneyButton.setText(price);
                addr5.setText(address);
                bed6.setText(String.valueOf(bed));
                if (image != null) ImageInancorpane.setImage(image);
            }
            default -> System.out.println("Extra data: Price: " + price + ", Address: " + address + ", Beds: " + bed);
        }
    }

    // Add text field effects
    public void tfEffect() {
        PriceMinButton.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                PriceMinButton.getStyleClass().remove("has-text");
            } else {
                if (!PriceMinButton.getStyleClass().contains("has-text")) {
                    PriceMinButton.getStyleClass().add("has-text");
                }
            }
        });


        PriceMaxButton.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                PriceMaxButton.getStyleClass().remove("has-text");
            } else {
                if (!PriceMaxButton.getStyleClass().contains("has-text")) {
                    PriceMaxButton.getStyleClass().add("has-text");
                }
            }
        });

        MinBed.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                MinBed.getStyleClass().remove("has-text");
            } else {
                if (!MinBed.getStyleClass().contains("has-text")) {
                    MinBed.getStyleClass().add("has-text");
                }
            }
        });

        MaxBed.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                MaxBed.getStyleClass().remove("has-text");
            } else {
                if (!MaxBed.getStyleClass().contains("has-text")) {
                    MaxBed.getStyleClass().add("has-text");
                }
            }
        });

        priceMinItem1.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                priceMinItem1.getStyleClass().remove("has-text");
            } else {
                if (!priceMinItem1.getStyleClass().contains("has-text")) {
                    priceMinItem1.getStyleClass().add("has-text");
                }
            }
        });
    }
}



