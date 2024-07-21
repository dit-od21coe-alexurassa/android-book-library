package com.example.androidsql;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    Button loginBtn;
    TextView orSignupBtn;
    EditText emailTxt, passwordTxt;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();
        // initialize views
        emailTxt = findViewById(R.id.loginEmailTxt);
        passwordTxt = findViewById(R.id.loginPasswordTxt);

        loginBtn = findViewById(R.id.loginBtn);
        orSignupBtn = findViewById(R.id.orRegister);

        // listen for click events
        loginBtn.setOnClickListener(v -> {
            String email, password;
            email = String.valueOf(emailTxt.getText());
            password = String.valueOf(passwordTxt.getText());

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please provide email and password to login.", Toast.LENGTH_SHORT).show();
                return;
            }

            // signIn with email and password
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Sign in success
                            FirebaseUser user = mAuth.getCurrentUser();
                            // navigate to main activity screen
                            if (user != null) {
                                Toast.makeText(LoginActivity.this, String.format("Welcome back, %s !", user.getDisplayName()), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

        });

        // navigate to signup
        orSignupBtn.setOnClickListener(v -> {
            // navigate to signup screen
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        });

    }
}