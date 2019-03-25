/*
 * Class Name: MyBooks
 *
 * Version: 1.0
 *
 * Copyright 2019 TEAM GITGOING
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.example.cmput301w19t15;
//:)
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



/**
 * Represents the books that an owner owns
 * @author Thomas, Anjesh, Breanne, Josh, Yourui
 * @version 1.0
 * @see Book
 * @see BookAdapter
 * @see BookInfo
 * @see AcceptPage
 * @see Exchange
 * @see Location
 * @since 1.0
 */
public class MyBooks extends AppCompatActivity implements BookAdapter.OnItemClickListener {

    private MyBooks activity = this;
    private static User loggedInUser;
    private BookAdapter mBookAdapter;
    private ArrayList<Book> mBookList;
    private RecyclerView mRecyclerView;
    private static final int NEW_BOOK = 1;
    private Book clickedBook;

    /**
     * Called when activity is first created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);

        mRecyclerView = findViewById(R.id.recylcerView);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //gets books from user and loads them into screen
        loggedInUser = MainActivity.getUser();
        try {
            mBookList = loggedInUser.getMyBooks();
        }catch(Exception e){
            e.printStackTrace();
        }
        mBookAdapter = new BookAdapter(MyBooks.this,mBookList);
        mRecyclerView.setAdapter(mBookAdapter);
        mBookAdapter.setOnItemClickListener(MyBooks.this);

        //adds new book by starting add book info class
        Button addBook = (Button) findViewById(R.id.add_book);
        addBook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addIntent = new Intent(MyBooks.this, AddBookInfo.class);
                startActivityForResult(addIntent, NEW_BOOK);
            }
        });

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
        mBookAdapter = new BookAdapter(MyBooks.this,mBookList);
        mRecyclerView.setAdapter(mBookAdapter);
        mBookAdapter.setOnItemClickListener(MyBooks.this);

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
        Intent intent = new Intent(MyBooks.this, BookInfo.class);
        intent.putExtra("BOOKID",clickedBook.getBookID());
        intent.putExtra("POSITION",position);
        //setResult(RESULT_OK, intent);
        startActivityForResult(intent,1);
    }

}