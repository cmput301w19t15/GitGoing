package com.example.cmput301w19t15;
import android.media.Image;

import junit.framework.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * test location changes
 */
public class LocationTest {
    /**
     * test to see if the location class actually works
     */
    @Test
    public void testLocation() {
        Location x = new Location(123,-120);
        double array[] = x.getLocation();
        assertEquals(array[0], 123, 0.1);
        assertEquals(array[1],-120, 0.1);
        Location y = new Location(-178.12345,0);
        double array2[] = y.getLocation();
        assertEquals(array2[0], -178.12345, 0.1);
        assertEquals(array2[1],0, 0.1);
    }

    /**
     * test editing the location class
     */
    @Test
    public void testSetLocation() {
        Location x = new Location(123,-120);
        x.setLocation(12.9076,-180);
        double array[] = x.getLocation();
        assertEquals(array[0],12.9076, 0.1);
        assertEquals(array[1],-180, 0.1);
        x.setLocation(180,0);
        double array2[] = x.getLocation();
        assertEquals(array2[0],180, 0.1);
        assertEquals(array2[1],0, 0.1);
    }
}
