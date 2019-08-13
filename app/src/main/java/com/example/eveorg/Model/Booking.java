package com.example.eveorg.Model;

public class Booking {
    private String userID;

    public Booking(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
