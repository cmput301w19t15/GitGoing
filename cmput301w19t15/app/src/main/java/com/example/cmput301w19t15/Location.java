package com.example.cmput301w19t15;

import android.media.Image;
import java.util.ArrayList;
public class Location {

    private double longitude;
    private double latitude;
    private double geoCode[] = new double[2];
    public Location(double longitude, double latitude) {
        geoCode[0] = longitude;
        geoCode[1] = latitude;
    }

    public void setLocation(double longitude, double latitude) {
        geoCode[0] = longitude;
        geoCode[1] = latitude;
    }
    public double[] getLocation() {
        return geoCode;
    }
}
