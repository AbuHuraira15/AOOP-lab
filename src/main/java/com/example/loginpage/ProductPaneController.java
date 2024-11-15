package com.example.loginpage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ProductPaneController implements Initializable {
    @FXML
    private AnchorPane card_form;

    @FXML
    private Label prod_name;

    @FXML
    private Label prod_price;

    @FXML
    private ImageView prod_imageView;

    @FXML
    private Spinner<Integer> prod_spinner;

    @FXML
    private Button prod_addBtn;

    private SpinnerValueFactory<Integer> spin;

    private ProductData prodData;
    private Image image;

    private String prodID;
    private String type;
    private String prod_date;
    private String prod_image;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private Alert alert;

    public void setData(ProductData prodData) {
        this.prodData = prodData;

        prod_image = prodData.getImage();
        prod_date = String.valueOf(prodData.getDate());
        type = prodData.getType();
        prodID = prodData.getProduct_id();
        prod_name.setText(prodData.getProduct_Name());
        prod_price.setText("BDT " + String.valueOf(prodData.getPrice()));
        String path = "File:" + prodData.getImage();
        image = new Image(path, 190, 94, false, true);
        prod_imageView.setImage(image);
        pr =Double.parseDouble(prodData.getPrice());

    }
    private int qty;

    public void setQuantity() {
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        prod_spinner.setValueFactory(spin);
    }

    private double totalP;
    private double pr;

    public void addBtn() {

        SuperShopController mForm = new SuperShopController();
        mForm.customerID();

        qty = prod_spinner.getValue();
        String check = "";
        String checkAvailable = "SELECT status FROM product WHERE product_id = '"
                + prodID + "'";

        connect = DatabaseConnection.getConnection();

        try {
            int checkStck = 0;
            String checkStock = "SELECT stock FROM product WHERE product_id = '"
                    + prodID + "'";

            prepare = connect.prepareStatement(checkStock);
            result = prepare.executeQuery();

            if (result.next()) {
                checkStck = result.getInt("stock");
            }

            if(checkStck == 0){

                String updateStock = "UPDATE product SET product_Name = '"
                        + prod_name.getText() + "', type = '"
                        + type + "', stock = 0, price = " + pr
                        + ", status = 'Unavailable', image = '"
                        + prod_image + "', date = '"
                        + prod_date + "' WHERE product_id = '"
                        + prodID + "'";
                prepare = connect.prepareStatement(updateStock);
                prepare.executeUpdate();

            }

            prepare = connect.prepareStatement(checkAvailable);
            result = prepare.executeQuery();

            if (result.next()) {
                check = result.getString("status");
            }

            if (!check.equals("Available") || qty == 0) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Something Wrong :3");
                alert.showAndWait();
            } else {

                if (checkStck < qty) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid. This product is Out of stock");
                    alert.showAndWait();
                } else {
                    prod_image = prod_image.replace("\\", "\\\\");

                    String insertData = "INSERT INTO customer "
                            + "(customer_id, product_Name, quantity, price, date, em_username) "
                            + "VALUES(?,?,?,?,?,?)";
                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, String.valueOf(data.cID));
                    //prepare.setString(2, prodID);
                    prepare.setString(2, prod_name.getText());
                    prepare.setString(3,String.valueOf(qty));
                    //prepare.setString(3, type);

                    totalP = (qty * pr);
                    prepare.setString(4, String.valueOf(totalP));

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(5, String.valueOf(sqlDate));

                    //prepare.setString(7, prod_image);
                    prepare.setString(6, data.username);

                    prepare.executeUpdate();

                    int upStock = checkStck - qty;



                    System.out.println("Date: " + prod_date);
                    System.out.println("Image: " + prod_image);

                    String updateStock = "UPDATE product SET product_Name = '"
                            + prod_name.getText() + "', type = '"
                            + type + "', stock = " + upStock + ", price = " + pr
                            + ", status = '"
                            + check + "', image = '"
                            + prod_image + "', date = '"
                            + prod_date + "' WHERE product_id = '"
                            + prodID + "'";

                    prepare = connect.prepareStatement(updateStock);
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                   // mForm.menuGetTotal();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setQuantity();
    }

}
