package com.example.cmput301w19t15.Activity;
//:)

import android.annotation.SuppressLint;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Prepares information to scan for owner return
 */

public class ViewReturnRequestOwner extends AppCompatActivity implements ZXingScannerView.ResultHandler, OnMapReadyCallback {

    private Button exit, request, decline, scan, verify;
    private Book newBook;
    private User owner;
    User loggedInUser = MainActivity.getUser();
    String ownerId, author, title, ownerEmail, isbn, status, bookId, correctScan;
    private ZXingScannerView scannerView;
    Integer SCAN_ISBN = 3;
    Notification notif;

    private MapView mapView;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private static final int DEFAULT_ZOOM = 20;
    private LatLng latLng;

    private String bookID;

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
        TextView isbnText = (TextView) findViewById(R.id.isbn);
        isbnText.setText(notif.getISBN());
        TextView ownerEmailText = (TextView) findViewById(R.id.owner);
        ownerEmailText.setText(notif.getNotifyFromEmail());
        Log.d("hello", "youri bad");
        final TextView scanStatus = (TextView) findViewById(R.id.scan_status);
        isbn =  notif.getISBN();
        bookId = notif.getBookID();
        correctScan = "false";
        if (correctScan.equals("false")){
            scanStatus.setText("Scan Incomplete");
        }
        //initialize map
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }


        //retrieve data from firebase
        final DatabaseReference laRef = FirebaseDatabase.getInstance().getReference("notifications").child(notif.getNotifID());
        laRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Log.d("MapTest","We've been here");
                String mlatitude = (String) dataSnapshot.child("latitude").getValue();
                String mlongitude = (String) dataSnapshot.child("longitude").getValue();
                double la = Double.valueOf(mlatitude);
                double lo = Double.valueOf(mlongitude);
                latLng = new LatLng(la,lo);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
        });

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);

        mapView.getMapAsync(this);



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
                    Log.d("MapTest", bookId);
                    scanStatus.setText("Scan Complete");
                    Log.d("hello", "0");
                    //loggedInUser.addToMyBorrowedBooks(bookID);
                    DatabaseReference bookRef = FirebaseDatabase.getInstance().getReference().child("books").child(bookId);
                    bookRef.child("status").setValue("Available").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("MapTest", "Successfully Added Notification 1");
                            //startRating();
                        }

                    });

                    DatabaseReference notifRef = FirebaseDatabase.getInstance().getReference().child("notifications").child(notif.getBookID());
                    notifRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            bookID = (String) dataSnapshot.getValue();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            
                        }
                    });
                    FirebaseDatabase.getInstance().getReference().child("books").child(bookID).child("status").setValue("Available");

                }
                else{
                    Toast.makeText(getApplicationContext(),"Isbn scan not complete",Toast.LENGTH_LONG).show();
                }
            }
        });
        Log.d("hello", notif.getOwnerScanned());
        if (notif.getOwnerScanned().equals("True")){
            Toast.makeText(getApplicationContext(),"Isbn scan matched",Toast.LENGTH_LONG).show();
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
        Intent scannerIntent = new Intent(ViewReturnRequestOwner.this, ScanBarcode.class);
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
    public void addBookToBorrowed(){

        loggedInUser.addToMyRequestedBooksID(bookId);
        //Check over this
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("users").child(ownerId).child("myBooks");
        FirebaseDatabase.getInstance().getReference().child("users").child(loggedInUser.getUserID())
                .child("myBorrowedBooks").setValue(bookId);

    }

    // override all methods for mapView
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap map) {
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.addMarker(new MarkerOptions().position(latLng));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                latLng, DEFAULT_ZOOM));

    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


}