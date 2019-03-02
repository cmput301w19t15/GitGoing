package com.example.cmput301w19t15;

import android.media.Image;

import java.util.UUID;

public class Book {
    private String title;
    private String author;
    private int ISBN;
    private String status;
    private String photo;
    private String BookID;

    public Book(String title, String author, int ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        if(this.BookID == null || this.BookID.isEmpty())
            this.BookID = UUID.randomUUID().toString();
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
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return this.status;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public String getPhoto() {
        return this.photo;
    }
    public String getBookID() {
        return this.BookID;
    }
}
