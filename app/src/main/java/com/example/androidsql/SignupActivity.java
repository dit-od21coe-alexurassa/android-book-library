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
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignupActivity extends AppCompatActivity {

    Button signupBtn;
    TextView orLoginBtn;
    EditText fullNameTxt, emailTxt, passwordTxt;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        // initialize views
        fullNameTxt = findViewById(R.id.signupFullName);
        emailTxt = findViewById(R.id.signupEmailTxt);
        passwordTxt = findViewById(R.id.signupPasswordTxt);
        signupBtn = findViewById(R.id.signupBtn);
        orLoginBtn = findViewById(R.id.orLogin);

        // listen for click events
        signupBtn.setOnClickListener(v -> {
            String fullName, email, password;
            fullName = String.valueOf(fullNameTxt.getText());
            email = String.valueOf(emailTxt.getText());
            password = String.valueOf(passwordTxt.getText());

            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignupActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            // Authenticate the user
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            if (user != null) {
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(fullName)
                                        .build();

                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(task1 -> {
                                        });
                            }

                            // navigate to main activity screen
                            Toast.makeText(SignupActivity.this, "Your account created successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignupActivity.this, "Account creation failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        orLoginBtn.setOnClickListener(v -> {
            // navigate to login screen
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        });
    }
}