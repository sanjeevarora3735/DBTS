package com.example.dbts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class Signup extends AppCompatActivity {
    // For Switching into Different Modes
    Button SignupTabButton, SigninTabButton; // initializing Two Buttons For Sign-in & Sign-up Operations
    // For Action After Clicked on Signup Button
    Button SignupButton;
    // Fetch Value Of EditTexts
    TextInputEditText FullName,Email,Password,Student_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // Initialization Of Buttons
        SignupTabButton = findViewById(R.id.Signup_Tab); // Fetch The Signup-Tab Button From XML File
        SigninTabButton = findViewById(R.id.SigninButtonTab); // Fetch The Button From XML File
        SignupButton = findViewById(R.id.SignupButton);

        // Initialization of TextInputEditText
        FullName = findViewById(R.id.FullNameEditText);
        Email = findViewById(R.id.EmailEditText);
        Student_ID = findViewById(R.id.Student_IDEditText);
        Password = findViewById(R.id.PasswordEditText);

        // On Click Listeners
        SigninTabButton.setOnClickListener(v -> {
            SigninTabButton.setBackgroundResource(R.drawable.activated_rounded_button);
            SignupTabButton.setBackgroundResource(R.drawable.nonactivated_rounded_button);
            SignupTabButton.setTextColor(getResources().getColor(R.color.black));
            SigninTabButton.setTextColor(getResources().getColor(R.color.white));
            startActivity(new Intent(Signup.this, Signin.class));
        });
        SignupButton.setOnClickListener(v -> {
            String Information[] = new String[4] ;
            Information[0] = FullName.getText().toString();
            Information[1] = Email.getText().toString();
            Information[2] = Student_ID.getText().toString();
            Information[3] = Password.getText().toString();
            Intent AccountVerification =  new Intent(this,account_verification.class);
            AccountVerification.putExtra("SignupDetails",Information);
            startActivity(AccountVerification);
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Signup.this, GetStarted.class));
        super.onBackPressed();
    }
}