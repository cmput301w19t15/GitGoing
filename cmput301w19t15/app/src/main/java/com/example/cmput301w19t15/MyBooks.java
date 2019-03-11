package com.example.cmput301w19t15;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;


public class MyBooks extends AppCompatActivity implements BookAdapter.OnItemClickListener {

    private MyBooks activity = this;
    private static User loggedInUser;
    private BookAdapter mBookAdaptor;
    private ArrayList<Book> mBookList;
    private RecyclerView mRecyclerView;
    private static final int NEW_BOOK = 1;




    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);



        //gets books from user and loads them into screen
        listBook();
        loggedInUser = MainActivity.getUser();
        mBookList = loggedInUser.getMyBooks();
        mBookAdaptor = new BookAdapter(MyBooks.this,mBookList);
        mRecyclerView.setAdapter(mBookAdaptor);
        mBookAdaptor.setOnItemClickListener(MyBooks.this);

        //adds new book by starting add book info class
        Button addBook = (Button) findViewById(R.id.add_book);
        addBook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addIntent = new Intent(MyBooks.this, AddBookInfo.class);
                startActivityForResult(addIntent, NEW_BOOK);
            }
        });

    }

    /**
     * checks to make sure we can get recyclerview set up
     */
    private void listBook() {
        try{
            mRecyclerView = findViewById(R.id.recylcerView);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        } catch (Exception e) {
            e.printStackTrace();         }
    }

    @Override
    protected void onStart() {
        super.onStart();
        int arraySize = loggedInUser.getMyBooks().size();
        //TextView textView = findViewById(R.id.textView2);
        //textView.setText("Number of books: " + arraySize);

    }

    @Override
    public void onItemClick(int position) {
        Book clickedBook = mBookList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("POS",position);
        bundle.putString("BOOKID",clickedBook.getBookID());
        Intent detailIntent = new Intent(this, BookInfo.class);
        detailIntent.putExtra("BOOKINFO", bundle);
        startActivityForResult(detailIntent, NEW_BOOK);

    }

}