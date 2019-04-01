/*
 * Class Name: FindBooks
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
 * Represents a database search for the books
 * @author Thomas, Anjesh, Eisha, Breanne, Yourui, Josh
 * @version 1.0
 * @see com.example.cmput301w19t15.Objects.Book
 * @see com.example.cmput301w19t15.Objects.BookAdapter
 *
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
import android.widget.EditText;
import android.widget.Toast;

import com.example.cmput301w19t15.Functions.FetchBookWithList;
import com.example.cmput301w19t15.Objects.Book;
import com.example.cmput301w19t15.Objects.BookAdapter;
import com.example.cmput301w19t15.R;
import com.example.cmput301w19t15.Objects.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * load all the books from firebase and then search them,
 * display the searching result
 */
public class FindBooks extends AppCompatActivity implements BookAdapter.OnItemClickListener{
    private FindBooks activity = this;
    private static User loggedInUser;
    private BookAdapter mBookAdapter;
    private ArrayList<Book> mBookList;
    private ArrayList<String> mBookListID;
    private RecyclerView mRecyclerView;
    private String filterText;
    private EditText filterView;

    /**
     * Calls when activity is first created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_books);

        mRecyclerView = findViewById(R.id.recylcerView);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        filterView = findViewById(R.id.searchTextView);
        filterText = filterView.getText().toString();

        loggedInUser = MainActivity.getUser();

        try {
            mBookListID = loggedInUser.getMyBooksID();
            mBookList = new ArrayList<>();
        }catch(Exception e){
            e.printStackTrace();
        }

        mBookAdapter = new BookAdapter(FindBooks.this, mBookList);
        mRecyclerView.setAdapter(mBookAdapter);
        mBookAdapter.setOnItemClickListener(FindBooks.this);

        //list all books not owned
        try {
            new FetchBookWithList(mBookList, mBookListID, mBookAdapter).execute("findBooks");
        }catch (Exception e){
            e.printStackTrace();
        }

        Button searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("testing", "boost size: " + mBookList.size());
                filterText = filterView.getText().toString();
                new FetchBookWithList(mBookList,mBookListID, mBookAdapter).execute("filter", filterText);
                //loadBooks();
                //new FetchBookWithList(mBookList,mBookListID, mBookAdapter).execute("findBooks");
            }
        });


/*
        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book book = (Book) bookList.getItemAtPosition(i);
                String ownerEmail = book.getOwnerEmail();
                User borrower = MainActivity.getUser();
                //Request request = new Request(owner, borrower, book);
                borrower.addToMyRequestedBooks(book);
                //owner.addToRequestedBooks(book);


            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }

        });
*/


        //mBookAdapter = new ArrayAdapter<Book>(this, R.layout.list_item, mBookList);


    }

    @Override
    protected void onStart() {
        super.onStart();
        //updateBooks();
    }
    //not used for now. NOPE it's actually running
    private void updateBooks(){
        try {
            if(mBookListID == null){
                mBookListID = loggedInUser.getMyRequestedBooksID();
            }else{
                mBookListID.clear();
            }
            if(mBookList == null){
                mBookList = new ArrayList<>();
            }else{
                mBookList.clear();
            }
            if(mBookAdapter == null) {
                mBookAdapter = new BookAdapter(FindBooks.this, mBookList);
                mRecyclerView.setAdapter(mBookAdapter);
                mBookAdapter.setOnItemClickListener(FindBooks.this);
            }
            //loadBooks();
            //new FetchBookWithList(mBookList, mBookListID, mBookAdapter).execute("findBooks");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Load books into recyclerview
     */
    public void loadBooks(){
        loadMyBookFromFireBase(new loadBookCallBack() {
            @Override
            public void loadBookCallBack(ArrayList<Book> value) {
                mBookList = (ArrayList<Book>) value.clone();
                Log.d("testing","book size: "+ mBookList.size());
                mBookAdapter = new BookAdapter(FindBooks.this, mBookList);
                mRecyclerView.setAdapter(mBookAdapter);
                mBookAdapter.setOnItemClickListener(FindBooks.this);
            }
        });
    }

    /**
     * The interface Load book callback.
     */
    public interface loadBookCallBack {
        /**
         * Load book call back.
         *
         * @param value the value
         */
        void loadBookCallBack(ArrayList<Book> value);
    }

    /**
     * Load book from firebase.
     *
     * @param myCallback part of loadMyBookFromFireBase
     */
    public void loadMyBookFromFireBase(final loadBookCallBack myCallback){

        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("books");
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    try {
                        ArrayList<Book> allBooks = new ArrayList<>();
                        ArrayList<Book> filteredBooks = new ArrayList<>();
                        for (DataSnapshot books : dataSnapshot.getChildren()) {
                            Book book = books.getValue(Book.class);
                                if (book.getOwnerID() != null && !book.getOwnerID().equals(loggedInUser.getUserID())) {
                                    allBooks.add(book);

                            }
                        }
                        // filter books
                        Log.d("hey","Test0");
                        if (filterText != " ") {
                            Log.d("hey","Test1");
                            for (Book book : allBooks) {
                                if(book.getTitle().toLowerCase().contains(filterText.toLowerCase())
                                || book.getAuthor().toLowerCase().contains(filterText.toLowerCase())
                                || book.getOwnerEmail().toLowerCase().contains(filterText.toLowerCase())
                                || book.getISBN().toLowerCase().contains(filterText.toLowerCase())
                                || book.getStatus().toLowerCase().contains(filterText.toLowerCase())) {
                                    Log.e("hey","Test2");
                                    filteredBooks.add(book);
                                }
                            }
                        } else {
                            Log.e("TAG", "NOPE " + filterText);
                            filteredBooks = allBooks;
                        }
                        myCallback.loadBookCallBack(filteredBooks);
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

    /**
     * Returns book details on click
     * @param position the index of an item from the arrayList
     */
    @Override
    public void onItemClick(int position) {
        Book book = (Book) mBookList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("BOOKID",book.getBookID());
        Intent intent = new Intent(FindBooks.this, CreateRequest.class);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        startActivity(intent);
    }


}