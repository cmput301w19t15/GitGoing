package com.example.cmput301w19t15.Activity;
//:)
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cmput301w19t15.Functions.ConvertPhoto;
import com.example.cmput301w19t15.Functions.FetchBookInfo;
import com.example.cmput301w19t15.Functions.FetchBookWithID;
import com.example.cmput301w19t15.Functions.FetchBookWithList;
import com.example.cmput301w19t15.Objects.Book;
import com.example.cmput301w19t15.R;
import com.example.cmput301w19t15.Objects.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.cmput301w19t15.Functions.ConvertPhoto.convert;

public class BookInfo extends AppCompatActivity {
    //on create happens when book in list of my books is clicked
    /**
     *User is able to edit or delete the book they have selected
     * @param saveInstanceState
     * @see MyBooks , FindBooks
     */
    private boolean notComplete = false;
    User loggedInUser;
    ArrayList<Book> book = new ArrayList<>();
    String bookID;
    EditText titleEditText,authorEditText,ISBNEditText;

    private String bookPhoto;
    private ImageView image;
    Integer SELECT_FILE = 0, REQUEST_CAMERA = 1, SCAN_ISBN = 3;

    //ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        loggedInUser = MainActivity.getUser();

        //Get book values from the MyBook class the user has clicked on the book
        bookID = (String) getIntent().getExtras().getString("BOOKID");
        //display book information as edit text
        titleEditText = findViewById(R.id.editMyBookTitle);
        authorEditText = findViewById(R.id.editMyBookAuthor);
        ISBNEditText = findViewById(R.id.editMyBookISBN);
        //image = findViewById(R.id.imageView);

        new FetchBookWithID(book,titleEditText,authorEditText,ISBNEditText).execute(bookID);
        ////need to get and set the current image - user will be able to update or delete the image
        //titleEditText.setText("hello");
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
        updatePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto();
            }
        });
        Button deletePhoto = findViewById(R.id.deletePhoto);

        deletePhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                book.get(0).setPhoto("");
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
        String photo = this.bookPhoto;

        book.get(0).setTitle(title);
        book.get(0).setAuthor(author);
        book.get(0).setISBN(isbn);
        book.get(0).setPhoto(photo);
        FirebaseDatabase.getInstance().getReference("books").child(bookID).setValue(book.get(0));// update books
        finish();
    }
    /**
     * Remove the book from the users class and and remove the book from firebase
     * --need to remove book from other users that have requested the books
     */
    private void deleteBook(){
        loggedInUser.removeMyBooksID(bookID);
        FirebaseDatabase.getInstance().getReference("books").child(bookID).removeValue();// delete book
        finish();
    }

    private void selectPhoto() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(BookInfo.this);
        builder.setTitle("Upload Photo");

        /*
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
     * this method is to handle the result that's been passed back from intent
     * @param requestCode a code that's required when an intent ihs called
     * @param resultCode a code that's required when an intent is to return data
     * @param data the data that's returned by the activity
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            /*
              return bitmap and assign it to book's attribute
             */
            if (requestCode == REQUEST_CAMERA) {
                Bundle bundle = data.getExtras();
                final Bitmap bitmap = (Bitmap) bundle.get("data");
                this.bookPhoto = convert(bitmap);
                image.setImageBitmap(bitmap);
            }

            /*
             * return uri, then comvert to bitmap and assign it to book's attribute
             */
            else if (requestCode == SELECT_FILE) {
                Uri photoUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                    this.bookPhoto = convert(bitmap);
                    image.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //Log.d("testing",this.bookPhoto);
    }

}
