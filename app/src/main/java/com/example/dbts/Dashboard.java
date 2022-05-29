package com.example.dbts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Dashboard extends AppCompatActivity {

    private int SelectedTab = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashborad);

        // FindViewByID @ LinearLayouts :: 1 To 4
        final LinearLayout HomeLayout = findViewById(R.id.HomeLayout);
        final LinearLayout ScheduleLayout = findViewById(R.id.ScheduleLayout);
        final LinearLayout NotificationLayout = findViewById(R.id.NotificationLayout);
        final LinearLayout ProfileLayout = findViewById(R.id.ProfileLayout);

        // FindViewByID @ LinearLayouts_Images :: 1 To 4
        final ImageView HomeLayoutImage = findViewById(R.id.HomeLayoutImage);
        final ImageView ScheduleLayoutImage = findViewById(R.id.ScheduleLayoutImage);
        final ImageView NotificationLayoutImage = findViewById(R.id.NotificationLayoutImage);
        final ImageView ProfileLayoutImage = findViewById(R.id.ProfileLayoutImage);

        // FindViewByID @ LinearLayouts_TextView :: 1 To 4
        final TextView HomeLayoutTextView = findViewById(R.id.HomeLayoutTextView);
        final TextView ScheduleLayoutTextView = findViewById(R.id.ScheduleLayoutTextView);
        final TextView NotificationLayoutTextView = findViewById(R.id.NotificationLayoutTextView);
        final TextView ProfileLayoutTextView = findViewById(R.id.ProfileLayoutTextView);


        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.FragmentTabHost,HomeFragment.class,null)
                .commit();

//        Intent CurrentUser = getIntent();
//        Object o = CurrentUser.getBundleExtra("CurrentUserObject");
//        Toast.makeText(getApplicationContext(), o.toString(), Toast.LENGTH_SHORT).show();
        HomeLayout.setOnClickListener(v -> {

            if(SelectedTab != 1){

                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.FragmentTabHost,HomeFragment.class,null)
                        .commit();

                ScheduleLayoutTextView.setVisibility(View.GONE);
                ProfileLayoutTextView.setVisibility(View.GONE);
                NotificationLayoutTextView.setVisibility(View.GONE);

                NotificationLayoutImage.setImageResource(R.drawable.notificationbell_icon);
                ProfileLayoutImage.setImageResource(R.drawable.profile_icon);
                ScheduleLayoutImage.setImageResource(R.drawable.schedule_icon);

                ScheduleLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                NotificationLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                ProfileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                // Select Home Tab :

                HomeLayoutTextView.setVisibility(View.VISIBLE);
                HomeLayoutImage.setImageResource(R.drawable.home_selected_icon);
                HomeLayout.setBackgroundResource(R.drawable.round_home_back_100);

                ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                scaleAnimation.setDuration(200);
                scaleAnimation.setFillAfter(true);
                HomeLayout.setAnimation(scaleAnimation);
                SelectedTab = 1;
            }
        });

        ScheduleLayout.setOnClickListener(v ->{

            if(SelectedTab != 2){

                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.FragmentTabHost,ScheduleFragment.class,null)
                        .commit();

                HomeLayoutTextView.setVisibility(View.GONE);
                ProfileLayoutTextView.setVisibility(View.GONE);
                NotificationLayoutTextView.setVisibility(View.GONE);

                NotificationLayoutImage.setImageResource(R.drawable.notificationbell_icon);
                ProfileLayoutImage.setImageResource(R.drawable.profile_icon);
                HomeLayoutImage.setImageResource(R.drawable.home_icon);

                HomeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                NotificationLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                ProfileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                // Select Home Tab :

                ScheduleLayoutTextView.setVisibility(View.VISIBLE);
                ScheduleLayoutImage.setImageResource(R.drawable.schedule_selected_icon);
                ScheduleLayout.setBackgroundResource(R.drawable.round_schedule_back_100);

                ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                scaleAnimation.setDuration(200);
                scaleAnimation.setFillAfter(true);
                ScheduleLayout.setAnimation(scaleAnimation);
                SelectedTab = 2;
            }
        });

        NotificationLayout.setOnClickListener(v ->{

            if(SelectedTab != 3){

                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.FragmentTabHost,NotificationFragment.class,null)
                        .commit();

                HomeLayoutTextView.setVisibility(View.GONE);
                ProfileLayoutTextView.setVisibility(View.GONE);
                ScheduleLayoutTextView.setVisibility(View.GONE);

                ScheduleLayoutImage.setImageResource(R.drawable.schedule_icon);
                ProfileLayoutImage.setImageResource(R.drawable.profile_icon);
                HomeLayoutImage.setImageResource(R.drawable.home_icon);

                HomeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                ScheduleLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                ProfileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                // Select Home Tab :

                NotificationLayoutTextView.setVisibility(View.VISIBLE);
                NotificationLayoutImage.setImageResource(R.drawable.notificationbell_selected_icon);
                NotificationLayout.setBackgroundResource(R.drawable.round_notification_back_100);

                ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                scaleAnimation.setDuration(200);
                scaleAnimation.setFillAfter(true);
                NotificationLayout.setAnimation(scaleAnimation);
                SelectedTab = 3;
            }
        });

        ProfileLayout.setOnClickListener(v ->{

            if(SelectedTab != 4){


                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.FragmentTabHost,ProfileFragment.class,null)
                        .commit();

                HomeLayoutTextView.setVisibility(View.GONE);
                NotificationLayoutTextView.setVisibility(View.GONE);
                ScheduleLayoutTextView.setVisibility(View.GONE);

                ScheduleLayoutImage.setImageResource(R.drawable.schedule_icon);
                NotificationLayoutImage.setImageResource(R.drawable.notificationbell_icon);
                HomeLayoutImage.setImageResource(R.drawable.home_icon);

                HomeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                ScheduleLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                NotificationLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                // Select Home Tab :

                ProfileLayoutTextView.setVisibility(View.VISIBLE);
                ProfileLayoutImage.setImageResource(R.drawable.profile_selected_icon);
                ProfileLayout.setBackgroundResource(R.drawable.round_profile_back_100);

                ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                scaleAnimation.setDuration(200);
                scaleAnimation.setFillAfter(true);
                ProfileLayout.setAnimation(scaleAnimation);
                SelectedTab = 4;
            }
        });

    }
}