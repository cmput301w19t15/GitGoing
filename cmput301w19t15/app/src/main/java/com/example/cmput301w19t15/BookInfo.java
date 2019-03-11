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
    /**
     *User is able to edit or delete the book they have selected
     */
    User loggedInUser;
    Book book;
    String bookID;
    EditText titleEditText,authorEditText,ISBNEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        loggedInUser = MainActivity.getUser();

        //Get book values from the MyBook class the user has clicked on the book
        bookID = (String) getIntent().getExtras().getString("BOOKID");
        final int position = (int) getIntent().getExtras().getInt("POSITION");
        book = loggedInUser.getMyBooks().get(position);
        //display book information as edit text
        titleEditText = findViewById(R.id.editMyBookTitle);
        authorEditText = findViewById(R.id.editMyBookAuthor);
        ISBNEditText = findViewById(R.id.editMyBookISBN);
        //set the text as the values the book currently is set to
        titleEditText.setText(book.getTitle());
        authorEditText.setText(book.getAuthor());
        ISBNEditText.setText(book.getISBN());
        ////need to get and set the current image - user will be able to update or delete the image

        Button updateBook = findViewById(R.id.updateBook);
        updateBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBook();
            }
        });

        Button deleteBook = findViewById(R.id.deleteBook);
        deleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBook();
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

    /**
     * When the User clicks update book it will remove the book from the user and add
     * the new book then update firebase
     */
    private void updateBook(){
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
    /**
     * Remove the book from the users class and and remove the book from firebase
     * --need to remove book from other users that have requested the books
     */
    private void deleteBook(){
        loggedInUser.removeMyBooks(book);
        FirebaseDatabase.getInstance().getReference("books").child(book.getBookID()).removeValue();// delete book
        finish();
    }
}
