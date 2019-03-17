package com.example.cmput301w19t15;

import java.util.UUID;

public class Notification {

    private String type;  // requested, accepted
    private String bookID;
    private String ownerID;
    private String borrowerID;
    private String notifyTo; // userID or borrowerID
    private String notifID;


    public Notification(String type, String book, String owner, String borrower, String user) {
        this.type = type;
        this.bookID = book;
        this.ownerID = owner;
        this.borrowerID = borrower;
        this.notifyTo = user;
        if(this.notifID == null || this.notifID.isEmpty())
            this.notifID = UUID.randomUUID().toString();
    }

    public void setType (String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


    public void setBookID (String book) {
        this.bookID = book;
    }

    public String getBookID() {
        return bookID;
    }

    public void setOwnerID (String owner) {
        this.ownerID = owner;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setBorrowerID (String borrower) {
        this.borrowerID = borrower;
    }

    public String getBorrowerID() {
        return borrowerID;
    }

    public void setNotifyTo(String user) {
        this.notifyTo = user;
    }

    public String getNotifyTo() {
        return notifyTo;
    }

    public String getNotifID() {
        return notifID;
    }
}

