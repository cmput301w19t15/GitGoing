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

// @reuse: https://github.com/google-developer-training/android-fundamentals/tree/master/WhoWroteIt
public class FetchBookWithList extends AsyncTask<String, Void, ArrayList<Book>> {

    private ArrayList<Book> bookList;
    private ArrayList<String> bookListID;
    private BookAdapter bookAdapter;
    private String listType;

    public FetchBookWithList(ArrayList<Book> bookList, ArrayList<String> idList, BookAdapter bookAdapter){
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
                            for (DataSnapshot books : dataSnapshot.getChildren()) {
                                Log.d("testing","books " + books.toString());
                                String bookid = books.child("bookID").getValue().toString();
                                if(listType != null){
                                    Book book = books.getValue(Book.class);
                                    if(listType.equalsIgnoreCase("findBooks") && !bookidlist.contains(bookid)){
                                        bookList.add(book);
                                    }else if(listType.equalsIgnoreCase("Requested") && bookidlist.contains(bookid)){
                                        bookList.add(book);
                                    }else if(listType.equalsIgnoreCase("WatchList") && bookidlist.contains(bookid)) {
                                        bookList.add(book);
                                    }else if(bookidlist.contains(bookid) && book.getStatus().equalsIgnoreCase(listType)) {
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