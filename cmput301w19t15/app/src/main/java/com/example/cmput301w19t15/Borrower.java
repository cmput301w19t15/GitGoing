package com.example.cmput301w19t15;

import java.util.ArrayList;

public class Borrower extends User {

    private ArrayList<Book> requestedBooks = new ArrayList<Book>();
    private ArrayList<Book> borrowedBooks = new ArrayList<Book>();

    public Borrower(String username, String name, String email, String phone) {
        super(username, name, email, phone);

    }
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

}
