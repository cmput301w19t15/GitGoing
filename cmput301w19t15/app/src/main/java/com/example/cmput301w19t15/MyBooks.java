package com.example.cmput301w19t15;

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


public class MyBooks extends AppCompatActivity implements BookAdapter.OnItemClickListener {

    private MyBooks activity = this;
    private static User loggedInUser;
    private BookAdapter mBookAdapter;
    private ArrayList<Book> mBookList;
    private RecyclerView mRecyclerView;
    private static final int NEW_BOOK = 1;
    private Book clickedBook;



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
    protected void onStart() {
        super.onStart();
        //TextView textView = findViewById(R.id.textView2);
        //textView.setText("Number of books: " + arraySize);
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

    /**
     * update the booklist based of what the user does
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            mBookList.clear();
            mBookList.addAll(loggedInUser.getMyBooks());
            mBookAdapter.notifyDataSetChanged();
        }
        if(requestCode == 2){
            mBookList.clear();
            mBookList.addAll(loggedInUser.getMyBooks());
            mBookAdapter.notifyDataSetChanged();
        }
    }//onActivityResult
}