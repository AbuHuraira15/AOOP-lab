package com.example.loginpage;

public class SessionManager {
    private static int userId;
    private static String loggedInEmail;
    private static double balance;

    public static double getBalance() {
        return balance;
    }

    public static void setBalance(double balance) {
        SessionManager.balance = balance;
    }

    public static void setUserId(int id) {
        userId = id;
    }

    public static int getUserId() {
        return userId;
    }

    public static void setLoggedInEmail(String email) {
        loggedInEmail = email;
    }

    public static String getLoggedInEmail() {
        return loggedInEmail;
    }
}

