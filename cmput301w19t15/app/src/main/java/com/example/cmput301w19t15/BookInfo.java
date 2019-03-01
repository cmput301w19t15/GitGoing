package com.example.cmput301w19t15;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BookInfo extends AppCompatActivity {
//on create happens when book in list of my books is clicked
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        //get intents/book from on click in my books list
        Intent intent = getIntent();
        Book book = (Book) intent.getSerializableExtra("Book");
        //display book information as edit text

    }
}
