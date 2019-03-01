/*package com.example.cmput301w19t15;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class AddBookInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_info);
    }
}
*/

package com.example.cmput301w19t15;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        Button saveButton = findViewById(R.id.save);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Book book = new Book(booktitleText, authorText, isbnText);
                Bundle result = new Bundle();
                Intent returnIntent = new Intent(AddBookInfo.this, MyBooks.class);
                //result.putSerializable("putresut", book);
                //========SAVE TO FIREBASE IDK HOW=================//
                returnIntent.putExtra("result", result);
                setResult(RESULT_OK, returnIntent);
                finish();
                // Meow ^_^

            }
        });

    }
}