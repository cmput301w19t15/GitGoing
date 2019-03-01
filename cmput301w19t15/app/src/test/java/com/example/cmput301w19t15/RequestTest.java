package com.example.cmput301w19t15;

import org.junit.Test;
import static org.junit.Assert.*;

public class RequestTest {
    /***
     * default requestStatus will return false
     * if RequestStatus works then we can proceed testing the next 2 methods
     */
    @Test
    public void RequestStatus(){
        User owner = new User("user123", "first last", "example@email.com", "111-111-1111");
        User borrower = new User("userb", "first last", "example@email.com", "111-111-1111");
        Book book = new Book("test title","test author",123);
        Request request = new Request(owner,borrower,book);
        assertEquals(request.requestStatus(),false);
    }

    @Test
    public void AcceptRequest(){
        User owner = new User("user123", "first last", "example@email.com", "111-111-1111");
        User borrower = new User("userb", "first last", "example@email.com", "111-111-1111");
        Book book = new Book("test title","test author",123);
        Request request = new Request(owner,borrower,book);
        request.acceptRequest();
        assertEquals(request.requestStatus(),true);
    }

    @Test
    public void DenyRequest(){
        User owner = new User("user123", "first last", "example@email.com", "111-111-1111");
        User borrower = new User("userb", "first last", "example@email.com", "111-111-1111");
        Book book = new Book("test title","test author",123);
        Request request = new Request(owner,borrower,book);
        request.denyRequest();
        assertEquals(request.requestStatus(),false);
    }
}
