
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

public class AddBookInfo extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private EditText booktitle;
    private EditText author;
    private EditText isbn;

    private String booktitleText;
    private String authorText;
    private String isbnText;
    private String bookPhoto;

    private ZXingScannerView scannerView;
    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_info);

        booktitle = findViewById(R.id.booktitle);
        author = findViewById(R.id.author);
        isbn = findViewById(R.id.isbn);

        Button saveButton = findViewById(R.id.deleteBook);
        Button addPhoto = findViewById(R.id.addPhoto);
        Button scanInfo = findViewById(R.id.scanInfo);

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

                //pick book table to same the book
                DatabaseReference newBook = FirebaseDatabase.getInstance().getReference().child("books").child(book.getBookID());

                //add the book in the database
                newBook.setValue(book);
                newBook.setValue(book).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddBookInfo.this, "Successfully Added Book", Toast.LENGTH_SHORT).show();
                            try {
                                //set time in mili
                                Thread.sleep(3000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                //returnIntent.putExtra("result", result);
                //setResult(RESULT_OK, returnIntent);
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

    private void selectPhoto() {
        /**
         * build an AlertDialog
         * let user choose among Camera, Gallery or Cancel this action
         */
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddBookInfo.this);
        builder.setTitle("Upload Photo");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /**
                 * if user chooses Camera, call camera and will return a bitmap as a result
                 */
                if (items[which] == "Camera"){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                }
                /**
                 * if user chooses gallery, call mediaStorage and will return a uri object
                 */
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
     * Source: https://github.com/dm77/barcodescanner
     * this methods initialize scanner
     * @param view
     */
    public void scan(View view){
        scannerView = new ZXingScannerView(getApplicationContext());
        setContentView(scannerView);
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    /**
     * this method will override the pause class,
     * in this case, it'll stop camera.

    @Override
    protected void onPause(){
        super.onPause();
        scannerView.stopCamera();
    }
    */

    /**
     * this method is from the ZXing class we implement,
     * it will override the handleResult class from its interface
     * and display a message whenever we scan barcode
     * @param result
     */
    @Override
    public void handleResult(Result result){
        Toast.makeText(getApplicationContext(),result.getText(),Toast.LENGTH_LONG).show();
        scannerView.resumeCameraPreview(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == Activity.RESULT_OK){
            /**
             * return bitmap and assign it to book's attribute
             */
            if (requestCode == REQUEST_CAMERA){

                Bundle bundle = data.getExtras();
                final Bitmap bitmap =  (Bitmap) bundle.get("data");
                String bookPhoto = ConvertPhoto.convert(bitmap);
                this.bookPhoto = bookPhoto;

            }
            /**
             * return uri, then comvert to bitmap and assign it to book's attribute
             */
            else if (requestCode == SELECT_FILE){
                Uri photoUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),photoUri);
                    String bookPhoto = ConvertPhoto.convert(bitmap);
                    this.bookPhoto = bookPhoto;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.d("testing",this.bookPhoto);
        }
    }
}