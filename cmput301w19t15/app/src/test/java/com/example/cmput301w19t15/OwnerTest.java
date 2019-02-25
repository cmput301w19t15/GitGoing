package com.example.cmput301w19t15;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class OwnerTest {
    @Test
    public void testOwner() {
        Owner x = new Owner("user123", "first last", "example@email.com", "111-111-1111");
        assertEquals(x.getUsername(),"user123");
        assertEquals(x.getName(),"first last");
        assertEquals(x.getEmail(),"example@email.com");
        assertEquals(x.getPhone(),"111-111-1111");
    }

    @Test
    public void testAddMyBook(){
        Owner x = new Owner("user123", "first last", "example@email.com", "111-111-1111");
        Book b = new Book(123);
        x.addMyBook(b);
        assertTrue(x.hasBook(b));

    }
    @Test
    public void testDeleteMyBook(){
        Owner x = new Owner("user123", "first last", "example@email.com", "111-111-1111");
        Book b = new Book(123);
        x.addMyBook(b);
        x.deleteMyBook(b);
        assertFalse(x.hasBook(b));
    }
    @Test
    public void testAddAvailableBook(){
        Owner x = new Owner("user123", "first last", "example@email.com", "111-111-1111");
        Book b = new Book(123);
        x.addAvailableBook(b);
        assertTrue(x.bookAvailable(b));

    }
    @Test
    public void testDeleteAvailableBook(){
        Owner x = new Owner("user123", "first last", "example@email.com", "111-111-1111");
        Book b = new Book(123);
        x.addAvailableBook(b);
        x.deleteAvailableBook(b);
        assertFalse(x.bookAvailable(b));
    }
    @Test
    public void testAddPendingRequest(){
        Owner x = new Owner("user123", "first last", "example@email.com", "111-111-1111");
        Request b = new Request();
        x.addPendingRequest(b);
        assertTrue(x.requestPending(b));
    }
    @Test
    public void testDeletePendingRequest(){
        Owner x = new Owner("user123", "first last", "example@email.com", "111-111-1111");
        Request b = new Request();
        x.addPendingRequest(b);
        x.deletePendingRequest(b);
        assertFalse(x.requestPending(b));

    }
    @Test
    public void testAddAcceptedRequest(){
        Owner x = new Owner("user123", "first last", "example@email.com", "111-111-1111");
        Request b = new Request();
        x.addAcceptedRequest(b);
        assertTrue(x.requestAccepted(b));

    }
    @Test
    public void testDeleteAcceptedRequest(){
        Owner x = new Owner("user123", "first last", "example@email.com", "111-111-1111");
        Request b = new Request();
        x.addAcceptedRequest(b);
        x.deleteAcceptedRequest(b);
        assertFalse(x.requestAccepted(b));

    }

}
