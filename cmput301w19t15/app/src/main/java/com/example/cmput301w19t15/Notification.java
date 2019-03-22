package com.example.cmput301w19t15;

import java.util.UUID;

public class Notification {

    private String type;  // requested, accepted
    private String bookID;
    private String title;
    private String notifyFromID;
    private String notifyFromEmail;
    private String notifyToID;
    private String notifyToEmail;
    private String ISBN;
    private String photo;
    private String notifID;
    private Boolean read;

    public Notification(String type, String bookID, String title, String notifyFromID, String notifyFromEmail,
                        String notifyToID, String notifyToEmail, String ISBN, String photo, Boolean read) {
        this.type = type;
        this.bookID = bookID;
        this.title = title;
        this.notifyFromID = notifyFromID;
        this.notifyFromEmail = notifyFromEmail;
        this.notifyToID = notifyToID;
        this.notifyToEmail = notifyToEmail;
        this.ISBN = ISBN;
        this.photo = photo;
        if(this.notifID == null || this.notifID.isEmpty())
            this.notifID = UUID.randomUUID().toString();
        this.read = read;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setNotifyFromID(String notifyFromID) {
        this.notifyFromID = notifyFromID;
    }

    public String getNotifyFromID() {
        return notifyFromID;
    }

    public void setNotifyFromEmail(String notifyFromName) {
        this.notifyFromEmail = notifyFromName;
    }

    public String getNotifyFromEmail() {
        return notifyFromEmail;
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

    public void setNotifyToEmail(String notifyToName) {
        this.notifyToEmail = notifyToName;
    }

    public String getNotifyToEmail() {
        return notifyToEmail;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public Boolean getRead() { return read; }

    public void setRead(Boolean read) {this.read = read; }
}

