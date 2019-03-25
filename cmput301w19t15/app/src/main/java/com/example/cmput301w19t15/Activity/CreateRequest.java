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

import com.example.cmput301w19t15.Objects.Notification;
import com.example.cmput301w19t15.Objects.Book;
import com.example.cmput301w19t15.R;
import com.example.cmput301w19t15.InProgress.Request;
import com.example.cmput301w19t15.Objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private Book newBook;
    private User owner;
    User loggedInUser = MainActivity.getUser();
    String ownerId, author, title, ownerEmail, isbn, status, bookId, photo;
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
        photo = (String) bundle.get("PHOTO");

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
                Notification notif = new Notification("requested", bookId, title, loggedInUser.getUserID(), loggedInUser.getEmail(),
                        ownerId, ownerEmail, isbn, photo, false);
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
    /*private void addBookToRequest(){
        FirebaseDatabase.getInstance().getReference("users")
                .orderByChild("userID").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    Log.d("TAG: ", "I'M HEREEEEEEEEE");
                    if (child.getKey().equals(ownerId)) {
                        //owner = (child.getValue(User.class));
                        owner = new User(ownerEmail, ownerId);
                        ArrayList<Book> ownersBooks = owner.getMyBooks();
                        for (Book book : ownersBooks) {
                            if (bookId.equals(book.getBookID())) {
                                String borrowerID = loggedInUser.getUserID();
                                newBook = new Book(book);
                                newBook.setBorrowerID(borrowerID);
                                DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("users").child(loggedInUser.getUserID());
                                user.child("myRequestedBooksAccepted").setValue(newBook);
                                Log.d("TAG: ", "SAVE MEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");

                                Log.d("TAG: ", "SAVE MEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE" + newBook.getTitle());
                                //owner.addToMyRequestedBooks(newBook);
                                break;
                            }
                            else{
                                Log.d("TAG ", "NOT MATCH "+ bookId + " " + book.getBookID());
                            }
                        }
                        break;
                    }
                }
                owner.addToMyRequestedBooks(newBook);
                //Log.d("TAG: ", "SAVE MEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE" + newBook.getTitle());
                //loggedInUser.addToMyRequestedBooks(newBook);
                //DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("users").child(loggedInUser.getUserID());
                //user.child("myRequestedBooksAccepted").setValue(newBook);
                finish();
            }
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
    }*/

    public interface loadBookCallBack {
        /**
         * Load book call back.
         *
         * @param value the value
         */
        void loadBookCallBack(ArrayList<Book> value);
    }


    public void addBookToRequest(){
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("users").child(ownerId).child("myBooks");
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    try {
                        //ArrayList<Book> allBooks = new ArrayList<>();
                        for (DataSnapshot books : dataSnapshot.getChildren()) {
                            Log.d("TAG", "HEREEEEEEEee");
                            Book book = books.getValue(Book.class);
                            if (book.getBookID().equals(bookId)){
                                loggedInUser.addToMyRequestedBooks(book);
                            }
                            //allBooks.add(book);
                        }
                        //myCallback.loadBookCallBack(allBooks);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("testing","Error: ", databaseError.toException());
            }
        });
    }
}