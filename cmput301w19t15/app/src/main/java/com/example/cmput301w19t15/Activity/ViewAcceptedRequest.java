package com.example.cmput301w19t15.Activity;
//:)
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301w19t15.Functions.FetchBookWithID;
import com.example.cmput301w19t15.Functions.ScanBarcode;
import com.example.cmput301w19t15.Objects.Notification;
import com.example.cmput301w19t15.Objects.Book;
import com.example.cmput301w19t15.R;
import com.example.cmput301w19t15.Objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ViewAcceptedRequest extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private Button exit, request, decline, scan, verify;
    private ArrayList<Book> newBook = new ArrayList<>();
    private User owner;
    User loggedInUser = MainActivity.getUser();
    String ownerId, author, title, ownerEmail, isbn, status, bookId, correctScan;
    private ZXingScannerView scannerView;
    Integer SCAN_ISBN = 3;
    Notification notif;
    TextView authorText,statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_accepted_request);

        /**
         * save the book values passed from the FindBooks classes when clicked on
         */
        final Notification notif = (Notification) getIntent().getSerializableExtra("Notification");
        TextView titleText = (TextView) findViewById(R.id.booktitle);
        titleText.setText(notif.getTitle());
        TextView isbnText = (TextView) findViewById(R.id.isbn);
        isbnText.setText(notif.getISBN());
        TextView ownerEmailText = (TextView) findViewById(R.id.owner);
        ownerEmailText.setText(notif.getNotifyFromEmail());
        Log.d("hello", "youri bad");
        final TextView scanStatus = (TextView) findViewById(R.id.scan_status);
        isbn =  notif.getISBN();
        correctScan = "false";
        if (correctScan.equals("false")){
            scanStatus.setText("Scan Incomplete");
        }
        exit = (Button) findViewById(R.id.cancel);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        verify = (Button) findViewById(R.id.verify);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctScan.equals("true")) {
                    scanStatus.setText("Scan Complete");
                    loggedInUser.addToMyBorrowedBooks(bookId);
                    try {
                        FirebaseDatabase.getInstance().getReference().child("books").child(bookId).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    try {
                                        Book book = dataSnapshot.getValue(Book.class);
                                        Book bookNew = new Book(book.getTitle(), book.getAuthor(), book.getISBN(), book.getPhoto(), book.getOwnerEmail(),
                                                book.getOwnerID(), book.getRating(), book.getRatingCount(), book.getRatingTotal());
                                        bookNew.setBookID(bookId);
                                        bookNew.setStatus("Borrowed");
                                        DatabaseReference newBook = FirebaseDatabase.getInstance().getReference().child("books").child(bookId);
                                        newBook.setValue(bookNew).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
                                        createReturnNotifications(notif);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.w("testing", "Error: ", databaseError.toException());
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Isbn scan not complete",Toast.LENGTH_LONG).show();
                }
            }
        });
        Log.d("hello", notif.getOwnerScanned());
        if (notif.getOwnerScanned().equals("True")){
            Toast.makeText(getApplicationContext(),"Isbn scan matched",Toast.LENGTH_LONG).show();
            Log.d("hello", "youris bad");
            scan = (Button) findViewById(R.id.scan);
            scan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scan(v);
                }
            });
        }

    }
    /*
    @Override
    protected void onResume(Bundle savedInstanceState) {
        super.onResume();
        if (notif.getOwnerScanned() == Boolean.TRUE){
            Log.d("hello", "youris bad");
            scan = (Button) findViewById(R.id.scan);
            scan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scan(v);
                }
            });
        }
    }
    */
    public void scan(View view){
        //https://github.com/ravi8x/Barcode-Reader
        Intent scannerIntent = new Intent(ViewAcceptedRequest.this, ScanBarcode.class);
        startActivityForResult(scannerIntent,SCAN_ISBN);
    }

    @Override
    public void handleResult(Result result){
        Toast.makeText(getApplicationContext(),result.getText(),Toast.LENGTH_LONG).show();
        scannerView.resumeCameraPreview(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Toast.makeText(getApplicationContext(), "Result ok", Toast.LENGTH_LONG).show();
            if (requestCode == SCAN_ISBN) {
                String barcode = data.getStringExtra("ISBN");
                //Toast.makeText(getApplicationContext(), "got proper request code", Toast.LENGTH_LONG).show();
                if (barcode.equals(isbn)) {
                    Toast.makeText(getApplicationContext(), "Isbn scan matched", Toast.LENGTH_LONG).show();
                    this.correctScan = "true";
                    /*
                    notif2 = new Notification("scanned", notif.getBookID(), notif.getTitle(), notif.getNotifyToID(), notif.getNotifyToEmail(),

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
                                Toast.makeText(ViewAcceptedOwnerRequest.this, "Successfully Added Notification ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    */
                    //createReturnNotifications();
                    //addBookToBorrowedBooks();
                    //FirebaseDatabase.getInstance().getReference("notifications").child(notif.getNotifID()).removeValue();
                } else{
                    Toast.makeText(getApplicationContext(), barcode, Toast.LENGTH_LONG).show();
                }
            }
        }

    }
    public void createReturnNotifications(Notification notif) {
        Notification notif2 = new Notification("ReturnBorrower", notif.getBookID(), notif.getTitle(), notif.getNotifyToID(), notif.getNotifyToEmail(),

                notif.getNotifyFromID(), notif.getNotifyFromEmail(), notif.getISBN(), notif.getPhoto(), false);

        DatabaseReference newNotif = FirebaseDatabase.getInstance().getReference().child("notifications").child(notif2.getNotifID());

        //add notif to database
        //newNotif.setValue(notif2);
        newNotif.setValue(notif2).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ViewAcceptedRequest.this, "Successfully Added Notification ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Notification notif3 = new Notification("ReturnOwner", notif.getBookID(), notif.getTitle(), notif.getNotifyFromID(), notif.getNotifyFromEmail(),
                notif.getNotifyToID(), notif.getNotifyToEmail(), notif.getISBN(), notif.getPhoto(), false);
        DatabaseReference newNotif2 = FirebaseDatabase.getInstance().getReference().child("notifications").child(notif3.getNotifID());

        //add notif to database
        newNotif2.setValue(notif3).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ViewAcceptedRequest.this, "Successfully Added Notification ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void addBookToBorrowed(){

        loggedInUser.addToMyBorrowedBooks(bookId);
        //Check over this
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("users").child(ownerId).child("myBooks");
        FirebaseDatabase.getInstance().getReference().child("users").child(loggedInUser.getUserID())
                .child("myBorrowedBooks").setValue(bookId);

    }
}