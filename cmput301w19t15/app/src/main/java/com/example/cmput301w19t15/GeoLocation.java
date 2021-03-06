package com.example.cmput301w19t15;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.widget.Toast;
import android.location.Location;


import com.example.cmput301w19t15.Activity.ViewAcceptedRequest;
import com.example.cmput301w19t15.InProgress.AcceptRequest;
import com.example.cmput301w19t15.Objects.Notification;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;


/**
 * this activity creates a google map object,
 * allow user to navigate through the map
 * and choose a specific location to meet each other
 */
public class GeoLocation extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private static final int LOCATION_REQUEST_CODE = 1;
    private static final int DEFAULT_ZOOM = 20;

    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private FloatingActionButton selectLocation;

    private GoogleMap mMap;
    private CameraPosition cameraPosition;
    private GeoDataClient geoDataClient;
    private PlaceDetectionClient placeDetectionClient;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location previousLocation;
    private boolean userPermission;
    private Location currentLocation;



    private LatLng resultLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Notification acceptedNotif = (Notification) getIntent().getSerializableExtra("Accepted");
        final Notification acceptedOwnerNotif = (Notification) getIntent().getSerializableExtra("AcceptedOwner");

        //instantiate all services required to detect device and geolocation
        //fusedlocation is the sercive that gets user's location
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        geoDataClient = Places.getGeoDataClient(this,null);
        placeDetectionClient = Places.getPlaceDetectionClient(this,null);


        setContentView(R.layout.activity_geo_location);
        selectLocation = findViewById(R.id.selectLocation);

        selectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resultLocation != null) {
                    String la = Double.toString(resultLocation.latitude);
                    String lo = Double.toString(resultLocation.longitude);
                    Toast.makeText(GeoLocation.this, la + ' ' + lo, Toast.LENGTH_SHORT).show();
                    acceptedNotif.setLatLng(resultLocation);
                    acceptedOwnerNotif.setLatLng(resultLocation);


                    FirebaseDatabase.getInstance().getReference().child("notifications").child(acceptedNotif.getNotifID()).child("latitude").setValue(la);
                    FirebaseDatabase.getInstance().getReference().child("notifications").child(acceptedNotif.getNotifID()).child("longitude").setValue(lo);


                    DatabaseReference notifRef = FirebaseDatabase.getInstance().getReference().child("notifications").child(acceptedOwnerNotif.getNotifID());
                    //FirebaseDatabase.getInstance().getReference().child("notifications").child(acceptedOwnerNotif.getNotifID()).child("latitude").setValue(la);
                    //FirebaseDatabase.getInstance().getReference().child("notifications").child(acceptedOwnerNotif.getNotifID()).child("longitude").setValue(lo);

                    notifRef.child("latitude").setValue(la).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("MapTest","Successfully Added Notification 1");
                        }
                    });



                    notifRef.child("longitude").setValue(lo).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Log.d("MapTest","Successfully Added Notification 2");
                            }
                        }
                    });


                    finish();
                }
                else{
                    Toast.makeText(GeoLocation.this, "no marker decected",Toast.LENGTH_LONG).show();


                }
            }
        });


        geoDataClient = Places.getGeoDataClient(this,null);
        placeDetectionClient = Places.getPlaceDetectionClient(this,null);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        if (savedInstanceState != null) {
            currentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        mapFragment.getMapAsync(this);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, previousLocation);
            super.onSaveInstanceState(outState);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This will first check user permission
     * if permission is granted. it will add location UI to the app
     * Then get the current device location and move camera
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        userPermission();
        updateUI();
        getDeviceLocation();
        mMap.setOnMapClickListener(this);

    }

    /**
     * this method is to ask user for location permission
     */
    public void userPermission() {

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            userPermission = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /**
     * if permission is acquired, it will get user's current location and
     * move camera
     */
    private void getDeviceLocation() {

        try {
            if (userPermission) {
                Task locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            previousLocation = (Location) task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(previousLocation.getLatitude(),
                                            previousLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {
                            Log.d("TAG", "Current location is null. Using defaults.");
                            Log.e("TAG", "Exception: %s", task.getException());
                            //default location
                            LatLng edmonton = new LatLng(54, -113);
                            mMap.addMarker(new MarkerOptions().position(edmonton).title("Marker in Edmonton"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(edmonton,DEFAULT_ZOOM));
                            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    /**
     * this class is to handle the permission request
     * if permission is acquired, it will also display user's current location
     * if not, then display an error message
     * this method will always update the current UI no matter what
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        userPermission = false;
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
                    Toast.makeText(this,"Location Permission Allowed", Toast.LENGTH_SHORT).show();
                    userPermission = true;
                    displayLocation();
                    Log.d("Test","Location Permission Allowed");
                    //Permission Granted
                } else
                    //Log.d("Test","Location Permission Denied");
                break;
        }
        updateUI();
    }


    /**
     * this method update map UI
     * if permission is acquired,
     * this will add 'find current locatrion button' into UI
     */
    private void updateUI(){

        if (mMap == null) {
            return;
        }
        try {
            if (userPermission) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                mMap.getUiSettings().setMapToolbarEnabled(true);
                mMap.getUiSettings().setZoomControlsEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                previousLocation = null;
                userPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }



    /**
     * @resue: https://androidclarified.com/display-current-location-google-map-fusedlocationproviderclient/
     * display location with latitude and longitude
     */
    private void displayLocation(){
        @SuppressLint("MissingPermission") Task<android.location.Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;

                    Toast.makeText(GeoLocation.this,currentLocation.getLatitude()+" "+currentLocation.getLongitude(),Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    supportMapFragment.getMapAsync(GeoLocation.this);
                }else{
                    Toast.makeText(GeoLocation.this,"No Location recorded",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onMapClick(LatLng latLng) {
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(latLng.latitude, latLng.longitude)).title("New Marker");
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng));
        resultLocation = latLng;

    }


    /**
     * override to disable back button
     * force user to choose a location
     */
    @Override
    public void onBackPressed(){

    }


}

