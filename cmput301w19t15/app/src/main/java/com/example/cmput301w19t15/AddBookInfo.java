
package com.example.cmput301w19t15;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.os.FileObserver.DELETE;

public class AddBookInfo extends AppCompatActivity {

    private EditText booktitle;
    private EditText author;
    private EditText isbn;

    private String booktitleText;
    private String authorText;
    private Integer isbnText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_info);

        booktitle = (EditText) findViewById(R.id.booktitle);
        author = (EditText) findViewById(R.id.author);
        isbn = (EditText) findViewById(R.id.isbn);

        Button saveButton = findViewById(R.id.addBook);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // still need to check for incorrect data types
                booktitleText = booktitle.getText().toString();
                authorText = author.getText().toString();
                isbnText = Integer.parseInt(isbn.getText().toString()); // look up better way
                User loggedInUser = MainActivity.getUser();

                Book book = new Book(booktitleText, authorText, isbnText,"null.png",loggedInUser.getEmail(),loggedInUser.getUserID());
                loggedInUser.addToMyBooks(book);

                Bundle result = new Bundle();
                Intent returnIntent = new Intent(AddBookInfo.this, MyBooks.class);

                //result.putSerializable("putresut", book);

                //pick book table to same the book
                DatabaseReference newBook = FirebaseDatabase.getInstance().getReference().child("books").child(book.getBookID());

                //add the book in the database
                newBook.setValue(book);
                newBook.setValue(book).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(AddBookInfo.this,"Successfully Added Book",Toast.LENGTH_SHORT).show();
                            try {
                                //set time in mili
                                Thread.sleep(3000);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                });
                returnIntent.putExtra("result", result);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });


    }
}