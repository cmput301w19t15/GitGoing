/*
 * Class Name: Request
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

package com.example.cmput301w19t15.InProgress;
//:)


import com.example.cmput301w19t15.Objects.Book;
import com.example.cmput301w19t15.Objects.User;
import com.example.cmput301w19t15.Activity.RequestedBookList;

import java.util.Date;
/**
 * Represents a request for book
 * @author Thomas
 * @version 1.0
 * @see RequestedBookList
 * @since 1.0
 */
public class Request {
    private User owner;
    private User borrower;
    private Book book;
    private boolean accept = false;
    private Date date;


    /**
     * Instantiates a new Request.
     *
     * @param owner    the owner
     * @param borrower the borrower
     * @param book     the book
     */
    public Request(User owner, User borrower, Book book){
        this.owner = owner;
        this.borrower = borrower;
        this.book = book;
    }

    /**
     * Accept book request.
     */
    public void acceptRequest(){
        this.accept = true;
        book.setStatus("accepted");
    }

    /**
     * Deny book request.
     */
    public void denyRequest(){
        this.accept = false;
        book.setStatus("available");
    }

    /**
     * Request book status as boolean.
     *
     * @return the boolean
     */
    public boolean requestStatus(){
        return this.accept;
    }
}
