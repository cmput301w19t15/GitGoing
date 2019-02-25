package com.example.cmput301w19t15;

import java.util.Date;

public class Exchange {
    private Location location;
    private Date date;
    private Owner owner;
    private Borrower borrower;
    private Book book;

    public Exchange(Owner owner, Borrower borrower, Book book){
        this.owner = owner;
        this.borrower = borrower;
        this.book = book;
    }
    public void setLocation(Location newlocation){ location = newlocation;}
    public Location getLocation(){return location;}
    public void setDate(Date newdate){date = newdate;}
    public Owner getOwnwer(){return owner;}
    public Borrower getBorrower(){return borrower;}
    public Book getBook(){return book;}

}
