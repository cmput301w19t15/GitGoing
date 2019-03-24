package com.example.cmput301w19t15;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AcceptRequest extends AppCompatActivity {

        private Button exit,request,decline;
        private Book newBook;
        private User owner;
        User loggedInUser = MainActivity.getUser();
        String ownerID, borrowerID, author, title, borrowerEmail, isbn, status, bookId;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_accept_request);

            /**
             * save the book values passed from the FindBooks classes when clicked on
             */
            final Notification notif = (Notification) getIntent().getSerializableExtra("Notification");


            /**
             * set the text field with the values that was passed over
             */

            TextView titleText = (TextView) findViewById(R.id.booktitle);
            titleText.setText(notif.getTitle());
            TextView isbnText = (TextView) findViewById(R.id.isbn);
            isbnText.setText(notif.getISBN());
            TextView ownerEmailText = (TextView) findViewById(R.id.owner);
            ownerEmailText.setText(notif.getNotifyFromEmail());
            bookId = notif.getBookID();
            borrowerID = notif.getNotifyFromID();
            borrowerEmail = notif.getNotifyFromEmail();

            request = (Button) findViewById(R.id.accept_button);

            request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //addBookToRequest();
                    //Notification notif = new Notification("requested", bookId, loggedInUser.getUserID(), ownerId);


                    //pivk notification table to save the notif
                    final Notification notif2 = new Notification("accepted", notif.getBookID(), notif.getTitle(), notif.getNotifyToID(), notif.getNotifyToEmail(),
                            notif.getNotifyFromID(), notif.getNotifyFromEmail(), notif.getISBN(), notif.getPhoto(), false);
                    ownerID = loggedInUser.getUserID();
                    DatabaseReference newNotif = FirebaseDatabase.getInstance().getReference().child("notifications").child(notif2.getNotifID());

                    //add notif to database
                    //newNotif.setValue(notif2);
                    newNotif.setValue(notif2).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(com.example.cmput301w19t15.AcceptRequest.this, "Successfully Added Notification ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    //add to borrower's myAcceptedRequests
                    addBookToAccepted();
                    finish();
                }
            });

            exit = (Button) findViewById(R.id.cancel);
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            decline= (Button) findViewById(R.id.decline_button);
            decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FirebaseDatabase.getInstance().getReference("notifications").child(notif.getNotifID()).removeValue();
                    finish();
                }
            });


        }
        /**
         * add the book to to borrower's accepted books
         * and add the book as a book that has been accepted
         * updates firebase
         */
        private void addBookToAccepted(){
            FirebaseDatabase.getInstance().getReference("users")
                    .orderByChild("userID").addListenerForSingleValueEvent(new ValueEventListener() {
                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        if (child.getKey().equals(ownerID)) {
                            owner = (child.getValue(User.class));
                            ArrayList<Book> ownersBooks = owner.getMyBooks();
                            for (Book book : ownersBooks) {
                                if (bookId.equals(book.getBookID())) {
                                    newBook = new Book(book);
                                    newBook.setBorrowerID(borrowerID);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    DatabaseReference borrower = FirebaseDatabase.getInstance().getReference().child("users")
                            .child(borrowerID);
                    borrower.child("myAcceptedBooks").setValue(newBook);
                    //borrower.addToMyRequestedBooksAccepted(newBook);
                    //loggedInUser.addToMyRequestedBooks(newBook);
                    finish();
                }
                public void onCancelled(DatabaseError databaseError) {
                    // ...
                }
            });

        }

        }


