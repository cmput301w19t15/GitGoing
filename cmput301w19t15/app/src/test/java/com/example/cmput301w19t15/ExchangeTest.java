package com.example.cmput301w19t15;

import org.junit.Test;
import java.util.Date;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ExchangeTest {
    @Test
    public void SetLocation(){
        User owner = new User("user123", "first last", "example@email.com", "111-111-1111", "exampleID");
        User borrower = new User("user123", "first last", "example@email.com", "111-111-1111", "exampleID");
        Book book = new Book("testTitle","testAuthor","123","testPhoto","testOwnerEmail","testOwnerID");
        Exchange exchange = new Exchange(owner, borrower, book);
        double longitude = 25;
        double latitude = 30;
        Location location = new Location(longitude,latitude);
        exchange.setLocation(location);
        assertEquals(location,exchange.getLocation());
    }
    @Test
    public void SetDate(){
        User owner = new User("user123", "first last", "example@email.com", "111-111-1111", "exampleID");
        User borrower = new User("user123", "first last", "example@email.com", "111-111-1111", "exampleID");
        Book book = new Book("testTitle","testAuthor","123","testPhoto","testOwnerEmail","testOwnerID");
        Exchange exchange = new Exchange(owner, borrower, book);
        Date date = new Date();
        exchange.setDate(date);
        assertEquals(date,exchange.getDate());
    }

}
