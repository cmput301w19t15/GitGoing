package com.example.cmput301w19t15;
import android.media.Image;

import junit.framework.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class LocationTest {
    @Test
    public void testLocation() {
        Location x = new Location(123,-120);
        assertEquals(x.getLongitude(), 123);
        assertEquals(x.getLatitide(),-120);
        Location x = new Location(-178.12345,0);
        assertEquals(x.getLongitude(), -178.12345);
        assertEquals(x.getLatitide()),0);
    }
    @Test
    public void testSetLongitude() {
        Location x = new Location(123,-120);
        x.setLongitude(12.9076)
        assertEquals(x.getLogitude(),12.9076);
        x.setLongitude(-180)
        assertEquals(x.getLogitude(),-180);
    }
    @Test
    public void testSetLatitude() {
        Location x = new Location(123,-120);
        x.setLatitude(180)
        assertEquals(x.getLatitude(),180);
        x.setLongitude(-156.4734)
        assertEquals(x.getLogitude(),-156.4734);
    }
}
