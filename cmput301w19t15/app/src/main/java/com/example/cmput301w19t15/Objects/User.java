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
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.cmput301w19t15.Activity.FindUsers;
import com.example.cmput301w19t15.Activity.LoginActivity;
import com.example.cmput301w19t15.Activity.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private String userID;
    private Float rating;

    //public static ArrayList<String> testArray = new ArrayList<>();
    private ArrayList<Book> myBooks = new ArrayList<>();//my books i own
    private ArrayList<Book> myRequestedBooks = new ArrayList<>();//books i have requested from others - with status
    private ArrayList<Book> myRequestedBooksAvailable = new ArrayList<>();//requested books that are available - with status
    private ArrayList<Book> myRequestedBooksAccepted = new ArrayList<>();//requested books that have been accepted - with status
    private ArrayList<Book> borrowedBooks = new ArrayList<>();//book that i have borrowed from others
    private ArrayList<Book> requestedBooks = new ArrayList<>();//books others have requested from me

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
     * @param book the book
     */

    public void addToMyBooks(Book book){
        try {
            myBooks.add(book);
            FirebaseDatabase.getInstance().getReference("users").child(userID).child("myBooks").child(book.getBookID()).setValue(book);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Remove my books.
     *
     * @param book the book
     */
    public void removeMyBooks(Book book){
        try {
            myBooks.remove(book);
            FirebaseDatabase.getInstance().getReference("users").child(userID).child("myBooks").child(book.getBookID()).removeValue();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Returns my books array list.
     *
     * @return the array list
     */
    public ArrayList<Book> getMyBooks(){
        return myBooks;
    }

    /**
     * Add to my requested books (books I have requested from others - with status).
     *
     * @param book the book
     */

    public void addToMyRequestedBooks(Book book){
        try {
            myRequestedBooks.add(book);
            FirebaseDatabase.getInstance().getReference("users").child(userID).child("myRequestedBooks").child(book.getBookID()).setValue(book);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Remove my requested books.
     *
     * @param book the book
     */
    public void removeMyRequestedBooks(Book book){
        try {
            myRequestedBooks.remove(book);
            FirebaseDatabase.getInstance().getReference("users").child(userID).child("myRequestedBooks").setValue(myRequestedBooks);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Get my requested books array list.
     *
     * @return the array list
     */
    public ArrayList<Book> getMyRequestedBooks(){
        return myRequestedBooks;
    }

    /**
     * Add to requested books (books others have requested from me).
     *
     * @param book the book
     */

    public void addToRequestedBooks(Book book){
        try {
            requestedBooks.add(book);
            FirebaseDatabase.getInstance().getReference("users").child(userID).child("requestedBooks").setValue(requestedBooks);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Remove requested books.
     *
     * @param book the book
     */
    public void removeRequestedBooks(Book book){
        try {
            requestedBooks.remove(book);
            FirebaseDatabase.getInstance().getReference("users").child(userID).child("requestedBooks").setValue(requestedBooks);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Get requested books array list.
     *
     * @return the array list
     */
    public ArrayList<Book> getRequestedBooks(){
        return requestedBooks;
    }

    /**
     * Add to my borrowed books (book that i have borrowed from others)
     * @param book the book
     */

    public void addToMyBorrowedBooks(Book book){
        try {
            borrowedBooks.add(book);
            FirebaseDatabase.getInstance().getReference("users").child(userID).child("borrowedBooks").setValue(borrowedBooks);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Remove my borrowed books.
     *
     * @param book the book
     */
    public void removeMyBorrowedBooks(Book book){
        try {
            borrowedBooks.remove(book);
            FirebaseDatabase.getInstance().getReference("users").child(userID).child("borrowedBooks").setValue(borrowedBooks);
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
     * Load books.
     * @reuse https://stackoverflow.com/questions/47847694/how-to-return-datasnapshot-value-as-a-result-of-a-method
     * @param bookListType the book list type
     */
    public void loadBooks(final String bookListType){
        this.bookListType = bookListType;
        loadMyBookFromFireBase(new loadBookCallBack() {
            @Override
            public void loadBookCallBack(ArrayList<Book> value) {
                switch(bookListType) {
                    case "myBooks": myBooks = (ArrayList<Book>) value.clone(); break;
                    case "myRequestedBooks": myRequestedBooks = (ArrayList<Book>) value.clone(); break;
                    case "requestedBooks": requestedBooks = (ArrayList<Book>) value.clone(); break;
                    case "borrowedBooks": borrowedBooks = (ArrayList<Book>) value.clone(); break;
                }
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
     * The interface Load book call back.
     * @reuse https://stackoverflow.com/questions/47847694/how-to-return-datasnapshot-value-as-a-result-of-a-method
     */
    public interface loadBookCallBack {
        /**
         * Load book call back.
         *
         * @param value the value
         */
        void loadBookCallBack(ArrayList<Book> value);
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

    /**
     * Load my book from fire base.
     * @reuse https://stackoverflow.com/questions/47847694/how-to-return-datasnapshot-value-as-a-result-of-a-method
     * @param myCallback the my callback
     */
    public void loadMyBookFromFireBase(final loadBookCallBack myCallback){
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("users").child(this.userID).child(bookListType);
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    try {
                        ArrayList<Book> allBooks = new ArrayList<>();
                        for (DataSnapshot books : dataSnapshot.getChildren()) {
                            Book book = books.getValue(Book.class);
                            allBooks.add(book);
                        }
                        myCallback.loadBookCallBack(allBooks);
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


    /** requested books that are avaliable - with status */

    public void addToMyRequestedBooksAvailable(Book book){
        myRequestedBooksAvailable.add(book);
        FirebaseDatabase.getInstance().getReference("users").child(userID).child("myRequestedBooksAvailable").removeValue();
        FirebaseDatabase.getInstance().getReference("users").child(userID).child("myRequestedBooksAvailable").setValue(myRequestedBooksAvailable);
    }

    /**
     * Remove my requested books available.
     *
     * @param book the book
     */
    public void removeMyRequestedBooksAvailable(Book book){
        myRequestedBooksAvailable.remove(book);
        FirebaseDatabase.getInstance().getReference("users").child(userID).child("myRequestedBooksAvailable").removeValue();
        FirebaseDatabase.getInstance().getReference("users").child(userID).child("myRequestedBooksAvailable").setValue(myRequestedBooksAvailable);
    }

    /**
     * Get my requested books available array list.
     *
     * @return the array list
     */
    public ArrayList<Book> getMyRequestedBooksAvailable(){
        return myRequestedBooksAvailable;
    }

    /**
     * Add to my requested books accepted (requested books that have been accepted - with status)
     *
     * @param book the book
     */

    public void addToMyRequestedBooksAccepted(Book book){
        //myRequestedBooksAccepted.add(book);
        //FirebaseDatabase.getInstance().getReference("users").child(userID).child("myRequestedBooksAccepted").removeValue();
        //FirebaseDatabase.getInstance().getReference("users").child(userID).child("myRequestedBooksAccepted").setValue(myRequestedBooksAccepted);
        try {
            myRequestedBooksAccepted.add(book);
            FirebaseDatabase.getInstance().getReference("users").child(userID).child("myRequestedBooksAccepted").child(book.getBookID()).setValue(book);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Remove my requested books accepted.
     *
     * @param book the book
     */
    public void removeMyRequestedBooksAccepted(Book book){
        myRequestedBooksAccepted.remove(book);
        FirebaseDatabase.getInstance().getReference("users").child(userID).child("myRequestedBooksAccepted").removeValue();
        FirebaseDatabase.getInstance().getReference("users").child(userID).child("myRequestedBooksAccepted").setValue(myRequestedBooksAccepted);
    }

    /**
     * Get my requested books accepted array list.
     *
     * @return the array list
     */
    public ArrayList<Book> getMyRequestedBooksAccepted(){
        ArrayList<Book> myacceptedBooks = new ArrayList<>();
        for (Book book : myRequestedBooks){
            //Log.e("TAG: ", "BOOOOOK" + book.getBorrowerID() + "  " + this.userID);
            if (book.getBorrowerID().equals(this.userID)) {
                myacceptedBooks.add(book);
            }
        }
        return myacceptedBooks;
    }


}
