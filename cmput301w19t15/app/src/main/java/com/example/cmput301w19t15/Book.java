package com.example.cmput301w19t15;

import android.media.Image;

public class Book {
    private String title;
    private String author;
    private Integer ISBN;
    private String status;
    private Image photo;

    public Book(Integer ISBN) {
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
        return 1;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return this.status;
    }
    public void setPhoto(Image photo) {
        this.photo = photo;
    }
    public Image getPhoto() {
        return this.photo;
    }
}
