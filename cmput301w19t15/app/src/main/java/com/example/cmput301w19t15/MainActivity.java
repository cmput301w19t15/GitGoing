package com.example.cmput301w19t15;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    //private ProgressBar progressBar;
    private Button logOut, myBooks, myBorrows, myRequests, findUser, findBook;
    private ImageButton myProfile, notifyButton;

    private static User loggedInUser;
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
                Intent intent = new Intent(MainActivity.this, MyBooks.class);
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
                Intent intent = new Intent(MainActivity.this, MyBooks.class);
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
    }
    protected void onResume() {
        super.onResume();
        //progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
    @Override
    public void onBackPressed() {
        //do nothing
    }
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
    public static User getUser(){
        return loggedInUser;
    }
}
