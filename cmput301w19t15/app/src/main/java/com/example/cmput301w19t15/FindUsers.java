package com.example.cmput301w19t15;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FindUsers extends AppCompatActivity implements UserAdapter.OnItemClickListener {
    private UserAdapter adapter;
    private ArrayList<User> listOfUsers;
    private RecyclerView mRecyclerView;
    private String filterText;
    private EditText filterView;


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
     * reference from https://stackoverflow.com/questions/47847694/how-to-return-datasnapshot-value-as-a-result-of-a-method
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
    public interface loadUserCallBack {
        void loadUserCallBack(ArrayList<User> value);
    }

    public void loadUsersFromFireBase(final loadUserCallBack myCallback){
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("users");
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    try {
                        ArrayList<User> allUsers = new ArrayList<>();
                        ArrayList<User> filteredUsers = new ArrayList<>();
                        for (DataSnapshot users : dataSnapshot.getChildren()) {
                                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user != null) {
                                    String userID = users.child("name").getValue().toString();
                                    String emailID = users.child("email").getValue().toString();
                                    User currentUser = new User(emailID, userID);
                                    //ArrayList<String> userInformation = new ArrayList<>();
                                    //userInformation.add(dataSnapshot.child("name").getValue().toString());
                                    //userInformation.add(dataSnapshot.child("email").getValue().toString());
                                    allUsers.add(currentUser);
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
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("testing", "Error: ", databaseError.toException());
            }
        });
    }

    @Override
    public void onItemClick(int position) {

    }
}
