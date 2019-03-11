package com.example.cmput301w19t15;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RequestedBookList extends AppCompatActivity implements BookAdapter.OnItemClickListener{
    private RequestedBookList activity = this;
    private Button all, accepted;
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


        Log.d("testing","done");

    }

    public void loadBooks(){
        loadMyBookFromFireBase(new FindBooks.loadBookCallBack() {
            @Override
            public void loadBookCallBack(ArrayList<Book> value) {
                listOfBooks = (ArrayList<Book>) value.clone();
                Log.d("testing","book size: "+listOfBooks.size());
                adapter = new BookAdapter(RequestedBookList.this,listOfBooks);
                mRecyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(RequestedBookList.this);
            }
        });
    }

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
                                Log.d("testing",books.getKey());
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
