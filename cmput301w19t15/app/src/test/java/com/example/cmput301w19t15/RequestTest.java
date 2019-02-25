package com.example.cmput301w19t15;

import junit.framework.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class RequestTest {
    /***
     * default requestStatus will return false
     * if RequestStatus works then we can proceed testing the next 2 methods
     */
    @Test
    public void RequestStatus(){
        Owner owner = new Owner();
        Borrower borrower = new Borrower();
        Book book = new Book(1);
        Request request = new Request(owner,borrower,book);
        assertEquals(request.requestStatus(),false);
    }

    @Test
    public void AcceptRequest(){
        Owner owner = new Owner();
        Borrower borrower = new Borrower();
        Book book = new Book(1);
        Request request = new Request(owner,borrower,book);
        request.acceptRequest();
        assertEquals(request.requestStatus(),true);
    }

    @Test
    public void DenyRequest(){
        Owner owner = new Owner();
        Borrower borrower = new Borrower();
        Book book = new Book(1);
        Request request = new Request(owner,borrower,book);
        request.denyRequest();
        assertEquals(request.requestStatus(),false);
    }
}
