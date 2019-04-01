/*
 * Class Name: Profile
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
//:)
/**
 * Represents a user's profile
 * @author Thomas, Anjesh, Breanne
 * @version 1.0
 * @see User
 * @since 1.0
 */

import android.media.Rating;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;


import com.example.cmput301w19t15.Activity.MainActivity;
import com.example.cmput301w19t15.Objects.User;
import com.example.cmput301w19t15.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Enables user to change the details of their profile
 */
public class Profile extends AppCompatActivity {
    private EditText inputEmail, inputPassword, inputNewPassword, inputName, inputPhoneNumber, currentFocus;
    private RatingBar rating;

    Button saveButton, cancelButton;
    //private ProgressBar progressBar;
    private FirebaseAuth auth;
    private boolean emailError = false,usernameError = false,passwordError = false,nameError = false,phoneError = false, newPasswordError = false;

    final User user = MainActivity.getUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //get info from logged in user
        auth = FirebaseAuth.getInstance();

        saveButton = findViewById(R.id.save);
        cancelButton = findViewById(R.id.cancel);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.pass);
        inputNewPassword = findViewById(R.id.newPassword);
        inputName = findViewById(R.id.name);
        inputPhoneNumber = findViewById(R.id.phone);
        rating = findViewById(R.id.userRatingBar);

        inputName.setText(user.getName());
        inputEmail.setText(user.getEmail());
        inputPhoneNumber.setText(user.getPhone());

        try {
            rating.setRating(user.getRating());
        }
        catch (Exception e){
            Log.d("testing","no user rating set");
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUpdates();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }

    /**
     * Update the user information with the changes made
     */
    private void saveUpdates(){
        final String email = inputEmail.getText().toString().trim().toLowerCase();
        final String password = inputPassword.getText().toString().trim();
        final String name = inputName.getText().toString().trim();
        final String phone = inputPhoneNumber.getText().toString().trim();
        final String newPassword = inputNewPassword.getText().toString().trim();

        //check all things to make sure duplicate users not made and such
        if(!checkEmail(email)  && !checkPassword(password) && !checkName(name) && !checkPhoneNumber(phone) && !checkNewPassword(newPassword)) {
            //update user information
            if(currentFocus != null){
                currentFocus.requestFocus();
            }
            //reauthenciate user to make sure it is actual owner
            AuthCredential credential = EmailAuthProvider.getCredential(auth.getCurrentUser().getEmail(),password);
            auth.getCurrentUser().reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        //user is reauthenticated
                        //update the email and other values that have been changed
                        auth.getCurrentUser().updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    if(!newPassword.isEmpty()){
                                        auth.getCurrentUser().updatePassword(newPassword);
                                    }
                                    //email was successfully updated, with name and phone
                                    user.setEmail(email);
                                    user.setName(name);
                                    user.setPhone(phone);
                                    MainActivity.updateUser();
                                    finish();
                                }else{
                                    emailError = setFocus(inputEmail,"Email already Exists");
                                }
                            }
                        });
                    } else{
                        passwordError = setFocus(inputPassword,"Password is incorrect");
                        //Log.d("testing","Password is Incorrect");
                    }

                }
            });
        }
    }

    /**
     * prompts user to enter email which must match the email pattern (must contain '@' and '.xx
     *  where xx is some email extension) and must not already exist
     * @param email
     * @return
     * @reuse: https://stackoverflow.com/questions/1819142/how-should-i-validate-an-e-mail-address?rq=1
     */
    private boolean checkEmail(String email){
        if (email.isEmpty()) {
            emailError = setFocus(inputEmail,"Email is required");
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailError = setFocus(inputEmail,"Please enter a valid email");
        }else if(!email.equalsIgnoreCase(user.getEmail())){
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
     * prompts user to select a passowrd which must be at least 6 characters long; no other
     * restrictions are present
     * @param password
     * @return
     */
    private boolean checkPassword(String password){
        if (password.isEmpty()) {
            passwordError = setFocus(inputPassword,"Password is required");
        }else if (password.length() < 6) {
            passwordError = setFocus(inputPassword,getString(R.string.minimum_password));
        }
        return passwordError;
    }

    /**
     * prompts user to select a new password which must be at least 6 characters long; no other
     * restrictions are present
     * @param password
     * @return
     */
    private boolean checkNewPassword(String password){
        if (password.isEmpty()) {
            //ignore
        }else if (password.length() < 6) {
            newPasswordError = setFocus(inputNewPassword,getString(R.string.minimum_password));
        }
        return newPasswordError;
    }

    /**
     * prompts user to select a name; no restrictions on the name
     * @param name
     * @return
     */
    private boolean checkName(String name){
        if (name.isEmpty()) {
            nameError = setFocus(inputName,"Please Enter your Name!");
        }
        return nameError;
    }

    /**
     * prompts user to enter a phone number; no restrictions are present
     * @param phone
     * @return
     */
    private boolean checkPhoneNumber(String phone){
        if (phone.isEmpty()) {
            phoneError = setFocus(inputPhoneNumber, "Please Enter your Name!");
        }
        return phoneError;
    }

    /**
     * Resumes activity
     */
    @Override
    protected void onResume() {
        super.onResume();
        //progressBar.setVisibility(View.GONE);
    }

    /**
     * dont let the user ress back
     */
    @Override
    public void onBackPressed() {
        //do nothing
        finish();
    }

    /**
     * Changes focus of activity
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
