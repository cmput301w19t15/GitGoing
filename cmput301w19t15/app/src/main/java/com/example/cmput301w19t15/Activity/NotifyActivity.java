package com.example.cmput301w19t15.Activity;
//:)
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.cmput301w19t15.InProgress.AcceptRequest;
import com.example.cmput301w19t15.Objects.NotifAdapter;
import com.example.cmput301w19t15.Objects.Notification;
import com.example.cmput301w19t15.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Creates notifications and allows interactions with notifications
 * @see Notification
 */
public class NotifyActivity extends AppCompatActivity implements NotifAdapter.OnItemClickListener {

    private NotifAdapter adapter;
    private ArrayList<Notification> listOfNotif;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate (Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);
        setContentView(R.layout.activity_notify);
        mRecyclerView = findViewById(R.id.recylcerNotifView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        listOfNotif = new ArrayList<>();
        loadNotif();
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadNotif();
        Log.d("TAG", "wutttt");
    }

    /**
     * Load notif from firebase
     */
    public void loadNotif() {
        loadNotifFromFirebBase(new loadNotifCallBack() {
            @Override
            public void loadNotifCallBack(ArrayList<Notification> value) {
                listOfNotif = (ArrayList<Notification>) value.clone();
                adapter = new NotifAdapter(NotifyActivity.this, listOfNotif);
                mRecyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(NotifyActivity.this);
            }
        });
    }

    /**
     * The interface Load notif call back.
     */
    public interface loadNotifCallBack {
        /**
         * Load notif call back.
         *
         * @param value the value
         */
        void loadNotifCallBack(ArrayList<Notification> value);
    }

    /**
     * Joins user with their notifications and allows interactions with notifications
     *
     * @param myCallback the my callback
     */
    public void loadNotifFromFirebBase(final loadNotifCallBack myCallback) {
        DatabaseReference notifReference = FirebaseDatabase.getInstance().getReference().child("notifications");
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        notifReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    try {
                        ArrayList<Notification> allNotif = new ArrayList<>();
                        for (DataSnapshot notif : dataSnapshot.getChildren()) {

                            //Log.d("testing1",user.getUid());

                            if (user.getUid() != null){
                                Notification currentNotif = notif.getValue(Notification.class);
                                Log.d("testing",user.getUid());
                                if (currentNotif.getNotifyToID().equals(user.getUid())){
                                    allNotif.add(currentNotif);
                                    //Toast.makeText(NotifyActivity.this, currentNotif.getTitle(), Toast.LENGTH_SHORT).show();

                                }
                            }
                        }
                        myCallback.loadNotifCallBack(allNotif);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemClick(int position) {

        Notification notif = listOfNotif.get(position);
        String bookID = notif.getBookID();
        String ISBN = notif.getISBN();
        String notifID = notif.getNotifID();
        String notifyFromEmail = notif.getNotifyFromEmail();
        String notifyFromID = notif.getNotifyFromID();
        String notifyToEmail = notif.getNotifyToEmail();
        String notifyToID = notif.getNotifyToID();
        String photo = notif.getPhoto();
        Boolean read = notif.getRead();
        String title = notif.getTitle();
        String type = notif.getType();


        if (read == false) {
            read = true;
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("notifications").child(notifID);
            ref.child("read").setValue(read);
            //FirebaseDatabase.getInstance().getReference("notifications").child(notif.getNotifID()).removeValue();
            notif.setRead(read);
            //notif.setNotifID(notifID);
            //FirebaseDatabase.getInstance().getReference("notifications").child(notif.getNotifID()).setValue(notif);

        }
        //else {
        //read = true;
        //}

        // delete old notif and save with updated read value


        if (notif.getType().equals("Requested")) {
            Intent intent = new Intent(NotifyActivity.this, AcceptRequest.class);
            intent.putExtra("Notification", notif);
            intent.putExtra("NotifID", notifID);
            startActivityForResult(intent, 1);
        }
        else if (notif.getType().equals("Accepted")) {
            Intent intent = new Intent(NotifyActivity.this, ViewAcceptedRequest.class);
            intent.putExtra("Notification", notif);
            intent.putExtra("NotifID", notifID);
            startActivityForResult(intent, 1);
        }
        else if (notif.getType().equals("AcceptedOwner")) {
            Intent intent = new Intent(NotifyActivity.this, ViewAcceptedOwnerRequest.class);
            intent.putExtra("Notification", notif);
            intent.putExtra("NotifID", notifID);
            startActivityForResult(intent, 1);
        }
        else if (notif.getType().equals("ReturnOwner")) {
            Intent intent = new Intent(NotifyActivity.this, ViewReturnRequestOwner.class);
            intent.putExtra("Notification",notif);
            intent.putExtra("NotifID",notifID);
            startActivityForResult(intent, 1);
            }
        else if (notif.getType().equals("ReturnBorrower")) {
            Intent intent = new Intent(NotifyActivity.this, ViewReturnRequestBorrower.class);
            intent.putExtra("Notification",notif);
            intent.putExtra("NotifID",notifID);
            startActivityForResult(intent, 1);
        }else if(notif.getType().equalsIgnoreCase("requestedDeleted")){
        }else if(notif.getType().equalsIgnoreCase("watchListDeleted")){

        }
    }
}