package com.example.cmput301w19t15.Objects;
//:)

import com.example.cmput301w19t15.Activity.AddBookInfo;
import com.example.cmput301w19t15.Activity.BookInfo;
import com.example.cmput301w19t15.Functions.BookMethodCalls;
import com.example.cmput301w19t15.InProgress.BorrowerBookView;
import com.example.cmput301w19t15.InProgress.DetailedBookInfo;
import com.example.cmput301w19t15.Activity.FindBooks;
import com.example.cmput301w19t15.Activity.MyBooks;
import com.example.cmput301w19t15.InProgress.Request;
import com.example.cmput301w19t15.Activity.RequestedBookList;

import java.util.ArrayList;
import java.util.UUID;

/**
 * This Book class has book object's attributes.
 *
 * @see AddBookInfo
 * @see BookAdapter
 * @see BookInfo
 * @see BookMethodCalls
 * @see BorrowerBookView
 * @see DetailedBookInfo
 * @see FindBooks
 * @see MyBooks
 * @see Request
 * @see RequestedBookList
 * @version 1.0
 */
public class Book {
    private String title = "";//
    private String author = "";//
    private String ISBN = "";//
    private String photo = "";//
    private String ownerEmail = "";//
    private String ownerID = "";//
    private String BookID = "";//
    private String rating = "";
    private long returnDate = System.currentTimeMillis();

    private int ratingTotal = 0;
    private int ratingCount = 0;

    private String status = "Available";//

    private String borrowerID = "";


    /**
     * @param title  String - book title
     * @param author  String -  book author
     * @param ISBN String -  book ISBN
     * @param photo String a photo that user can add manually (base-64)
     * @param ownerEmail String - Email address for owner
     * @param ownerID String - owner's unique id when he/she register
    //* @param "bookID String - book's unique id, a UUID, every book object will receive a different unique id, differ from ISBN
    //* @param "returnDate long - the time book will be returned, so far it's set up just for test purpose
     *
    //* @param "status Status - a user defined type of variable that has 4 value(Avaliable, Requested Accepted and Borrowed)
    //* @param "borrowerID String - ID of borrower, similar to ownerID
     *
     */
    public Book(String title, String author, String ISBN, String photo, String ownerEmail, String ownerID, String rating, int ratingCount, int ratingTotal) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.photo = photo;
        this.ownerEmail = ownerEmail;
        this.ownerID = ownerID;
        this.rating = rating;
        //this.status = status;
        if(this.BookID == null || this.BookID.isEmpty())
            this.BookID = UUID.randomUUID().toString();

        this.ratingCount = ratingCount;
        this.ratingTotal = ratingTotal;
    }

    public Book(long returnDate, String borrowerID, String author, String isbn, int ratingTotal, String ownerID, int ratingCount, String title, String bookID, String ownerEmail, String status) {
        this.returnDate = returnDate;
        this.borrowerID = borrowerID;
        this.author = author;
        this.ISBN = isbn;
        this.ratingTotal = ratingTotal;
        this.ownerID = ownerID;
        this.ratingCount = ratingCount;
        this.title = title;
        this.BookID = bookID;
        this.ownerEmail = ownerEmail;
        this.status = status;
    }


    public Book(){}
    public Book(Book book) {
        this.title = book.title;
        this.author = book.author;
        this.ISBN = book.ISBN;
        this.photo = book.photo;
        this.ownerEmail = book.ownerEmail;
        this.ownerID = book.ownerID;
        this.status = book.status;
        this.BookID = book.BookID;
        this.rating = book.rating;
        this.ratingCount = book.ratingCount;
        this.ratingTotal = book.ratingTotal;
        this.returnDate = book.returnDate;
        this.borrowerID = book.borrowerID;
        //this.rating = book.rating;
    }

    public Book(FindBooks findBooks, ArrayList<Book> listOfBooks){}
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
        this.status = status;
    }
    public String getStatus(){
        return this.status;
    }
    public void setBorrowerID(String borrowerID){
        this.borrowerID = borrowerID;
    }
    public String getBorrowerID(){
        return borrowerID;
    }
    public String getRating() { return rating; }
    public void setRating(String rating) {this.rating = rating; }

    public int getRatingTotal() { return ratingTotal;}
    public int getRatingCount() { return ratingCount; }
    public void setRatingTotal(int ratingTotal) { this.ratingTotal = ratingTotal;}
    public void setRatingCount(int ratingCount) { this.ratingCount = ratingCount; }
    public void addRating(int rating){ this.ratingTotal += rating; this.ratingCount += 1;}
    public void removeRating(int rating){ this.ratingTotal -= rating; this.ratingCount -= 1;}


}