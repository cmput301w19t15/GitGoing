package com.example.cmput301w19t15;
import junit.framework.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class BookTest {

    @Test
    public void testSetAuthor() {
        Book x = new Book(123);
        x.setAuthor("Gradle");
        assertEquals(x.getAuthor(),"Gradle");
        x.setAuthor("Example2");
        assertEquals(x.getAuthor(),"Example2");
    }
    @Test
    public void testSetTitle() {
        Book x = new Book(123);
        x.setTitle("Gradle");
        assertEquals(x.getTitle(),"Gradle");
        x.setTitle("Example2");
        assertEquals(x.getTitle(),"Example2");
    }
    @Test
    public void testSetISBN() {
        Book x = new Book(123);
        x.setISBN(345);
        assertEquals(x.getISBN(),(Integer) 345);
        x.setISBN(567);
        assertEquals(x.getISBN(),(Integer) 567);
    }
    @Test
    // Don't understand how photos work yet
    public void testSetPhoto() {
        Book x = new Book(123);
        x.setPhoto();
        assertEquals(x.getISBN(),(Integer) 345);
        x.setISBN(567);
        assertEquals(x.getISBN(),(Integer) 567);
    }
    public void testBook() {
        Book x = new Book(123);
        assertEquals(x.getISBN(),(Integer) 123);
        Book y = new Book(345);
        assertEquals(y.getISBN(),(Integer) 345);
    }

}
