package com.example.loginpage;

import com.example.regpage.HelloApplication;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.loginpage.DatabaseConnection.databaseLink;
import static com.example.loginpage.DatabaseConnection.getConnection;

public class AdminFrameController implements Initializable {
    Alert alert;
    @FXML
    private Button backButton_inviewresidentinfo,inventory_clearbtn,InventoryDelete_button;
    @FXML
    private ImageView Imagevoew_from_dashboard,InventoryImport_Picture;
    @FXML
    private AnchorPane ViewButtonanchorpane;
    @FXML
    private Label firstname_viewLabel,lastnameviewLabel,fathersnameViewlabel,mobileViewlabel,nidviewLabel,Emailviewlabel,passwordviewlabel,dobviewlabel,addressviewlabel;
    @FXML
    private  Label TotalMamberLebel;
    @FXML
    private AnchorPane Update_Residential_Information;
    @FXML
    private Button DashBoard_Admin;
    @FXML
    private TextField Firstname_tf;
    @FXML
    private TextField address_tf;
    @FXML
    private TextField email_tf;
    @FXML
    private TextField fatersname_tf;
    @FXML
    private TextField lastname_tf;
    @FXML
    private TextField mobile_tf;
    @FXML
    private TextField password_tf;
    private Image image;

    @FXML
    private Button ResidentsManagement,InventoryImport_btn,Inventory_updateButton;
    @FXML
    private AnchorPane DashboardAnchorpane,ProdcutAnchorpane;

    @FXML
    private AnchorPane ResidentMangementAnchorpane;
    @FXML
    private TableView<ResidentInformationSearch> tableview;

    @FXML
    private TableColumn<ResidentInformationSearch, String> firstnametablecolumn;

    @FXML
    private TableColumn<ResidentInformationSearch, String> lastnametablecolumn;

    @FXML
    private TableColumn<ResidentInformationSearch, String> fathersnametablecolumn;

    @FXML
    private TableColumn<ResidentInformationSearch, String> nidtablecolumn;

    @FXML
    private TableColumn<ResidentInformationSearch, String> mobiletablecolumn;

    @FXML
    private TableColumn<ResidentInformationSearch, String> emailtablecolumn;

    @FXML
    private TableColumn<ResidentInformationSearch, String> passwordtablecolumn;

    @FXML
    private TableColumn<ResidentInformationSearch, String> dobtablecolumn;
    @FXML
    private TableColumn<ResidentInformationSearch, Integer> idnameTable_column;
    @FXML
    private TableColumn<ResidentInformationSearch, String> Actiontablecolumn;


    @FXML
    private TableColumn<ResidentInformationSearch, String> addresstablecolumn;
    ObservableList<ResidentInformationSearch> residentInformationSearchObserveList = FXCollections.observableArrayList();

    @FXML
    private ResidentInformationSearch selectedResident;
    @FXML
    private TextField keywordTextField;
    @FXML
    private Label dateLabel;
    @FXML
    private Label timeLabel;// Link this with your "Time" label in FXML
    @FXML
    private ImageView imageview_in_view_ResidentInformation;
    @FXML
    private Button changePictureButton;
    @FXML
    private TextField prod_id;

    @FXML
    private TextField prod_name;

    @FXML
    private TextField prod_price;

    @FXML
    private TextField prod_stock;

    @FXML
    private ComboBox<String> Prod_status;
    @FXML
    private ComboBox<String> prod_type;
    @FXML
    private Button Inventory_Addbutton;


    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    @FXML
    private TableView<ProductData> inventory_TableView;
    @FXML
    private TableColumn<ProductData, Date> inventory_product_Date;

    @FXML
    private TableColumn<ProductData, String> inventory_product_Id;

    @FXML
    private TableColumn<ProductData, String> inventory_product_Name;

    @FXML
    private TableColumn<ProductData, String> inventory_product_Status;

    @FXML
    private TableColumn<ProductData, String> inventory_product_Stock;

    @FXML
    private TableColumn<ProductData, String> inventory_product_price;

    @FXML
    private TableColumn<ProductData, String> inventory_product_type;

    private String [] typeList = {"Drinks","Meals","Cloths","Cosmatics"};

