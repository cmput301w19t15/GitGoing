package com.example.cmput301w19t15.Activity;
//:)

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301w19t15.Functions.FetchBookInfo;
import com.example.cmput301w19t15.Functions.ScanBarcode;
import com.example.cmput301w19t15.InProgress.AcceptRequest;
import com.example.cmput301w19t15.Objects.Book;
import com.example.cmput301w19t15.Objects.Notification;
import com.example.cmput301w19t15.Objects.User;
import com.example.cmput301w19t15.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ViewAcceptedOwnerRequest extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private Button exit, scan;
    private Book newBook;
    private User owner;
    User loggedInUser = MainActivity.getUser();
    String ownerID, author, title, ownerEmail, isbn, status, bookId;
    Integer SCAN_ISBN = 3;
    private ZXingScannerView scannerView;
    Notification notif, notif2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_accepted_owner_request);

        /**
         * save the book values passed from the FindBooks classes when clicked on
         */
        final Notification notif = (Notification) getIntent().getSerializableExtra("Notification");
        TextView titleText = (TextView) findViewById(R.id.booktitle);
        titleText.setText(notif.getTitle());
        isbn = notif.getISBN();
        TextView isbnText = (TextView) findViewById(R.id.isbn);
        isbnText.setText(isbn);
        TextView RequesterEmailText = (TextView) findViewById(R.id.owner);
        RequesterEmailText.setText(notif.getNotifyToEmail());

        exit = (Button) findViewById(R.id.cancel);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        scan = (Button) findViewById(R.id.scan);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan(v);
            }
        });
    }

    /**
     * @reuse https://github.com/dm77/barcodescanner
     * this methods initialize scanner
     */
    public void scan(View view){
        //https://github.com/ravi8x/Barcode-Reader
        Intent scannerIntent = new Intent(ViewAcceptedOwnerRequest.this, ScanBarcode.class);
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
            if (requestCode == SCAN_ISBN) {
                String barcode = data.getStringExtra("ISBN");
                if (barcode.equals(isbn)) {
                    Toast.makeText(getApplicationContext(),"Isbn scan matched",Toast.LENGTH_LONG).show();
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
                    changeStatusForRequester();
                    FirebaseDatabase.getInstance().getReference("notifications").child(notif.getNotifID()).removeValue();
                }
            }
        }
    }
    public void changeStatusForRequester() {
        


    }

}