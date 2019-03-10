package com.example.cmput301w19t15;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FindBooks extends AppCompatActivity implements BookAdapter.OnItemClickListener{
    private FindBooks activity = this;
    private EditText bookSearch, filter2Add;
    private Button addFilter, searchBtn;
    private ListView bookList;
    private static User loggedInUser;
    private BookAdapter adapter;
    private ArrayList<Book> listOfBooks;
    private RecyclerView mRecyclerView;
    private String filterText;
    private EditText filterView;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set up the login form.
        setContentView(R.layout.activity_find_books);




        mRecyclerView = findViewById(R.id.recylcerView);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        listOfBooks = new ArrayList<>();
        loadBooks();


        Log.d("testing","done");


        filterView = findViewById(R.id.searchTextView);
        Button searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterText = filterView.getText().toString();
                loadBooks();
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


        //adapter = new ArrayAdapter<Book>(this, R.layout.list_item, listOfBooks);


    }

    public void loadBooks(){
        loadMyBookFromFireBase(new loadBookCallBack() {
            @Override
            public void loadBookCallBack(ArrayList<Book> value) {
                listOfBooks = (ArrayList<Book>) value.clone();
                Log.d("testing","book size: "+listOfBooks.size());
                adapter = new BookAdapter(FindBooks.this,listOfBooks);
                mRecyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(FindBooks.this);
            }
        });
    }
    public interface loadBookCallBack {
        void loadBookCallBack(ArrayList<Book> value);
    }

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
                            if(books.child("date").getValue().equals(null) || books.child("date").getValue().equals("null")) {
                                Log.d("testing", books.getKey());
                            } else {Book book = books.getValue(Book.class);
                                allBooks.add(book);
                            }
                        }
                        // filter books
                        if (filterText != " ") {
                            Log.d("debuging", filterText);
                            for (Book book : allBooks) {
                                if(book.getTitle().toLowerCase().contains(filterText.toLowerCase())) {
                                    filteredBooks.add(book);
                                }
                            }
                        } else {
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



    @Override
    public void onItemClick(int position) {

    }


}