    public void EventButtonOnAction() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("EventManage.fxml")); // Load the FXML file
        Scene scene = new Scene(fxmlLoader.load()); // Create a new scene

        Stage newStage = new Stage(); // Create a new stage (window)
        newStage.setScene(scene); // Set the new scene on the new stage
        newStage.setTitle("Main Frame"); // Optional: Set a title for the new window
        newStage.initModality(Modality.APPLICATION_MODAL); // Optional: Make it modal (blocks interaction with other windows)
        newStage.show();
    }

    public void inventoryTypeList(){
        List<String> typeL = new ArrayList<>();
        for(String data : typeList){
            typeL.add(data);
        }

        ObservableList listData =  FXCollections.observableArrayList(typeL);
        prod_type.setItems(listData);
    }

    private String [] status_List ={"Available","Unavailable"};
    public void inventory_StatusList(){
        List<String> StatusL = new ArrayList<>();
        for(String data :status_List){
            StatusL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(StatusL);
        Prod_status.setItems(listData);
    }
    public ObservableList<ProductData> inventoryDataList(){
        ObservableList<ProductData> listData = FXCollections.observableArrayList();
        Connection connectDB = getConnection();
        String Sql = "SELECT * FROM product";
        connect = DatabaseConnection.getConnection();
        ProductData  prodData;
        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(Sql);

            while (resultSet.next()){
                prodData = new ProductData(resultSet.getInt("id"),resultSet.getString("product_id")
                        ,resultSet.getString("product_Name"),resultSet.getString("stock"),
                        resultSet.getString("price"),resultSet.getString("status"),resultSet.getString("type")
                        ,resultSet.getString("image"),resultSet.getDate("date"));

                listData.add(prodData);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    private ObservableList<ProductData> inventoryListData;

    public void inventoryShowData(){

        inventoryListData=inventoryDataList();
        inventory_product_Id.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        inventory_product_Name.setCellValueFactory(new PropertyValueFactory<>("product_Name"));
        inventory_product_Stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        inventory_product_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        inventory_product_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventory_product_Status.setCellValueFactory(new PropertyValueFactory<>("status"));
        inventory_product_Date.setCellValueFactory(new PropertyValueFactory<>("date"));

        inventory_TableView.setItems(inventoryListData);

    }
    @FXML
    public void FrameChange(ActionEvent event) {
        if (event.getSource() == DashBoard_Admin) {
            DashboardAnchorpane.setVisible(true);
            ResidentMangementAnchorpane.setVisible(false);
            ViewButtonanchorpane.setVisible(false);
            Update_Residential_Information.setVisible(false);
            ProdcutAnchorpane.setVisible(false);
        }
        if (event.getSource() == ResidentsManagement) {
            DashboardAnchorpane.setVisible(false);
            ResidentMangementAnchorpane.setVisible(true);
            ProdcutAnchorpane.setVisible(false);

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

    public void updateResidentialInformation(
            ResidentInformationSearch resident,
            String firstName,
            String lastName,
            String mobile,
            String email,
            ObservableList<ResidentInformationSearch> dataList

    ) {

        try (Connection conn = getConnection()) {
            if (conn != null) {
                String query = "UPDATE register SET firstName = ?, lastName = ?, mobile = ?, email = ?, fatherName = ?, address = ?, password = ? WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setString(1, firstName);
                stmt.setString(2, lastName);
                stmt.setString(3, mobile);
                stmt.setString(4, email);
                stmt.setString(5, resident.getFatherName());
                stmt.setString(6, resident.getAddress());
                stmt.setString(7, resident.getPassword());
                stmt.setInt(8, resident.getId());

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    // Update the ObservableList to reflect changes
                    resident.setFirstName(firstName);
                    resident.setLastName(lastName);
                    resident.setMobile(mobile);
                    resident.setEmail(email);
                    dataList.set(dataList.indexOf(resident), resident);

                    displayTotalMembers();

                    System.out.println("Record updated successfully.");
                } else {
                    System.out.println("No record found with the specified ID.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void deleteResident(ResidentInformationSearch resident) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = getConnection();

        String deleteQuery = "DELETE FROM register WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, resident.getId());
            System.out.println(resident.getId());// Use the unique id to delete
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Successfully deleted
                residentInformationSearchObserveList.remove(resident);
                displayTotalMembers();// Remove from TableView
                System.out.println("Resident deleted successfully.");
            } else {
                System.out.println("No matching resident found to delete.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void HandleUpdateProductOnAction(ActionEvent event) {
        if (selectedResident != null) {
            // Collect updated values from the form
            String updatedFirstName = Firstname_tf.getText();
            String updatedLastName = lastname_tf.getText();
            String updatedFatherName = fatersname_tf.getText();
            String updatedMobile = mobile_tf.getText();
            String updatedAddress = address_tf.getText();
            String updatedEmail = email_tf.getText();
            String updatedPassword = password_tf.getText();

            // Call the update method
            updateResidentialInformation(
                    selectedResident,
                    updatedFirstName,
                    updatedLastName,
                    updatedMobile,
                    updatedEmail,
                    residentInformationSearchObserveList
            );

            // Update additional fields in the TableView object
            selectedResident.setFatherName(updatedFatherName);
            selectedResident.setAddress(updatedAddress);
            selectedResident.setPassword(updatedPassword);

            // Refresh TableView to reflect changes
            tableview.refresh();

            // Hide the update form
            Update_Residential_Information.setVisible(false);
        }
    }

        public void ResidentManagementPage () {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = getConnection();
            String InformationQuery = "SELECT id,firstName,lastName, FatherName, Nid, mobile, email, password, dob, address FROM register";

            try {
                Statement Statement = connectDB.createStatement();
                ResultSet queryOutput = Statement.executeQuery(InformationQuery);

                while ((queryOutput.next())) {
                    int queryId = queryOutput.getInt("id");
                    String queryFirstname = queryOutput.getString("firstName");
                    String queryLastname = queryOutput.getString("lastName");
                    String queryFathername = queryOutput.getString("FatherName");
                    String querynid = queryOutput.getString("Nid");
                    String queryMobile = queryOutput.getString("mobile");
                    String queryEmail = queryOutput.getString("email");
                    String queryPassword = queryOutput.getString("password");
                    String queryDob = queryOutput.getString("dob");
                    String queryAddress = queryOutput.getString("address");
                    residentInformationSearchObserveList.add(new ResidentInformationSearch(queryId, queryFirstname, queryLastname, queryFathername, querynid, queryMobile, queryEmail, queryPassword, queryDob, queryAddress));

                }
                idnameTable_column.setCellValueFactory(new PropertyValueFactory<>("id"));
                firstnametablecolumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
                lastnametablecolumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
                fathersnametablecolumn.setCellValueFactory(new PropertyValueFactory<>("FatherName"));
                nidtablecolumn.setCellValueFactory(new PropertyValueFactory<>("Nid"));
                mobiletablecolumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
                emailtablecolumn.setCellValueFactory(new PropertyValueFactory<>("email"));
                passwordtablecolumn.setCellValueFactory(new PropertyValueFactory<>("password"));
                dobtablecolumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
                addresstablecolumn.setCellValueFactory(new PropertyValueFactory<>("address"));

                // Add a new column for the buttons
                TableColumn<ResidentInformationSearch, Void> actionColumn = new TableColumn<>("Action");

// Use a factory to create buttons for each row
                actionColumn.setCellFactory(param -> new TableCell<>() {
                    private final Button viewButton = new Button("View");
                    private final Button editButton = new Button("Edit");
                    private final Button deleteButton = new Button("Delete");
                    private final HBox hBox = new HBox(10, viewButton, editButton, deleteButton); // Add buttons to an HBox with spacing

                    {

                        // Set actions for buttons
                        viewButton.setOnAction(event -> {
                            imageview_in_view_ResidentInformation.setImage(null);
                            ResidentInformationSearch data = getTableView().getItems().get(getIndex());
                            selectedResident = data;
                           // Handle "View" button click
                            //System.out.println("View button clicked for: " + data.getId());
                            try{
                                loadData();
                                // Load resident image into the ImageView
                                displayImageFromDB(imageview_in_view_ResidentInformation, selectedResident.getId());

                                // Apply the circular shape to the ImageView
                                imageView_circulerShape(imageview_in_view_ResidentInformation);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            ViewButtonanchorpane.setVisible(true);
                            Update_Residential_Information.setVisible(false);

                        });

                        editButton.setOnAction(event -> {

                            ResidentInformationSearch data = getTableView().getItems().get(getIndex());
                            // Populate the update form fields
                            Firstname_tf.setText(data.getFirstName());
                            lastname_tf.setText(data.getLastName());
                            fatersname_tf.setText(data.getFatherName());
                            mobile_tf.setText(data.getMobile());
                            address_tf.setText(data.getAddress());
                            email_tf.setText(data.getEmail());
                            password_tf.setText(data.getPassword());

                            // Store the selected record globally for updating
                            selectedResident = data;
                            Update_Residential_Information.setVisible(true);
                            ViewButtonanchorpane.setVisible(false);

                            System.out.println(data + "");
                            // Handle "Edit" button click
                            System.out.println("Edit button clicked for: " + data.getFirstName());

                        });

                        deleteButton.setOnAction(event -> {
                            Update_Residential_Information.setVisible(false);
                            ViewButtonanchorpane.setVisible(false);
                            ResidentInformationSearch data = getTableView().getItems().get(getIndex());

                            // Confirmation dialog before deletion
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Delete Confirmation");
                            alert.setHeaderText("Are you sure you want to delete this record?");
                            alert.setContentText("This action cannot be undone.");

                            // Wait for user response
                            alert.showAndWait().ifPresent(response -> {
                                if (response == ButtonType.OK) {
                                    // Delete from database
                                    deleteResident(data);
                                }
                            });
                        });

                        // Optional: Style the buttons (e.g., set widths, colors)
                        viewButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
                        editButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                        deleteButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");
                    }


                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(hBox);
                        }
                    }
                });

// Add the button column to the TableView
                tableview.getColumns().add(actionColumn);


                tableview.setItems(residentInformationSearchObserveList);

                FilteredList<ResidentInformationSearch> filtereddata = new FilteredList<>(residentInformationSearchObserveList, b -> true);
                keywordTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                    filtereddata.setPredicate(ResidentInformationSearch -> {
                        if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                            return true;
                        }
                        String searchKeyword = newValue.toLowerCase();


                        if (String.valueOf(ResidentInformationSearch.getId()).toLowerCase().indexOf(searchKeyword) > -1) {
                            System.out.println("ID: " + ResidentInformationSearch.getId() + ", SearchKeyword: " + searchKeyword);
                            return true;
                        } else if (ResidentInformationSearch.getFirstName().toLowerCase().indexOf(searchKeyword) > -1) {
                            return true;
                        } else if (ResidentInformationSearch.getLastName().toLowerCase().indexOf(searchKeyword) > -1) {
                            return true;
                        } else if (ResidentInformationSearch.getFatherName().toLowerCase().indexOf(searchKeyword) > -1) {
                            return true;
                        } else if (ResidentInformationSearch.getMobile().toLowerCase().indexOf(searchKeyword) > -1) {
                            return true;
                        } else if (ResidentInformationSearch.getEmail().toLowerCase().indexOf(searchKeyword) > -1) {
                            return true;
                        } else if (ResidentInformationSearch.getNid().toLowerCase().indexOf(searchKeyword) > -1) {
                            return true;
                        } else if (ResidentInformationSearch.getDob().toLowerCase().indexOf(searchKeyword) > -1) {
                            return true;
                        } else
                            return false;
                    });
                });

                SortedList<ResidentInformationSearch> sortData = new SortedList<>(filtereddata);
                sortData.comparatorProperty().bind(tableview.comparatorProperty());

                tableview.setItems(sortData);
            } catch (Exception e) {
                Logger.getLogger(ResidentInformationSearch.class.getName()).log(Level.SEVERE, null, e);
                e.printStackTrace();
            }
        }

        public void UpdateInformation_cancelButton(ActionEvent event){
            Update_Residential_Information.setVisible(false);
        }

        public void realTimeDate () {
            // Format the current date as DD:MM:YY
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yy");
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

    public void loadData() throws Exception{
        String query = "SELECT * FROM register WHERE id = ?"; // Modify query to select the appropriate record

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, String.valueOf(selectedResident.getId())); // Replace with the actual NID or another identifier

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                // Set the data to the labels
                firstname_viewLabel.setText(resultSet.getString("firstName"));
                lastnameviewLabel.setText(resultSet.getString("lastName"));
                fathersnameViewlabel.setText(resultSet.getString("FatherName"));
                mobileViewlabel.setText(resultSet.getString("mobile"));
                nidviewLabel.setText(resultSet.getString("Nid"));
                Emailviewlabel.setText(resultSet.getString("email"));
                passwordviewlabel.setText(resultSet.getString("password"));
                dobviewlabel.setText(resultSet.getString("dob"));
                addressviewlabel.setText(resultSet.getString("address"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void ViewButtonOn_action(ActionEvent event){

    }
    public void BackButtonOnAction(ActionEvent event){
        ViewButtonanchorpane.setVisible(false);
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
    public void handleChangePicture(ActionEvent event) throws Exception{
        // Open a file chooser dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        // Let the user select an image file
        File selectedFile = fileChooser.showOpenDialog(changePictureButton.getScene().getWindow());

        if (selectedFile != null) {
            try {
                // Display the selected image in the ImageView
                Image image = new Image(new FileInputStream(selectedFile));
                imageview_in_view_ResidentInformation.setImage(image);

                // Save the selected image to the database
                saveImageToDatabase(selectedFile);
                System.out.println(selectedFile);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    private void saveImageToDatabase(File imageFile) {


        String updateQuery = "UPDATE register SET image = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            FileInputStream fis = new FileInputStream(imageFile);
            preparedStatement.setBinaryStream(1, fis, (int) imageFile.length());

            System.out.println(selectedResident.getId());
            preparedStatement.setInt(2, selectedResident.getId());


            int rowsUpdated = preparedStatement.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);

            if (rowsUpdated > 0) {
                System.out.println("Profile picture updated successfully in the database!");
            } else {
                System.out.println("No rows updated. Check user ID.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inventoryImport_Button(ActionEvent eve) {

        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image File", "*png", "*jpg"));

        File file = openFile.showOpenDialog(changePictureButton.getScene().getWindow());

        if (file != null) {

            data.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 120, 127, false, true);

            InventoryImport_Picture.setImage(image);
        }
    }

    public void inventorySelectData() {

        ProductData prodData = inventory_TableView.getSelectionModel().getSelectedItem();
        int num = inventory_TableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        prod_id.setText(prodData.getProduct_id());
        prod_name.setText(prodData.getProduct_Name());
        prod_stock.setText(String.valueOf(prodData.getStock()));
        prod_price.setText(String.valueOf(prodData.getPrice()));

        data.path = prodData.getImage();

        String path = "File:" + prodData.getImage();
        data.date = String.valueOf(prodData.getDate());
        data.id = prodData.getId();

        image = new Image(path, 120, 127, false, true);
        InventoryImport_Picture.setImage(image);
    }




   public void inventory_addButton(){
        if(prod_id.getText().isEmpty()
        || prod_name.getText().isEmpty()
        || prod_stock.getText().isEmpty()
        || data.path == null
        || prod_price.getText().isEmpty()
        || prod_type.getSelectionModel().getSelectedItem() == null
        || Prod_status.getSelectionModel().getSelectedItem() == null
        ){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText(null);
            alert.showAndWait();
        }
        else{
            String CheckProd_id = "SELECT product_id FROM product WHERE product_id='"
                    +prod_id.getText() + "'";
            connect = DatabaseConnection.getConnection();
            try {
                Statement statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery(CheckProd_id);
                if(resultSet.next()){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(prod_id.getText()+"is already taken");
                    alert.showAndWait();
                }
                else {
                    String insertData = "INSERT INTO product "+
                            "(product_id,product_Name,stock,type,price,status,image,date)"
                            +"VALUES (?,?,?,?,?,?,?,?)";
                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1,prod_id.getText());
                    prepare.setString(2,prod_name.getText());
                    prepare.setString(3,prod_stock.getText());
                    prepare.setString(4,(String) prod_type.getSelectionModel().getSelectedItem());

                    prepare.setString(5,prod_price.getText());
                    prepare.setString(6,(String) Prod_status.getSelectionModel().getSelectedItem());

                    String path = data.path;
                    path = path.replace("\\", "\\\\");
                    prepare.setString(7, path);

                    java.util.Date date = new java.util.Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setDate(8, sqlDate);
                    // Use setDate for date values

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();
                    inventoryShowData();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void inventoryDeleteBtn() {
        if (data.id ==null) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE Product ID: " + prod_id.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                String deleteData = "DELETE FROM product WHERE id = " + data.id;
                try {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("successfully Deleted!");
                    alert.showAndWait();

                    // TO UPDATE YOUR TABLE VIEW
                    inventoryShowData();
                    // TO CLEAR YOUR FIELDS
                    inventoryClearBtn();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Cancelled");
                alert.showAndWait();
            }
        }
    }

    public void inventoryUpdateBtn() {

        if (prod_id.getText().isEmpty()
                || prod_name.getText().isEmpty()
                || prod_type.getSelectionModel().getSelectedItem() == null
                || prod_stock.getText().isEmpty()
                || prod_price.getText().isEmpty()
                || Prod_status.getSelectionModel().getSelectedItem() == null
                || data.path == null || data.id == 0) {

            System.out.println("prod_id: " + prod_id.getText());
            System.out.println("prod_name: " + prod_name.getText());
            System.out.println("prod_type: " + prod_type.getSelectionModel().getSelectedItem());
            System.out.println("prod_stock: " + prod_stock.getText());
            System.out.println("prod_price: " + prod_price.getText());
            System.out.println("Prod_status: " + Prod_status.getSelectionModel().getSelectedItem());
            System.out.println("data.path: " + data.path);
            System.out.println("data.id: " + data.id);

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {

            String path = data.path;
            path = path.replace("\\", "\\\\");

            String updateData = "UPDATE product SET "
                    + "product_id = '" + prod_id.getText() + "', product_Name = '"
                    + prod_name.getText() + "', type = '"
                    + prod_type.getSelectionModel().getSelectedItem() + "', stock = '"
                    + prod_stock.getText() + "', price = '"
                    + prod_price.getText() + "', status = '"
                    + Prod_status.getSelectionModel().getSelectedItem() + "', image = '"
                    + path + "', date = '"
                    + data.date + "' WHERE id = " + data.id;

            connect = DatabaseConnection.getConnection();

            try {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE PRoduct ID: " + prod_id.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(updateData);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    // TO UPDATE YOUR TABLE VIEW
                    inventoryShowData();
                    // TO CLEAR YOUR FIELDS
                    inventoryClearBtn();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled.");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void inventoryClearBtn() {

        prod_id.setText("");
        prod_name.setText("");
        prod_type.getSelectionModel().clearSelection();
        prod_stock.setText("");
        prod_price.setText("");
        Prod_status.getSelectionModel().clearSelection();
        data.path = "";
        data.id = 0;
        InventoryImport_Picture.setImage(null);

    }

    public void ProductButtonOnAction(ActionEvent event){
        ProdcutAnchorpane.setVisible(true);
        DashboardAnchorpane.setVisible(false);
        ResidentMangementAnchorpane.setVisible(false);
        Update_Residential_Information.setVisible(false);
        ViewButtonanchorpane.setVisible(false);
    }

    // Add text field effects
    public void tfEffect() {
        prod_id.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                prod_id.getStyleClass().remove("has-text");
            } else {
                if (!prod_id.getStyleClass().contains("has-text")) {
                    prod_id.getStyleClass().add("has-text");
                }
            }
        });


        prod_name.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                prod_name.getStyleClass().remove("has-text");
            } else {
                if (!prod_name.getStyleClass().contains("has-text")) {
                    prod_name.getStyleClass().add("has-text");
                }
            }
        });

        prod_stock.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                prod_stock.getStyleClass().remove("has-text");
            } else {
                if (!prod_stock.getStyleClass().contains("has-text")) {
                    prod_stock.getStyleClass().add("has-text");
                }
            }
        });

        prod_price.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                prod_price.getStyleClass().remove("has-text");
            } else {
                if (!prod_price.getStyleClass().contains("has-text")) {
                    prod_price.getStyleClass().add("has-text");
                }
            }
        });

        String validStyle = "-fx-border-color: #4CAF50; -fx-border-width: 2px; -fx-border-radius: 5px;"; // Soft green
        String errorStyle = "-fx-border-color: #FF5252; -fx-border-width: 2px; -fx-border-radius: 5px;"; // Soft red

        // Add event listeners for Type ComboBox
        prod_type.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.isEmpty()) {
                prod_type.setStyle(validStyle);

            } else {
               // prod_type.setStyle(errorStyle);
            }
        });

        // Add event listeners for Type ComboBox
        Prod_status.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.isEmpty()) {
                Prod_status.setStyle(validStyle);

            } else {
               // Prod_status.setStyle(errorStyle);
            }
        });

        // Optional: Style the buttons (e.g., set widths, colors)
        Inventory_Addbutton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        Inventory_updateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        InventoryDelete_button.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");

        // Add custom styles
        inventory_clearbtn.setStyle("-fx-background-color: lightgray; -fx-text-fill: black; -fx-font-weight: bold;");
        InventoryImport_btn.setStyle("-fx-background-color: lightsteelblue; -fx-text-fill: black; -fx-font-weight: bold;");


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        realTime();//realtime
        realTimeDate();//realtimeDate
        ResidentManagementPage();
        displayTotalMembers();
        imageView_circulerShape(Imagevoew_from_dashboard);//image round shape
        inventoryShowData();
        inventoryTypeList();
        inventory_StatusList();
        tfEffect();




    }

}

