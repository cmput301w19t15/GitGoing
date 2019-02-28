package com.example.cmput301w19t15;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private FirebaseAuth auth;
    // UI references.
    private AutoCompleteTextView inputEmail;
    private EditText inputPassword;
    private View progressBar;
    private Button btnLogin,btnRegister,btnResetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set up the login form.

        setContentView(R.layout.activity_login);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        //if user is already logged in goto main activity
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        //else let user login
        inputEmail = (AutoCompleteTextView) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);

        btnLogin = (Button) findViewById(R.id.login_button);
        btnRegister = (Button) findViewById(R.id.register_button);
        btnResetPassword = (Button) findViewById(R.id.reset_button);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);

        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();

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

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // there was an error
                            Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        btnRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btnResetPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }
}

