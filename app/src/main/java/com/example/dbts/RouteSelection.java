package com.example.dbts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class RouteSelection extends AppCompatActivity {

    Button ContinueButton;
    ImageButton Route1, Route2, Route3;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_selection);

        ContinueButton = findViewById(R.id.ContinueButton);

        Route1 = findViewById(R.id.RouteNo1ImageButton);
        Route2 = findViewById(R.id.RouteNo2ImageButton);
        Route3 = findViewById(R.id.RouteNo3ImageButton);

        // Formatting The Options of ToolBar For Customization
        toolbar = findViewById(R.id.ToolBar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        ContinueButton.setOnClickListener(v ->
        {
            startActivity( new Intent(RouteSelection.this , locationRequest.class));
        });

        Route1.setOnClickListener(v -> {
            Route1.setBackgroundResource(R.drawable.route_selected);
            Route2.setBackgroundResource(R.drawable.route_selector);
            Route3.setBackgroundResource(R.drawable.route_selector);
        });
        Route2.setOnClickListener(v -> {
            Route2.setBackgroundResource(R.drawable.route_selected);
            Route1.setBackgroundResource(R.drawable.route_selector);
            Route3.setBackgroundResource(R.drawable.route_selector);
        });
        Route3.setOnClickListener(v -> {
            Route3.setBackgroundResource(R.drawable.route_selected);
            Route1.setBackgroundResource(R.drawable.route_selector);
            Route2.setBackgroundResource(R.drawable.route_selector);
        });

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
}