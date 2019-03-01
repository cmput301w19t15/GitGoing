package com.example.cmput301w19t15;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MyBooks extends AppCompatActivity {

    /** Called when MyBooks Activity first created**/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);

        //get content
        final ListView mybookslist = (ListView) findViewById(R.id.mybooklistview);
        Button addnewbook = (Button) findViewById(R.id.addNewBook);

        //when book in list clicked, book is passed to book info and its activity is started
        mybookslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MyBooks.this, BookInfo.class);
                Book book = (Book) mybookslist.getItemAtPosition(i);

                intent.putExtra("Book",book);
                setResult(RESULT_OK, intent);
                startActivity(intent);
            }
        });

    }

}
