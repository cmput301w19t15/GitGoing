package com.example.cmput301w19t15;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private ProgressBar progressBar;
    private Button btnChangeEmail, btnChangePassword, btnSendResetEmail, btnRemoveUser,
            changeEmail, changePassword, sendEmail, remove, logOut, myBooks;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        //check if the user is logged in/user exists
        getLoggedinUser();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null user logsout
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
        logOut = (Button) findViewById(R.id.logout_button);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //logout user
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                finish();
            }
        });

        myBooks = (Button) findViewById(R.id.my_books);

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
        progressBar.setVisibility(View.GONE);
    }
    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
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
    User loggedinUser;
    private void getLoggedinUser(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            final String userEmail = user.getEmail();
            FirebaseDatabase.getInstance().getReference().child("users").orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        for(DataSnapshot user: dataSnapshot.getChildren()){
                            if(user.child("email").getValue().toString().equalsIgnoreCase(userEmail)){
                                //loggedinUser = user.getValue(User.class);
                                Log.d("testing",user.child("email").getValue().toString());
                                Log.d("testing",user.child("name").getValue().toString());
                                //Log.d("testing",loggedinUser.getEmail());
                                //Log.d("testing",loggedinUser.getName());
                                break;
                            }
                        }
                    }else {}
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });
        }
        //Log.d("testing",loggedinUser.getEmail());
    }
    private void saveLoggedinUser(DataSnapshot data){
        //loggedinUser = data.getValue(User.class);
    }
}
