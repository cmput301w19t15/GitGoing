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
        Owner owner = new Owner("user123", "first last", "example@email.com", "111-111-1111");
        Borrower borrower = new Borrower("userb", "first last", "example@email.com", "111-111-1111");
        Book book = new Book("test title","test author",123);
        Request request = new Request(owner,borrower,book);
        assertEquals(request.requestStatus(),false);
    }

    @Test
    public void AcceptRequest(){
        Owner owner = new Owner("user123", "first last", "example@email.com", "111-111-1111");
        Borrower borrower = new Borrower("userb", "first last", "example@email.com", "111-111-1111");
        Book book = new Book("test title","test author",123);
        Request request = new Request(owner,borrower,book);
        request.acceptRequest();
        assertEquals(request.requestStatus(),true);
    }

    @Test
    public void DenyRequest(){
        Owner owner = new Owner("user123", "first last", "example@email.com", "111-111-1111");
        Borrower borrower = new Borrower("userb", "first last", "example@email.com", "111-111-1111");
        Book book = new Book("test title","test author",123);
        Request request = new Request(owner,borrower,book);
        request.denyRequest();
        assertEquals(request.requestStatus(),false);
    }
}
