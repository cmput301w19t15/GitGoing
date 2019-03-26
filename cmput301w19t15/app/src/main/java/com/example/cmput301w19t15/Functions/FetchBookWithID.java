package com.example.cmput301w19t15.Functions;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.cmput301w19t15.Objects.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FetchBookWithID extends AsyncTask<String, Void, Book> {

    private Book book;
    private String bookID;

    public FetchBookWithID(String bookID){
        this.bookID = bookID;
    }

    @Override
    protected Book doInBackground(String... strings) {
        try{
            FirebaseDatabase.getInstance().getReference().child("books").child(bookID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        try {
                            for (DataSnapshot books : dataSnapshot.getChildren()) {
                                book = books.getValue(Book.class);
                                break;
                            }
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return book;
        }
    }

    @Override
    protected void onPostExecute(Book s){
        super.onPostExecute(s);
        try {
            Log.d("testing", "do something here");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}