package com.example.cmput301w19t15;
import android.media.Image;

import junit.framework.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class BookTest {
    @Test
    public void testBook() {

        Book x = new Book("testTitle","testAuthor",123);
        assertEquals(x.getISBN(),(Integer) 123);
        Book y = new Book(345);
        assertEquals(y.getISBN(),(Integer) 345);
    }
    @Test
    public void testSetAuthor() {
        Book x = new Book("testTitle","testAuthor",123);
        x.setAuthor("Gradle");
        assertEquals(x.getAuthor(),"Gradle");
        x.setAuthor("Example2");
        assertEquals(x.getAuthor(),"Example2");
    }
    @Test
    public void testSetTitle() {
        Book x = new Book("testTitle","testAuthor",123);
        x.setTitle("Gradle");
        assertEquals(x.getTitle(),"Gradle");
        x.setTitle("Example2");
        assertEquals(x.getTitle(),"Example2");
    }
    @Test
    public void testSetISBN() {
        Book x = new Book("testTitle","testAuthor",123);
        x.setISBN(345);
        assertEquals(x.getISBN(),(Integer) 345);
        x.setISBN(567);
        assertEquals(x.getISBN(),(Integer) 567);
    }
    @Test
    public void testSetStatus() {
        Book x = new Book("testTitle","testAuthor",123);
        x.setStatus("Borrowed");
        assertEquals(x.getStatus(),"Borrowed");
        x.setTitle("Available");
        assertEquals(x.getStatus(),"Available");
    }
    @Test
    public void testSetPhoto() {
        Book x = new Book("testTitle","testAuthor",123);
        String image = "https://s3.amazonaws.com/spoonflower/public/design_thumbnails/0464/6961/rr12x12_Dirt_Block_2015_shop_thumb.png";
        x.setPhoto(image);
        assertEquals(x.getPhoto(),image);
    }


}
