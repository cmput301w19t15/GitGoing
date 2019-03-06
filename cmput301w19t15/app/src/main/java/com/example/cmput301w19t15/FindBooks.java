package com.example.cmput301w19t15;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class FindBooks extends AppCompatActivity {
    private EditText bookSearch, filter2Add;
    private Button addFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_books);

        bookSearch = findViewById(R.id.search);
        filter2Add = findViewById(R.id.filter);
        addFilter = findViewById(R.id.addF);
    }

}
