package com.example.cmput301w19t15.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.cmput301w19t15.Objects.Book;
import com.example.cmput301w19t15.Objects.BookAdapter;
import com.example.cmput301w19t15.Objects.Rating;
import com.example.cmput301w19t15.Objects.User;
import com.example.cmput301w19t15.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RatingTest extends AppCompatActivity {
    EditText userComment;
    private static User loggedInUser;
    private String temper = "4827b617-4675-492a-9af7-5decde33e89a";
    private ArrayList<Book> listOfRatings;
    Rating currentRating;
    private Book newBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_test);
        //bookID = (String) getIntent().getExtras().getString("BOOKID");
        loggedInUser = MainActivity.getUser();

        final RatingBar ratingBar = findViewById(R.id.stars);
        Button submit = findViewById(R.id.button);
        userComment = findViewById(R.id.comment);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //currentRating.setComment(userComment.getText().toString());
                //currentRating.setValues(""+ratingBar.getRating());
                FirebaseDatabase.getInstance().getReference("books").child(temper).child("ratings").child(loggedInUser.userID).child("value").setValue(currentRating.getValues());
                FirebaseDatabase.getInstance().getReference("books").child(temper).child("ratings").child(loggedInUser.userID).child("comment").setValue(currentRating.getComment());
                getMainRating();
                finish();
            }
        });
    }

    public void getMainRating() {
        listOfRatings = new ArrayList<>();
        loadMyRatingFromFireBase(new FindBooks.loadBookCallBack() {
            @Override
            public void loadBookCallBack(ArrayList<Book> value) {
                listOfRatings = (ArrayList<Book>) value.clone();
            }
        });
    }

    public void loadMyRatingFromFireBase(final FindBooks.loadBookCallBack myCallback){
        final DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("books");
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                float totalRatings = 0;
                Log.d("HELLO",""+totalRatings);
                int numRating = 0;
                float avgRating = 0;
                String temp = "";
                ArrayList<Book> allBooks = new ArrayList<>();
                if(dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot books : dataSnapshot.getChildren()) {
                            Rating currentRating = books.getValue(Rating.class);
                            temp = books.child(temper).child("ratings").child(loggedInUser.userID).child("value").getValue().toString();
                            totalRatings += Float.parseFloat(temp);
                            Log.d("HELLO",""+totalRatings);
                            numRating += 1;

                            Book book = books.getValue(Book.class);
                            allBooks.add(book);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
                String finalRating;
                avgRating = totalRatings/numRating;
                if (avgRating == 0) {
                    finalRating = "No reviews";
                }
                else {
                    finalRating = ""+avgRating;
                }

                for (Book book : allBooks) {
                    if (temper.equals(book.getBookID())) {
                        newBook = new Book(book);
                    }
                }
                newBook.setRating(finalRating);
                FirebaseDatabase.getInstance().getReference("books").child(temper).child("avgRating").setValue(finalRating);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("testing","Error: ", databaseError.toException());
            }
        });
    }
}