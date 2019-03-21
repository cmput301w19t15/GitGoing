
package com.example.cmput301w19t15;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;

import java.io.IOException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * this activity is to add information about the book
 * when creating a new book.
 * @see Book
 * @see BookInfo
 * @version 1.0
 */
public class AddBookInfo extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private EditText booktitle;
    private EditText author;
    private EditText isbn;

    private String booktitleText;
    private String authorText;
    private String isbnText;
    private String bookPhoto;

    private ZXingScannerView scannerView;
    Integer SELECT_FILE = 0, REQUEST_CAMERA = 1, SCAN_ISBN = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_info);

        booktitle = findViewById(R.id.booktitle);
        author = findViewById(R.id.bookAuthor);
        isbn = findViewById(R.id.isbn);

        Button saveButton = findViewById(R.id.deleteBook);
        Button addPhoto = findViewById(R.id.addPhoto);
        Button scanInfo = findViewById(R.id.scanInfo);

        /**
         * this method will create a new book object with all the
         * information entered and upload it to firebase
         */
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // still need to check for incorrect data types
                booktitleText = booktitle.getText().toString();
                authorText = author.getText().toString();
                isbnText = isbn.getText().toString(); // look up better way
                User loggedInUser = MainActivity.getUser();

                Book book = new Book(booktitleText, authorText, isbnText, bookPhoto, loggedInUser.getEmail(), loggedInUser.getUserID());
                loggedInUser.addToMyBooks(book);

                Bundle result = new Bundle();
                Intent returnIntent = new Intent(AddBookInfo.this, MyBooks.class);

                //result.putSerializable("putresut", book);

                //pick book table to save the book
                DatabaseReference newBook = FirebaseDatabase.getInstance().getReference().child("books").child(book.getBookID());

                //add the book in the database
                newBook.setValue(book).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddBookInfo.this, "Successfully Added Book", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                //returnIntent.putExtra("result", result);
                setResult(1,returnIntent);
                finish();
            }
        });

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto();
            }
        });
        scanInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan(v);
            }
        });
    }

    /**
     * build an AlertDialog
     * let user choose among Camera, Gallery or Cancel this action
     */
    private void selectPhoto() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddBookInfo.this);
        builder.setTitle("Upload Photo");

        /**
         * if user chooses Camera, call camera and will return a bitmap as a result
         */
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (items[which] == "Camera"){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                }

                 //if user chooses gallery, call mediaStorage and will return a uri object

                else if (items[which] == "Gallery"){
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);

                }
                else if (items[which] == "Cancel"){
                    dialog.dismiss();
                }
            }
        });
        //this is to show the alertdiaglog
        builder.show();
    }

    /**
     * @reuse https://github.com/dm77/barcodescanner
     * this methods initialize scanner
     */
    public void scan(View view){
        //https://github.com/ravi8x/Barcode-Reader
        Intent scannerIntent = new Intent(AddBookInfo.this,ScanBarcode.class);
        startActivityForResult(scannerIntent,SCAN_ISBN);
    }

    /**
     * this method is from the ZXing class we implement,
     * it will override the handleResult class from its interface
     * and display a message whenever we scan barcode
     * @param result the result that's returned from scanner intent
     */
    @Override
    public void handleResult(Result result){
        Toast.makeText(getApplicationContext(),result.getText(),Toast.LENGTH_LONG).show();
        scannerView.resumeCameraPreview(this);
    }

    /**
     * this method is to handle the result that's been passed back from intent
     * @param requestCode a code that's required when an intent ihs called
     * @param resultCode a code that's required when an intent is to return data
     * @param data the data that's returned by the activity
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SCAN_ISBN) {
                String barcode = data.getStringExtra("ISBN");
                isbn.setText(barcode);
            }
            /**
             * return bitmap and assign it to book's attribute
             */
            if (requestCode == REQUEST_CAMERA) {
                Bundle bundle = data.getExtras();
                final Bitmap bitmap = (Bitmap) bundle.get("data");
                String bookPhoto = ConvertPhoto.convert(bitmap);
                this.bookPhoto = bookPhoto;
            }

            /**
             * return uri, then comvert to bitmap and assign it to book's attribute
             */
            else if (requestCode == SELECT_FILE) {
                Uri photoUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                    String bookPhoto = ConvertPhoto.convert(bitmap);
                    this.bookPhoto = bookPhoto;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //Log.d("testing",this.bookPhoto);
    }
}