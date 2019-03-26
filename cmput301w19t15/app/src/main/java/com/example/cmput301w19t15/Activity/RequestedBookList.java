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

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

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
    //private Button all, accepted;
    private BookAdapter adapter;
    private ArrayList<Book> listOfBooks;
    private RecyclerView mRecyclerView;
    private static User loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_book_list);
        mRecyclerView = findViewById(R.id.recylcerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        listOfBooks = new ArrayList<>();
        loggedInUser = MainActivity.getUser();
        listOfBooks = loggedInUser.getMyRequestedBooks();
        adapter = new BookAdapter(RequestedBookList.this,listOfBooks);
        mRecyclerView.setAdapter(adapter);

        //loadBooks();


        //Log.d("testing","done");

    }

    /**
     * Load books.
     */
    public void loadBooks(){
        loadMyBookFromFireBase(new FindBooks.loadBookCallBack() {
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
    public void loadMyBookFromFireBase(final FindBooks.loadBookCallBack myCallback){
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

    }

}
