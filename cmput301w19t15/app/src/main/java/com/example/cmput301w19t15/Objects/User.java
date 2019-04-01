/*
 * Class Name: User
 *
 * Version: 1.0
 *
 * Copyright 2019 TEAM GITGOING
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.example.cmput301w19t15.Objects;
//:)
import android.app.admin.DeviceAdminInfo;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.cmput301w19t15.Activity.FindUsers;
import com.example.cmput301w19t15.Activity.LoginActivity;
import com.example.cmput301w19t15.Activity.Profile;
import com.example.cmput301w19t15.Activity.RequestedBookList;
import com.google.android.gms.common.data.DataBufferSafeParcelable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.spec.ECField;
import java.util.ArrayList;

/**
 * Represents a user
 * @author Anjesh, Josh, Yourui, Breanne
 * @version 1.0
 * @see FindUsers
 * @see Profile
 * @see LoginActivity
 * @since 1.0
 */
public class User {
    private String name;
    private String email;
    private String phone;
    public String userID;
    private Float rating;

    //public static ArrayList<String> testArray = new ArrayList<>();
    private ArrayList<Book> myBooks = new ArrayList<>();//books I own
    private ArrayList<Book> myRequestedBooks = new ArrayList<>();//books I have requested from others - with status
    private ArrayList<Book> myRequestedBooksAvailable = new ArrayList<>();//requested books that are available - with status
    private ArrayList<Book> myRequestedBooksAccepted = new ArrayList<>();//requested books that have been accepted - with status
    private ArrayList<Book> borrowedBooks = new ArrayList<>();//book that I have borrowed from others
    private ArrayList<Book> requestedBooks = new ArrayList<>();//books others have requested from me
    private ArrayList<Book> watchlistBooks = new ArrayList<>();//books I want to track status

    //
    private ArrayList<String> myBooksID = new ArrayList<>();
    private ArrayList<String> myRequestedBooksID = new ArrayList<>();
    private ArrayList<String> myWatchListBooksID = new ArrayList<>();
    private String bookListType = "myBooks";

    /**
     * Instantiates a new User with no attributes
     */
//need this constructor DO NOT REMOVE OR EDIT
    public User(){}

    /**
     * Instantiates a new User with just email and ID
     *
     * @param emailID the email id
     * @param userID  the user id
     */
    public User(String emailID, String userID){
        this.email = emailID;
        this.userID = userID;
    }

    /**
     * Instantiates a new User with email, ID, and phone number
     *
     * @param emailID the email id
     * @param userID  the user id
     * @param phone   the phone
     */
    public User(String emailID, String userID, String phone){
        this.email = emailID;
        this.userID = userID;
        this.phone = phone;
    }

