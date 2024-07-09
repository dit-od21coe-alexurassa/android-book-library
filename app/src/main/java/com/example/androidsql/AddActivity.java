package com.example.androidsql;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddActivity extends AppCompatActivity {

    EditText titleInput, authorInput, pagesInput;
    Button addBookBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);

        titleInput = findViewById(R.id.titleInput);
        authorInput = findViewById(R.id.authorInput);
        pagesInput = findViewById(R.id.numPagesInput);
        addBookBtn = findViewById(R.id.addBookBtn);

        addBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookDatabaseHelper bookDB = new BookDatabaseHelper(AddActivity.this);
                bookDB.addBook(
                        titleInput.getText().toString().trim(),
                        authorInput.getText().toString().trim(),
                        Integer.parseInt(pagesInput.getText().toString().trim())
                );
            }
        });
    }
}