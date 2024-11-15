package com.example.regpage;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EventManage {

    @FXML
    private TextField eventName,eventTime;
    @FXML
    private DatePicker eventDate,startVoting,endVoting;
    @FXML
    private Label wrong;

    public void start(){
        if(checkData()){
            wrong.setText("Done");
            eventWrite();
        }
    }
    public boolean checkData(){
        if(eventName.getText()==null){
            wrong.setText("Set event name");
            return false;
        }
        else if(eventTime.getText()==null){
            wrong.setText("Set event time");
            return false;
        }
        else if(eventDate.getValue()==null){
            wrong.setText("Sent event date");
            return false;
        }
        else if(endVoting.getValue()==null){
            wrong.setText("Set voting end");
            return false;
        }
        else if(startVoting.getValue()==null){
            wrong.setText("Set Start voting");
            return false;
        }
        return true;
    }
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bankmanagementsystem";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "shamim";
    public void eventWrite(){
        String query = "INSERT INTO eventType (eventName, eventTime, eventDate,votingStart,votingEnd) VALUES (?, ?, ?,?,?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, eventName.getText());
            ps.setString(2, eventTime.getText());
            ps.setString(3, eventDate.getValue().toString());
            ps.setString(4,startVoting.getValue().toString());
            ps.setString(5,endVoting.getValue().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Consider adding proper error handling here
        }
    }
}
