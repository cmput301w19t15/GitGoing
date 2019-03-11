package com.example.cmput301w19t15;

import android.media.Image;
import java.util.ArrayList;
public class Location {

    private double longitude;
    private double latitude;
    private double geoCode[] = new double[2];

    /**
     * location where the two users will meet
     * @param longitude
     * @param latitude
     */
    public Location(double longitude, double latitude) {
        geoCode[0] = longitude;
        geoCode[1] = latitude;
    }

    /**
     * update the location of the meeting place
     * @param longitude
     * @param latitude
     */
    public void setLocation(double longitude, double latitude) {
        geoCode[0] = longitude;
        geoCode[1] = latitude;
    }

    /**
     * return the location of meeting place
     * @return
     */
    public double[] getLocation() {
        return geoCode;
    }
}
