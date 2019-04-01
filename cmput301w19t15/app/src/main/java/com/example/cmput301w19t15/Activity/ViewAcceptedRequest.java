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

import com.example.cmput301w19t15.Functions.FetchBookWithID;
import com.example.cmput301w19t15.Functions.ScanBarcode;
import com.example.cmput301w19t15.Objects.Notification;
import com.example.cmput301w19t15.Objects.Book;
import com.example.cmput301w19t15.R;
import com.example.cmput301w19t15.Objects.User;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;

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

public class ViewAcceptedRequest extends AppCompatActivity implements ZXingScannerView.ResultHandler, OnMapReadyCallback {

    private Button exit, request, decline, scan, verify;

    private ArrayList<Book> newBook = new ArrayList<>();

    private User owner;
    User loggedInUser = MainActivity.getUser();
    String ownerId, author, title, ownerEmail, isbn, status, bookID, correctScan,image,rating;
    Long returnDate;
    private ZXingScannerView scannerView;
    Integer SCAN_ISBN = 3;
    Notification notif;
    TextView authorText,statusText;

    private MapView mapView;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private static final int DEFAULT_ZOOM = 20;
    private LatLng latLng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_accepted_request);

        /**
         * save the book values passed from the FindBooks classes when clicked on
         */
        notif = (Notification) getIntent().getSerializableExtra("Notification");
        TextView titleText = (TextView) findViewById(R.id.booktitle);
        titleText.setText(notif.getTitle());
        TextView isbnText = (TextView) findViewById(R.id.isbn);
        isbnText.setText(notif.getISBN());
        TextView ownerEmailText = (TextView) findViewById(R.id.owner);

        isbn = notif.getISBN();


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
                Log.d("MapTest","We've been here");
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


        ownerEmailText.setText(notif.getNotifyFromEmail());



        Log.d("hello", "youri bad");
        final TextView scanStatus = (TextView) findViewById(R.id.scan_status);


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
                    loggedInUser.addToMyBorrowedBooks(bookID);
                    try {
                        FirebaseDatabase.getInstance().getReference().child("books").child(bookID).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    try {
                                        Book book = dataSnapshot.getValue(Book.class);
                                        Book bookNew = new Book(book.getTitle(), book.getAuthor(), book.getISBN(), book.getPhoto(), book.getOwnerEmail(),
                                                book.getOwnerID(), book.getRating(), book.getRatingCount(), book.getRatingTotal());
                                        bookNew.setBookID(bookID);
                                        bookNew.setStatus("Borrowed");
                                        DatabaseReference newBook = FirebaseDatabase.getInstance().getReference().child("books").child(bookID);
                                        newBook.setValue(bookNew).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    createReturnNotifications(notif);
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
                    addBookToBorrowed();
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

        //fetch book by id
        new FetchBookWithID(newBook,returnDate,title,author,isbn,ownerEmail,status,image,rating).execute(bookID);
        Book book = newBook.get(0);
        //changebookstatus
        Book bookNew = new Book(notif.getTitle(), book.getAuthor(), book.getISBN(), book.getPhoto(), book.getOwnerEmail(),
                book.getOwnerID(), book.getRating(), book.getRatingCount(), book.getRatingTotal());
        bookNew.setBookID(bookID);
        bookNew.setStatus("Borrowed");
        //updatefirebase
        DatabaseReference newBook = FirebaseDatabase.getInstance().getReference().child("books").child(bookID);
        newBook.setValue(bookNew).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
    });
/*
        loggedInUser.addToMyRequestedBooksID(bookId);

        //Check over this
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("users").child(ownerId).child("myBooks");
        FirebaseDatabase.getInstance().getReference().child("users").child(loggedInUser.getUserID())
                .child("myBorrowedBooks").setValue(bookId);
*/
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
