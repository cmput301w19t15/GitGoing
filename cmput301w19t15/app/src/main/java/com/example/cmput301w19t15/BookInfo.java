package com.example.cmput301w19t15;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class BookInfo extends AppCompatActivity {
    //on create happens when book in list of my books is clicked////editable because of this, but there should be an uneditable version for book info from other users
    private User loggedinuser;
    private Book book;
    private ArrayList<Book> mBookList;
    private Integer pos;
    private Button saveButton, scanButton, deleteButton;
    private EditText title, author, ISBN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

        //get intents/book id from on click in my books list
        Bundle bundle = getIntent().getExtras();
        pos = bundle.getInt("POS");

        //get current user and load the book from them
        loggedinuser = MainActivity.getUser();
        mBookList = loggedinuser.getMyBooks();
        book = mBookList.get(pos);
        //display book information as edit text and image, but i don't know how to display the image yet tbh
        title = findViewById(R.id.editMyBookTitle);
        author = findViewById(R.id.editMyBookAuthor);
        ISBN = findViewById(R.id.editMyBookISBN);
        //ImageView image = findViewById(R.id.imageView2);


        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        ISBN.setText(book.getISBN());
        //image.setImageDrawable(book.getPhoto());

        saveButton = findViewById(R.id.saveInfo);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTitle = title.getText().toString();
                String newAuthor = author.getText().toString();
                String newISBN = ISBN.getText().toString();
                book.setTitle(newTitle);
                book.setAuthor(newAuthor);
                book.setISBN(newISBN);
                finish();
                ///currently updates list but does not show until you go to main menu and then back to mybooks again. have to fix this.

            }
        });
/*
        /*
        // only show if user is not the owner of this book
        /*
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
