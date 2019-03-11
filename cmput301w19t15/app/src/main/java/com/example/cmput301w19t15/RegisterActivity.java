/*
 * Class Name: RegisterActivity
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

/**
 * Represents an important Tweet
 * @author Thomas, Anjesh, Breanne
 * @version 1.0
 * @see LonelyTwitterActivity
 * @see ImportantTweet
 * @since 1.0
 */

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class RegisterActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword, inputName, inputPhoneNumber,currentFocus;

    Button btnLogin, btnRegister, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private boolean emailError = false,passwordError = false,nameError = false,phoneError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.d("testing","im here");
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnLogin = findViewById(R.id.login_button);
        btnRegister = findViewById(R.id.register_button);
        btnResetPassword = findViewById(R.id.reset_button);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        inputName = findViewById(R.id.name);
        inputPhoneNumber = findViewById(R.id.pass);
        progressBar = findViewById(R.id.progressBar);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputEmail.getText().toString().trim().toLowerCase();
                String password = inputPassword.getText().toString().trim();
                final String name = inputName.getText().toString().trim();
                final String phone = inputPhoneNumber.getText().toString().trim();

                if(!checkEmail(email) && !checkPassword(password) && !checkName(name) && !checkPhoneNumber(phone)) {
                    progressBar.setVisibility(View.VISIBLE);
                    //create user
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(RegisterActivity.this, "Successfully Registered:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
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
                                User addUser = new User(name,email,phone,userID);
                                //save the user in the database
                                newUser.setValue(addUser);
                                //close register
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                if(currentFocus != null){
                    currentFocus.requestFocus();
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    /**
     * prompts user to enter email which must match the email pattern (must contain '@' and '.xx
     *  where xx is some email extension) and must not already exist
     * @param email
     * @return
     */
    private boolean checkEmail(String email){
        if (email.isEmpty()) {
            emailError = setFocus(inputEmail,"Email is required");
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailError = setFocus(inputEmail,"Please enter a valid email");
        }else{
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
            databaseReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
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

    /**
     * prompts user to select a passowrd which must be a least 6 characters long; no other
     * restrictions are present
     * @param password
     * @return
     */
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

    /**
     * prompts user to select a name; no restrictions on the name
     * @param name
     * @return
     */
    private boolean checkName(String name){
        if (name.isEmpty()) {
            nameError = setFocus(inputName,"Please Enter your Name!");
        }else{
            nameError = false;
        }
        return nameError;
    }

    /**
     * prompts user to enter a phone number; no restrictions are present
     * @param phone
     * @return
     */
    private boolean checkPhoneNumber(String phone){
        if (phone.isEmpty()){
            phoneError = setFocus(inputPhoneNumber,"Please Enter your Phone Number!");
        }else{
            phoneError = false;
        }
        return phoneError;
    }

    /**
     * displays progress bar when action is selected
     */
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
    @Override
    public void onBackPressed() {
        //do nothing
    }

    /**
     * changes the focus of the activity
     * @param editText
     * @param message
     * @return
     */
    private boolean setFocus(EditText editText, String message){
        editText.setError(message);
        currentFocus = editText;
        return true;
    }
}
