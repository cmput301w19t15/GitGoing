package com.example.cmput301w19t15;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class BorrowerTest {
    @Test
    public void testBorrower(){
        User x = new User("user123", "first last", "example@email.com", "111-111-1111");
        assertEquals(x.getUsername(),"user123");
        assertEquals(x.getName(),"first last");
        assertEquals(x.getEmail(),"example@email.com");
        assertEquals(x.getPhone(),"111-111-1111");
    }
    @Test
    public void testRequestBook(){
        User x = new User("user123", "first last", "example@email.com", "111-111-1111");
        Book b = new Book("a","a",123);
        x.requestBook(b);
        assertTrue(x.requested(b));
    }
    @Test
    public void testAddAcceptedRequest(){
        User x = new User("user123", "first last", "example@email.com", "111-111-1111");
        Book b = new Book("a","a",123);
        x.addAcceptedRequest(b);
        assertTrue(x.borrowed(b));
    }
    @Test
    public void testDeleteRequest(){
        User x = new User("user123", "first last", "example@email.com", "111-111-1111");
        Book b = new Book("a","a",123);
        x.requestBook(b);
        x.deleteRequest(b);
        assertFalse(x.requested(b));
    }
    @Test
    public void testReturnBook(){
        User x = new User("user123", "first last", "example@email.com", "111-111-1111");
        Book b = new Book("a","a",123);
        x.addAcceptedRequest(b);
        x.returnBook(b);
        assertFalse(x.borrowed(b));

    }
}
