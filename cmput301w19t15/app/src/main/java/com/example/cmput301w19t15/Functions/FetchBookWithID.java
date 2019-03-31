package com.example.cmput301w19t15.Functions;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cmput301w19t15.Objects.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FetchBookWithID extends AsyncTask<String, Void, String> {

    private ArrayList<Book> books;
    private EditText titleEditText,authorEditText,ISBNEditText;
    private ImageView image;

    public FetchBookWithID(ArrayList<Book> book, EditText title, EditText author, EditText isbn, ImageView image){
        this.books = book;
        this.titleEditText = title;
        this.authorEditText = author;
        this.ISBNEditText = isbn;
        this.image = image;
    }

    @Override
    protected String doInBackground(String... strings) {
        final String bookID = strings[0];
        try{
            FirebaseDatabase.getInstance().getReference().child("books").child(bookID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        try {
                            Book book = dataSnapshot.getValue(Book.class);
                            books.add(book);

                            titleEditText.setText(book.getTitle());
                            authorEditText.setText(book.getAuthor());
                            ISBNEditText.setText(book.getISBN());
                            String imageString = book.getPhoto();
                            image.setImageBitmap(ConvertPhoto.convert(imageString));

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
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s){
        super.onPostExecute(s);
    }
}