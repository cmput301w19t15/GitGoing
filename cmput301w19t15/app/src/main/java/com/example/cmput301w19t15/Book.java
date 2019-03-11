package com.example.cmput301w19t15;

import android.graphics.Bitmap;
import android.media.Image;

import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Book {
    private String title = "";//
    private String author = "";//
    private String ISBN = "";//
    private String photo = "";//
    private String ownerEmail = "";//
    private String ownerID = "";//
    private String BookID = "";//
    private long returnDate = System.currentTimeMillis();
    private Status status = Status.Available;//
    private String borrowerID = "";


    public Book(){}
    public Book(Book book){
        this.title = book.title;
        this.author = book.author;
        this.ISBN = book.ISBN;
        this.photo = book.photo;
        this.ownerEmail = book.ownerEmail;
        this.ownerID = book.ownerID;
        this.status = Status.Available;
        this.BookID = book.BookID;
    }
    public Book(FindBooks findBooks, ArrayList<Book> listOfBooks){}
    public Book(String title, String author, String ISBN, String photo, String ownerEmail, String ownerID) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.photo = photo;
        this.ownerEmail = ownerEmail;
        this.ownerID = ownerID;
        this.status = Status.Available;
        if(this.BookID == null || this.BookID.isEmpty())
            this.BookID = UUID.randomUUID().toString();
    }
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
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
    public String getISBN() {
        return this.ISBN;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public String getPhoto() {
        return this.photo;
    }
    public void setOwnerEmail(String ownerEmail){this.ownerEmail = ownerEmail;}
    public String getOwnerEmail(){return this.ownerEmail;}
    public void setOwnerID(String ownerID){this.ownerID = ownerID;}
    public String getOwnerID(){return this.ownerID;}
    public void setBookID(String BookID){
        this.BookID = BookID;
    }
    public String getBookID() {
        return this.BookID;
    }
    public void setDate(long date){
        this.returnDate = date;
    }
    public long getDate(){return returnDate;}
    public void setStatus(String status){
        switch (status) {
            case "available" : this.status = Status.Available; break;
            case "requested" : this.status = Status.Requested; break;
            case "accepted"    : this.status = Status.Accepted; break;
            case "borrowed"    : this.status = Status.Borrowed; break;
        }
    }
    public String getStatus(){
        return status.toString();
    }
    public void setBorrowerID(String borrowerID){
        this.borrowerID = borrowerID;
    }
    public String getBorrowerID(){
        return borrowerID;
    }
}
