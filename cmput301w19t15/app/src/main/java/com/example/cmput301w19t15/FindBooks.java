package com.example.cmput301w19t15;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class FindBooks extends AppCompatActivity {

    // clicking on a book should take to BookInfo activity where it can be requested

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_books);
    }
}
