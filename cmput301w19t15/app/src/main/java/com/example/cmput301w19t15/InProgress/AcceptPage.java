package com.example.cmput301w19t15.InProgress;
//:)
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.cmput301w19t15.R;
import com.example.cmput301w19t15.Activity.RequestedBookList;

/**
 * this activity is intended to allow user to accept book request
 * @see RequestedBookList
 */
public class AcceptPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_page);
    }
}
