package com.example.cmput301w19t15;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    private EditText inputEmail, inputPassword, inputName, inputPhoneNumber, currentFocus;
    Button saveButton, cancelButton;
    //private ProgressBar progressBar;
    private FirebaseAuth auth;
    private boolean emailError = false,usernameError = false,passwordError = false,nameError = false,phoneError = false;
    final User user = MainActivity.getUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //get info from logged in user
        //final User user = MainActivity.getUser();

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        saveButton = findViewById(R.id.save);
        cancelButton = findViewById(R.id.cancel);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.pass);
        inputName = findViewById(R.id.name);
        inputPhoneNumber = findViewById(R.id.phone);

        inputName.setText(user.getName());
        inputEmail.setText(user.getEmail());
        inputPhoneNumber.setText(user.getPhone());

        //progressBar = findViewById(R.id.progressBar);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputEmail.getText().toString().trim().toLowerCase();
                String password = inputPassword.getText().toString().trim();
                final String name = inputName.getText().toString().trim();
                final String phone = inputPhoneNumber.getText().toString().trim();

                //check all things to make sure duplicate users not made and such
                if(!checkEmail(email)  && !checkPassword(password) && !checkName(name) && !checkPhoneNumber(phone)) {
                    //progressBar.setVisibility(View.VISIBLE);
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
                                Log.d("testing","Password is Incorrect");
                            }

                        }
                    });
                }

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }
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
        finish();
    }
    private boolean setFocus(EditText editText, String message){
        editText.setError(message);
        currentFocus = editText;
        return true;
    }

}
