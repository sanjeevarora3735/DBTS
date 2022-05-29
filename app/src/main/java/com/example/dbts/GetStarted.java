package com.example.dbts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class GetStarted extends AppCompatActivity {
    Button GetStarted; // initializing For GetStarted Operations
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

//        Testing Purpose @ Smile :::::::::::::::::    0000    :::::::::::::::::
        startActivity(new Intent(GetStarted.this,StoppagePoints.class));

        GetStarted = findViewById(R.id.GetStartedButton); // Fetch Button From XML File
        GetStarted.setOnClickListener(v -> {
            // Navigation to Signup Activity
            startActivity(new Intent(GetStarted.this, Signin.class));
        });
    }
}