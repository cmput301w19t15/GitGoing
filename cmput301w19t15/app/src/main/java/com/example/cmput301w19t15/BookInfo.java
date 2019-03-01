package com.example.cmput301w19t15;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BookInfo extends AppCompatActivity {
    //on create happens when book in list of my books is clicked
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

        //get intents/book from on click in my books list
        Intent intent = getIntent();
        final Book book = (Book) intent.getSerializableExtra("Book");

        //display book information as edit text
        EditText title = (EditText)findViewById(R.id.editMyBookTitle);
        EditText author = (EditText)findViewById(R.id.editMyBookAuthor);
        EditText ISBN = (EditText)findViewById(R.id.editMyBookISBN);

        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        ISBN.setText(book.getISBN());


        /*
        // only show if user is not the owner of this book
        Button requestButton = findViewById(R.id.request);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make the request object
                // notify owner about request
                book.setStatus("requested");

            }
        });
        */

    }
}
