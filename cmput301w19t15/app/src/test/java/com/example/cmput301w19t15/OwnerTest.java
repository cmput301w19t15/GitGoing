package com.example.cmput301w19t15;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class OwnerTest {
    @Test
    public void testOwner() {
        Owner owner = new Owner("user123", "first last", "example@email.com", "111-111-1111");
        assertEquals(owner.getUsername(),"user123");
        assertEquals(owner.getName(),"first last");
        assertEquals(owner.getEmail(),"example@email.com");
        assertEquals(owner.getPhone(),"111-111-1111");
    }

    @Test
    public void testAddMyBook(){
        Owner owner = new Owner("user123", "first last", "example@email.com", "111-111-1111");
        Book b = new Book(123);
        owner.addMyBook(b);
        assertTrue(owner.hasBook(b));

    }
    @Test
    public void testDeleteMyBook(){
        Owner owner = new Owner("user123", "first last", "example@email.com", "111-111-1111");
        Book b = new Book(123);
        owner.addMyBook(b);
        owner.deleteMyBook(b);
        assertFalse(owner.hasBook(b));
    }
    @Test
    public void testAddAvailableBook(){
        Owner owner = new Owner("user123", "first last", "example@email.com", "111-111-1111");
        Book b = new Book(123);
        owner.addAvailableBook(b);
        assertTrue(owner.bookAvailable(b));

    }
    @Test
    public void testDeleteAvailableBook(){
        Owner owner = new Owner("user123", "first last", "example@email.com", "111-111-1111");
        Book b = new Book(123);
        owner.addAvailableBook(b);
        owner.deleteAvailableBook(b);
        assertFalse(owner.bookAvailable(b));
    }
    @Test
    public void testAddPendingRequest(){
        Owner owner = new Owner("user123", "first last", "example@email.com", "111-111-1111");
        Borrower borrower = new Borrower("user123", "first last", "example@email.com", "111-111-1111");
        Book b = new Book(123);
        Request request = new Request(owner,borrower,b);
        owner.addPendingRequest(request);
        assertTrue(owner.requestPending(request));
    }
    @Test
    public void testDeletePendingRequest(){
        Owner owner = new Owner("user123", "first last", "example@email.com", "111-111-1111");
        Borrower borrower = new Borrower("user123", "first last", "example@email.com", "111-111-1111");
        Book b = new Book(123);
        Request request = new Request(owner,borrower,b);
        owner.addPendingRequest(request);
        owner.deletePendingRequest(request);
        assertFalse(owner.requestPending(request));

    }
    @Test
    public void testAddAcceptedRequest(){
        Owner owner = new Owner("user123", "first last", "example@email.com", "111-111-1111");
        Borrower borrower = new Borrower("user123", "first last", "example@email.com", "111-111-1111");
        Book b = new Book(123);
        Request request = new Request(owner,borrower,b);
        owner.addAcceptedRequest(request);
        assertTrue(owner.requestAccepted(request));

    }
    @Test
    public void testDeleteAcceptedRequest(){
        Owner owner = new Owner("user123", "first last", "example@email.com", "111-111-1111");
        Borrower borrower = new Borrower("user123", "first last", "example@email.com", "111-111-1111");
        Book b = new Book(123);
        Request request = new Request(owner,borrower,b);
        owner.addAcceptedRequest(request);
        owner.deleteAcceptedRequest(request);
        assertFalse(owner.requestAccepted(request));

    }

}
