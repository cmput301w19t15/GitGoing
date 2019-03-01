package com.example.cmput301w19t15;

import java.util.ArrayList;

public class Owner extends User {

    private ArrayList<Book> myBooks = new ArrayList<Book>();
    private ArrayList<Book> availableBooks = new ArrayList<Book>();
    private ArrayList<Request> pendingRequests = new ArrayList<Request>();
    private ArrayList<Request> acceptedRequests = new ArrayList<Request>();

    public Owner(String username, String name, String email, String phone) {
        super(username, name, email, phone);

    }
    public void addMyBook(Book book){
        myBooks.add(book);
    }
    public Boolean hasBook(Book book){
        return myBooks.contains(book);
    }
    public void deleteMyBook(Book book){
        myBooks.remove(book);
    }
    public void addAvailableBook(Book book){
        availableBooks.add(book);
    }
    public Boolean bookAvailable(Book book){
        return availableBooks.contains(book);
    }
    public void deleteAvailableBook(Book book){
        availableBooks.remove(book);
    }
    public void addPendingRequest(Request request){
        pendingRequests.add(request);
    }
    public boolean requestPending(Request request){
        return pendingRequests.contains(request);
    }
    public void deletePendingRequest(Request request){
        pendingRequests.remove(request);
    }
    public void addAcceptedRequest(Request request){
        acceptedRequests.add(request);
    }
    public boolean requestAccepted(Request request){
        return acceptedRequests.contains(request);
    }
    public void deleteAcceptedRequest(Request request){
        acceptedRequests.remove(request);
    }


}
