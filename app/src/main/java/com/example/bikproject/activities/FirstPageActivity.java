package com.example.bikproject.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bikproject.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirstPageActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Intent firstPageIntent;
    private EditText etNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        FirebaseApp.initializeApp(FirstPageActivity.this);

        etNumber = findViewById(R.id.editTextNumberPassword);
        Button loginButton = findViewById(R.id.logInButton);

        firstPageIntent = new Intent(this, LoginPageActivity.class);

        firebaseAuth = FirebaseAuth.getInstance();
        loginButton.setOnClickListener(view -> onStart());
    }

    @Override
    protected void onStart() {
        super.onStart();

        String number = etNumber.getText().toString().trim();

        if (TextUtils.isEmpty(number)) {
            int a = 1;
        } else {
            startActivity(firstPageIntent);
            finish();
        }
    }
}
