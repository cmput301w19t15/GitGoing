package com.example.cmput301w19t15.InProgress;
//:)
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

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
    private User owner;
    User loggedInUser = MainActivity.getUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_book_view);

        //need to get book info and display it (uneditable)
        //display due date?
        //display status
        //store owner info and borrower info somehow also
        //when borrowed, maybe on scan from notification, and book to borrowed and set due date?
        //create return button that removes book from borrowed,

        /* if borrowed:
                display return button
         */
    }
}
