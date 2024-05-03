package com.example.task51c;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText fullNameEditText, usernameEditText, passwordEditText, confirmPasswordEditText;
    private Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullNameEditText = findViewById(R.id.fullName);
        usernameEditText = findViewById(R.id.newUsername);
        passwordEditText = findViewById(R.id.newPassword);
        confirmPasswordEditText = findViewById(R.id.confirmPassword);
        createAccountButton = findViewById(R.id.createAccountButton);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        if (fieldsAreValid()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean fieldsAreValid() {
        if (fullNameEditText.getText().toString().isEmpty() ||
                usernameEditText.getText().toString().isEmpty() ||
                passwordEditText.getText().toString().isEmpty() ||
                confirmPasswordEditText.getText().toString().isEmpty()) {

            if (fullNameEditText.getText().toString().isEmpty())
                fullNameEditText.setError("Required");
            if (usernameEditText.getText().toString().isEmpty())
                usernameEditText.setError("Required");
            if (passwordEditText.getText().toString().isEmpty())
                passwordEditText.setError("Required");
            if (confirmPasswordEditText.getText().toString().isEmpty())
                confirmPasswordEditText.setError("Required");

            return false;
        }

        if (!passwordEditText.getText().toString().equals(confirmPasswordEditText.getText().toString())) {
            confirmPasswordEditText.setError("Passwords do not match");
            return false;
        }

        return true;
    }
}