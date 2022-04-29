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
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class account_verification extends AppCompatActivity {
    // Top Declaration Block For All Elements & Members
    private Toolbar toolbar;
    private Button ContinueButton;
    private TextInputEditText CODE1, CODE2, CODE3, CODE4;
    private TextInputLayout CODE1TextInputLayout, CODE2TextInputLayout, CODE3TextInputLayout, CODE4TextInputLayout;
    private TextView SendLink;
    private long pressedTime;
    private TextView ErrorDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_verification);
        // FindViewById @ ToolBar
        toolbar = findViewById(R.id.ToolBar);

        // FindViewById @ TextInputEditText : OTP CODE
        CODE1 = findViewById(R.id.CODE1EditText);
        CODE2 = findViewById(R.id.CODE2EditText);
        CODE3 = findViewById(R.id.CODE3EditText);
        CODE4 = findViewById(R.id.CODE4EditText);

        // FindViewById @ TextInputLayout : OTP CODE
        CODE1TextInputLayout = findViewById(R.id.CODE1);
        CODE2TextInputLayout = findViewById(R.id.CODE2);
        CODE3TextInputLayout = findViewById(R.id.CODE3);
        CODE4TextInputLayout = findViewById(R.id.CODE4);

        // FindViewById @ Button : Continue Button
        ContinueButton = findViewById(R.id.ContinueButton);

        // FindViewById @ TextView For Resend Link  & ErrorDisplay:
        SendLink = findViewById(R.id.ResendLink);
        ErrorDisplay = findViewById(R.id.ErrorDisplay);

        // Declaration & Initialization @ Back Button :
        View logoView = getToolbarLogoView(toolbar);

        // Some Modification On ActionBar || ToolBar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        // Creating The HREF Link For Send The Code Again To The User
        String html = "<u>Send again</u>";
        SendLink.setText(Html.fromHtml(html));
        SendLink.setOnClickListener(v ->{
            Toast.makeText(this, "Link Has Been Sent Again ", Toast.LENGTH_SHORT).show();
        });


        // OnClickListener @ Continue Button - Verification Button Done
        ContinueButton.setOnClickListener(v ->{
            // Fetching The Right Code For The Verification :
            Intent intent = getIntent();
            String OTP = intent.getStringExtra("VerificationCode");
            String EnteredOTP = String.format("%s%s%s%s", CODE1.getText().toString(), CODE2.getText().toString(), CODE3.getText().toString(), CODE4.getText().toString());
            if((CODE1.getText().toString()).equalsIgnoreCase("") || (CODE2.getText().toString()).equalsIgnoreCase("")|| (CODE3.getText().toString()).equalsIgnoreCase("")|| (CODE4.getText().toString()).equalsIgnoreCase("")){
                ErrorDisplay.setText(R.string.CodeBlankError);
            }else if(!EnteredOTP.equalsIgnoreCase(OTP)){
                ErrorDisplay.setText(R.string.CodeWrongError);
            }
            else {
                ErrorDisplay.setText("");
                startActivity(new Intent(account_verification.this,RouteSelection.class));
            }
            Toast.makeText(getApplicationContext(), new StringBuilder().append(CODE1.getText().toString()).append(CODE2.getText().toString()).append(CODE3.getText().toString()).append(CODE4.getText().toString()).toString(), Toast.LENGTH_SHORT).show();
        });



        // addTextChangedListener @ CODE - 1 : For Next Focus : Automatically
        CODE1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!CODE1.getText().toString().equalsIgnoreCase("")){
                    CODE2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // addTextChangedListener @ CODE - 2 : For Next Focus : Automatically
        CODE2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!CODE2.getText().toString().equalsIgnoreCase("")){
                    CODE3.requestFocus();
                }            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // addTextChangedListener @ CODE - 3 : For Next Focus : Automatically
        CODE3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!CODE3.getText().toString().equalsIgnoreCase("")){
                    CODE4.requestFocus();
                }            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        // Fetch Logo (Back Button) For Setting the OnClickListeners
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

    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to canceling the signup", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
}