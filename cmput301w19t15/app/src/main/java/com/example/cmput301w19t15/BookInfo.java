package com.example.cmput301w19t15;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookInfo extends AppCompatActivity {
    //on create happens when book in list of my books is clicked
    User loggedInUser;
    Book book;
    String bookID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        loggedInUser = MainActivity.getUser();


        bookID = (String) getIntent().getExtras().getString("BOOKID");
        final int position = (int) getIntent().getExtras().getInt("POSITION");
        book = loggedInUser.getMyBooks().get(position);
        //display book information as edit text
        final EditText titleEditText = findViewById(R.id.editMyBookTitle);
        final EditText authorEditText = findViewById(R.id.editMyBookAuthor);
        final EditText ISBNEditText = findViewById(R.id.editMyBookISBN);

        titleEditText.setText(book.getTitle());
        authorEditText.setText(book.getAuthor());
        ISBNEditText.setText(book.getISBN());

        Button updateBook = findViewById(R.id.updateBook);
        updateBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString();
                String author = authorEditText.getText().toString();
                String isbn = ISBNEditText.getText().toString();
                loggedInUser.removeMyBooks(book);
                book.setTitle(title);
                book.setAuthor(author);
                book.setISBN(isbn);
                loggedInUser.addToMyBooks(book);
                FirebaseDatabase.getInstance().getReference("books").child(book.getBookID()).setValue(book);// update books
                finish();
            }
        });

        Button deleteBook = findViewById(R.id.deleteBook);
        deleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loggedInUser.removeMyBooks(book);
                FirebaseDatabase.getInstance().getReference("books").child(book.getBookID()).removeValue();// delete book
                finish();
            }
        });



        Button cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
