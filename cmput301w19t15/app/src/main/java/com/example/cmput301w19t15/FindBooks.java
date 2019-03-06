package com.example.cmput301w19t15;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class FindBooks extends AppCompatActivity {
    private EditText bookSearch, filter2Add;
    private Button addFilter;
    private FindBooks activity = this;
    private ListView bookList;
    private static User loggedInUser;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set up the login form.
        setContentView(R.layout.activity_find_books);

        bookList = (ListView) findViewById(R.id.book_list);
        bookSearch = findViewById(R.id.search);
        filter2Add = findViewById(R.id.filter);
        addFilter = findViewById(R.id.addF);

        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book book = (Book) bookList.getItemAtPosition(i);
                User owner = book.getOwner();
                User borrower = MainActivity.getUser();
                Request request = new Request(owner, borrower, book);
                borrower.addToMyRequestedBooks(book);
                owner.addToRequestedBooks(book);



                }
            });

        }
    }

}
