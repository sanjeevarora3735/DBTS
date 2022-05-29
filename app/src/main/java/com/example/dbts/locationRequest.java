package com.example.dbts;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

public class locationRequest extends AppCompatActivity {
    protected static final int MY_PERMISSION_REQUEST_CODE = 123;
    protected int i = 0;
    // Top Declaration Block For All Elements & Members
    Toolbar toolbar;
    Button TurnonLocation;
    protected FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_request);

        // Formatting The Options of ToolBar For Customization
        toolbar = findViewById(R.id.ToolBar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        // FindViewByID @ TurnonLocation Button :
        TurnonLocation = findViewById(R.id.Turn_On);

        // Location Work will be Here .....

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // OnClickListener For : Continue @ TurnonLocation
        TurnonLocation.setOnClickListener(v ->{
//            RequestLocation();
            Intent SettingsIntent = new Intent();
            SettingsIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package",getPackageName(),null);
                SettingsIntent.setData(uri);
                startActivity(SettingsIntent);

        });



        //  Onclick Listener For Back Button
        View logoView = getToolbarLogoView(toolbar);
        logoView.setOnClickListener(v -> {
            //logo clicked
            onBackPressed();
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        RequestPermissionsFromUser();

    }

    private void RequestPermissionsFromUser() {
        if(ContextCompat.checkSelfPermission(locationRequest.this,Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ){
            if(ActivityCompat.shouldShowRequestPermissionRationale(locationRequest.this, Manifest.permission.ACCESS_FINE_LOCATION)){
                AlertDialog.Builder RequestDialog = new AlertDialog.Builder(locationRequest.this,R.style.AlertDialogTheme);
                RequestDialog.setTitle("Required Location Permission")
                        .setMessage("Location Permission is required to track your location and receive bus location updates.")
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(locationRequest.this,new String[] {
                                Manifest.permission.ACCESS_FINE_LOCATION
                        },MY_PERMISSION_REQUEST_CODE);
                    }
                });
                RequestDialog.setNegativeButton("Cancel", null);
                AlertDialog alertDialog = RequestDialog.create();
                alertDialog.show();
            }else {
                ActivityCompat.requestPermissions(locationRequest.this,new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION
                },MY_PERMISSION_REQUEST_CODE);
            }
        }else {
            startActivity(new Intent(locationRequest.this,Dashboard.class));
            Toast.makeText(this, "Permissions are already Granted !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            if ((grantResults.length > 0) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(new Intent(locationRequest.this, Dashboard.class));
                Toast.makeText(getApplicationContext(), "Permissions are Granted @", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Permission are Denied @", Toast.LENGTH_SHORT).show();
                RequestPermissionsFromUser();
            }
        }else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //
//    private void RequestLocation() {
//        if (ContextCompat.checkSelfPermission(
//                getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
//                PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(locationRequest.this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
//
//                new AlertDialog.Builder(this,R.style.AlertDialogTheme)
//                        .setTitle("Required Location Permission")
//                        .setMessage("You have to give this permission to use this app.")
//                        .setIcon(R.drawable.bus_icon)
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                ActivityCompat.requestPermissions(locationRequest.this,
//                                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
//                                        MY_PERMISSION_REQUEST_CODE);
//                            }
//                        })
//                        .create()
//                        .show();
//
//
//            } else {
//
//                ActivityCompat.requestPermissions(locationRequest.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_REQUEST_CODE);
//            }
//        } else {
//
//        }
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if(requestCode == MY_PERMISSION_REQUEST_CODE){
//            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                Toast.makeText(this, "Permission Granted : HKD", Toast.LENGTH_SHORT).show();
//            }else {
//                Toast.makeText(this, String.valueOf(++i), Toast.LENGTH_SHORT).show();
////                RequestLocation();
//
//            }
//        }
//    }

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