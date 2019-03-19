package com.example.cmput301w19t15;

import java.util.UUID;

public class Notification {

    private String type;  // requested, accepted
    private String bookID;
    private String notifyFromID;
    private String notifyToID;
    private String notifID;


    public Notification(String type, String bookID, String notifyFromID, String notifyToID) {
        this.type = type;
        this.bookID = bookID;
        this.notifyFromID = notifyFromID;
        this.notifyToID = notifyToID;
        if(this.notifID == null || this.notifID.isEmpty())
            this.notifID = UUID.randomUUID().toString();
    }

    public Notification() {}

    public void setType (String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getBookID() {
        return bookID;
    }

    public void setNotifyFromID(String notifyFromID) {
        this.notifyFromID = notifyFromID;
    }

    public String getNotifyFromID() {
        return notifyFromID;
    }

    public void setNotifyToID(String notifyToID) {
        this.notifyToID = notifyToID;
    }

    public String getNotifyToID() {
        return notifyToID;
    }

    public String getNotifID() {
        return notifID;
    }
}


