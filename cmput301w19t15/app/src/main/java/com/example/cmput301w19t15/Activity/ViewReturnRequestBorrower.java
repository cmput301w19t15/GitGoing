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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


/**
 * The type View return request owner.
 */
public class ViewReturnRequestBorrower extends AppCompatActivity implements ZXingScannerView.ResultHandler, OnMapReadyCallback {

    private Button exit, scan, verify;
    private Book newBook;
    private User owner;
    /**
     * The Logged in user.
     */
    User loggedInUser = MainActivity.getUser();
    /**
     * The Owner id.
     */
    String ownerID, /**
     * The Author.
     */
    author, /**
     * The Title.
     */
    title, /**
     * The Owner email.
     */
    ownerEmail, /**
     * The Isbn.
     */
    isbn, /**
     * The Status.
     */
    status, /**
     * The Book id.
     */
    bookId;
    private String correctScan, oldrequesterID;
    /**
     * The Scan isbn.
     */
    Integer SCAN_ISBN = 3;
    private ZXingScannerView scannerView;
    /**
     * The Notif.
     */
    Notification notif, /**
     * The Notif 2.
     */
    notif2, /**
     * The Requester notification.
     */
    requesterNotification;

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
     * Scan.
     *
     * @param view the view
     * @reuse https ://github.com/dm77/barcodescanner this methods initialize scanner
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