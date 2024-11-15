package com.example.regpage;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class VotingPage extends Thread implements Initializable {
    // FXML Controls
    @FXML private Label timer, resultYes, resultNo, name, sDate, eDate, time, date;
    @FXML private Rectangle vote1, vote2;
    @FXML private Button YesId, NoId;

    // Date controls and thread control
    private DatePicker startDate, endDate;
    private volatile boolean running = true;

    // Database configuration
    private static final String DB_URL = "jdbc:mysql://localhost:3306/eventDbs";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Abuhuraira@15";

    // User and voting data
    private String userID = "141511";
    String a,b;
    private List<VotingData> votingHistory = Collections.synchronizedList(new ArrayList<>());

    double x,y;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        x=vote1.getY();y=vote2.getY();
        eventRead();
        initializeDatePickers();
        readVotingData();
        checkUserVotingStatus();
        this.start();  // Start the timer thread
    }

    private void initializeDatePickers() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        StringConverter<LocalDate> converter = createDateConverter(formatter);

        startDate = new DatePicker();
        endDate = new DatePicker();
        startDate.setConverter(converter);
        endDate.setConverter(converter);

        // Set initial dates
        String startDateStr = a;
        String endDateStr = b;
        startDate.setValue(LocalDate.parse(startDateStr, formatter));
        endDate.setValue(LocalDate.parse(endDateStr, formatter));

    }

    private StringConverter<LocalDate> createDateConverter(DateTimeFormatter formatter) {
        return new StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                return date != null ? formatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                try {
                    return LocalDate.parse(string, formatter);
                } catch (Exception e) {
                    return null;
                }
            }
        };
    }


    @Override
    public void run() {
        while (running) {
            Platform.runLater(this::updateTimer);
            try {
                Thread.sleep(1000);
                readVotingData();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void updateTimer() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDateTime = startDate.getValue().atStartOfDay();
        LocalDateTime endDateTime = endDate.getValue().atTime(23, 59, 59);

        if (now.isBefore(startDateTime)) {
            long secondsUntilStart = java.time.Duration.between(now, startDateTime).getSeconds();
            timer.setText(formatDuration(secondsUntilStart) + " until voting starts");
        } else if (now.isAfter(endDateTime)) {
            timer.setText("Voting has ended");
            disableVoting();
        } else {
            long secondsUntilEnd = java.time.Duration.between(now, endDateTime).getSeconds();
            timer.setText(formatDuration(secondsUntilEnd));
        }
    }

    private void disableVoting() {
        Platform.runLater(() -> {
            YesId.setVisible(false);
            NoId.setVisible(false);
        });
        stopTimer();
    }

    private String formatDuration(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }

    @FXML
    public void yes() {
        if (canUserVote()) {
            updateVotes(userID, true);
        }
    }

    @FXML
    public void no() {
        if (canUserVote()) {
            updateVotes(userID, false);
        }
    }

    private boolean canUserVote() {
        return votingHistory.stream()
                .noneMatch(vote -> vote.id.equals(userID));
    }

    private void checkUserVotingStatus() {
        boolean hasVoted = !canUserVote();
        Platform.runLater(() -> {
            YesId.setVisible(!hasVoted);
            NoId.setVisible(!hasVoted);
        });
    }

    private void updateVotes(String userID, boolean isYesVote) {
        VotingData lastData = getLastVotingData();
        if (lastData != null) {
            int updatedYes = isYesVote ? lastData.yes + 1 : lastData.yes;
            int updatedNo = isYesVote ? lastData.no : lastData.no + 1;
            writeVotingData(userID, updatedYes, updatedNo);
            readVotingData();
            updateVotingDisplay();
        }
    }

    private void updateVotingDisplay() {
        VotingData lastData = getLastVotingData();
        if (lastData != null) {
            Platform.runLater(() -> {
                resultYes.setText(String.valueOf(lastData.yes));
                resultNo.setText(String.valueOf(lastData.no));
                updateVotingGraphics(lastData);
                checkUserVotingStatus();
            });
        }
    }

    private void updateVotingGraphics(VotingData data) {
        /*vote1.setY(435 - data.yes);
        vote2.setY(435 - data.no);
        vote1.setHeight(data.yes);
        vote2.setHeight(data.no);*/
    }

    private VotingData getLastVotingData() {
        synchronized (votingHistory) {
            return votingHistory.isEmpty() ? null : votingHistory.get(votingHistory.size() - 1);
        }
    }

    private void stopTimer() {
        running = false;
    }

    public void writeVotingData(String id, int yesVotes, int noVotes) {
        String query = "INSERT INTO voting (ID, VotingYes, VotingNO) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, id);
            ps.setInt(2, yesVotes);
            ps.setInt(3, noVotes);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Consider adding proper error handling here
        }
    }




    public void eventRead() {
         String DB_URL = "jdbc:mysql://localhost:3306/bankmanagementsystem";
         String DB_USERNAME = "root";
         String DB_PASSWORD = "shamim";
        String query = "SELECT eventName, eventTime, eventDate, votingStart, votingEnd FROM eventType";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                name.setText(rs.getString("eventName"));
                time.setText(rs.getString("eventTime"));
                date.setText(rs.getString("eventDate"));
                a=rs.getString("votingStart");
                sDate.setText(a);
                b=rs.getString("votingEnd");
                eDate.setText(b);


            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Consider adding proper error handling here
        }
    }

    public void readVotingData() {
        String query = "SELECT * FROM voting";
        votingHistory.clear();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                VotingData data = new VotingData();
                data.id = rs.getString("ID");
                data.yes = rs.getInt("VotingYes");
                data.no = rs.getInt("VotingNO");
                votingHistory.add(data);
            }

            // After reading data, update the voting display
            updateVotingDisplay();

            // Check if there is data and update the graph in Platform.runLater to ensure it's on the JavaFX Application Thread
            Platform.runLater(() -> {
                if (!votingHistory.isEmpty()) {
                    VotingData lastData = votingHistory.get(votingHistory.size() - 1); // Access the last element
                    // Update graph
                    vote1.setHeight(lastData.yes+5);
                    vote2.setHeight(lastData.no+5);
                    vote2.setY(y-lastData.no);
                    vote1.setY(x-lastData.yes);
                    /*vote1.setY(435 - lastData.yes);
                    vote2.setY(435 - lastData.no);*/
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
            // Consider adding proper error handling here
        }
    }




    // Inner class for voting data
    private static class VotingData {
        String id;
        int yes;
        int no;
    }
}
