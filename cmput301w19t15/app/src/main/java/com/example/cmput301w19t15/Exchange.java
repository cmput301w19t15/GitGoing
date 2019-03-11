/*
 * Class Name: Exchange
 *
 * Version: 1.0
 *
 * Copyright 2019 TEAM GITGOING
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.example.cmput301w19t15;

/**
 * Represents a book exchange
 * @author Yourui, Eisha, Breanne, Anjesh
 * @version 1.0
 * @see com.example.cmput301w19t15.User
 * @see com.example.cmput301w19t15.Book
 * @see com.example.cmput301w19t15.Location
 * @since 1.0
 */

import java.util.Date;

public class Exchange {
    private Location location;
    private Date date;
    private User owner;
    private User borrower;
    private Book book;

    /**
     * @deprecated will not be using current state of exchange
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
