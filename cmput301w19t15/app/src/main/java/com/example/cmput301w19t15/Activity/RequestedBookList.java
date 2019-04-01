/*
 * Class Name: RequestedBookList
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

package com.example.cmput301w19t15.Activity;
//:)
/**
 * Adapter for book class
 * @author Thomas, Josh
 * @version 1.0
 * @see BookAdapter
 * @see User
 * @since 1.0
 */
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.cmput301w19t15.Functions.FetchBookWithList;
import com.example.cmput301w19t15.InProgress.BorrowerBookView;
import com.example.cmput301w19t15.InProgress.Request;
import com.example.cmput301w19t15.Objects.Book;
import com.example.cmput301w19t15.Objects.BookAdapter;
import com.example.cmput301w19t15.Objects.User;
import com.example.cmput301w19t15.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * The type Requested book list.
 */
public class RequestedBookList extends AppCompatActivity implements BookAdapter.OnItemClickListener{
    private RequestedBookList activity = this;
    private Button requested, accepted,borrowed, watchlist;

    private BookAdapter adapter;
    private ArrayList<Book> currentBookList;
    ArrayList<String> requestedIDList;
    private Book clickedBook;
    private RecyclerView mRecyclerView;
    private User loggedInUser = MainActivity.getUser();

    private boolean loadBooksOnce = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_book_list);
        mRecyclerView = findViewById(R.id.recylcerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        currentBookList = new ArrayList<>();
        adapter = new BookAdapter(RequestedBookList.this,currentBookList);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(RequestedBookList.this);

        requestedIDList = loggedInUser.getMyRequestedBooksID();
        if(loadBooksOnce) {
            new FetchBookWithList(currentBookList, requestedIDList, adapter).execute("Requested");
            loadBooksOnce = false;
        }

        requested = (Button) findViewById(R.id.requested);
        accepted = (Button) findViewById(R.id.accepted);
        borrowed = (Button) findViewById(R.id.borrowed);
        watchlist = (Button) findViewById(R.id.watchlist);

        requested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBookList.clear();
                new FetchBookWithList(currentBookList,requestedIDList,adapter).execute("Requested");

            }
        });

        accepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBookList.clear();
                new FetchBookWithList(currentBookList,requestedIDList,adapter).execute("Accepted");

            }
        });

        borrowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBookList.clear();
                new FetchBookWithList(currentBookList,requestedIDList,adapter).execute("Borrowed");

            }
        });

        watchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBookList.clear();

                new FetchBookWithList(currentBookList,loggedInUser.getMyWatchListBooksID(),adapter).execute("WatchList");
            }
        });

    }
    @Override
    public void onItemClick(int position) {
        clickedBook = (Book) currentBookList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("STATUS",clickedBook.getStatus());
        bundle.putString("BOOKID",clickedBook.getBookID());
        Intent intent = new Intent(RequestedBookList.this, BorrowerBookView.class);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        startActivity(intent);
    }
}
