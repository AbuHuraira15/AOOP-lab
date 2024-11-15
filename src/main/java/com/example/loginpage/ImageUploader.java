package com.example.loginpage;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ImageUploader {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/bankmanagementsystem";
        String user = "root";
        String password = "shamim";

        // SQL query to update images for a specific ID
        String query = "UPDATE properties SET image1 = ?, image2 = ?, image3 = ?, image4 = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set image files
            pstmt.setBinaryStream(1, new FileInputStream(new File("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\frame5\\WhatsApp Image 2024-12-25 at 19.50.21_6b91df02.jpg")));
            pstmt.setBinaryStream(2, new FileInputStream(new File("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\frame5\\WhatsApp Image 2024-12-25 at 19.50.22_9857a19a.jpg")));
            pstmt.setBinaryStream(3, new FileInputStream(new File("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\frame5\\WhatsApp Image 2024-12-25 at 19.55.13_28dcd2e7.jpg")));
            pstmt.setBinaryStream(4, new FileInputStream(new File("C:\\Users\\USER\\OneDrive\\Desktop\\3RD SEMISTER\\JAVA\\LoginPage\\src\\main\\resources\\UnitedAreaPhotos\\BuyPhotos\\frame5\\WhatsApp Image 2024-12-25 at 19.55.13_d6a504d2.jpg")));
            // Set the ID condition
            pstmt.setInt(5, 15); // Replace 101 with the target ID

            pstmt.executeUpdate();
            System.out.println("Images updated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

