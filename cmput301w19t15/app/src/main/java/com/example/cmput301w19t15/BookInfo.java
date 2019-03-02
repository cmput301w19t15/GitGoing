package com.example.cmput301w19t15;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BookInfo extends AppCompatActivity {
    //on create happens when book in list of my books is clicked
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

        //get intents/book from onclick in books list
        Intent intent = getIntent();
        final Book book = (Book) intent.getSerializableExtra("Book");

        //display book information as edit text
        final EditText title = (EditText)findViewById(R.id.editMyBookTitle);
        final EditText author = (EditText)findViewById(R.id.editMyBookAuthor);
        final EditText ISBN = (EditText)findViewById(R.id.editMyBookISBN);

        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        ISBN.setText(book.getISBN());


        //Scan book// to do

        //update any edits to book *** editing should only be possible if user is owner ***
        //also i could put this in a save button instead tbh
        //then i wouldn't need the listeners...will consider in the future. I do need a scan book thing either way
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                book.setTitle(title.getText().toString());
            }
        });
        author.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                book.setAuthor(author.getText().toString());
            }
        });
        ISBN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String temp = ISBN.getText().toString();
                Integer isbn = Integer.valueOf(temp);
                book.setISBN(isbn);
            }
        });



        //>>>>>>>This class is called so far only if clicked in mybooks list, will need to add intent to other lists(find books,borrower books) to change that
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
