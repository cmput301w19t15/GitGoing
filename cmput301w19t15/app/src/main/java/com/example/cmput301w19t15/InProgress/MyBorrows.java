package com.example.cmput301w19t15.InProgress;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.cmput301w19t15.Activity.AddBookInfo;
import com.example.cmput301w19t15.Activity.BookInfo;
import com.example.cmput301w19t15.Activity.MainActivity;
import com.example.cmput301w19t15.Activity.MyBooks;
import com.example.cmput301w19t15.R;
import com.example.cmput301w19t15.Functions.FetchBookWithID;
import com.example.cmput301w19t15.Functions.FetchBookWithList;
import com.example.cmput301w19t15.InProgress.Exchange;
import com.example.cmput301w19t15.InProgress.AcceptPage;
import com.example.cmput301w19t15.InProgress.Location;
import com.example.cmput301w19t15.Objects.Book;
import com.example.cmput301w19t15.Objects.BookAdapter;
import com.example.cmput301w19t15.Objects.User;

import java.util.ArrayList;

public class MyBorrows extends AppCompatActivity implements BookAdapter.OnItemClickListener {
    private MyBorrows activity = this;
    private static User loggedInUser;
    private BookAdapter mBookAdapter;
    private ArrayList<Book> mBookList;
    private RecyclerView mRecyclerView;
    private static final int NEW_BOOK = 1;
    private Book clickedBook;


    ArrayList<Book> testBookList = new ArrayList<>();
    ArrayList<String> testBookID = new ArrayList<>();

    /**
     * Called when activity is first created
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_borrows);

        mRecyclerView = findViewById(R.id.recyclerView1);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //gets books from user and loads them into screen
        loggedInUser = MainActivity.getUser();
        try {
            mBookList = loggedInUser.getBorrowedBooks();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //Work in Progress
        mBookAdapter = new BookAdapter(MyBorrows.this, testBookList);
        mRecyclerView.setAdapter(mBookAdapter);
        mBookAdapter.setOnItemClickListener(MyBorrows.this);


        try {
            testBookList.clear();
            for (Book books : mBookList) {
                testBookID.add(books.getBookID());
            }
            new FetchBookWithList(testBookList, testBookID, mBookAdapter).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Work in Progress

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //gets books from user and loads them into screen
        loggedInUser = MainActivity.getUser();
        try {
            mBookList = loggedInUser.getBorrowedBooks();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //Work in Progress
        mBookAdapter = new BookAdapter(MyBorrows.this, testBookList);
        mRecyclerView.setAdapter(mBookAdapter);
        mBookAdapter.setOnItemClickListener(MyBorrows.this);
        mBookAdapter.notifyDataSetChanged();


        try {
            testBookList.clear();
            for (Book books : mBookList) {
                testBookID.add(books.getBookID());
            }
            new FetchBookWithList(testBookList, testBookID, mBookAdapter).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Work in Progress
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
        mBookAdapter.notifyDataSetChanged();
    }

    /**
     * Open a open a book that is clicked on that will be editable
     *
     * @param position - index of clicked book
     */
    @Override
    public void onItemClick(int position) {
        //Work in Progress
        clickedBook = (Book) testBookList.get(position);
        Intent intent = new Intent(MyBorrows.this, BookInfo.class);
        intent.putExtra("BOOKID", clickedBook.getBookID());
        intent.putExtra("POSITION", position);
        //setResult(RESULT_OK, intent);
        startActivityForResult(intent, 1);
    }
}
