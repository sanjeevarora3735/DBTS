package com.example.dbts;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.drawable.DrawableCompat;

public class RouteSelection extends AppCompatActivity {

    CardView Route1, Route2, Route3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_selection);
        //Initializing The CardView :
        Route1 = findViewById(R.id.RouteNo1Card);
        Route2 = findViewById(R.id.RouteNo2Card);
        Route3 = findViewById(R.id.RouteNo3Card);

        Route1.setOnClickListener(v -> {

            Drawable CardViewDrawable = Route1.getBackground();
            CardViewDrawable = DrawableCompat.wrap(CardViewDrawable);
            Drawable CardViewDrawableNon = Route2.getBackground();
            CardViewDrawableNon = DrawableCompat.wrap(CardViewDrawableNon);
            //the color is a direct color int and not a color resource
            DrawableCompat.setTint(CardViewDrawableNon, Color.GRAY);
            Route2.setBackground(CardViewDrawableNon);
            Route3.setBackground(CardViewDrawableNon);
            DrawableCompat.setTint(CardViewDrawable, Color.RED);
            Route1.setBackground(CardViewDrawable);
            Route1.setCardBackgroundColor(Color.BLUE);
        });
    }
}