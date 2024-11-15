package com.example.regpage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainFrame implements Initializable {
    @FXML
    private AnchorPane frame;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPage("BankPage.fxml");
    }

    private void loadPage(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Pane newPane = fxmlLoader.load();

            // Pass data to the loaded page's controller
            BankPage bankPageController = fxmlLoader.getController();
            String id = "100151"; // Example ID
            bankPageController.SentData(frame, id);

            // Set the loaded pane to fill the frame
            AnchorPane.setTopAnchor(newPane, 0.0);
            AnchorPane.setRightAnchor(newPane, 0.0);
            AnchorPane.setBottomAnchor(newPane, 0.0);
            AnchorPane.setLeftAnchor(newPane, 0.0);

            frame.getChildren().clear();
            frame.getChildren().add(newPane);

        } catch (IOException e) {
            System.err.println("Error loading page: " + fxmlFile);
            e.printStackTrace();
        }
    }

    public void changePage(String fxmlFile) {
        loadPage(fxmlFile);
    }

    public AnchorPane getFrame() {
        return frame;
    }
}
