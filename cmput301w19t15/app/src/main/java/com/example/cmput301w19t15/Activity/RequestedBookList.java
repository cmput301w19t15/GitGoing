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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import com.example.cmput301w19t15.InProgress.BorrowerBookView;
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
    private BookAdapter adapter, adapterAccepted,adapterBorrowed;
    private ArrayList<Book> currentBookList, listAccepted, listBorrowed;
    private Book clickedBook;
    private RecyclerView mRecyclerView;
    private static User loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_book_list);
        mRecyclerView = findViewById(R.id.recylcerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        currentBookList = new ArrayList<>();
        //listAccepted = new ArrayList<>();
        //listBorrowed = new ArrayList<>();

        loggedInUser = MainActivity.getUser();
        currentBookList = loggedInUser.getMyRequestedBooks();
        //listAccepted = loggedInUser.getMyRequestedBooksAccepted();
        //listBorrowed = loggedInUser.getBorrowedBooks();
        adapter = new BookAdapter(RequestedBookList.this,currentBookList);
        //adapterAccepted = new BookAdapter(RequestedBookList.this, listAccepted);
        //adapterBorrowed = new BookAdapter(RequestedBookList.this, listBorrowed);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(RequestedBookList.this);


        requested = (Button) findViewById(R.id.requested);
        accepted = (Button) findViewById(R.id.accepted);
        borrowed = (Button) findViewById(R.id.borrowed);
        watchlist = (Button) findViewById(R.id.watchlist);

        requested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBookList = loggedInUser.getMyRequestedBooks();
                adapter = new BookAdapter(RequestedBookList.this,currentBookList);
                adapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(RequestedBookList.this);
            }
        });


        accepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBookList = loggedInUser.getMyRequestedBooksAccepted();
                adapter = new BookAdapter(RequestedBookList.this,currentBookList);
                adapter.notifyDataSetChanged();;
                mRecyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(RequestedBookList.this);
            }
        });

        borrowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBookList = loggedInUser.getBorrowedBooks();
                adapter = new BookAdapter(RequestedBookList.this,currentBookList);
                adapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(RequestedBookList.this);

            }
        });

        watchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBookList = loggedInUser.getWatchlistBooks();
                adapter = new BookAdapter(RequestedBookList.this,currentBookList);
                adapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(RequestedBookList.this);

            }
        });

        //loadBooks();


        //Log.d("testing","done");

    }

    /**
     * Load books.
     */
    /*public void loadAcceptedBooks(){
        loadMyRequestedBooksFromFireBase(new FindBooks.loadBookCallBack() {
            @Override
            public void loadBookCallBack(ArrayList<Book> value) {
                listOfBooks = (ArrayList<Book>) value.clone();
                //Log.d("testing","book size: "+listOfBooks.size());
                adapter = new BookAdapter(RequestedBookList.this,listOfBooks);
                mRecyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(RequestedBookList.this);
            }
        });
    }
    /**
     * Load my book from fire base.
     *
     * @param myCallback the my callback
     */
    public void loadMyRequestedBooksFromFireBase(final FindBooks.loadBookCallBack myCallback){
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("books");
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    try {
                        ArrayList<Book> allBooks = new ArrayList<>();
                        for (DataSnapshot books : dataSnapshot.getChildren()) {
                            if(books.child("date").getValue().equals(null) || books.child("date").getValue().equals("null")) {
                                //Log.d("testing",books.getKey());
                            }else{
                                Book book = books.getValue(Book.class);
                                allBooks.add(book);
                            }
                        }
                        myCallback.loadBookCallBack(allBooks);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("testing","Error: ", databaseError.toException());
            }
        });
    }
    @Override
    public void onItemClick(int position) {
        clickedBook = (Book) currentBookList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("TITLE",clickedBook.getTitle());
        bundle.putString("AUTHOR",clickedBook.getAuthor());
        bundle.putString("ISBN",clickedBook.getISBN());
        bundle.putString("OWNEREMAIL",clickedBook.getOwnerEmail());
        bundle.putString("OWNERID",clickedBook.getOwnerID());
        bundle.putString("STATUS",clickedBook.getStatus());
        bundle.putString("BOOKID",clickedBook.getBookID());
        bundle.putString("IMAGE",clickedBook.getPhoto());
        Intent intent = new Intent(RequestedBookList.this, BorrowerBookView.class);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        startActivity(intent);

    }

}
