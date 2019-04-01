package com.example.cmput301w19t15.Objects;
//:)
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.UUID;

/**
 * Represents a notification
 */
public class Notification implements Serializable {

    private String type;  // requested, accepted
    private String bookID;
    private String title;
    private String notifyFromID;
    private String notifyFromEmail;
    private String notifyToID;
    private String notifyToEmail;
    private String ISBN;
    private String photo;
    private String notifID;
    private Boolean read;

    private Boolean OwnerScanned;
    private LatLng latLng;
    private String latitude;
    private String longitude;

    private String ownerScanned;


    /**
     * Instantiates a new Notification.
     *
     * @param type            the type
     * @param bookID          the book id
     * @param title           the title
     * @param notifyFromID    the notify from id
     * @param notifyFromEmail the notify from email
     * @param notifyToID      the notify to id
     * @param notifyToEmail   the notify to email
     * @param ISBN            the isbn
     * @param photo           the photo
     * @param read            the read
     */
    public Notification(String type, String bookID, String title, String notifyFromID, String notifyFromEmail,
                        String notifyToID, String notifyToEmail, String ISBN, String photo, Boolean read) {
        this.type = type;
        this.bookID = bookID;
        this.title = title;
        this.notifyFromID = notifyFromID;
        this.notifyFromEmail = notifyFromEmail;
        this.notifyToID = notifyToID;
        this.notifyToEmail = notifyToEmail;
        this.ISBN = ISBN;
        this.photo = photo;
        if(this.notifID == null || this.notifID.isEmpty()){
            this.notifID = UUID.randomUUID().toString();}
        this.read = read;
        this.ownerScanned = "false";
    }

    /**
     * Instantiates a new Notification.
     */
    public Notification() {}

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType (String type) {
        this.type = type;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets book id.
     *
     * @param bookID the book id
     */
    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    /**
     * Gets book id.
     *
     * @return the book id
     */
    public String getBookID() {
        return bookID;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets notify from id.
     *
     * @param notifyFromID the notify from id
     */
    public void setNotifyFromID(String notifyFromID) {
        this.notifyFromID = notifyFromID;
    }

    /**
     * Gets notify from id.
     *
     * @return the notify from id
     */
    public String getNotifyFromID() {
        return notifyFromID;
    }

    /**
     * Sets notify from email.
     *
     * @param notifyFromName the notify from name
     */
    public void setNotifyFromEmail(String notifyFromName) {
        this.notifyFromEmail = notifyFromName;
    }

    /**
     * Gets notify from email.
     *
     * @return the notify from email
     */
    public String getNotifyFromEmail() {
        return notifyFromEmail;
    }

    /**
     * Sets notify to id.
     *
     * @param notifyToID the notify to id
     */
    public void setNotifyToID(String notifyToID) {
        this.notifyToID = notifyToID;
    }

    /**
     * Gets notify to id.
     *
     * @return the notify to id
     */
    public String getNotifyToID() {
        return notifyToID;
    }

    /**
     * Gets notif id.
     *
     * @return the notif id
     */
    public String getNotifID() {
        return notifID;
    }

    /**
     * Sets notif id.
     *
     * @param notifID the notif id
     */
    public void setNotifID(String notifID) {
        this.notifID = notifID;
    }

    /**
     * Sets notify to email.
     *
     * @param notifyToName the notify to name
     */
    public void setNotifyToEmail(String notifyToName) {
        this.notifyToEmail = notifyToName;
    }

    /**
     * Gets notify to email.
     *
     * @return the notify to email
     */
    public String getNotifyToEmail() {
        return notifyToEmail;
    }

    /**
     * Sets isbn.
     *
     * @param ISBN the isbn
     */
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    /**
     * Gets isbn.
     *
     * @return the isbn
     */
    public String getISBN() {
        return ISBN;
    }

    /**
     * Sets photo.
     *
     * @param photo the photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * Gets photo.
     *
     * @return the photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * Gets read.
     *
     * @return the read
     */
    public Boolean getRead() { return read; }

    /**
     * Sets read.
     *
     * @param read the read
     */
    public void setRead(Boolean read) {this.read = read; }

    /**
     * Set lat lng.
     *
     * @param latLng the lat lng
     */
//
    public void setLatLng (LatLng latLng){ this.latLng = latLng; }

    /**
     * Get lat lng lat lng.
     *
     * @return the lat lng
     */
    public LatLng getLatLng (){ return this.latLng; }

    /**
     * Gets owner scanned.
     *
     * @return the owner scanned
     */
    public String getOwnerScanned() { return ownerScanned; }

    /**
     * Sets owner scanned.
     *
     * @param scanned the scanned
     */
    public void setOwnerScanned(String scanned) {this.ownerScanned = scanned; }

    /**
     * Gets latitude.
     *
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Sets latitude.
     *
     * @param latitude the latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets longitude.
     *
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Sets longitude.
     *
     * @param longitude the longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}

