package com.example.cmput301w19t15.InProgress;
//:)
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301w19t15.Activity.CreateRequest;
import com.example.cmput301w19t15.Activity.MainActivity;
import com.example.cmput301w19t15.Functions.FetchBookWithID;
import com.example.cmput301w19t15.Objects.Book;
import com.example.cmput301w19t15.Objects.Notification;
import com.example.cmput301w19t15.Objects.User;
import com.example.cmput301w19t15.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * this is intended to list borrowers and display it
 *
 */

public class BorrowerBookView extends AppCompatActivity {
    private Button returnBook,location,scanBook;
    private Book book;
    private String bookID,status;
    private ArrayList<Book> newBook = new ArrayList<>();
    private Integer postion;
    private User owner;
    User loggedInUser = MainActivity.getUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_book_view);

        /**
         * get book information and display it
         */
        Bundle bundle = getIntent().getExtras();
        status = (String) bundle.get("STATUS");
        bookID = (String) bundle.get("BOOKID");
        location = (Button) findViewById(R.id.location);


        TextView titleText = (TextView) findViewById(R.id.booktitle_view);
        TextView authorText = (TextView) findViewById(R.id.bookauthor_view);
        TextView isbnText = (TextView) findViewById(R.id.isbn_view);
        TextView ownerEmailText = (TextView) findViewById(R.id.owner_view);
        TextView statusText = (TextView) findViewById(R.id.status_view);

        new FetchBookWithID(newBook,titleText,authorText,isbnText,ownerEmailText,statusText).execute(bookID);

        /**
         * hide return button unless book is borrowed
         * hide location button unless book is either accepted or borrowed
         */
        returnBook = (Button) findViewById(R.id.return_book);
        if(status.equals("borrowed")){
            returnBook.setVisibility(View.VISIBLE);
        }
        else{
            returnBook.setVisibility(View.INVISIBLE);
        }

        location.setVisibility(View.INVISIBLE);

        //display due date?

        //when borrowed, maybe on scan from notification, and book to borrowed and set due date?
        //create return button that removes book from borrowed,
        //on click return we should
        returnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = newBook.get(0);
                Notification notif = new Notification("ReturnRequest", bookID, book.getTitle(), loggedInUser.getUserID(), loggedInUser.getEmail(), book.getOwnerID(), book.getOwnerEmail(), book.getISBN(), book.getPhoto(),false);
                //pick notification table to save the notif
                DatabaseReference newNotif = FirebaseDatabase.getInstance().getReference().child("notifications").child(notif.getNotifID());

                //add notif to database
                newNotif.setValue(notif).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(BorrowerBookView.this, "Successfully Added Notification", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //need to view location from where you picked up the book
                //step one: start geolocation activity
                //Intent intent = new Intent(BorrowerBookView.this, GeoLocation.class);
                //add in location as extra intent or something
                //idk where or how we are getting location from notifications and such


            }
        });
    }
}
