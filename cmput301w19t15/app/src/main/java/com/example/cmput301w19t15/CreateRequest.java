package com.example.cmput301w19t15;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * this activity is intended to send request to owner of the book,
 * at current state, it will change the status of the book and
 * update in firebase
 * @version : 1.0
 * @see : Request, MyBooks, Fingbooks
 */
public class CreateRequest extends AppCompatActivity {

    private Button request,cancel;
    private Book newBook;
    private User owner;
    User loggedInUser = MainActivity.getUser();
    String ownerId, author, title, ownerEmail, isbn, status, bookId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);

        /**
         * save the book values passed from the FindBooks classes when clicked on
         */
        Bundle bundle = getIntent().getExtras();
        ownerId = (String) bundle.get("OWNERID");
        author = (String) bundle.get("AUTHOR");
        ownerEmail = (String) bundle.get("OWNEREMAIL");
        isbn = (String) bundle.get("ISBN");
        title = (String) bundle.get("TITLE");
        status = (String) bundle.get("STATUS");
        bookId = (String) bundle.get("BOOKID");

        /**
         * set the text field with the values that was passed over
         */
        TextView authorText = (TextView) findViewById(R.id.bookauthor);
        authorText.setText(author);
        TextView titleText = (TextView) findViewById(R.id.booktitle);
        titleText.setText(title);
        TextView isbnText = (TextView) findViewById(R.id.isbn);
        isbnText.setText(isbn);
        TextView ownerEmailText = (TextView) findViewById(R.id.owner);
        ownerEmailText.setText(ownerEmail);
        TextView statusText = (TextView) findViewById(R.id.status);
        statusText.setText(status);
        request = (Button) findViewById(R.id.request_button);

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBookToRequest();
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
    private void addBookToRequest(){
        FirebaseDatabase.getInstance().getReference("users")
                .orderByChild("userID").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    if (child.getKey().equals(ownerId)) {
                        owner = (child.getValue(User.class));
                        ArrayList<Book> ownersBooks = owner.getMyBooks();
                        for (Book book : ownersBooks) {
                            if (bookId.equals(book.getBookID())) {
                                String borrowerID = loggedInUser.getUserID();
                                newBook = new Book(book);
                                newBook.setBorrowerID(borrowerID);
                                break;
                            }
                        }
                        break;
                    }
                }
                owner.addToRequestedBooks(newBook);
                loggedInUser.addToMyRequestedBooks(newBook);
                finish();
            }
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
    }
}