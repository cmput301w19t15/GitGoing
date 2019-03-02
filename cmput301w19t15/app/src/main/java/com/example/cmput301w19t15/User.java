package com.example.cmput301w19t15;

import android.media.Image;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String username;
    private String name;
    private String email;
    private String phone;
    private Float rating;

    public User(String username, String name, String email, String phone) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone() {
        return phone;
    }
    public void setRating(Float rating) {
        this.rating = rating;
    }
    public Float getRating() {
        return rating;
    }

    /***
     
    private HashMap<String,Book> requestedBooks = new HashMap<String,Book>();
    private HashMap<String,Book> borrowedBooks = new HashMap<String,Book>();




    public void requestBook(Book book){
        String bookId = book.getBookID();
        requestedBooks.put(bookId,book);
    }
    public Boolean requested(Book book){

        return requestedBooks.containsValue(book);
    }
    public void addAcceptedRequest(Book book){
        String bookId = book.getBookID();
        borrowedBooks.put(bookId,book);
    }
    public Boolean borrowed(Book book){

        return borrowedBooks.containsValue(book);
    }
    public void deleteRequest(Book book){

        requestedBooks.remove(book);
    }
    public void returnBook(Book book){

        borrowedBooks.remove(book);
    }



    private HashMap<String,Book> myBooks = new HashMap<String,Book>();
    private HashMap<String,Book> availableBooks = new HashMap<String,Book>();




    public void addMyBook(Book book){
        myBooks.add(book);
    }
    public Boolean hasBook(Book book){
        return myBooks.contains(book);
    }
    public void deleteMyBook(Book book){
        myBooks.remove(book);
    }
    public void addAvailableBook(Book book){
        availableBooks.add(book);
    }
    public Boolean bookAvailable(Book book){
        return availableBooks.contains(book);
    }
    public void deleteAvailableBook(Book book){
        availableBooks.remove(book);
    }
    **/

}
