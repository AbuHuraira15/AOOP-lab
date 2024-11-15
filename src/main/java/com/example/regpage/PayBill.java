package com.example.regpage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class PayBill implements Initializable {
    @FXML
    private Label userName, billMonthShow, wifiPrice, gasPrice, rentPrice, utilityPrice, wrongAmount;
    @FXML
    private TextField UserId, getAmount;
    @FXML
    private DatePicker getBillMonth;
    @FXML
    private CheckBox wifi, gas, EcBill, HomeRent, Utility;
    @FXML
    private ComboBox<String> wifiSpeed;
    @FXML
    private HBox wifiDisable, gasDisable, EcDisable, homeDisable, utilityDisable;
    @FXML
    private ScrollPane ShowOrNot;
    @FXML
    private AnchorPane mainPage,size;

    private String ids;
    public AnchorPane setPage(AnchorPane mainPage,String id){
        this.size=this.mainPage;
        this.mainPage=mainPage;
        this.ids=id;
        return size;
    }
    public void back(){
        loadPage("BankPage.fxml");
    }


    private void loadPage(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Pane newPane = fxmlLoader.load();
            // Get the controller if needed
            BankPage bankPageController = fxmlLoader.getController();
            bankPageController.SentData(mainPage,ids);
            // Make the loaded content fill the entire frame
            AnchorPane.setTopAnchor(newPane, 0.0);
            AnchorPane.setRightAnchor(newPane, 0.0);
            AnchorPane.setBottomAnchor(newPane, 0.0);
            AnchorPane.setLeftAnchor(newPane, 0.0);

            // Clear existing content and add new content
            mainPage.getChildren().clear();
            mainPage.getChildren().add(newPane);

        } catch (IOException e) {
            System.err.println("Error loading page: " + fxmlFile);
            e.printStackTrace();
        }
    }

    private static final String DB_URL = "jdbc:mysql://localhost:3306/bankmanagementsystem";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "shamim";

    private Connection connection;
    private double bill,balance;

    private int id;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connectToDatabase();
        wifiSpeed.setOnAction(event -> wifiPrice.setText(getWifiPrice()));
        gasPrice.setText("2000");
        rentPrice.setText("25000");
        utilityPrice.setText("1500");
        ShowOrNot.setDisable(true);

    }

    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getWifiPrice() {
        String selectedSpeed = wifiSpeed.getValue();
        if (selectedSpeed == null || selectedSpeed.isEmpty()) {
            return "0";
        }
        return selectedSpeed.charAt(0) == '1' ? "1000" : "1500";
    }

    public void wifiPay() {
        if (wifi.isSelected() && wifiSpeed.getValue() != null) {
            if (!readRecords()) {
                insertRecord(Integer.parseInt(UserId.getText()), getBillMonth.getValue().toString());
                double a=Double.parseDouble(wifiPrice.getText());
                if(balance-a>0){
                    balance-=a;
                    updateBalanceById(String.valueOf(id),balance);
                    updateBillRecord("wifi", "paid");
                    refreshBalance();
                }
                else {
                    billMonthShow.setText("Not sufficient balance!!!");
                }

            } else {
                double a=Double.parseDouble(wifiPrice.getText());
                if(balance-a>0){
                    balance-=a;
                    updateBalanceById(String.valueOf(id),balance);
                    updateBillRecord("wifi", "paid");
                }
                else {
                    billMonthShow.setText("Not sufficient balance!!!");
                }
            }
        }
    }

    public void gasPay() {
        if (gas.isSelected()) {
            if (!readRecords()) {
                insertRecord(Integer.parseInt(UserId.getText()), getBillMonth.getValue().toString());
                double a=Double.parseDouble(gasPrice.getText());
                if(balance-a>0){
                    balance-=a;
                    updateBalanceById(String.valueOf(id),balance);
                    updateBillRecord("gas", "paid");
                    refreshBalance();
                }
                else {
                    billMonthShow.setText("Not sufficient balance!!!");
                }
            } else {
                double a=Double.parseDouble(gasPrice.getText());
                if(balance-a>0){
                    balance-=a;
                    updateBalanceById(String.valueOf(id),balance);
                    updateBillRecord("gas", "paid");
                    refreshBalance();
                }
                else {
                    billMonthShow.setText("Not sufficient balance!!!");
                }
            }
        }
    }

    private void refreshBalance() {
        String url = "jdbc:mysql://localhost:3306/bankmanagementsystem";
        String query = "SELECT balance FROM register WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, DB_USER, DB_PASSWORD);
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, UserId.getText());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    balance = rs.getDouble("balance");
                    System.out.println("Updated Balance: " + balance);
                    // Update the UI label if necessary
                    if (userName != null) {
                        userName.setText("Current Balance: " + balance);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void EcPay() {
        if (EcBill.isSelected() && validateAmount()) {
            if (!readRecords()) {
                insertRecord(Integer.parseInt(UserId.getText()), getBillMonth.getValue().toString());
                double a=Double.parseDouble(getAmount.getText());
                if(balance-a>0){
                    bill+=a;
                    wrongAmount.setText(String.valueOf(bill));
                    balance-=a;
                    updateBalanceById(String.valueOf(id),balance);
                    updateBillRecord("EcBill", String.valueOf(bill+a));
                }
                else {
                    billMonthShow.setText("Not sufficient balance!!!");
                }
            } else {
                wrongAmount.setText(String.valueOf(bill));
                double a=Double.parseDouble(getAmount.getText());
                if(balance-a>0){
                    balance-=a;
                    bill+=a;
                    wrongAmount.setText(String.valueOf(bill));
                    updateBalanceById(String.valueOf(id),balance);
                    updateBillRecord("EcBill", String.valueOf(bill+a));
                    refreshBalance();
                }
                else {
                    billMonthShow.setText("Not sufficient balance!!!");
                }
            }
        }
    }

    private boolean validateAmount() {
        try {
            Double.parseDouble(getAmount.getText());
            return true;
        } catch (NumberFormatException e) {
            wrongAmount.setText("Invalid amount entered!");
            return false;
        }
    }

    public void rentPay() {
        if (HomeRent.isSelected()) {
            if (!readRecords()) {
                insertRecord(Integer.parseInt(UserId.getText()), getBillMonth.getValue().toString());
                double a=Double.parseDouble(rentPrice.getText());
                if(balance-a>0){
                    balance-=a;
                    updateBalanceById(String.valueOf(id),balance);
                    updateBillRecord("homeRent", "paid");
                }
                else {
                    billMonthShow.setText("Not sufficient balance!!!");
                }
            } else {
                double a=Double.parseDouble(rentPrice.getText());
                if(balance-a>0){
                    balance-=a;
                    updateBalanceById(String.valueOf(id),balance);
                    updateBillRecord("homeRent", "paid");
                }
                else {
                    billMonthShow.setText("Not sufficient balance!!!");
                }
            }
        }
    }

    public void utilityPay() {
        if (Utility.isSelected()) {
            if (!readRecords()) {
                insertRecord(Integer.parseInt(UserId.getText()), getBillMonth.getValue().toString());
                double a=Double.parseDouble(utilityPrice.getText());
                if(balance-a>0){
                    balance-=a;
                    updateBalanceById(String.valueOf(id),balance);
                    updateBillRecord("utility", "paid");
                }
            } else {
                double a=Double.parseDouble(utilityPrice.getText());
                if(balance-a>0){
                    balance-=a;
                    updateBalanceById(String.valueOf(id),balance);
                    updateBillRecord("utility", "paid");
                }            }
        }
    }

    private boolean checkData() {
        if (UserId.getText() == null || UserId.getText().isEmpty()) {
            userName.setText("Enter user ID:");
            return false;
        } else if (getBillMonth.getValue() == null) {
            billMonthShow.setText("Enter bill month");
            return false;
        }
        return true;
    }

    private void insertRecord(int id, String billMonth) {
        String[] parts = billMonth.split("-");
        String formattedMonth = parts[0] + "-" + parts[1];
        String query = "INSERT INTO paidOrNot (id, homeRent, EcBill, utility, wifi, gas, billMonth) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, "no");
            preparedStatement.setDouble(3, 0.0);
            preparedStatement.setString(4, "no");
            preparedStatement.setString(5, "no");
            preparedStatement.setString(6, "no");
            preparedStatement.setString(7, formattedMonth);
            preparedStatement.executeUpdate();
            System.out.println("Record inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean readRecords() {
        String query = "SELECT * FROM paidOrNot";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            String billMonth = getBillMonth.getValue().toString();
            String[] parts = billMonth.split("-");
            String formattedMonth = parts[0] + "-" + parts[1];

            while (resultSet.next()) {
                int recordId = resultSet.getInt("id");
                if (UserId.getText().equals(String.valueOf(recordId)) && formattedMonth.equals(resultSet.getString("billMonth"))) {
                    updateBillUI(resultSet);
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void updateBillUI(ResultSet resultSet) throws SQLException {
        //rentPrice.setText(resultSet.getString("homeRent"));
        String d=resultSet.getString("homeRent");
        if(Objects.equals(d, "no")){
            updateUIField(HomeRent, rentPrice,d );
            HomeRentRead();
        }else{
            updateUIField(HomeRent, rentPrice,d );
        }
         d=resultSet.getString("wifi");
        if(Objects.equals(d, "no")){
            updateUIField(wifi, wifiPrice,d );
            wifiPrice.setText("0");
        }else{
            updateUIField(wifi, wifiPrice,d );
        }
        d=resultSet.getString("gas");
        if(Objects.equals(d, "no")){
            updateUIField(gas, gasPrice,d );
            gasPrice.setText("2000");
        }else{
            updateUIField(gas, gasPrice,d );
        }
        d=resultSet.getString("utility");
        if(Objects.equals(d, "no")){
            updateUIField(Utility, utilityPrice,d );
            utilityPrice.setText("1500");
        }
        else {
            updateUIField(Utility, utilityPrice,d );
        }

        bill = resultSet.getDouble("EcBill");
    }

    private void updateUIField(CheckBox checkBox, Label label, String value) {
        if ("paid".equalsIgnoreCase(value)) {
            checkBox.setDisable(true);
            label.setText("Paid");
        }
        else {
            checkBox.setDisable(false);
        }

    }

    private void updateBillRecord(String billType, String status) {
        if (UserId.getText() == null || getBillMonth.getValue() == null) {
            System.out.println("User ID or Bill Month is missing.");
            return;
        }

        String billMonth = getBillMonth.getValue().toString(); // Format bill month correctly
        String[] parts = billMonth.split("-");
        String formattedMonth = parts[0] + "-" + parts[1];
        String query = "UPDATE paidOrNot SET " + billType + " = ? WHERE id = ? AND billMonth = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            if(billType.equals("EcBill")){
                preparedStatement.setDouble( 1,Double.parseDouble(status));
            }
            else {
                preparedStatement.setString(1, status);
            }
            preparedStatement.setInt(2, Integer.parseInt(UserId.getText()));
            preparedStatement.setString(3, formattedMonth);

            System.out.println("Executing query: " + query);
            System.out.println("Parameters: " + status + ", " + UserId.getText() + ", " + formattedMonth);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println(billType + " updated successfully.");
            } else {
                System.out.println("No matching record found for the update.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating " + billType);
        }
    }



    public void HomeRentRead() {
        String url = "jdbc:mysql://localhost:3306/bankmanagementsystem";
        String query = "SELECT rent FROM HomeRent WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, DB_USER, DB_PASSWORD);
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, UserId.getText());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rentPrice.setText(rs.getString("rent"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadUserData() {
        if (checkData()) {
            id=Integer.parseInt(UserId.getText());
            load();
            HomeRentRead();
            wrongAmount.setText(String.valueOf(bill));
            if(!readRecords()){
                insertRecord(Integer.parseInt(UserId.getText()),getBillMonth.getValue().toString());
                loadUserData();
                wrongAmount.setText(String.valueOf(bill));
                wifiPrice.setText("0");
                gasPrice.setText("2000");
                utilityPrice.setText("1500");
            }
            else {
                wrongAmount.setText(String.valueOf(bill));
            }

            billMonthShow.setText(String.valueOf(getBillMonth.getValue()));
        }
    }

    private void load() {
        if (UserId.getText() == null || UserId.getText().isEmpty()) {
            userName.setText("Enter user ID!");
        } else {
            String userDetails = getUserDetailsById(UserId.getText());
            if (userDetails == null) {
                ShowOrNot.setDisable(true);
                userName.setText("Enter a valid user ID!");
                return;
            }
            ShowOrNot.setDisable(false);
            updateUserName(userDetails);
        }
    }

    private void updateUserName(String userDetails) {
        String[] details = userDetails.split(" ");
        ArrayList<String> nameParts = new ArrayList<>(Arrays.asList(details).subList(1, details.length));
        userName.setText(String.join(" ", nameParts));
        homeDisable.setDisable(!"none".equals(details[0]));
    }

    private String getUserDetailsById(String id) {
        String url = "jdbc:mysql://localhost:3306/bankmanagementsystem";
        String query = "SELECT  firstName, lastName,balance FROM register WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, DB_USER, DB_PASSWORD);
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                   balance= rs.getDouble("balance");
                    System.out.println(balance);
                    return /*rs.getString("userType") + " " +*/ rs.getString("firstName") + " " + rs.getString("lastName");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private void updateBalanceById(String id, double newBalance) {
        String url = "jdbc:mysql://localhost:3306/bankmanagementsystem";
        String query = "UPDATE register SET balance = ? WHERE ID = ?";
        try (Connection connection = DriverManager.getConnection(url, DB_USER, DB_PASSWORD);
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setDouble(1, newBalance);
            ps.setString(2, id);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Balance updated successfully for ID: " + id);
            } else {
                System.out.println("No matching record found for ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
