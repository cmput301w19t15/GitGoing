/*
 * Class Name: MainActivity
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

package com.example.cmput301w19t15;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
/**
 * Represents the primary activity after login
 * @author Thomas, Eisha, Breanne, Anjesh
 * @version 1.0
 * @since 1.0
 */
public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    //private ProgressBar progressBar;
    private Button logOut, myBooks, myBorrows, myRequests, findUser, findBook, myMap;
    private ImageButton myProfile, notifyButton;

    private static User loggedInUser;

    /**
     * Calls when activity is first made
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_updated);

        checkLogIn();
        getLoggedinUser();

        logOut = findViewById(R.id.logout_button);
        //progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //logout user
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
            }
        });

        myBorrows = findViewById(R.id.my_borrows);
        myBorrows.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyBooks.class);
                startActivity(intent);
                //finish();
            }
        });

        findUser = findViewById(R.id.find_user);
        findUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FindUsers.class);
                startActivity(intent);
                //finish();
            }
        });

        findBook = findViewById(R.id.find_book);
        findBook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FindBooks.class);
                startActivity(intent);
                //finish();
            }
        });

        notifyButton = findViewById(R.id.notify);
        notifyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotifyActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        myProfile = findViewById(R.id.profile);
        myProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Profile.class);
                startActivity(intent);
                //finish();
            }
        });

        myRequests = findViewById(R.id.my_requests);
        myRequests.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RequestedBookList.class);
                startActivity(intent);
                //finish();
            }
        });

        myBooks = findViewById(R.id.my_books);
        myBooks.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyBooks.class);
                startActivity(intent);
                //finish();
            }
        });

        myMap = findViewById(R.id.my_map);
        myMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainActivity.this, GeoLocation.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Called when activity is resumed
     */
    protected void onResume() {
        super.onResume();
        //progressBar.setVisibility(View.GONE);
    }

    /**
     * Called when activity is stopped
     */
    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    /**
     * calls when back button is pressed; currently does nothing
     */
    @Override
    public void onBackPressed() {
        //do nothing
    }

    /**
     * Checks if user is currently logged in or if user exists
     */
    public void checkLogIn(){
        auth = FirebaseAuth.getInstance();
        //check if the user is logged in/user exists
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null user logout // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
    }

    /**
     * loads the proper data when user is logged in
     */
    private void getLoggedinUser(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            String userID = user.getUid();
            String emailID = user.getEmail();
            loggedInUser = new User(emailID,userID);
            loggedInUser.loadUserInformation();
            loggedInUser.loadBooks("myBooks");
            loggedInUser.loadBooks("myRequestedBooks");
            loggedInUser.loadBooks("requestedBooks");
            loggedInUser.loadBooks("borrowedBooks");
        }
    }

    /**
     * Returns the current logged in user
     * @return the user
     */
    public static User getUser(){
        return loggedInUser;
    }

    /**
     * Updates the user and their information
     */
    public static void updateUser(){
        FirebaseDatabase.getInstance().getReference().child("users").child(loggedInUser.getUserID()).setValue(loggedInUser);
    }
}

