package com.example.cmput301w19t15;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class UserTest {
    @Test
    public void testUser() {
        User x = new User("first last", "example@email.com", "111-111-1111", "exampleID");
        assertEquals(x.getName(), "first last");
        assertEquals(x.getEmail(), "example@email.com");
        assertEquals(x.getPhone(), "111-111-1111");
    }

    @Test
    public void testSetName() {
        User x = new User("first last", "example@email.com", "111-111-1111", "exampleID");
        x.setName("Gradle Build");
        assertEquals(x.getName(), "Gradle Build");
        x.setName("Example Name");
        assertEquals(x.getName(), "Example Name");
    }

    @Test
    public void testSetEmail() {
        User x = new User("first last", "example@email.com", "111-111-1111", "exampleID");
        x.setEmail("gradle@build.com");
        assertEquals(x.getEmail(), "gradle@build.com");
        x.setEmail("Uptown@funk.com");
        assertEquals(x.getEmail(), "Uptown@funk.com");
    }

    @Test
    public void testSetPhone() {
        User x = new User("first last", "example@email.com", "111-111-1111", "exampleID");
        x.setPhone("800-267-2001");
        assertEquals(x.getPhone(), "800-267-2001");
        x.setPhone("737-373-7373");
        assertEquals(x.getPhone(), "737-373-7373");
    }

    @Test
    // Don't understand how photos work yet
    public void testSetRating() {
        User x = new User("first last", "example@email.com", "111-111-1111", "exampleID");
        x.setRating(5f);
        assertEquals(x.getRating(), (Float) 5f);
        x.setRating(4f);
        assertEquals(x.getRating(), (Float) 4f);
    }
    ///Owner Tests//
/*
    @Test
    public void testAddToMyBooks() {
        User user = new User("first last", "example@email.com", "111-111-1111", "exampleID");
        Book b = new Book("testTitle", "testAuthor", "123", "testPhoto", "testOwnerEmail", "testOwnerID");
        user.addToMyBooks(b);

        ArrayList<Book> mbooklist = user.getMyBooks();
        assertTrue(mbooklist.contains(b));

    }

    @Test
    public void testRemoveMyBooks() {
        User user = new User("first last", "example@email.com", "111-111-1111", "exampleID");
        Book b = new Book("testTitle", "testAuthor", "123", "testPhoto", "testOwnerEmail", "testOwnerID");
        user.addToMyBooks(b);
        ArrayList<Book> mbooklist = user.getMyBooks();
        user.removeMyBooks(b);
        assertFalse(mbooklist.contains(b));
    }

    @Test
    public void testAddToRequestedBooks() {
        User owner = new User("first last", "example@email.com", "111-111-1111", "exampleID");
        User borrower = new User("first last", "example@email.com", "111-111-1111", "exampleID");
        Book b = new Book("testTitle", "testAuthor", "123", "testPhoto", "testOwnerEmail", "testOwnerID");
        Request request = new Request(owner, borrower, b);
        owner.addToRequestedBooks(b);
        ArrayList<Book> obooklist = owner.getRequestedBooks();
        assertTrue(obooklist.contains(b));
    }

    @Test
    public void testRemoveRequestedBooks() {
        User owner = new User("first last", "example@email.com", "111-111-1111", "exampleID");
        User borrower = new User("first last", "example@email.com", "111-111-1111", "exampleID");
        Book b = new Book("testTitle", "testAuthor", "123", "testPhoto", "testOwnerEmail", "testOwnerID");
        Request request = new Request(owner, borrower, b);
        owner.addToRequestedBooks(b);
        owner.removeRequestedBooks(b);
        ArrayList<Book> obooklist = owner.getRequestedBooks();
        assertFalse(obooklist.contains(b));

    }

    //Borrower Tests//
    @Test
    public void testMyRequestedBooks(){
        User user = new User("first last", "example@email.com", "111-111-1111", "exampleID");
        Book b = new Book("testTitle","testAuthor","123","testPhoto","testOwnerEmail","testOwnerID");
        user.addToMyRequestedBooks(b);
        ArrayList<Book> rbooklist = user.getMyRequestedBooks();
        assertTrue(rbooklist.contains(b));
    }
    @Test
    public void testRemoveMyRequestedBooks(){
        User user = new User("first last", "example@email.com", "111-111-1111", "exampleID");
        Book b = new Book("testTitle","testAuthor","123","testPhoto","testOwnerEmail","testOwnerID");
        user.addToMyRequestedBooks(b);
        user.removeMyRequestedBooks(b);
        ArrayList<Book> rbooklist = user.getMyRequestedBooks();
        assertFalse(rbooklist.contains(b));
    }
    @Test
    public void testMyRequestedBooksAccepted(){
        User user = new User("first last", "example@email.com", "111-111-1111", "exampleID");
        Book b = new Book("testTitle","testAuthor","123","testPhoto","testOwnerEmail","testOwnerID");
        user.addToMyRequestedBooksAccepted(b);
        ArrayList<Book> abooklist = user.getMyRequestedBooksAccepted();
        assertTrue(abooklist.contains(b));
    }

    @Test
    public void testAddToMyBorrowedBooks() {
        User user = new User("first last", "example@email.com", "111-111-1111", "exampleID");
        Book b = new Book("testTitle", "testAuthor", "123", "testPhoto", "testOwnerEmail", "testOwnerID");
        user.addToMyBorrowedBooks(b);
        ArrayList<Book> bbooklist = user.getBorrowedBooks();
        assertTrue(bbooklist.contains(b));
    }
    @Test
    public void testRemoveMyBorrowedBooks() {
        User user = new User("first last", "example@email.com", "111-111-1111", "exampleID");
        Book b = new Book("testTitle", "testAuthor", "123", "testPhoto", "testOwnerEmail", "testOwnerID");
        user.addToMyBorrowedBooks(b);
        user.removeMyBorrowedBooks(b);
        ArrayList<Book> bbooklist = user.getBorrowedBooks();
        assertFalse(bbooklist.contains(b));
    }
*/
}
