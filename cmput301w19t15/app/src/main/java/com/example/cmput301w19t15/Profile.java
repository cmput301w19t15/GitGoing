package com.example.cmput301w19t15;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class Profile extends AppCompatActivity {


    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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

    }

}
