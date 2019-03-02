
package com.example.cmput301w19t15;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.FileObserver.DELETE;

public class AddBookInfo extends AppCompatActivity {

    ImageView imageView;

    private EditText booktitle;
    private EditText author;
    private EditText isbn;

    private Uri file;

    private String booktitleText;
    private String authorText;
    private Integer isbnText;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_info);

        Button btnCamera = (Button) findViewById(R.id.takeBookPhoto);
        ImageView imageView = (ImageView) findViewById(R.id.bookPhoto);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });


        booktitle = (EditText) findViewById(R.id.booktitle);
        author = (EditText) findViewById(R.id.author);
        isbn = (EditText) findViewById(R.id.isbn);

        Button saveButton = findViewById(R.id.addBook);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Book book = new Book(booktitleText, authorText, isbnText);
                Bundle result = new Bundle();
                Intent returnIntent = new Intent(AddBookInfo.this, MyBooks.class);
                //result.putSerializable("putresut", book);
                //========SAVE TO FIREBASE IDK HOW=================//

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
                // Meow ^_^

            }
        });


        /*photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);


                /*Intent photoIntent = new Intent(AddBookInfo.this, BookPhoto.class);
                photoIntent.putExtra("flag", "add");
                startActivityForResult(photoIntent, 1000);
            }
        });*/

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(resultCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
    }
}