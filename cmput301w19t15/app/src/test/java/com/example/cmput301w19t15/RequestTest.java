package com.example.cmput301w19t15;

import com.example.cmput301w19t15.InProgress.Request;
import com.example.cmput301w19t15.Objects.Book;
import com.example.cmput301w19t15.Objects.User;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * test requesting books
 */
public class RequestTest {
    /***
     * default requestStatus will return false
     * if RequestStatus works then we can proceed testing the next 2 methods
     */
    @Test
    public void RequestStatus(){
        User owner = new User("first last", "example@email.com", "111-111-1111", "exampleID");
        User borrower = new User("first last", "example@email.com", "111-111-1111", "exampleID");
        Book book = new Book("testTitle","testAuthor","123","testPhoto","testOwnerEmail","testOwnerID");
        Request request = new Request(owner,borrower,book);
        assertEquals(request.requestStatus(),false);
    }

    @Test
    public void AcceptRequest(){
        User owner = new User("first last", "example@email.com", "111-111-1111", "exampleID");
        User borrower = new User("first last", "example@email.com", "111-111-1111", "exampleID");
        Book book = new Book("testTitle","testAuthor","123","testPhoto","testOwnerEmail","testOwnerID");
        Request request = new Request(owner,borrower,book);
        request.acceptRequest();
        assertEquals(request.requestStatus(),true);
    }

    @Test
    public void DenyRequest(){
        User owner = new User("first last", "example@email.com", "111-111-1111", "exampleID");
        User borrower = new User("first last", "example@email.com", "111-111-1111", "exampleID");
        Book book = new Book("testTitle","testAuthor","123","testPhoto","testOwnerEmail","testOwnerID");
        Request request = new Request(owner,borrower,book);
        request.denyRequest();
        assertEquals(request.requestStatus(),false);
    }
}
