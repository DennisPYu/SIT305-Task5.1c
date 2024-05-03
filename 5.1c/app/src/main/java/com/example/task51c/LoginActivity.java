package com.example.task51c;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton, signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        signUpButton = findViewById(R.id.sign_up);

        loginButton.setOnClickListener(v -> checkFieldsAndProceed());
        signUpButton.setOnClickListener(v -> navigateToSignUp());
    }

    private void checkFieldsAndProceed() {
        if (!usernameEditText.getText().toString().isEmpty() && !passwordEditText.getText().toString().isEmpty()) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        } else {
            usernameEditText.setError("Required");
            passwordEditText.setError("Required");
        }
    }

    private void navigateToSignUp() {
        startActivity(new Intent(this, SignUpActivity.class));
    }
}