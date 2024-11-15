package com.example.loginpage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GroupChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT =8;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter serverWriter = new PrintWriter(socket.getOutputStream(), true)) {

            // Thread to read messages from the server
            new Thread(() -> {
                try {
                    String message;
                    while ((message = serverReader.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            }).start();

            // Send user input to the server
            String input;
            while ((input = consoleReader.readLine()) != null) {
                serverWriter.println(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
