package com.example.androidsql;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateActivity extends AppCompatActivity {
    EditText titleInput, authorInput, pagesInput;
    Button updateBtn, deleteBtn;
    String id, title, author, pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update);


        titleInput = findViewById(R.id.titleInput2);
        authorInput = findViewById(R.id.authorInput2);
        pagesInput = findViewById(R.id.numPagesInput2);
        updateBtn = findViewById(R.id.updateBookBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        getAndSetIntentData();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookDatabaseHelper databaseHelper = new BookDatabaseHelper(UpdateActivity.this);
                databaseHelper.updateData(id, titleInput.getText().toString(), authorInput.getText().toString(), pagesInput.getText().toString());
            }
        });


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete();
            }
        });
    }

    private void getAndSetIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra("id") && intent.hasExtra("title") && intent.hasExtra("author") && intent.hasExtra("pages")) {
            // getting data
            id = intent.getStringExtra("id");
            title = intent.getStringExtra("title");
            author = intent.getStringExtra("author");
            pages = intent.getStringExtra("pages");

            // setting data
            titleInput.setText(title);
            authorInput.setText(author);
            pagesInput.setText(pages);
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title);
        builder.setMessage("Are you sure you want to delete " + title);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BookDatabaseHelper bookDatabaseHelper = new BookDatabaseHelper(UpdateActivity.this);
                bookDatabaseHelper.deleteSingleRow(id);
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
}