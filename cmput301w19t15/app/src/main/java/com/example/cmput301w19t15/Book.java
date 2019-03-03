package com.example.cmput301w19t15;

import android.media.Image;

import java.util.Date;
import java.util.UUID;

public class Book {
    private String title;
    private String author;
    private int ISBN;
    private String photo;
    private String ownerID;
    private String BookID;
    private Date returnDate;
    private Status status;
    private String borrowerID;

    public Book(String title, String author, int ISBN, String photo, String ownerID) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.photo = photo;
        this.ownerID = ownerID;
        this.status = Status.Available;
        if(this.BookID == null || this.BookID.isEmpty())
            this.BookID = UUID.randomUUID().toString();
    }

    /***
     * this enum class will give 4 options to status,
     * each field is a constant value, cannot be changed,
     * Status can be only one of them
     */
    public enum Status{
        Available,
        Requested,
        Accepted,
        Borrowed
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getAuthor() {
        return this.author;
    }
    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }
    public int getISBN() {
        return this.ISBN;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public String getPhoto() {
        return this.photo;
    }
    public void setOwnerID(String ownerID){this.ownerID = ownerID;}
    public String getOwnerID(){return this.ownerID;}
    public String getBookID() {
        return this.BookID;
    }
    public void setReturnDate(Date date){
        this.returnDate = date;
    }
    public Date getDate(){return returnDate;}
    public void setStatus(String status){
        switch (status) {
            case "available" : this.status = Status.Available; break;
            case "requested" : this.status = Status.Requested; break;
            case "accepted"    : this.status = Status.Accepted; break;
            case "borrowed"    : this.status = Status.Borrowed; break;
        }
    }
    public void setBorrowerID(String borrowerID){
        this.borrowerID = borrowerID;
    }
    public String getBorrowerID(){
        return borrowerID;
    }
    public String getStatus(){
        return status.toString();
    }
}
