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
        User owner = new User("user123", "first last", "example@email.com", "111-111-1111", "exampleID");
        User borrower = new User("user123", "first last", "example@email.com", "111-111-1111", "exampleID");
        Book book = new Book("testTitle","testAuthor","123","testPhoto","testOwnerEmail","testOwnerID");
        Request request = new Request(owner,borrower,book);
        assertEquals(request.requestStatus(),false);
    }

    @Test
    public void AcceptRequest(){
        User owner = new User("user123", "first last", "example@email.com", "111-111-1111", "exampleID");
        User borrower = new User("user123", "first last", "example@email.com", "111-111-1111", "exampleID");
        Book book = new Book("testTitle","testAuthor","123","testPhoto","testOwnerEmail","testOwnerID");
        Request request = new Request(owner,borrower,book);
        request.acceptRequest();
        assertEquals(request.requestStatus(),true);
    }

    @Test
    public void DenyRequest(){
        User owner = new User("user123", "first last", "example@email.com", "111-111-1111", "exampleID");
        User borrower = new User("user123", "first last", "example@email.com", "111-111-1111", "exampleID");
        Book book = new Book("testTitle","testAuthor","123","testPhoto","testOwnerEmail","testOwnerID");
        Request request = new Request(owner,borrower,book);
        request.denyRequest();
        assertEquals(request.requestStatus(),false);
    }
}
