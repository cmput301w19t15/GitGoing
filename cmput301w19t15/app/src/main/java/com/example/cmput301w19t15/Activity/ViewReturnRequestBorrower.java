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

import com.example.cmput301w19t15.Functions.ScanBarcode;
import com.example.cmput301w19t15.Objects.Book;
import com.example.cmput301w19t15.Objects.Notification;
import com.example.cmput301w19t15.Objects.User;
import com.example.cmput301w19t15.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ViewReturnRequestBorrower extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private Button exit, scan, verify;
    private Book newBook;
    private User owner;
    User loggedInUser = MainActivity.getUser();
    String ownerID, author, title, ownerEmail, isbn, status, bookId;
    private String correctScan, oldrequesterID;
    Integer SCAN_ISBN = 3;
    private ZXingScannerView scannerView;
    Notification notif, notif2, requesterNotification;

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
        isbn = notif.getISBN();
        TextView isbnText = (TextView) findViewById(R.id.isbn);
        isbnText.setText(isbn);
        TextView RequesterEmailText = (TextView) findViewById(R.id.owner);
        RequesterEmailText.setText(notif.getNotifyToEmail());
        correctScan = "false";
        final TextView scanStatus = (TextView) findViewById(R.id.scan_status);
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

        scan = (Button) findViewById(R.id.scan);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan(v);
                Toast.makeText(getApplicationContext(),"Isbn scan matched",Toast.LENGTH_LONG).show();
                Log.d("hello", correctScan);
            }

        });
        verify = (Button) findViewById(R.id.verify);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctScan.equals("true")) {
                    scanStatus.setText("Scan Complete");
                    Log.d("hello", "0");
                    try {
                        Log.d("hello", "1");
                        FirebaseDatabase.getInstance().getReference().child("notifications").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Log.d("hello", "2");
                                if (dataSnapshot.exists()) {
                                    try {
                                        for (DataSnapshot notifs : dataSnapshot.getChildren()) {
                                            Log.d("hello", notif.getNotifyFromID());
                                            if (notifs.child("notifyFromID").getValue().equals(notif.getNotifyToID()) &&
                                                    notifs.child("notifyToID").getValue().equals(notif.getNotifyFromID()) &&
                                                    notifs.child("isbn").getValue().equals(notif.getISBN())) {
                                                requesterNotification = notifs.getValue(Notification.class);
                                                Log.d("hello", "3");
                                                //requesterNotification.setOwnerScanned("True");
                                                oldrequesterID= notif.getNotifID();
                                                //FirebaseDatabase.getInstance().getReference("notifications").child(requesterNotification.getNotifID()).setValue(requesterNotification);
                                                Log.d("hello", "thomas bad");
                                                FirebaseDatabase.getInstance().getReference("notifications").child(oldrequesterID).removeValue();
                                                DatabaseReference notifRef = FirebaseDatabase.getInstance().getReference().child("notifications").child(requesterNotification.getNotifID());
                                                notifRef.child("ownerScanned").setValue("True").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("MapTest","Successfully Added Notification 1");
                                                        //startRating();
                                                    }
                                                });

                                                break;
                                            }
                                        }
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
}

    /**
     * @reuse https://github.com/dm77/barcodescanner
     * this methods initialize scanner
     */
    public void scan(View view){
        //https://github.com/ravi8x/Barcode-Reader
        Intent scannerIntent = new Intent(ViewReturnRequestBorrower.this, ScanBarcode.class);
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
                    this.correctScan = "true";

                }
            }
        }
    }
}