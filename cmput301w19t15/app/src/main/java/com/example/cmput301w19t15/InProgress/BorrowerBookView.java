package com.example.cmput301w19t15.InProgress;
//:)
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cmput301w19t15.Activity.MainActivity;
import com.example.cmput301w19t15.Objects.Book;
import com.example.cmput301w19t15.Objects.User;
import com.example.cmput301w19t15.R;

/**
 * this is intended to list borrowers and display it
 * work in progress
 */

public class BorrowerBookView extends AppCompatActivity {
    private Button returnBook,location,scanBook;
    private Book book;
    private String bookID, author,title,ownerID,ISBN,status,ownerEmail;
    private Integer postion;
    private User owner;
    private ImageView image;
    User loggedInUser = MainActivity.getUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_book_view);


        //get book info and display it
        Bundle bundle = getIntent().getExtras();
        ownerID = (String) bundle.get("OWNERID");
        author = (String) bundle.get("AUTHOR");
        ownerEmail = (String) bundle.get("OWNEREMAIL");
        ISBN = (String) bundle.get("ISBN");
        title = (String) bundle.get("TITLE");
        status = (String) bundle.get("STATUS");
        bookID = (String) bundle.get("BOOKID");
        returnBook = (Button) findViewById(R.id.return_book);

        TextView authorText = (TextView) findViewById(R.id.bookauthor_view);
        authorText.setText(author);
        TextView titleText = (TextView) findViewById(R.id.booktitle_view);
        titleText.setText(title);
        TextView isbnText = (TextView) findViewById(R.id.isbn_view);
        isbnText.setText(ISBN);
        TextView ownerEmailText = (TextView) findViewById(R.id.owner_view);
        ownerEmailText.setText(ownerEmail);
        TextView statusText = (TextView) findViewById(R.id.status_view);
        statusText.setText(status);

        //hide return button


        if(status == "borrowed"){
            returnBook.setVisibility(View.VISIBLE);
        }
        else{
            returnBook.setVisibility(View.INVISIBLE);
        }
        //display due date?

        //when borrowed, maybe on scan from notification, and book to borrowed and set due date?
        //create return button that removes book from borrowed,


    }
}
