package com.example.androidsql;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addButton;
    RecyclerView recyclerView;
    ImageView noDataImage;
    TextView noDataTxt;
    BookDatabaseHelper libraryDB;
    ArrayList<String> bookId, bookTitle, bookAuthor, bookPages;
    BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.floatingAddButton);
        recyclerView = findViewById(R.id.recyclerView);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(addIntent);
            }
        });

        this.libraryDB = new BookDatabaseHelper(MainActivity.this);
        this.bookId = new ArrayList<String>();
        this.bookTitle = new ArrayList<String>();
        this.bookAuthor = new ArrayList<String>();
        this.bookPages = new ArrayList<String>();

        // no data
        noDataImage = findViewById(R.id.noDataImage);
        noDataTxt = findViewById(R.id.noDataText);

        storeDataInArray();

        // initialize and set adapter for recyclerview
        bookAdapter = new BookAdapter(MainActivity.this, this, bookId, bookTitle, bookAuthor, bookPages);
        recyclerView.setAdapter(bookAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    void storeDataInArray() {
        Cursor cursor = libraryDB.readAllData();
        if (cursor.getCount() == 0) {  // Check if the cursor is empty
            noDataImage.setVisibility(View.VISIBLE);
            noDataTxt.setVisibility(View.VISIBLE);
        } else {
            if (cursor.moveToFirst()) {  // Move the cursor to the first row
                do {
                    this.bookId.add(cursor.getString(0));
                    this.bookTitle.add(cursor.getString(1));
                    this.bookAuthor.add(cursor.getString(2));
                    this.bookPages.add(cursor.getString(3));
                } while (cursor.moveToNext());  // Move to the next row
                noDataImage.setVisibility(View.GONE);
                noDataTxt.setVisibility(View.GONE);
            }
            cursor.close();  // Always close the cursor to avoid memory leaks
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.deleteAll) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete All books");
            builder.setMessage("Are you sure you want to delete all books");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    BookDatabaseHelper libraryDB = new BookDatabaseHelper(MainActivity.this);
                    libraryDB.deleteAllData();

                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create().show();
        }
        return super.onOptionsItemSelected(item);
    }
}