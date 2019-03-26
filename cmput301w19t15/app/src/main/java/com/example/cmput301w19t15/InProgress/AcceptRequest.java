package com.example.cmput301w19t15.InProgress;
//:)
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.example.cmput301w19t15.Objects.Book;
import com.example.cmput301w19t15.Activity.MainActivity;
import com.example.cmput301w19t15.Objects.Notification;
import com.example.cmput301w19t15.R;
import com.example.cmput301w19t15.Objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AcceptRequest extends AppCompatActivity {

        private Button exit,request,decline;
        private Book newBook;
        private User owner;
        private String notifID;
        private Notification notif2, notif3;
        User loggedInUser = MainActivity.getUser();
        String ownerID, borrowerID, author, title, borrowerEmail, isbn, status, bookId, ownerEmail;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_accept_request);

            /**
             * save the book values passed from the FindBooks classes when clicked on
             */
            final Notification notif = (Notification) getIntent().getSerializableExtra("Notification");
            notifID = (String) getIntent().getSerializableExtra("NotifID");
            notif.setNotifID(notifID);

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

            /**
             * owner accepts the request
             */
            request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //addBookToRequest();
                    //Notification notif = new Notification("requested", bookId, loggedInUser.getUserID(), ownerId);


                    //pick notification table to save the notif
                    notif2 = new Notification("accepted", notif.getBookID(), notif.getTitle(), notif.getNotifyToID(), notif.getNotifyToEmail(),
                            notif.getNotifyFromID(), notif.getNotifyFromEmail(), notif.getISBN(), notif.getPhoto(), false);
                    ownerID = loggedInUser.getUserID();
                    ownerEmail = loggedInUser.getEmail();
                    DatabaseReference newNotif = FirebaseDatabase.getInstance().getReference().child("notifications").child(notif2.getNotifID());

                    //add notif to database
                    //newNotif.setValue(notif2);
                    newNotif.setValue(notif2).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AcceptRequest.this, "Successfully Added Notification ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    //add to borrower's myAcceptedRequests
                    addBookToAccepted();

                    notif3 = new Notification("acceptedOwner", notif.getBookID(), notif.getTitle(), notif.getNotifyFromID(), notif.getNotifyFromEmail(),
                            notif.getNotifyToID(), notif.getNotifyToEmail(), notif.getISBN(), notif.getPhoto(), false);
                    DatabaseReference newNotif2 = FirebaseDatabase.getInstance().getReference().child("notifications").child(notif3.getNotifID());

                    //add notif to database
                    newNotif2.setValue(notif3).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AcceptRequest.this, "Successfully Added Notification ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    FirebaseDatabase.getInstance().getReference("notifications").child(notif.getNotifID()).removeValue();
                    finish();
                    //Intent intent = new Intent(AcceptRequest.this, NotifyActivity.class);
                    //startActivity(intent);
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
        /*private void addBookToAccepted(){
            FirebaseDatabase.getInstance().getReference("users")
                    .orderByChild("userID").addListenerForSingleValueEvent(new ValueEventListener() {
                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        if (child.getKey().equals(ownerID)) {
                            //owner = (child.getValue(User.class));
                            owner = new User(ownerEmail, ownerID);
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
                    borrower.child("myRequestedBooksAccepted").setValue(newBook);
                    finish();
                }
                public void onCancelled(DatabaseError databaseError) {
                    // ...
                }
            });

        }*/

    /**
     * add owner's book to accepted requests for the borrower
     */
    public void addBookToAccepted(){
            DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("users").child(loggedInUser.getUserID()).child("myBooks");
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
                                    //borrowerID.addToMyRequestedBooks(book);
                                    book.setStatus("Accepted");
                                    book.setBorrowerID(borrowerID);
                                    DatabaseReference borrower = FirebaseDatabase.getInstance().getReference().child("users")
                                            .child(borrowerID);
                                    //borrower.child("myRequestedBooksAccepted").setValue(book);
                                    User user = new User(borrowerEmail, borrowerID);
                                    user.addToMyRequestedBooks(book);
                                    /**
                                     * update the original and owner's book to borrowed status
                                     */
                                    FirebaseDatabase.getInstance().getReference().child("books").child(book.getBookID()).setValue(book);
                                    FirebaseDatabase.getInstance().getReference().child("users").child(loggedInUser.getUserID()).child("myBooks").child(book.getBookID()).setValue(book);
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


