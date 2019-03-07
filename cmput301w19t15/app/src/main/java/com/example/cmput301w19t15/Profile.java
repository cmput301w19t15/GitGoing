package com.example.cmput301w19t15;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
<<<<<<< HEAD
import android.widget.EditText;
=======
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
>>>>>>> 50afdaa7c56ece89c44e36c79f764a59a76b4231

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    private EditText inputEmail, inputUsername, inputPassword, inputName, inputPhoneNumber, currentFocus;
    Button saveButton;
    //private ProgressBar progressBar;
    private FirebaseAuth auth;
    private boolean emailError = false,usernameError = false,passwordError = false,nameError = false,phoneError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
/** Might keep, might not
        //get edit text
        EditText username = (EditText)findViewById(R.id.username);
        EditText name = (EditText)findViewById(R.id.name);
        EditText email = (EditText)findViewById(R.id.email);
        EditText password = (EditText)findViewById(R.id.password);
        EditText number = (EditText)findViewById(R.id.phone2);

        //get info from logged in user
        User user = MainActivity.getUser();

        //
        username.setText(user.getUsername());
        name.setText(user.getName());
        email.setText(user.getEmail());
        number.setText(user.getPhone());

        Log.d("testing","im here");
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        saveButton = findViewById(R.id.save);
        inputEmail = findViewById(R.id.email);
        inputUsername = findViewById(R.id.username);
        inputPassword = findViewById(R.id.password);
        inputName = findViewById(R.id.name);
        inputPhoneNumber = findViewById(R.id.phone);
       //progressBar = findViewById(R.id.progressBar);
**/
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputEmail.getText().toString().trim().toLowerCase();
                final String username = inputUsername.getText().toString().trim().toLowerCase();
                String password = inputPassword.getText().toString().trim();
                final String name = inputName.getText().toString().trim();
                final String phone = inputPhoneNumber.getText().toString().trim();

                /*
                Log.d("testing","Email " + emailError);
                checkEmail(email);
                Log.d("testing","Email " + emailError);
                Log.d("testing","Username " + usernameError);
                checkUsername(username);
                Log.d("testing","Username " + usernameError);
                Log.d("testing","Password " + passwordError);
                checkPassword(password);
                Log.d("testing","Password " + passwordError);
                //Log.d("testing","Name " + nameError);
                checkName(name);
                //Log.d("testing","Name " + nameError);
                //Log.d("testing","Phone " + phoneError);
                checkPhoneNumber(phone);
                //Log.d("testing","Phone " + phoneError);

                Log.d("testing",".");
                Log.d("testing",".");

                */
                //if(!emailError && !usernameError && !passwordError && !nameError && !phoneError) {
                if(!checkEmail(email) && !checkUsername(username) && !checkPassword(password)
                        && !checkName(name) && !checkPhoneNumber(phone)) {
                    //progressBar.setVisibility(View.VISIBLE);
                    //create user
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                            Profile.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(Profile.this, "Successfully Registered:" +
                                    task.isSuccessful(), Toast.LENGTH_SHORT).show();
                            //progressBar.setVisibility(View.GONE);
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (task.isSuccessful()) {
                                //get database
                                DatabaseReference dataBase, newUser;
                                FirebaseUser currentUser = task.getResult().getUser();
                                String userID = currentUser.getUid();
                                //pick users table to same the user in
                                dataBase = FirebaseDatabase.getInstance().getReference().child("users");
                                newUser = dataBase.child(currentUser.getUid());
                                //create the user
                                User addUser = new User(username,name,email,phone,userID);
                                //save the user in the database
                                newUser.setValue(addUser);
                                //close register
                                startActivity(new Intent(Profile.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(Profile.this, "Authentication failed." +
                                        task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                if(currentFocus != null){
                    currentFocus.requestFocus();
                }
            }
        });
    }
    private boolean checkEmail(String email){
        if (email.isEmpty()) {
            emailError = setFocus(inputEmail,"Email is required");
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailError = setFocus(inputEmail,"Please enter a valid email");
        }else{
            DatabaseReference databaseReference = FirebaseDatabase.
                    getInstance().getReference().child("users");
            databaseReference.orderByChild("email").equalTo(email).
                    addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        emailError = setFocus(inputEmail,"Email already Exists");
                    }else{
                        emailError = false;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });
        }
        return emailError;
    }
    private boolean checkUsername(String username){
        if(username.isEmpty()) {
            usernameError = setFocus(inputUsername,"Enter a username");
        }else{
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
            databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        usernameError = setFocus(inputUsername,"Username already Exists");
                        //check your password in the same way and grant access if it exists too
                    }else {
                        // wrong details entered/ user does not exist
                        usernameError = false;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        return usernameError;
    }
    private boolean checkPassword(String password){
        if (password.isEmpty()) {
            passwordError = setFocus(inputPassword,"Password is required");
        }else if (password.length() < 6) {
            passwordError = setFocus(inputPassword,getString(R.string.minimum_password));
        }else{
            passwordError = false;
        }
        return passwordError;
    }
    private boolean checkName(String name){
        if (name.isEmpty()) {
            nameError = setFocus(inputName,"Please Enter your Name!");
        }else{
            nameError = false;
        }
        return nameError;
    }
    private boolean checkPhoneNumber(String phone){
        if (phone.isEmpty()){
            phoneError = setFocus(inputPhoneNumber,"Please Enter your Name!");
        }else{
            phoneError = false;
        }
        return phoneError;
    }
    @Override
    protected void onResume() {
        super.onResume();
        //progressBar.setVisibility(View.GONE);
    }
    @Override
    public void onBackPressed() {
        //do nothing
    }
    private boolean setFocus(EditText editText, String message){
        editText.setError(message);
        currentFocus = editText;
        return true;
    }

}
