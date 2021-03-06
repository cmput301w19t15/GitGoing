package com.example.cmput301w19t15.Functions;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cmput301w19t15.Objects.Book;
import com.example.cmput301w19t15.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Allows the retrieval of a book using the book's ID
 * this activity is intended to get the book object from firebase
 * and update the the listview or edittext when called with the
 * email isbn, owner email and etc..
 * 
 */
public class FetchBookWithID extends AsyncTask<String, Void, String> {

    private ArrayList<Book> books;
    private EditText titleEditText,authorEditText,ISBNEditText;
    private ImageView image;
    /**
     * The Author text.
     */
    TextView authorText, /**
     * The Title text.
     */
    titleText, /**
     * The Isbn text.
     */
    isbnText, /**
     * The Owner email text.
     */
    ownerEmailText, /**
     * The Status text.
     */
    statusText;
    /**
     * The Title.
     */
    String title, /**
     * The Author.
     */
    author, /**
     * The Isbn.
     */
    isbn, /**
     * The Owner email.
     */
    ownerEmail, /**
     * The Status.
     */
    status, /**
     * The Photo.
     */
    photo, /**
     * The Rating.
     */
    rating;
    /**
     * The Return date.
     */
    Long returnDate;

    /**
     * Gets the book title, id, author, and ISBN
     *
     * @param book   the book
     * @param title  the title
     * @param author the author
     * @param isbn   the isbn
     */
    public FetchBookWithID(ArrayList<Book> book, EditText title, EditText author, EditText isbn){
        this.books = book;
        this.titleEditText = title;
        this.authorEditText = author;
        this.ISBNEditText = isbn;
        //this.image = image;
    }

    /**
     * Gets the status as well as the above
     *
     * @param book   the book
     * @param title  the title
     * @param author the author
     * @param isbn   the isbn
     * @param owner  the owner
     * @param status the status
     */
    public FetchBookWithID(ArrayList<Book> book, TextView title, TextView author, TextView isbn, TextView owner, TextView status){
        this.books = book;
        this.titleText = title;
        this.authorText = author;
        this.isbnText = isbn;
        this.ownerEmailText = owner;
        this.statusText = status;
    }

    /**
     * Gets the photo and rating as well as the above
     *
     * @param book       the book
     * @param returnDate the return date
     * @param title      the title
     * @param author     the author
     * @param isbn       the isbn
     * @param owner      the owner
     * @param status     the status
     * @param photo      the photo
     * @param rating     the rating
     */
    public FetchBookWithID(ArrayList<Book> book, Long returnDate, String title, String author, String isbn, String owner, String status, String photo,String rating){
        this.books = book;
        this.title= title;
        this.author = author;
        this.isbn = isbn;
        this.ownerEmail = owner;
        this.status = status;
        this.photo = photo;
        this.returnDate =returnDate;
        this.rating = rating;
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
                            books.add(0,book);

                            if(titleEditText != null && authorEditText != null && ISBNEditText != null) {
                                titleEditText.setText(book.getTitle());

                                authorEditText.setText(book.getAuthor());
                                ISBNEditText.setText(book.getISBN());
                                String imageString = book.getPhoto();
                                image.setImageBitmap(ConvertPhoto.convert(imageString));
                            }

                            if(titleText != null && authorText != null && isbnText != null){

                                titleText.setText(book.getTitle());
                                authorText.setText(book.getAuthor());
                                isbnText.setText(book.getISBN());
                                ownerEmailText.setText(book.getOwnerEmail());
                                statusText.setText(book.getStatus());

                            }
                            if(title != null && author != null && isbn != null){
                                title=book.getTitle();
                                author=book.getAuthor();
                                isbn=book.getISBN();
                                ownerEmail=book.getOwnerEmail();
                                status=book.getStatus();
                                photo = book.getPhoto();
                                returnDate = book.getDate();
                            }

                        } catch (Exception e){
                            Log.d("testing",e.toString());
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