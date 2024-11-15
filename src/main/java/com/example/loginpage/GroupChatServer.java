
package com.example.loginpage;
/*



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class GroupChatServer {
    private static final int PORT = 8080;
    private static List<String> messageHistory = new ArrayList<>();
    private static Map<Socket, String> clientNames = new Hashtable<>();
    private static int clientCounter = 0;

    public static void main(String[] args) {
        System.out.println("Server starting...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                String clientName = "Person-" + clientCounter++;
                clientNames.put(clientSocket, clientName);

                System.out.println(clientName + " connected.");
                Show(clientName + " joined the chat.", null);

                // Start a thread for each client
                new ClientHandler(clientSocket, clientName).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static synchronized void Show(String message, Socket excludeSocket) {
        messageHistory.add(message);
        for (Socket client : clientNames.keySet()) {
            if (client != excludeSocket) {
                try {
                    PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
                    writer.println(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ClientHandler extends Thread {
        private final Socket clientSocket;
        private final String clientName;

        public ClientHandler(Socket socket, String name) {
            this.clientSocket = socket;
            this.clientName = name;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                String message;

                // Send chat history to the new client
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                writer.println("Welcome, " + clientName + "!");
                writer.println("Previous messages:");
                for (String history : messageHistory) {
                    writer.println(history);
                }

                // Handle messages from the client
                while ((message = reader.readLine()) != null) {
                    String fullMessage = clientName + ": " + message;
                    System.out.println(fullMessage);
                    Show(fullMessage, clientSocket);
                }
            } catch (IOException e) {
                System.out.println(clientName + " disconnected.");
            } finally {
                clientNames.remove(clientSocket);
                Show(clientName + " left the chat.", null);
            }
        }
    }
}

*/

import java.io.*;
import java.net.*;
import java.util.*;

public class GroupChatServer {
    private static final int PORT = 8;
    private static List<String> messageHistory = new ArrayList<>();
    private static Map<Socket, String> clientNames = new Hashtable<>();
    private static int clientCounter = 0;

    public static void main(String[] args) throws Exception{
        System.out.println("Server starting...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                String clientName = "Person-" + clientCounter++;
                clientNames.put(clientSocket, clientName);

                System.out.println(clientName + " connected.");
                Show(clientName + " joined the chat.", null);

                // Start a thread for each client
                new ClientHandler(clientSocket, clientName).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static synchronized void Show(String message, Socket excludeSocket) {
        messageHistory.add(message);
        for (Socket client : clientNames.keySet()) {
            if (client != excludeSocket) {
                try {
                    PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
                    writer.println(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ClientHandler extends Thread {
        private final Socket clientSocket;
        private final String clientName;

        public ClientHandler(Socket socket, String name) {
            this.clientSocket = socket;
            this.clientName = name;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                String message;

                // Send chat history to the new client
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                writer.println("Welcome, " + clientName + "!");
                writer.println("Previous messages:");
                for (String history : messageHistory) {
                    writer.println(history);
                }

                // Handle messages from the client
                while ((message = reader.readLine()) != null) {
                    String fullMessage = clientName + ": " + message;
                    System.out.println(fullMessage);
                    Show(fullMessage, clientSocket);
                }
            } catch (IOException e) {
                System.out.println(clientName + " disconnected.");
            } finally {
                clientNames.remove(clientSocket);
                Show(clientName + " left the chat.", null);
            }
        }
    }
}
