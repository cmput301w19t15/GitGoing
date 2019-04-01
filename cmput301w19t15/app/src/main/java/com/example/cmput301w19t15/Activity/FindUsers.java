/*
 * Class Name: FindUser
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
package com.example.cmput301w19t15.Activity;
//


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cmput301w19t15.R;
import com.example.cmput301w19t15.Objects.User;
import com.example.cmput301w19t15.Objects.UserAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
/**
 * Represents a user search
 * @author Anjesh, Eisha
 * @version 1.0
 * @see UserAdapter
 * @see User
 * @since 1.0
 */
public class FindUsers extends AppCompatActivity implements UserAdapter.OnItemClickListener {
    private UserAdapter adapter;
    private ArrayList<User> listOfUsers;
    private RecyclerView mRecyclerView;
    private String filterText;
    private EditText filterView;

    /**
     * Called when activity is created
     * @param savedInstancesState
     */
    @Override
    protected void onCreate (Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);
        setContentView(R.layout.activity_find_users);

        mRecyclerView = findViewById(R.id.recylcerUserView);
        filterView = findViewById(R.id.searchUserView);
        filterText = filterView.getText().toString();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        listOfUsers = new ArrayList<>();
        loadUsers();

        Button searchButton = findViewById(R.id.searchUserButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterText = filterView.getText().toString();
                loadUsers();
            }
        });
    }


    /**
     * @reuse https://stackoverflow.com/questions/47847694/how-to-return-datasnapshot-value-as-a-result-of-a-method
     * loads all the current users into a recyclerview
     */
    public void loadUsers(){
        loadUsersFromFireBase(new loadUserCallBack() {
            @Override
            public void loadUserCallBack(ArrayList<User> value) {
                listOfUsers = (ArrayList<User>) value.clone();
                adapter = new UserAdapter(FindUsers.this, listOfUsers);
                mRecyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(FindUsers.this);
            }
        });
    }

    /**
     * * @reuse: https://stackoverflow.com/questions/47847694/how-to-return-datasnapshot-value-as-a-result-of-a-method
     */
    public interface loadUserCallBack {
        /**
         * Load user call back.
         *
         * @param value the value
         */
        void loadUserCallBack(ArrayList<User> value);
    }

    /**
     * * @reuse: https://stackoverflow.com/questions/47847694/how-to-return-datasnapshot-value-as-a-result-of-a-method
     * @param myCallback
     */
    public void loadUsersFromFireBase(final loadUserCallBack myCallback){
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("users");
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    try {
                        Log.e("TAG", "Test0");
                        ArrayList<User> allUsers = new ArrayList<>();
                        ArrayList<User> filteredUsers = new ArrayList<>();
                        for (DataSnapshot users : dataSnapshot.getChildren()) {
                            Log.e("TAG", "Test1");
                            //final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            Log.e("TAG", "Test3");
                            if (users != null) {
                                    Log.e("TAG", "Test2");
                                    try {
                                        String userID = users.child("name").getValue().toString();
                                        String emailID = users.child("email").getValue().toString();
                                        User currentUser = new User(emailID, userID);
                                        Log.e("TAG", "Made it here");
                                        //ArrayList<String> userInformation = new ArrayList<>();
                                        //userInformation.add(dataSnapshot.child("name").getValue().toString());
                                        //userInformation.add(dataSnapshot.child("email").getValue().toString());
                                        allUsers.add(currentUser);
                                    } catch (Exception e ){
                                        e.printStackTrace();
                                    }
                            }
                        }
                        // filter users
                        if (filterText != " ") {
                            for (User user : allUsers) {
                                if (user.getUserID().toLowerCase().contains(filterText.toLowerCase())
                                || user.getEmail().toLowerCase().contains(filterText.toLowerCase())) {
                                    filteredUsers.add(user);
                                }
                            }
                        } else {
                            filteredUsers = allUsers;
                        }
                        myCallback.loadUserCallBack(filteredUsers);
                    } catch (Exception e){
                        Log.d("testing", "URGHHHH");
                        e.printStackTrace();
                    }
                }
            }

            /**
             * used for testing to detect database error
             * @param databaseError
             */
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("testing", "Error: ", databaseError.toException());
            }
        });
    }

    /**
     * gets position of click
     * @param position the position
     */
    @Override
    public void onItemClick(int position) {

    }
}
