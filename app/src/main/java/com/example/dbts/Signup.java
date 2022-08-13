package com.example.dbts;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

public class Signup extends AppCompatActivity {
    // Top Declaration Block For All Elements & Members
    Button SignupTabButton, SigninTabButton;
    Button SignupButton;
    TextInputLayout FullNameTextInputLayout, EmailTextInputLayout, PasswordTextInputLayout, Student_IDTextInputLayout;
    TextInputEditText FullName, Email, Password, Student_ID;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        // FindViewById @ Buttons
        SignupTabButton = findViewById(R.id.Signup_Tab);
        SigninTabButton = findViewById(R.id.SigninButtonTab);
        SignupButton    = findViewById(R.id.SignupButton);

        // FindViewById @ TextInputEditText
        FullName   = findViewById(R.id.FullNameEditText);
        Email      = findViewById(R.id.EmailEditText);
        Student_ID = findViewById(R.id.Student_IDEditText);
        Password   = findViewById(R.id.PasswordEditText);

        // FindViewById @ TextInputLayout
        FullNameTextInputLayout   = findViewById(R.id.FullName);
        EmailTextInputLayout      = findViewById(R.id.Email);
        Student_IDTextInputLayout = findViewById(R.id.Student_ID);
        PasswordTextInputLayout   = findViewById(R.id.password);

        // Get Instance Of Firebase Authentication
        mAuth = FirebaseAuth.getInstance();


        // On Click Listener - @ Switching Modes - TabLinear Layout
        SigninTabButton.setOnClickListener(v -> {
            SigninTabButton.setBackgroundResource(R.drawable.activated_rounded_button);
            SignupTabButton.setBackgroundResource(R.drawable.nonactivated_rounded_button);
            SignupTabButton.setTextColor(getResources().getColor(R.color.black));
            SigninTabButton.setTextColor(getResources().getColor(R.color.white));
            startActivity(new Intent(Signup.this, Signin.class));
        });

        // On Click Listener - @ Signup Button - Registering Process
        SignupButton.setOnClickListener(v -> {
            boolean EverythingIsSet = false;
            if ((FullName.getText().toString().trim()).equalsIgnoreCase("")) {
                FullNameTextInputLayout.setErrorEnabled(true);
                FullNameTextInputLayout.setError("Name is required.");
            } else if ((Student_ID.getText().toString().trim()).equalsIgnoreCase("")) {
                Student_IDTextInputLayout.setErrorEnabled(true);
                Student_IDTextInputLayout.setError("STUDENT ID is required.");
            } else if (Integer.parseInt(Student_ID.getText().toString()) < 10000) {
                Student_IDTextInputLayout.setErrorEnabled(true);
                Student_IDTextInputLayout.setError("Please enter a valid student id");
            } else if (!DataHandler.isValidEmail(Email.getText().toString().trim())) {
                EmailTextInputLayout.setErrorEnabled(true);
                EmailTextInputLayout.setError("Please enter a valid email address");
            } else if ((Email.getText().toString().trim()).equalsIgnoreCase("")) {
                EmailTextInputLayout.setErrorEnabled(true);
                EmailTextInputLayout.setError("Email address is required.");
            } else {
                // Setting EverythingIsSet -> True @ All Errors are Resolved
                EverythingIsSet = true;

                // Removing Any Active Error Statement From TextInputLayout
                FullNameTextInputLayout.setErrorEnabled(false);
                EmailTextInputLayout.setErrorEnabled(false);
                Student_IDTextInputLayout.setErrorEnabled(false);
                PasswordTextInputLayout.setErrorEnabled(false);
            }
            if (EverythingIsSet) {

                // Create a Object of Class FirebaseDataCollection To Handle User's Data
                FirebaseDataCollection FCollectedData;
                FCollectedData = new FirebaseDataCollection(FullName.getText().toString().trim(), Integer.parseInt(Student_ID.getText().toString().trim()), Email.getText().toString().trim(), Password.getText().toString().trim());

                // Creating Account For User on Our Firebase Authentication and Firestore Database.
                CreateAccountWithEmailandPassword(FCollectedData.getEMAIL(), FCollectedData.getPASSWORD(), FCollectedData);
            }
        });


    }

    private void SaveInformationAsBackup(FirebaseDataCollection fCollectedData) {

        // Getting The Instance of Firebase Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Saving Authentication Data as Backup @ User Information
        db.collection("Users").document(fCollectedData.getEMAIL().toLowerCase())
                .set(fCollectedData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        FullName.setText("");
                        Student_ID.setText("");
                        Email.setText("");
                        Password.setText("");
//                                  mAuth.getCurrentUser().sendEmailVerification()
//                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<Void> task) {
//                                                if (task.isSuccessful()) {
//                                                    Toast.makeText(getApplicationContext(), "Check Your Inbox to get your OTP ", Toast.LENGTH_SHORT).show();
//                                                }
//                                            }
//                                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Signup.this, "Signup Failed! Try Again ...", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void CreateAccountWithEmailandPassword(String email, String password, FirebaseDataCollection fCollectedData) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            if (user.getDisplayName() == "" || user.getDisplayName() == null) {
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(FullName.getText().toString().trim())
                                        .build();


                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    SaveInformationAsBackup(fCollectedData);
                                                    Toast.makeText(getApplicationContext(), "Success.", Toast.LENGTH_SHORT).show();
                                                    Intent Verification_OTP = new Intent(Signup.this,account_verification.class);
                                                    Verification_OTP.putExtra("VerificationCode","3951");
                                                    startActivity(Verification_OTP);
                                                }
                                            }
                                        });
                            } else {
                                Toast.makeText(getApplicationContext(), user.getDisplayName(), Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Authentication failed. : " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Signup.this, GetStarted.class));
        super.onBackPressed();
    }

    private void updateUI(FirebaseUser currentUser) {
//        mAuth.signOut();
        if (currentUser != null) {
            Toast.makeText(this, currentUser.getDisplayName(), Toast.LENGTH_SHORT).show();

        }
    }
}