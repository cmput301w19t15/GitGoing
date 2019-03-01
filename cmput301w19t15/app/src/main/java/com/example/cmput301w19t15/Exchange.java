package com.example.cmput301w19t15;

import java.util.Date;

public class Exchange {
    private Location location;
    private Date date;
    private User owner;
    private User borrower;
    private Book book;

    public Exchange(User owner, User borrower, Book book){
        this.owner = owner;
        this.borrower = borrower;
        this.book = book;
    }
    public void setLocation(Location newlocation){ location = newlocation;}
    public Location getLocation(){return location;}
    public void setDate(Date newdate){date = newdate;}
    public User getOwnwer(){return owner;}
    public User getBorrower(){return borrower;}
    public Book getBook(){return book;}
    public Date getDate(){return date;}

}
