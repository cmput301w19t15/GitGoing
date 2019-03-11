package com.example.cmput301w19t15;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookMethodCalls {
    static Book book;
    static String bookID;

    public BookMethodCalls(){}

    public static Book getBook(){
        return book;
    }

    /**
     * @reuse: https://stackoverflow.com/questions/47847694/how-to-return-datasnapshot-value-as-a-result-of-a-method
     * @param bookid
     */
    public static void getBookWithID(final String bookid){
        bookID = bookid;
        loadMyBookFromFireBase(new loadBookCallBack() {
            @Override
            public void loadBookCallBack(Book value) {
                book = value;
            }
        });
    }
    /**
     * @reuse: https://stackoverflow.com/questions/47847694/how-to-return-datasnapshot-value-as-a-result-of-a-method
     */
    public interface loadBookCallBack {
        void loadBookCallBack(Book value);
    }
    /**
     * @reuse: https://stackoverflow.com/questions/47847694/how-to-return-datasnapshot-value-as-a-result-of-a-method
     */
    public static void loadMyBookFromFireBase(final loadBookCallBack myCallback){
        DatabaseReference bookReference = FirebaseDatabase.getInstance().getReference().child("books");
        bookReference.child(bookID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    try {
                        ArrayList<Book> allBooks = new ArrayList<>();
                        for (DataSnapshot books : dataSnapshot.getChildren()) {
                            Book book = books.getValue(Book.class);
                            allBooks.add(book);
                        }
                        myCallback.loadBookCallBack(allBooks.get(0));
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
