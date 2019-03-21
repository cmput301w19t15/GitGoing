package com.example.cmput301w19t15;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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

    public interface loadNotifCallBack {
        void loadNotifCallBack(ArrayList<Notification> value);
    }

    public void loadNotifFromFirebBase(final loadNotifCallBack myCallback) {
        DatabaseReference notifReference = FirebaseDatabase.getInstance().getReference().child("notifications");
        notifReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    try {
                        ArrayList<Notification> allNotif = new ArrayList<>();
                        for (DataSnapshot notif : dataSnapshot.getChildren()) {
                            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            //Log.d("testing1",user.getUid());

                            if (user.getUid() != null){
                                Notification currentNotif = notif.getValue(Notification.class);
                                //Log.d("testing",user.getUid());
                                if (currentNotif.getNotifyToID().equals(user.getUid())){
                                    allNotif.add(currentNotif);
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
        notif.setRead(true);



        FirebaseDatabase.getInstance().getReference("notifications").child(notif.getBookID()).setValue(notif);
    }
}
