package com.example.cmput301w19t15;

import android.media.Image;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class User {
    private String username;
    private String name;
    private String email;
    private String phone;
    private String userID;
    private Float rating;

    private ArrayList<Book> myBooks = new ArrayList<>();//my books i own
    private ArrayList<Book> myRequestedBooks = new ArrayList<>();//books i have requested from others - with status
    private ArrayList<Book> myRequestedBooksAvailable = new ArrayList<>();//requested books that are available - with status
    private ArrayList<Book> myRequestedBooksAccepted = new ArrayList<>();//requested books that have been accepted - with status
    private ArrayList<Book> borrowedBooks = new ArrayList<>();//book that i have borrowed from others
    private ArrayList<Book> requestedBooks = new ArrayList<>();//books others have requested from me

    //need this constructor DO NOT REMOVE OR EDIT
    public User(){}
    public User(String userID){
        this.userID = userID;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userID);
        //DatabaseReference userRef = databaseReference.child(userID).child("username");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String phoneValue = dataSnapshot.child("phone").getValue().toString();
                String nameValue = dataSnapshot.child("name").getValue().toString();
                String emailValue = dataSnapshot.child("email").getValue().toString();
                String usernameValue = dataSnapshot.child("username").getValue().toString();
                phone = phoneValue;
                name = nameValue;
                email = emailValue;
                username = usernameValue;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
    public User(String username, String name, String email, String phone, String userID) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.userID = userID;
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
    public void setUserID(String userID){
        this.userID = userID;
    }
    public String getUserID(){
        return userID;
    }



    //my books i own
    public void addToMyBooks(Book book){
        myBooks.add(book);
    }
    public void removeMyBooks(Book book){
        myBooks.remove(book);
    }
    public ArrayList<Book> getMyBooks(){
        return myBooks;
    }
    //books i have requested from others - with status
    public void addToMyRequestedBooks(Book book){
        myRequestedBooks.add(book);
    }
    public void removeMyRequestedBooks(Book book){
        myRequestedBooks.remove(book);
    }
    public ArrayList<Book> getMyRequestedBooks(){
        return myRequestedBooks;
    }
    //books others have requested from me
    public void addToRequestedBooks(Book book){
        requestedBooks.add(book);
    }
    public void removeRequestedBooks(Book book){
        requestedBooks.remove(book);
    }
    public ArrayList<Book> getRequestedBooks(){
        return requestedBooks;
    }
    //book that i have borrowed from others
    public void addToMyBorrowedBooks(Book book){
        borrowedBooks.add(book);
    }
    public void removeMyBorrowedBooks(Book book){
        borrowedBooks.remove(book);
    }
    public ArrayList<Book> getBorrowedBooks(){
        return borrowedBooks;
    }


    /*** Planned to Remove***/


    //requested books that are avaliable - with status
    public void addToMyRequestedBooksAvailable(Book book){
        myRequestedBooksAvailable.add(book);
    }
    public void removeMyRequestedBooksAvailable(Book book){
        myRequestedBooksAvailable.remove(book);
    }
    public ArrayList<Book> getMyRequestedBooksAvailable(){
        return myRequestedBooksAvailable;
    }
    //requested books that have been accepeted - with status
    public void addToMyRequestedBooksAccepted(Book book){
        myRequestedBooksAccepted.add(book);
    }
    public void removeMyRequestedBooksAccepted(Book book){
        myRequestedBooksAccepted.remove(book);
    }
    public ArrayList<Book> getMyRequestedBooksAccepted(){
        return myRequestedBooksAccepted;
    }


}
