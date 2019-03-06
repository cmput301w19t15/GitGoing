package com.example.cmput301w19t15;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class RequestedBookList extends AppCompatActivity {

    private ListView acceptedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_book_list);

        acceptedList = (ListView) findViewById(R.id.accepted);

    }
}
