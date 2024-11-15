package com.example.loginpage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class loginApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(loginApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //adding image icon
        Image realstate = new Image(new FileInputStream("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\realstateicon.png"));
        stage.getIcons().add(realstate);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}