package com.example.cmput301w19t15.Functions;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.cmput301w19t15.Activity.MainActivity;
import com.example.cmput301w19t15.Objects.Book;
import com.example.cmput301w19t15.Objects.BookAdapter;
import com.example.cmput301w19t15.Objects.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

/**
 * this activity is intended to get the all the books from a given
 * arraylist of book id and load books into recyclerview from the calling classes
 *
 */
// @reuse: https://github.com/google-developer-training/android-fundamentals/tree/master/WhoWroteIt
public class FetchBookWithList extends AsyncTask<String, Void, ArrayList<Book>> {

    private ArrayList<Book> bookList;
    private ArrayList<String> bookListID;
    private BookAdapter bookAdapter;
    private String listType;
    private String filter;
    private User loggedInUser = MainActivity.getUser();

    public FetchBookWithList(ArrayList<Book> bookList, ArrayList<String> idList, BookAdapter bookAdapter){
        this.bookList = bookList;
        this.bookListID = idList;
        this.bookAdapter = bookAdapter;
    }

    @Override
    protected ArrayList<Book> doInBackground(String... strings) {
        bookList.clear();
        final String bookidlist = bookListID.toString();
        if(strings.length == 1) {
            listType = strings[0];
        }
        if(strings.length == 2){
            listType = strings[0];
            filter = strings[1];
        }
        try{
            DatabaseReference bookReference = FirebaseDatabase.getInstance().getReference().child("books");
            bookReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        try {
                            for (DataSnapshot books : dataSnapshot.getChildren()) {
                                try {
                                    String bookid = books.child("bookID").getValue().toString();
                                    if (listType != null) {
                                        Book book = books.getValue(Book.class);
                                        if (listType.equalsIgnoreCase("findBooks") && !bookidlist.contains(bookid)) {
                                            bookList.add(book);
                                        } else if(listType.equalsIgnoreCase("filter")){
                                            if(filter.isEmpty() || filter.equalsIgnoreCase(" "))
                                                bookList.add(book);
                                            else if(book.getTitle().toLowerCase().contains(filter.toLowerCase())
                                                    || book.getAuthor().toLowerCase().contains(filter.toLowerCase())
                                                    || book.getOwnerEmail().toLowerCase().contains(filter.toLowerCase())
                                                    || book.getISBN().toLowerCase().contains(filter.toLowerCase())
                                                    || book.getStatus().toLowerCase().contains(filter.toLowerCase())) {
                                                bookList.add(book);
                                            }
                                        } else if(listType.equalsIgnoreCase("Requested") && book.getStatus().equalsIgnoreCase(listType) && bookidlist.contains(bookid)){
                                            bookList.add(book);
                                        } else if (listType.equalsIgnoreCase("WatchList") && bookidlist.contains(bookid)) {
                                            bookList.add(book);
                                        } else if ((listType.equalsIgnoreCase("Accepted") || listType.equalsIgnoreCase("Borrowed")) && book.getStatus().equalsIgnoreCase(listType) && bookidlist.contains(bookid) && book.getBorrowerID().equalsIgnoreCase(loggedInUser.getUserID())) {
                                            bookList.add(book);
                                        }


                                    } else if (bookidlist.contains(bookid)) {
                                        Book book = books.getValue(Book.class);
                                        bookList.add(book);
                                    }
                                }catch(Exception e){
                                    Log.d("tesseting",e.toString());
                                    e.printStackTrace();
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