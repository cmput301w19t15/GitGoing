package com.example.cmput301w19t15;
import android.media.Image;

import junit.framework.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class BookTest {
    @Test
    public void testBook() {

        Book x = new Book("testTitle","testAuthor","123","testPhoto","testOwnerEmail","testOwnerID");
        assertEquals(x.getISBN(), "123");
        Book y = new Book("testTitle","testAuthor","345","testPhoto","testOwnerEmail","testOwnerID");
        assertEquals(y.getISBN(), "345");
    }
    @Test
    public void testSetAuthor() {
        Book x = new Book("testTitle","testAuthor","123","testPhoto","testOwnerEmail","testOwnerID");
        x.setAuthor("Gradle");
        assertEquals(x.getAuthor(),"Gradle");
        x.setAuthor("Example2");
        assertEquals(x.getAuthor(),"Example2");
    }
    @Test
    public void testSetTitle() {
        Book x = new Book("testTitle","testAuthor","123","testPhoto","testOwnerEmail","testOwnerID");
        x.setTitle("Gradle");
        assertEquals(x.getTitle(),"Gradle");
        x.setTitle("Example2");
        assertEquals(x.getTitle(),"Example2");
    }
    @Test
    public void testSetISBN() {
        Book x = new Book("testTitle","testAuthor","123","testPhoto","testOwnerEmail","testOwnerID");
        x.setISBN("345");
        assertEquals(x.getISBN(), "345");
        x.setISBN("567");
        assertEquals(x.getISBN(), "567");
    }
    @Test
    public void testSetStatus() {
        Book x = new Book("testTitle","testAuthor","123","testPhoto","testOwnerEmail","testOwnerID");
        x.setStatus("borrowed");
        assertEquals(x.getStatus(),"Borrowed");
        x.setStatus("requested");
        assertEquals(x.getStatus(),"Requested");
        x.setStatus("available");
        assertEquals(x.getStatus(),"Available");
    }
    @Test
    public void testSetPhoto() {
        Book x = new Book("testTitle","testAuthor","123","testPhoto","testOwnerEmail","testOwnerID");
        String image = "https://s3.amazonaws.com/spoonflower/public/design_thumbnails/0464/6961/rr12x12_Dirt_Block_2015_shop_thumb.png";
        x.setPhoto(image);
        assertEquals(x.getPhoto(),image);
    }


}
