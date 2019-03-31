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
    private User owner;
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
                //checkIfExists();
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
        owner = new User(ownerEmail, ownerId);

        DatabaseReference ownerReference = FirebaseDatabase.getInstance().getReference().child("users").child(ownerId).child("myBooks");
        ownerReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        //ArrayList<Book> allBooks = new ArrayList<>();
                        for (DataSnapshot books : dataSnapshot.getChildren()) {
                            Book currBook = books.getValue(Book.class);
                            if (currBook.getBookID().equals(bookId)) {
                                /**
                                 * check status
                                 */
                                if (currBook.getStatus().equals("Accepted") || currBook.getStatus().equals("Borrowed")) {
                                    loggedInUser.addToWatchList(currBook);
                                    Toast.makeText(CreateRequest.this, "Added to Watchlist", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {

                                    /**
                                     *
                                     * check if already requested by user
                                     */
                                    Toast.makeText(CreateRequest.this, "Trying", Toast.LENGTH_SHORT).show();
                                    DatabaseReference bookReference = FirebaseDatabase.getInstance().getReference().child("users")
                                            .child(loggedInUser.getUserID()).child("myRequestedBooks");
                                    bookReference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                try {
                                                    boolean exists = false;
                                                    for (DataSnapshot reqBook : dataSnapshot.getChildren()) {
                                                        Book requestedBook = reqBook.getValue(Book.class);
                                                        if (requestedBook.getBookID().equals(bookId)) {
                                                            exists = true;
                                                        }
                                                    }
                                                    /**
                                                     * add book if exists false
                                                     */
                                                    if (!exists) {
                                                        DatabaseReference ownerReference = FirebaseDatabase.getInstance().getReference().child("users").child(ownerId).child("myBooks");
                                                        ownerReference.addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                if (dataSnapshot.exists()) {
                                                                    try {
                                                                        ArrayList<Book> allBooks = new ArrayList<>();
                                                                        for (DataSnapshot books : dataSnapshot.getChildren()) {
                                                                            Book currBook = books.getValue(Book.class);
                                                                            if (currBook.getBookID().equals(bookId)) {
                                                                                loggedInUser.addToMyRequestedBooks(currBook);

                                                                                Notification notif = new Notification("Requested", bookId, title, loggedInUser.getUserID(), loggedInUser.getEmail(),
                                                                                        ownerId, ownerEmail, isbn, photo, false);
                                                                                //pick notification table to save the notif
                                                                                DatabaseReference newNotif = FirebaseDatabase.getInstance().getReference().child("notifications").child(notif.getNotifID());

                                                                                //add notif to database
                                                                                Log.d("HEY", "WHY THO");
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

                                                                    } catch (Exception e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }

                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                    }
                                                    else{
                                                        Toast.makeText(CreateRequest.this, "Book Already Requested", Toast.LENGTH_SHORT).show();
                                                    }

                                                } catch (Exception e){
                                                    e.printStackTrace();
                                                }
                                            }
                                            else {
                                                Toast.makeText(CreateRequest.this, "Should add new", Toast.LENGTH_SHORT).show();
                                                DatabaseReference ownerReference = FirebaseDatabase.getInstance().getReference().child("users").child(ownerId).child("myBooks");
                                                ownerReference.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        if (dataSnapshot.exists()) {
                                                            try {
                                                                ArrayList<Book> allBooks = new ArrayList<>();
                                                                for (DataSnapshot books : dataSnapshot.getChildren()) {
                                                                    Book currBook = books.getValue(Book.class);
                                                                    if (currBook.getBookID().equals(bookId)) {
                                                                        loggedInUser.addToMyRequestedBooks(currBook);

                                                                        Notification notif = new Notification("Requested", bookId, title, loggedInUser.getUserID(), loggedInUser.getEmail(),
                                                                                ownerId, ownerEmail, isbn, photo, false);
                                                                        //pick notification table to save the notif
                                                                        DatabaseReference newNotif = FirebaseDatabase.getInstance().getReference().child("notifications").child(notif.getNotifID());

                                                                        //add notif to database
                                                                        Log.d("HEY", "WHY THO");
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

                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }
                                                        }

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }

                                    });
                                }
                            }

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }














    public interface loadBookCallBack {
        /**
         * Load book call back.
         *
         * @param value the value
         */
        void loadBookCallBack(ArrayList<Book> value);
    }

    public void checkIfExists() {
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("users").child(loggedInUser.getUserID()).child("myRequestedBooks");
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean cont = false;
                if (dataSnapshot.exists()) {
                    try {
                        //ArrayList<Book> allBooks = new ArrayList<>();
                        for (DataSnapshot books : dataSnapshot.getChildren()) {
                            Book book = books.getValue(Book.class);
                            if (!book.getBookID().equals(bookId)) {
                                cont = true;
                                Log.e("HI","YOU'RE A BOLD ONE");
                            }
                            //allBooks.add(book);
                        }
                        Log.d("TAG", ""+cont);
                        if (cont) {
                            Toast.makeText(CreateRequest.this, "Request Already Added", Toast.LENGTH_SHORT).show();
                        }
                        //myCallback.loadBookCallBack(allBooks);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("testing", "Error: ", databaseError.toException());
              
       final Book book = newBook.get(0);
        if (book.getStatus().equals("Accepted") || book.getStatus().equals("Borrowed")) {
            if(book.getBorrowerID().equalsIgnoreCase(loggedInUser.getUserID())) {
                Toast.makeText(CreateRequest.this, "You are currently borrowing this book", Toast.LENGTH_SHORT).show();
            }else{
                loggedInUser.addToWatchList(book);
                Toast.makeText(CreateRequest.this, "Added to Watch List", Toast.LENGTH_SHORT).show();
            }
        }else{
            //check if already requested by user
            String requestBookIDList = loggedInUser.getMyRequestedBooksID().toString();
            if (requestBookIDList.contains(bookID)) {
                Toast.makeText(CreateRequest.this, "Already in Watch List", Toast.LENGTH_SHORT).show();
            }else{
                loggedInUser.addToMyRequestedBooksID(bookID);
                Notification notif = new Notification("Requested", book.getBookID(), book.getTitle(), loggedInUser.getUserID(), loggedInUser.getEmail(),
                        book.getOwnerID(), book.getOwnerEmail(), book.getISBN(), book.getPhoto(), false);
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