package com.example.cmput301w19t15;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MyBorrows extends AppCompatActivity implements BookAdapter.OnItemClickListener{
    private MyBorrows activity = this;
    private static User loggedInUser;
    private BookAdapter mBookAdapter;
    private ArrayList<Book> mBookList;
    private RecyclerView mRecyclerView;
    private static final int NEW_BOOK = 1;
    private Book clickedBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_borrows);
        mRecyclerView = findViewById(R.id.recyclerView1);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //gets books from user and loads them into screen
        loggedInUser = MainActivity.getUser();
        try {
            mBookList = loggedInUser.getMyBooks();
        }catch(Exception e){
            e.printStackTrace();
        }
        mBookAdapter = new BookAdapter(MyBorrows.this,mBookList);
        mRecyclerView.setAdapter(mBookAdapter);
        mBookAdapter.setOnItemClickListener(MyBorrows.this);

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        //gets books from user and loads them into screen
        loggedInUser = MainActivity.getUser();
        try {
            mBookList = loggedInUser.getMyBooks();
        }catch(Exception e){
            e.printStackTrace();
        }
        mBookAdapter = new BookAdapter(MyBorrows.this,mBookList);
        mRecyclerView.setAdapter(mBookAdapter);
        mBookAdapter.setOnItemClickListener(MyBorrows.this);

        mBookAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mBookAdapter.notifyDataSetChanged();
    }

    /**
     * called everytime the activity is started
     */
    @Override
    protected void onStart() {
        super.onStart();
        //TextView textView = findViewById(R.id.textView2);
        //textView.setText("Number of books: " + arraySize);
        mBookAdapter.notifyDataSetChanged();

    }

    /**
     * Open a open a book that is clicked on that will be editable
     * @param position - index of clicked book
     */
    @Override
    public void onItemClick(int position) {
        clickedBook = (Book) mBookList.get(position);
        Intent intent = new Intent(MyBorrows.this, BookInfo.class);
        intent.putExtra("BOOKID",clickedBook.getBookID());
        intent.putExtra("POSITION",position);
        //setResult(RESULT_OK, intent);
        startActivityForResult(intent,1);
    }
}
