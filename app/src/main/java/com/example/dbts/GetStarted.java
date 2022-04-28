package com.example.dbts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class GetStarted extends AppCompatActivity {
    Button GetStarted; // initializing For GetStarted Operations
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        GetStarted = findViewById(R.id.GetStartedButton); // Fetch Button From XML File
        GetStarted.setOnClickListener(v -> {
            // Navigation to Signup Activity
            startActivity(new Intent(GetStarted.this, Signin.class));
        });
    }
}