package com.example.cmput301w19t15;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
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
    private EditText inputEmail, inputUsername, inputPassword, inputName, inputPhoneNumber,currentFocus;
    Button btnLogin, btnRegister, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private boolean emailError = false,usernameError = false,passwordError = false,nameError = false,phoneError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnLogin = (Button) findViewById(R.id.login_button);
        btnRegister = (Button) findViewById(R.id.register_button);
        btnResetPassword = (Button) findViewById(R.id.reset_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputUsername = (EditText) findViewById(R.id.username);
        inputPassword = (EditText) findViewById(R.id.password);
        inputName = (EditText) findViewById(R.id.name);
        inputPhoneNumber = (EditText) findViewById(R.id.phone);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputEmail.getText().toString().trim().toLowerCase();
                final String username = inputUsername.getText().toString().trim().toLowerCase();
                String password = inputPassword.getText().toString().trim();
                final String name = inputName.getText().toString().trim();
                final String phone = inputPhoneNumber.getText().toString().trim();

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

                            }else{
                                emailError = false;
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {}
                    });
                }

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
                //if (TextUtils.isEmpty(password)) {
                if (password.isEmpty()) {
                    passwordError = setFocus(inputPassword,"Password is required");
                }else if (password.length() < 6) {
                    passwordError = setFocus(inputPassword,getString(R.string.minimum_password));
                }else{
                    passwordError = false;
                }
                if (name.isEmpty()) {
                    nameError = setFocus(inputName,"Please Enter your Name!");
                }else{
                    nameError = false;
                }
                if (phone.isEmpty()){
                    phoneError = setFocus(inputPhoneNumber,"Please Enter your Name!");
                }else{
                    phoneError = false;
                }
                currentFocus.requestFocus();
                if(!emailError && !usernameError && !passwordError && !nameError && !phoneError) {
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
                                //
                                DatabaseReference dataBase, newUser;
                                FirebaseUser currentUser = task.getResult().getUser();
                                //pick users table to same the user in
                                dataBase = FirebaseDatabase.getInstance().getReference().child("users");
                                newUser = dataBase.child(currentUser.getUid());
                                //create the user
                                User addUser = new User(username,name,email,phone);
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

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
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
