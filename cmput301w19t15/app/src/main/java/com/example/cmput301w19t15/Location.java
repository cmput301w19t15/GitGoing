package com.example.cmput301w19t15;

import android.media.Image;

public class Location {

    private double longitude;
    private double latitude;

    public Book(double longitude, double latitude) {
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getLongitude() {
        return this.longitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLatitude() {
        return this.latitude;

    }
}
