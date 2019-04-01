package com.example.cmput301w19t15.Activity;
//:)
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301w19t15.Functions.FetchBookWithID;
import com.example.cmput301w19t15.Objects.Notification;
import com.example.cmput301w19t15.Objects.Book;
import com.example.cmput301w19t15.R;
import com.example.cmput301w19t15.InProgress.Request;
import com.example.cmput301w19t15.Objects.User;
//import com.google.android.gms.auth.api.credentials.IdToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * this activity is intended to send request to owner of the book,
 * at current state, it will change the status of the book and
 * update in firebase
 * @version : 1.0
 * @see Request
 * @see MyBooks
 * @see FindBooks
 */
public class CreateRequest extends AppCompatActivity {

    private Button request,cancel;
    private ArrayList<Book> newBook = new ArrayList<>();
    User loggedInUser = MainActivity.getUser();
    String bookID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);

        /**
         * save the book values passed from the FindBooks classes when clicked on
         */
        Bundle bundle = getIntent().getExtras();
        bookID = (String) bundle.get("BOOKID");
        /**
         * set the text field with the values that was passed over
         */
        TextView titleText = (TextView) findViewById(R.id.booktitle);
        TextView authorText = (TextView) findViewById(R.id.bookauthor);
        TextView isbnText = (TextView) findViewById(R.id.isbn);
        TextView ownerEmailText = (TextView) findViewById(R.id.owner);
        TextView statusText = (TextView) findViewById(R.id.status);

        new FetchBookWithID(newBook,titleText,authorText,isbnText,ownerEmailText,statusText).execute(bookID);

        request = (Button) findViewById(R.id.request_button);

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBookToRequest();
                finish();
            }
        });

        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    /**
     * add the book to to my requested books for the user requesting the book
     * and add the book as a book that has been requested
     * updates firebase
     */
    private void addBookToRequest() {
        final Book book = newBook.get(0);
        if (book.getStatus().equals("Accepted") || book.getStatus().equals("Borrowed")) {
            if(book.getBorrowerID().equalsIgnoreCase(loggedInUser.getUserID())) {
                Toast.makeText(CreateRequest.this, "You are currently borrowing this book", Toast.LENGTH_SHORT).show();
            }else{
                loggedInUser.addToMyWatchListBooksID(book.getBookID());
                Toast.makeText(CreateRequest.this, "Added to Watch List", Toast.LENGTH_SHORT).show();
            }
        }else{
            //check if already requested by user
            String requestBookIDList = loggedInUser.getMyRequestedBooksID().toString();
            String watchListBookIDList = loggedInUser.getMyWatchListBooksID().toString();

            if (requestBookIDList.contains(bookID)) {
                Toast.makeText(CreateRequest.this, "Already Requested", Toast.LENGTH_SHORT).show();
            } else if (watchListBookIDList.contains(bookID)){
                Toast.makeText(this, "Already in Watchlist", Toast.LENGTH_SHORT).show();
            } else{
                loggedInUser.addToMyRequestedBooksID(bookID);
                Notification notif = new Notification("Requested", book.getBookID(), book.getTitle(), loggedInUser.getUserID(), loggedInUser.getEmail(),
                        book.getOwnerID(), book.getOwnerEmail(), book.getISBN(), book.getPhoto(), false);

                //update book status
                book.setStatus("Requested");
                DatabaseReference newBook = FirebaseDatabase.getInstance().getReference().child("books").child(bookID);
                newBook.setValue(book).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("tesing", "set status to requested");
                    }
                });

                //pick notification table to save the notif
                DatabaseReference newNotif = FirebaseDatabase.getInstance().getReference().child("notifications").child(notif.getNotifID());

                //add notif to database
                newNotif.setValue(notif).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CreateRequest.this, "Successfully Added Notification", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
}
