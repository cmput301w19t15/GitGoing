package com.example.cmput301w19t15.Functions;


import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.cmput301w19t15.Objects.Book;
import com.example.cmput301w19t15.Objects.BookAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class FetchBooksWithListByType extends AsyncTask<String, Void, ArrayList<Book>> {

    private ArrayList<Book> bookList;
    private ArrayList<String> bookListID;
    private BookAdapter bookAdapter;
    private String listType;

    public FetchBooksWithListByType(ArrayList<Book> bookList, ArrayList<String> idList, BookAdapter bookAdapter){
        this.bookList = bookList;
        this.bookListID = idList;
        this.bookAdapter = bookAdapter;
    }

    @Override
    protected ArrayList<Book> doInBackground(String... strings) {
        bookList.clear();
        final String bookidlist = bookListID.toString();
        if(strings.length > 0) {
            listType = strings[0];  //
        }
        try{
            DatabaseReference bookReference = FirebaseDatabase.getInstance().getReference().child("books");
            bookReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        try {
                            Log.d("tesint","child cound: "+dataSnapshot.getChildrenCount());
                            for (DataSnapshot books : dataSnapshot.getChildren()) {
                                String bookid = books.child("bookID").getValue().toString();
                                if(listType != null){
                                    if(!bookidlist.contains(bookid)){
                                        Book book = books.getValue(Book.class);
                                        bookList.add(book);
                                    }
                                }else if(bookidlist.contains(bookid)){
                                    Book book = books.getValue(Book.class);
                                    bookList.add(book);
                                }
                            }
                        } catch (Exception e){
                            Log.d("Testing","1: " + e.toString());
                            e.printStackTrace();
                        }
                    }
                    bookAdapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w("testing","Error: ", databaseError.toException());
                }
            });
        } catch (Exception e) {
            Log.d("Testing","2: " + e.toString());
            e.printStackTrace();
        }
        return bookList;
    }

    @Override
    protected void onPostExecute(ArrayList<Book> s){
        super.onPostExecute(s);
    }

}
