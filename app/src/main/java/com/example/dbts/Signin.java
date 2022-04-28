package com.example.dbts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Signin extends AppCompatActivity {
    Button SignupButton , SigninButton; // initializing Two Buttons For Sign-in & Sign-up Operations

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        SignupButton = findViewById(R.id.Signup_Tab); // Fetch The Signup-Tab Button From XML File
        SigninButton = findViewById(R.id.SigninButtonTab); // Fetch The Button From XML File

        SignupButton.setOnClickListener(view -> {
            SignupButton.setBackgroundResource(R.drawable.activated_rounded_button);
            SigninButton.setBackgroundResource(R.drawable.nonactivated_rounded_button);
            SignupButton.setTextColor(getResources().getColor(R.color.white));
            SigninButton.setTextColor(getResources().getColor(R.color.black));
            startActivity(new Intent(Signin.this,Signup.class));
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Signin.this,Signup.class));
        super.onBackPressed();
    }
}