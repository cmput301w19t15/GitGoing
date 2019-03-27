package com.example.cmput301w19t15.Activity;
//:)
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cmput301w19t15.Functions.ConvertPhoto;
import com.example.cmput301w19t15.Objects.Book;
import com.example.cmput301w19t15.R;
import com.example.cmput301w19t15.Objects.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookInfo extends AppCompatActivity {
    //on create happens when book in list of my books is clicked
    /**
     *User is able to edit or delete the book they have selected
     * @param saveInstanceState
     * @see MyBooks , FindBooks
     */
    User loggedInUser;
    Book book;
    String bookID;
    EditText titleEditText,authorEditText,ISBNEditText;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        loggedInUser = MainActivity.getUser();

        //Get book values from the MyBook class the user has clicked on the book
        bookID = (String) getIntent().getExtras().getString("BOOKID");
        final int position = (int) getIntent().getExtras().getInt("POSITION");
        book = loggedInUser.getMyBooks().get(position);
        //display book information as edit text
        titleEditText = findViewById(R.id.editMyBookTitle);
        authorEditText = findViewById(R.id.editMyBookAuthor);
        ISBNEditText = findViewById(R.id.editMyBookISBN);
        //set the text as the values the book currently is set to
        titleEditText.setText(book.getTitle());
        authorEditText.setText(book.getAuthor());
        ISBNEditText.setText(book.getISBN());
        String imageString = book.getPhoto();
        image = findViewById(R.id.imageView);
        image.setImageBitmap(ConvertPhoto.convert(imageString));
        ////need to get and set the current image - user will be able to update or delete the image

        Button updateBook = findViewById(R.id.updateBook);
        updateBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBook();
            }
        });

        Button deleteBook = findViewById(R.id.addBook);
        deleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBook();
            }
        });

        Button cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button updatePhoto = findViewById(R.id.addPhoto);
        updatePhoto.setEnabled(false);
        Button deletePhoto = findViewById(R.id.deletePhoto);

        deletePhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                book.setPhoto("");
                image.setImageResource(0);
            }
        });
    }

    /**
     * When the User clicks update book it will remove the book from the user and add
     * the new book then update firebase
     */
    private void updateBook(){
        String title = titleEditText.getText().toString();
        String author = authorEditText.getText().toString();
        String isbn = ISBNEditText.getText().toString();
        loggedInUser.removeMyBooks(book);
        book.setTitle(title);
        book.setAuthor(author);
        book.setISBN(isbn);
        loggedInUser.addToMyBooks(book);
        FirebaseDatabase.getInstance().getReference("books").child(book.getBookID()).setValue(book);// update books
        finish();
    }
    /**
     * Remove the book from the users class and and remove the book from firebase
     * --need to remove book from other users that have requested the books
     */
    private void deleteBook(){
        loggedInUser.removeMyBooks(book);
        loggedInUser.removeMyBooksID(book.getBookID());
        FirebaseDatabase.getInstance().getReference("books").child(book.getBookID()).removeValue();// delete book

        /**
         * iterate through all users
         */
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("users");
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    try {
                        for (DataSnapshot user : dataSnapshot.getChildren()) {

                            //remove requested book
                            ArrayList<Book> userRequestedList = new ArrayList<>();
                            for(DataSnapshot data: user.child("myRequestedBooks").getChildren()){
                                Book singleBook = data.getValue(Book.class);
                                if(!singleBook.getBookID().equalsIgnoreCase(book.getBookID())) {
                                    userRequestedList.add(book);
                                }
                            }
                            FirebaseDatabase.getInstance().getReference("users").child(user.getKey()).child("myRequestedBooks").setValue(userRequestedList);

                            //remove requested book id
                            ArrayList<String> userRequestedListID = new ArrayList<>();
                            for(DataSnapshot data: user.child("myRequestedBooksID").getChildren()){
                                String singleBookID = data.getValue(String.class);
                                if(!singleBookID.equalsIgnoreCase(book.getBookID())) {
                                    userRequestedListID.add(singleBookID);
                                }
                            }
                            FirebaseDatabase.getInstance().getReference("users").child(user.getKey()).child("myRequestedBooks").setValue(userRequestedListID);



                            //old method

                                final String userID = user.child("userID").getValue().toString();
                                /**
                                 * iterate through requested books for each user
                                 */
                                /*
                                DatabaseReference bookReference = FirebaseDatabase.getInstance().getReference().child("users")
                                        .child(userID).child("myRequestedBooks");
                                bookReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists()){
                                            try{
                                                for (DataSnapshot bookTemp : dataSnapshot.getChildren()) {
                                                    String bookID = bookTemp.child("bookID").getValue().toString();

                                                    if (book.getBookID().equals(bookID)){
                                                        FirebaseDatabase.getInstance().getReference("users").child(userID).child("myRequestedBooks").child(bookID).removeValue();
                                                    }
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                             */
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        finish();
    }
}
