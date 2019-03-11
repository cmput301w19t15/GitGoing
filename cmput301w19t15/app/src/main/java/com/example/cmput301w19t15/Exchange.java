package com.example.cmput301w19t15;

import java.util.Date;

public class Exchange {
    private Location location;
    private Date date;
    private User owner;
    private User borrower;
    private Book book;

    /**
     * Setup an exchange/borrow book from the owner
     * @param owner - owner of the book
     * @param borrower - person requesting the book
     * @param book - the book that is being borrowed
     */
    public Exchange(User owner, User borrower, Book book){
        this.owner = owner;
        this.borrower = borrower;
        this.book = book;
    }

    /**
     * will set the location of the place both the owner and borrower will meet
     * @param newlocation - location where they will meet
     */
    public void setLocation(Location newlocation){ location = newlocation;}

    /**
     * return the location of where both will meet
     * @return location of meeting place
     */
    public Location getLocation(){return location;}

    /**
     * date the exchange will meet
     * @param newdate - date to meet
     */
    public void setDate(Date newdate){date = newdate;}

    /**
     * return the owner of the book
     * @return - owner of the book
     */
    public User getOwnwer(){return owner;}

    /**
     * return the borrower of the book
     * @return - borrower of the book
     */
    public User getBorrower(){return borrower;}

    /**
     * return the book that is being borrowed
     * @return - book that is borrowed
     */
    public Book getBook(){return book;}

    /**
     * return the date when they will meet
     * @return - day of meet
     */
    public Date getDate(){return date;}

}
