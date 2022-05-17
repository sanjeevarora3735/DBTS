package com.example.dbts;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.protobuf.Value;

public class Signin extends AppCompatActivity {
    Button SignupButton, SigninButton, SigninContinue; // initializing Two Buttons For Sign-in & Sign-up Operations
    TextInputEditText EmailAddressEditText, PasswordEditText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mAuth = FirebaseAuth.getInstance();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        EmailAddressEditText = findViewById(R.id.EmailEditText);
        PasswordEditText = findViewById(R.id.PasswordEditText);


        SignupButton = findViewById(R.id.Signup_Tab); // Fetch The Signup-Tab Button From XML File
        SigninButton = findViewById(R.id.SigninButtonTab); // Fetch The Button From XML File
        SigninContinue = findViewById(R.id.SigninButton);

        SignupButton.setOnClickListener(view -> {
            SignupButton.setBackgroundResource(R.drawable.activated_rounded_button);
            SigninButton.setBackgroundResource(R.drawable.nonactivated_rounded_button);
            SignupButton.setTextColor(getResources().getColor(R.color.white));
            SigninButton.setTextColor(getResources().getColor(R.color.black));
            startActivity(new Intent(Signin.this, Signup.class));
        });
        SigninContinue.setOnClickListener(v -> {
            SigninWithEmailandPassword(EmailAddressEditText.getText().toString().trim().toLowerCase(),PasswordEditText.getText().toString().trim().toLowerCase());
            startActivity(new Intent(getApplicationContext(), Dashboard.class));
        });
    }

    private void SigninWithEmailandPassword(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Signin.this, Signup.class));
        super.onBackPressed();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser);
        }
    }

    private void updateUI(FirebaseUser currFirebaseUser) {
        Intent UpdateUI = new Intent(Signin.this,Dashboard.class);
//        UpdateUI.putExtra("CurrentUserObject", (Bundle) o);
        startActivity(UpdateUI);
        Toast.makeText(getApplicationContext(), currFirebaseUser.getEmail().toString(), Toast.LENGTH_SHORT).show();
    }
}