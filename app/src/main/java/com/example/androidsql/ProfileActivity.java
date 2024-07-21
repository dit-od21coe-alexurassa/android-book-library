package com.example.androidsql;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private String DISPLAY_NAME_KEY = "DISPLAY_NAME";
    private String EMAIL_KEY = "EMAIL";
    private String FAVOURITE_BOOK_KEY = "FAVOURITE_BOOK";
    String displayName, email, favouriteBook;
    EditText displayNameTxt, emailTxt, favouriteBookTxt;
    Button profileSaveBtn;

    private SharedPreferences namePref;
    SharedPreferences emailPref;
    SharedPreferences favouriteBookPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);


        displayNameTxt = findViewById(R.id.displayNameTxt);
        emailTxt = findViewById(R.id.userEmailTxt);
        favouriteBookTxt = findViewById(R.id.favouriteBookNameTxt);
        profileSaveBtn = findViewById(R.id.profileSaveBtn);

        // Init preferences
        namePref = getSharedPreferences(DISPLAY_NAME_KEY, MODE_PRIVATE);
        emailPref = getSharedPreferences(EMAIL_KEY, MODE_PRIVATE);
        favouriteBookPref = getSharedPreferences(FAVOURITE_BOOK_KEY, MODE_PRIVATE);

        // set profile information if any available in
        displayNameTxt.setText(namePref.getString(DISPLAY_NAME_KEY, ""));
        emailTxt.setText(emailPref.getString(EMAIL_KEY, ""));
        favouriteBookTxt.setText(favouriteBookPref.getString(FAVOURITE_BOOK_KEY, ""));

        profileSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfilePreferences();
            }
        });
    }

    private void saveProfilePreferences() {
        // save profile prefs to shared preference where display name and email are required
        String nameValue = displayNameTxt.getText().toString();
        String emailValue = emailTxt.getText().toString();

        if(!nameValue.isEmpty() && !emailValue.isEmpty()) {
            // save display name
            SharedPreferences.Editor nameEditor = namePref.edit();
            nameEditor.putString(DISPLAY_NAME_KEY, nameValue);
            nameEditor.apply();

            // save email
            SharedPreferences.Editor emailEditor = emailPref.edit();
            emailEditor.putString(EMAIL_KEY, emailValue);
            emailEditor.apply();

            // save favourite book
            SharedPreferences.Editor favouriteBookEditor = favouriteBookPref.edit();
            favouriteBookEditor.putString(FAVOURITE_BOOK_KEY, favouriteBookTxt.getText().toString());
            favouriteBookEditor.apply();

            Toast.makeText(this, "Profile saved", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(this, "Display name and email is required.", Toast.LENGTH_SHORT).show();
        }
    }
}