package com.example.dbts;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class account_verification extends AppCompatActivity {
    // Declaring The ToolBar For Menu Attachment
    Toolbar toolbar;
    // ContinueButton
    Button ContinueButton;
    // Declaring The EditTextFields Of OTP
    TextInputEditText CODE1, CODE2, CODE3, CODE4;
    // SendLink TextView
    TextView SendLink;
    // For Back Button Pressed
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_verification);
        // Formatting The Options of ToolBar For Customization
        toolbar = findViewById(R.id.ToolBar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        // Initializing the EditText & Button (OTP) For Setting up the Events:
        CODE1 = findViewById(R.id.CODE1EditText);
        CODE2 = findViewById(R.id.CODE2EditText);
        CODE3 = findViewById(R.id.CODE3EditText);
        CODE4 = findViewById(R.id.CODE4EditText);
        ContinueButton = findViewById(R.id.ContinueButton);

        // Continue Button OnClickListener

        ContinueButton.setOnClickListener(v ->{
            startActivity(new Intent(account_verification.this,RouteSelection.class));
        });

        // SendLink TextView
        SendLink = findViewById(R.id.ResendLink);

        CODE1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CODE2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        CODE2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CODE3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        CODE3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CODE4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        // Send Link UnderLine && Clickable
        String html = "<u>Send again</u>";
        SendLink.setText(Html.fromHtml(html));
        SendLink.setOnClickListener(v ->{
            Toast.makeText(this, "Link Has Been Sent Again ", Toast.LENGTH_SHORT).show();
        });

        // Fetch Logo (Back Button) For Setting the OnClickListeners
        View logoView = getToolbarLogoView(toolbar);
        logoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logo clicked
                onBackPressed();
            }
        });
    }


    private View getToolbarLogoView(Toolbar toolbar) {
        //check if contentDescription previously was set
        boolean hadContentDescription = android.text.TextUtils.isEmpty(toolbar.getLogoDescription());
        String contentDescription = String.valueOf(!hadContentDescription ? toolbar.getLogoDescription() : "logoContentDescription");
        toolbar.setLogoDescription(contentDescription);
        ArrayList<View> potentialViews = new ArrayList<View>();
        //find the view based on it's content description, set programmatically or with android:contentDescription
        toolbar.findViewsWithText(potentialViews, contentDescription, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        //Nav icon is always instantiated at this point because calling setLogoDescription ensures its existence
        View logoIcon = null;
        if (potentialViews.size() > 0) {
            logoIcon = potentialViews.get(0);
        }
        //Clear content description if not previously present
        if (hadContentDescription)
            toolbar.setLogoDescription(null);
        return logoIcon;
    }

//    @Override
//    public void onBackPressed() {
//        if (pressedTime + 2000 > System.currentTimeMillis()) {
//            super.onBackPressed();
//            finish();
//        } else {
//            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
//        }
//        pressedTime = System.currentTimeMillis();
//    }
}