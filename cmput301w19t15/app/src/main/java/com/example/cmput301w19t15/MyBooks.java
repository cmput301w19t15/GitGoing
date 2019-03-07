package com.example.cmput301w19t15;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;


public class MyBooks extends AppCompatActivity {

    private static final int NEW_BOOK = 1;
    private RecyclerView mRecyclerView;
    //private BookAdapter mBookAdapter;
    private ArrayList<Book> mBookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);

        mRecyclerView = findViewById(R.id.recylcerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mBookList = new ArrayList<>();
        //mBookAdapter = new BookAdapter(MyBooks.this, mBookList);
        //mRecyclerView.setAdapter(mBookAdapter);
        //mBookAdapter.setOnItemClickListener(MyBooks.this);

        Button addBook = (Button) findViewById(R.id.add_book);

        addBook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addIntent = new Intent(MyBooks.this, AddBookInfo.class);
                startActivityForResult(addIntent, NEW_BOOK);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        User loggedInUser = MainActivity.getUser();
        int arraySize = loggedInUser.getMyBooks().size();
        //TextView textView = findViewById(R.id.textView2);
        //textView.setText("Number of books: " + arraySize);

    }


    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, BookInfo.class);
        Book clickedBook = mBookList.get(position);

        //detailIntent.putExtra()
    }

}