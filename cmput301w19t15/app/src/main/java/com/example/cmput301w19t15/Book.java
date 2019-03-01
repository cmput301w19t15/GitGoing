package com.example.cmput301w19t15;

import android.media.Image;

import java.util.UUID;

public class Book {
    private String title;
    private String author;
    private Integer ISBN;
    private String status;
    private String photo;
    private String BookID;

    public Book(String title, String author, Integer ISBN) {
        this.title = title;
        this.author = author;
        if(this.BookID.isEmpty() || this.BookID == null)
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
    public void setISBN(Integer ISBN) {
        this.ISBN = ISBN;
    }
    public Integer getISBN() {
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
