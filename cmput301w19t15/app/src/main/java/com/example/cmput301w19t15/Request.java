package com.example.cmput301w19t15;

import java.util.Date;

public class Request {
    private Owner owner;
    private Borrower borrower;
    private Book book;
    private boolean accept = false;
    private Date date;

    public Request(Owner owner, Borrower borrower, Book book){
        this.owner = owner;
        this.borrower = borrower;
        this.book = book;
    }

    public void acceptRequest(){
        this.accept = true;
    }
    public void denyRequest(){
        this.accept = false;
    }
    public boolean requestStatus(){
        return this.accept;
    }
}
