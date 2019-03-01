package com.example.cmput301w19t15;

import java.util.Date;

public class Request {
    private User owner;
    private User borrower;
    private Book book;
    private boolean accept = false;
    private Date date;


    public Request(User owner, User borrower, Book book){
        this.owner = owner;
        this.borrower = borrower;
        this.book = book;
    }

    public void acceptRequest(){
        this.accept = true;
        book.setStatus("accepted");
    }
    public void denyRequest(){
        this.accept = false;
        book.setStatus("available");
    }
    public boolean requestStatus(){
        return this.accept;
    }
}
