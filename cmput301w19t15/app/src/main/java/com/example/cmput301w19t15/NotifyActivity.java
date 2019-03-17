package com.example.cmput301w19t15;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class NotifyActivity extends AppCompatActivity implements NotifAdapter.OnItemClickListener {
    private NotifAdapter adapter;
    private ArrayList<Notification> listOfNotif;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate (Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);
        setContentView(R.layout.activity_notify);

        mRecyclerView = findViewById(R.id.recylcerUserView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        listOfNotif = new ArrayList<>();

    }

    @Override
    public void onItemClick(int position) {}
}