    /**
     * Instantiates a new User with name, email, phone, and ID
     *
     * @param name   the name
     * @param email  the email
     * @param phone  the phone
     * @param userID the user id
     */
    public User(String name, String email, String phone, String userID) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.userID = userID;
    }

    /**
     * Sets user name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns user name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets user email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns user email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets user phone number.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns user phone number.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets user rating.
     *
     * @param rating the rating
     */
    public void setRating(Float rating) {
        this.rating = rating;
    }

    /**
     * Returns user rating.
     *
     * @return the rating
     */
    public Float getRating() {
        return rating;
    }

    /**
     * Returns user id as a string.
     *
     * @return the string
     */
    public String getUserID(){
        return userID;
    }


    /**
     * Add to my books (the books I own).
     *
     * @param bookID the book
     */
    public void addToMyBooksID(String bookID){
        try {
            myBooksID.add(bookID);
            FirebaseDatabase.getInstance().getReference("users").child(userID).child("myBooksID").setValue(myBooksID);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Remove my books.
     *
     * @param bookID the book
     */
    public void removeMyBooksID(String bookID){
        try {
            myBooksID.remove(bookID);
            FirebaseDatabase.getInstance().getReference("users").child(userID).child("myBooksID").setValue(myBooksID);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Returns my books array list.
     *
     * @return the array list
     */
    public ArrayList<String> getMyBooksID(){
        return myBooksID;
    }
    //public  ArrayList<String> getMyRequestedBooksAcceptedID() {return my; }

    public void addToMyRequestedBooksID(String bookID){
        try {
            myRequestedBooksID.add(bookID);
            FirebaseDatabase.getInstance().getReference("users").child(userID).child("myRequestedBooksID").setValue(myRequestedBooksID);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addToMyWatchListBooksID(String bookID){
        try {
            myWatchListBooksID.add(bookID);
            FirebaseDatabase.getInstance().getReference("users").child(userID).child("myWatchListBooksID").setValue(myWatchListBooksID);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void removeMyWatchListBooksID(String bookID){
        try {
            myWatchListBooksID.remove(bookID);
            FirebaseDatabase.getInstance().getReference("users").child(userID).child("myWatchListBooksID").setValue(myWatchListBooksID);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public ArrayList<String> getMyWatchListBooksID(){
        return myWatchListBooksID;
    }

    public void removeMyRequestedBooksID(String bookID){
        try {
            myRequestedBooksID.remove(bookID);
            FirebaseDatabase.getInstance().getReference("users").child(userID).child("myRequestedBooksID").setValue(myRequestedBooksID);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<String> getMyRequestedBooksID(){
        return myRequestedBooksID;
    }
    /**
     * Add to my borrowed books (book that i have borrowed from others)
     * @param bookID the book
     */

    public void addToMyBorrowedBooks(String bookID){
        try {
            myBooksID.add(bookID);
            FirebaseDatabase.getInstance().getReference("users").child(userID).child("myBooksID").setValue(myBooksID);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Get borrowed books array list.
     *
     * @return the array list
     */
    public ArrayList<Book> getBorrowedBooks(){
        return borrowedBooks;
    }


    /**
     * Load user information.
     * @reuse https://stackoverflow.com/questions/47847694/how-to-return-datasnapshot-value-as-a-result-of-a-method
     */
    public void loadUserInformation(){
        loadUserInfoFromFireBase(new loadUserCallBack() {
            @Override
            public void loadUserCallBack(ArrayList<String> value) {
                name = value.get(0);
                email = value.get(1);
                phone = value.get(2);
            }
        });
    }

    /**
     * The interface Load user call back.
     *
     * @reuse https://stackoverflow.com/questions/47847694/how-to-return-datasnapshot-value-as-a-result-of-a-method
     */
    public interface loadUserCallBack{
        /**
         * Load user call back.
         * @param value the value
         */
        void loadUserCallBack(ArrayList<String> value);
    }

    /**
     * Load user info from fire base.
     * @reuse https://stackoverflow.com/questions/47847694/how-to-return-datasnapshot-value-as-a-result-of-a-method
     * @param myCallback the my callback
     */
    public void loadUserInfoFromFireBase(final loadUserCallBack myCallback){
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("users").child(this.userID);
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    try {
                        ArrayList<String> userInformatiom = new ArrayList<>();
                        userInformatiom.add(dataSnapshot.child("name").getValue().toString());
                        userInformatiom.add(dataSnapshot.child("email").getValue().toString());
                        userInformatiom.add(dataSnapshot.child("phone").getValue().toString());
                        myCallback.loadUserCallBack(userInformatiom);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("testing","Error: ", databaseError.toException());
            }
        });
    }

    public void loadBooksID(final String bookListType){
        this.bookListType = bookListType;
        loadMyBookIDFromFireBase(new loadBookIDCallBack() {
            @Override
            public void loadBookIDCallBack(ArrayList<String> value) {
                switch(bookListType) {
                    case "myBooksID": myBooksID = new ArrayList<>(value); break;
                    case "myRequestedBooksID": myRequestedBooksID = new ArrayList<>(value); break;
                    case "myWatchListBooksID": myWatchListBooksID = new ArrayList<>(value); break;
                }

            }
        });
    }

    public interface loadBookIDCallBack {
        /**
         * Load book call back.
         * @param value the value
         */
        void loadBookIDCallBack(ArrayList<String> value);
    }
    public void loadMyBookIDFromFireBase(final loadBookIDCallBack myCallback){
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("users").child(this.userID).child(bookListType);
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    try {
                        ArrayList<String> allBooks = new ArrayList<>();
                        for (DataSnapshot books : dataSnapshot.getChildren()) {
                            String bookID = (String) books.getValue();
                            allBooks.add(bookID);
                        }
                        myCallback.loadBookIDCallBack(allBooks);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("testing","Error: ", databaseError.toException());
            }
        });
    }

}
