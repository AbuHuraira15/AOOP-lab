package com.example.loginpage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import javax.xml.crypto.Data;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class SuperShopController implements Initializable {

    @FXML
    private Label menu_balance;

    @FXML
    private GridPane Menu_GridPane;
    @FXML
    private TextField menu_amount;

    @FXML
    private Button menu_pay;
    @FXML
    private TableView Menu_tableView;

    @FXML
    private ScrollPane menu_scrollpane;

    @FXML
    private Label menu_total,menu_change;

    @FXML
    private TableColumn<?, ?> menucolumn_Quality;

    @FXML
    private TableColumn<?, ?> menucolumn_price;

    String email;
    int id;


    @FXML
    private TableColumn<?, ?> menucolumn_productname;
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private ObservableList<ProductData> cardListData = FXCollections.observableArrayList();
    @FXML
    private AnchorPane superShopPane;
    public ObservableList<ProductData> menuGetData() {

        String sql = "SELECT * FROM product";

        ObservableList<ProductData> listData = FXCollections.observableArrayList();
        connect =DatabaseConnection.getConnection();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ProductData prod;

            while (result.next()) {
                prod = new ProductData(result.getInt("id"),
                        result.getString("product_id"),
                        result.getString("product_Name"),
                        result.getString("type"),
                        result.getInt("stock"),
                        result.getString("price"),
                        result.getString("image"),
                        result.getDate("date"));

                listData.add(prod);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    public void menuDisplayCard() {

        cardListData.clear();
        cardListData.addAll(menuGetData());

        int row = 0;
        int column = 0;

        Menu_GridPane.getChildren().clear();
        Menu_GridPane.getRowConstraints().clear();
        Menu_GridPane.getColumnConstraints().clear();

        for (int q = 0; q < cardListData.size(); q++) {

            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("ProductPane.fxml"));
                AnchorPane pane = load.load();
                ProductPaneController cardC = load.getController();
                cardC.setData(cardListData.get(q));

                if (column == 3) {
                    column = 0;
                    row += 1;
                }

                Menu_GridPane.add(pane, column++, row);

                GridPane.setMargin(pane, new Insets(10));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ObservableList<ProductData> menuGetOrder() {
        customerID();

        ObservableList<ProductData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM customer WHERE customer_id = " + cID;

        connect = DatabaseConnection.getConnection();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ProductData prod;

            while (result.next()) {
                prod = new ProductData(result.getInt("id"),
                        result.getString("product_id"),
                        result.getString("product_Name"),
                        result.getString("type"),
                        result.getInt("quantity"),
                        result.getString("price"),
                        result.getString("image"),
                        result.getDate("date"));
                listData.add(prod);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    private ObservableList<ProductData> menuOrderListData;

    public void menuShowOrderData() {
        menuOrderListData = menuGetOrder();

        menucolumn_productname.setCellValueFactory(new PropertyValueFactory<>("product_Name"));
        menucolumn_Quality.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        menucolumn_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        Menu_tableView.setItems(menuOrderListData);
    }
    private int getid;

    public void menuSelectOrder() throws  Exception{
        ProductData prod = (ProductData) Menu_tableView.getSelectionModel().getSelectedItem();
        int num = Menu_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }
        // TO GET THE ID PER ORDER
        getid = prod.getId();

    }




    private double totalP;

    public void menuGetTotal() {
        customerID();
        System.out.println(cID);
        String total = "SELECT SUM(price) FROM customer WHERE customer_id = " + cID;

        connect =DatabaseConnection.getConnection();

        try {

            prepare = connect.prepareStatement(total);
            result = prepare.executeQuery();

            if (result.next()) {
                totalP = (result.getDouble("SUM(price)"));
            }
            System.out.println(totalP);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void menuDisplayTotal() {
        menuGetTotal();
        menu_total.setText( totalP+" BDT");
    }

    Alert   alert;
    private double amount;
    private double change;


    public void menuAmount() {
        menuGetTotal();
        System.out.println("hii");
        //System.out.println(menu_amount.getText().isEmpty());
        System.out.println("TT : "+totalP);
        //menu_amount.setText("10000");
        if (menu_amount.getText().isEmpty() || totalP == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid :3");
            alert.showAndWait();
        } else {
            amount = Double.parseDouble(menu_amount.getText());
            System.out.println(Double.parseDouble(menu_amount.getText()));

            if ((Double.parseDouble(menu_amount.getText())) < totalP) {
                menu_amount.setText("");
                System.out.println("Change : "+((Double.parseDouble(menu_amount.getText())) - totalP));
            } else {
                change = ((Double.parseDouble(menu_amount.getText())) - totalP);

                menu_change.setText( ((Double.parseDouble(menu_amount.getText())) - totalP)+" BDT");
            }
        }
    }

    /*public void menuPayBtn() {


        if (totalP == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please choose your order first!");
            alert.showAndWait();
        } else {


            menuGetTotal();
            String insertPay = "INSERT INTO receipt (customer_id, total, date, em_username) "
                    + "VALUES(?,?,?,?)";

            connect = DatabaseConnection.getConnection();

            try {
                System.out.println(menu_amount.getText());

                System.out.println("Input Amount: " + ((menu_amount.getText())));
                System.out.println("Total Price: " + totalP);

                if (Double.parseDouble(menu_amount.getText()) == 0.0) {
                    //System.out.println(amount);
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Messaged");
                    alert.setHeaderText(null);
                    alert.setContentText("Something wrong :3");
                    alert.showAndWait();
                } else {
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure?");
                    Optional<ButtonType> option = alert.showAndWait();

                    if (option.get().equals(ButtonType.OK)) {
                        customerID();
                        menuGetTotal();
                        prepare = connect.prepareStatement(insertPay);
                        prepare.setString(1, String.valueOf(cID));
                        prepare.setString(2, String.valueOf(totalP));

                        Date date = new Date();
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                        prepare.setString(3, String.valueOf(sqlDate));
                        prepare.setString(4, data.username);

                        prepare.executeUpdate();

                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Infomation Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successful.");
                        alert.showAndWait();

                        menuShowOrderData();
                        menuRestart();

                    } else {
                        alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Infomation Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Cancelled.");
                        alert.showAndWait();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
*/


    public void menuPayBtn() {
        if (totalP == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please choose your order first!");
            alert.showAndWait();
        } else {
            menuGetTotal();
            String insertPay = "INSERT INTO receipt (customer_id, total, date, em_username) VALUES (?, ?, ?, ?)";
            String updateBalance = "UPDATE register SET balance = balance - ? WHERE id = ?";

            connect = DatabaseConnection.getConnection();

            try {
                System.out.println(menu_amount.getText());
                System.out.println("Input Amount: " + Double.parseDouble(menu_amount.getText()));
                System.out.println("Total Price: " + totalP);

                if (Double.parseDouble(menu_amount.getText()) < totalP) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Insufficient amount!");
                    alert.showAndWait();
                } else {
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to complete this payment?");
                    Optional<ButtonType> option = alert.showAndWait();

                    if (option.get().equals(ButtonType.OK)) {
                        // Insert payment record into receipt table
                        customerID();
                        prepare = connect.prepareStatement(insertPay);
                        prepare.setString(1, String.valueOf(cID));
                        prepare.setString(2, String.valueOf(totalP));

                        Date date = new Date();
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                        prepare.setString(3, String.valueOf(sqlDate));
                        prepare.setString(4, data.username);
                        prepare.executeUpdate();

                        // Update the customer's balance
                        prepare = connect.prepareStatement(updateBalance);
                        prepare.setDouble(1, totalP); // Deduct the total payment
                        prepare.setInt(2, SessionManager.getUserId()); // Use the correct customer ID
                        int rowsUpdated = prepare.executeUpdate();

                        if (rowsUpdated > 0) {
                            System.out.println("Balance updated successfully.");
                            refreshBalance();
                        } else {
                            System.out.println("Failed to update balance. Customer ID might be incorrect.");
                        }

                        // Show success alert
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Payment completed successfully!");
                        alert.showAndWait();

                        menuShowOrderData();
                        menuRestart();
                    } else {
                        alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Payment cancelled.");
                        alert.showAndWait();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public void menuRestart() {
        totalP = 0;
        change = 0;
        amount = 0;
        menu_total.setText("0.0 BDT");
        menu_amount.setText("");
        //menu_change.setText("0.0 BDT");
    }

    public void menuRemoveBtn() {

        if (getid == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the order you want to remove");
            alert.showAndWait();
        } else {
            String deleteData = "DELETE FROM customer WHERE id = " + getid;
            connect = DatabaseConnection.getConnection();
            try {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this order?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();
                }

                menuShowOrderData();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private int cID;

    public void customerID() {

        String sql = "SELECT MAX(customer_id) FROM customer";
        connect = DatabaseConnection.getConnection();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                cID =((result.getInt("MAX(customer_id)")));
            }

            String checkCID = "SELECT MAX(customer_id) FROM receipt";
            prepare = connect.prepareStatement(checkCID);
            result = prepare.executeQuery();
            int checkID = 0;
            if (result.next()) {
                checkID = result.getInt("MAX(customer_id)");
            }

            if (cID == 0) {
                cID += 1;
            } else if (cID == checkID) {
                cID += 1;
            }

            data.cID = cID;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshBalance() throws Exception{
        String sql = "SELECT balance FROM register WHERE id = ?";
        connect = DatabaseConnection.getConnection();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, SessionManager.getUserId()); // Replace with the correct user ID
            result = prepare.executeQuery();

            if (result.next()) {
                double currentBalance = result.getDouble("balance");
                SessionManager.setBalance(currentBalance);

                //menu_balance.setText(currentBalance+ "BDT");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            menuDisplayCard();
            menuGetOrder();
            menuDisplayTotal();
            menuShowOrderData();
            refreshBalance();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
