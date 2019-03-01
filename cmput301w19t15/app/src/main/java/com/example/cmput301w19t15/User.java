package com.example.cmput301w19t15;

import android.media.Image;

import java.util.ArrayList;

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
    }
    public String getUsername() {
        return username;
    }
    public void setName(String name) {
    }

    public String getName() {
        return name;
    }
    public void setEmail(String email) {
    }
    public String getEmail() {
        return email;
    }
    public void setPhone(String phone) {
    }
    public String getPhone() {
        return phone;
    }
    public void setRating(Float rating) {
    }
    public Float getRating() {
        return 0f ;
    }

    private ArrayList<Book> requestedBooks = new ArrayList<Book>();
    private ArrayList<Book> borrowedBooks = new ArrayList<Book>();

    public void requestBook(Book book){
        requestedBooks.add(book);
    }
    public Boolean requested(Book book){
        return requestedBooks.contains(book);
    }
    public void addAcceptedRequest(Book book){
        borrowedBooks.add(book);
    }
    public Boolean borrowed(Book book){
        return borrowedBooks.contains(book);
    }
    public void deleteRequest(Book book){
        requestedBooks.remove(book);
    }
    public void returnBook(Book book){
        borrowedBooks.remove(book);
    }



    private ArrayList<Book> myBooks = new ArrayList<Book>();
    private ArrayList<Book> availableBooks = new ArrayList<Book>();
    private ArrayList<Request> pendingRequests = new ArrayList<Request>();
    private ArrayList<Request> acceptedRequests = new ArrayList<Request>();

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
    public void addPendingRequest(Request request){
        pendingRequests.add(request);
    }
    public boolean requestPending(Request request){
        return pendingRequests.contains(request);
    }
    public void deletePendingRequest(Request request){
        pendingRequests.remove(request);
    }
    public void addAcceptedRequest(Request request){
        acceptedRequests.add(request);
    }
    public boolean requestAccepted(Request request){
        return acceptedRequests.contains(request);
    }
    public void deleteAcceptedRequest(Request request){
        acceptedRequests.remove(request);
    }

}
