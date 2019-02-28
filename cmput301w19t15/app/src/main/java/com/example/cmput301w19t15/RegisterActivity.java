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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword, inputName, inputPhoneNumber;
    private Button btnLogin, btnRegister, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

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
        inputPassword = (EditText) findViewById(R.id.password);
        inputName = (EditText) findViewById(R.id.name);
        inputPhoneNumber = (EditText) findViewById(R.id.phone);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                final String name = inputName.getText().toString().trim();
                final String phone = inputPhoneNumber.getText().toString().trim();
                //if (TextUtils.isEmpty(email)) {
                if (email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    //editTextEmail.setError("Email is required");
                    //editTextEmail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(getApplicationContext(), "Enter valid email!", Toast.LENGTH_SHORT).show();
                    //editTextEmail.setError("Please enter a valid email");
                    //editTextEmail.requestFocus();
                    return;
                }

                //if (TextUtils.isEmpty(password)) {
                if (password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    //editTextPassword.setError("Password is required");
                    //editTextPassword.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password to short!", Toast.LENGTH_SHORT).show();
                    inputPassword.setError(getString(R.string.minimum_password));
                    // editTextPassword.setError("Minimum lenght of password should be 6");
                    // editTextPassword.requestFocus();
                    return;
                }
                if (name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter your Name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please Enter your Phone Number!", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                //create user
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(RegisterActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
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
                            User usertest = new User("test",name,email,phone);
                            //save the user in the database
                            newUser.setValue(usertest);
                            //close register
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

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
}
