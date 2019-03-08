package com.example.cmput301w19t15;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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
    private ArrayList<Book> listOfBooks = new ArrayList<Book>();
    private String bookListType = "listOfBooks";
    private RecyclerView mRecyclerView;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set up the login form.
        setContentView(R.layout.activity_find_books);

        bookList = (ListView) findViewById(R.id.book_list);
        bookSearch = findViewById(R.id.search);
        searchBtn = findViewById(R.id.search_button);


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

        adapter = new BookAdapter(FindBooks.this,listOfBooks);
        //adapter = new ArrayAdapter<Book>(this, R.layout.list_item, listOfBooks);
        mRecyclerView.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadBooks();

    }

    public void loadBooks(){
        loadMyBookFromFireBase(new User.loadBookCallBack() {
            @Override
            public void loadBooksCallBack(ArrayList<Book> value) {
                listOfBooks = (ArrayList<Book>) value.clone();
            }
        });
    }

    @Override
    public void onItemClick(int position) {

    }


    public interface loadBookCallBack {
        void loadBooksCallBack(ArrayList<Book> value);
    }
    public void loadMyBookFromFireBase(final User.loadBookCallBack myCallback){
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("books");
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    try {
                        ArrayList<Book> allBooks = new ArrayList<>();
                        for (DataSnapshot books : dataSnapshot.getChildren()) {
                            Book book = books.getValue(Book.class);
                            allBooks.add(book);
                        }
                        myCallback.loadBooksCallBack(allBooks);

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

}