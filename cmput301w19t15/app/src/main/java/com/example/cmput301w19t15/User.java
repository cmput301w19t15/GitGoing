package com.example.cmput301w19t15;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String name;
    private String email;
    private String phone;
    private String userID;
    private Float rating;
    public static ArrayList<String> testArray = new ArrayList<>();
    private ArrayList<Book> myBooks = new ArrayList<>();//my books i own
    private ArrayList<Book> myRequestedBooks = new ArrayList<>();//books i have requested from others - with status
    private ArrayList<Book> myRequestedBooksAvailable = new ArrayList<>();//requested books that are available - with status
    private ArrayList<Book> myRequestedBooksAccepted = new ArrayList<>();//requested books that have been accepted - with status
    private ArrayList<Book> borrowedBooks = new ArrayList<>();//book that i have borrowed from others
    private ArrayList<Book> requestedBooks = new ArrayList<>();//books others have requested from me

    private String bookListType = "myBooks";

    //need this constructor DO NOT REMOVE OR EDIT
    public User(){}
    public User(String emailID, String userID){
        this.email = emailID;
        this.userID = userID;
    }
    public User(String emailID, String userID, String phone){
        this.email = emailID;
        this.userID = userID;
        this.phone = phone;
    }
    public User(String name, String email, String phone, String userID) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.userID = userID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone() {
        return phone;
    }
    public void setRating(Float rating) {
        this.rating = rating;
    }
    public Float getRating() {
        return rating;
    }
    public void setUserID(String userID){
        this.userID = userID;
    }
    public String getUserID(){
        return userID;
    }



    //my books i own
    public void addToMyBooks(Book book){
        try {
            myBooks.add(book);
            FirebaseDatabase.getInstance().getReference("users").child(userID).child("myBooks").setValue(myBooks);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void removeMyBooks(Book book){
        try {
            myBooks.remove(book);
            FirebaseDatabase.getInstance().getReference("users").child(userID).child("myBooks").setValue(myBooks);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public ArrayList<Book> getMyBooks(){
        return myBooks;
    }
    //books i have requested from others - with status
    public void addToMyRequestedBooks(Book book){
        try {
            myRequestedBooks.add(book);
            FirebaseDatabase.getInstance().getReference("users").child(userID).child("myRequestedBooks").setValue(myRequestedBooks);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void removeMyRequestedBooks(Book book){
        try {
            myRequestedBooks.remove(book);
            FirebaseDatabase.getInstance().getReference("users").child(userID).child("myRequestedBooks").setValue(myRequestedBooks);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public ArrayList<Book> getMyRequestedBooks(){
        return myRequestedBooks;
    }
    //books others have requested from me
    public void addToRequestedBooks(Book book){
        try {
            requestedBooks.add(book);
            FirebaseDatabase.getInstance().getReference("users").child(userID).child("requestedBooks").setValue(requestedBooks);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void removeRequestedBooks(Book book){
        try {
            requestedBooks.remove(book);
            FirebaseDatabase.getInstance().getReference("users").child(userID).child("requestedBooks").setValue(requestedBooks);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public ArrayList<Book> getRequestedBooks(){
        return requestedBooks;
    }
    //book that i have borrowed from others
    public void addToMyBorrowedBooks(Book book){
        try {
            borrowedBooks.add(book);
            FirebaseDatabase.getInstance().getReference("users").child(userID).child("borrowedBooks").setValue(borrowedBooks);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void removeMyBorrowedBooks(Book book){
        try {
            borrowedBooks.remove(book);
            FirebaseDatabase.getInstance().getReference("users").child(userID).child("borrowedBooks").setValue(borrowedBooks);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public ArrayList<Book> getBorrowedBooks(){
        return borrowedBooks;
    }

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
    public interface loadUserCallBack{
        void loadUserCallBack(ArrayList<String> value);
    }
    public interface loadBookCallBack {
        void loadBookCallBack(ArrayList<Book> value);
    }

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






    /*** Planned to Remove***/


    //requested books that are avaliable - with status
    public void addToMyRequestedBooksAvailable(Book book){
        myRequestedBooksAvailable.add(book);
        FirebaseDatabase.getInstance().getReference("users").child(userID).child("myRequestedBooksAvailable").removeValue();
        FirebaseDatabase.getInstance().getReference("users").child(userID).child("myRequestedBooksAvailable").setValue(myRequestedBooksAvailable);
    }
    public void removeMyRequestedBooksAvailable(Book book){
        myRequestedBooksAvailable.remove(book);
        FirebaseDatabase.getInstance().getReference("users").child(userID).child("myRequestedBooksAvailable").removeValue();
        FirebaseDatabase.getInstance().getReference("users").child(userID).child("myRequestedBooksAvailable").setValue(myRequestedBooksAvailable);
    }
    public ArrayList<Book> getMyRequestedBooksAvailable(){
        return myRequestedBooksAvailable;
    }
    //requested books that have been accepeted - with status
    public void addToMyRequestedBooksAccepted(Book book){
        myRequestedBooksAccepted.add(book);
        FirebaseDatabase.getInstance().getReference("users").child(userID).child("myRequestedBooksAccepted").removeValue();
        FirebaseDatabase.getInstance().getReference("users").child(userID).child("myRequestedBooksAccepted").setValue(myRequestedBooksAccepted);
    }
    public void removeMyRequestedBooksAccepted(Book book){
        myRequestedBooksAccepted.remove(book);
        FirebaseDatabase.getInstance().getReference("users").child(userID).child("myRequestedBooksAccepted").removeValue();
        FirebaseDatabase.getInstance().getReference("users").child(userID).child("myRequestedBooksAccepted").setValue(myRequestedBooksAccepted);
    }
    public ArrayList<Book> getMyRequestedBooksAccepted(){
        return myRequestedBooksAccepted;
    }


}
