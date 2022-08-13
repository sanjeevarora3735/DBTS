package com.example.dbts;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.io.IOException;
import java.util.List;


@SuppressWarnings("ALL")
public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Navigation Route Data Members :
    private static final String ROUTE_LAYER_ID = "route-layer-id";
    private static final String ROUTE_SOURCE_ID = "route-source-id";
    private static final String ICON_LAYER_ID = "icon-layer-id";
    private static final String ICON_SOURCE_ID = "icon-source-id";
    private static final String RED_PIN_ICON_ID = "red-pin-icon-id";
    Point origin = Point.fromLngLat(00.00, 00.00);
    Point destination = Point.fromLngLat(0.00, 0.00);

    // Elements That are Used in The Current Activity :
    MapboxMap mapboxMap;
    private MapboxDirections client;
    private String mParam1;
    private String mParam2;
    private FrameLayout FrameLayout_Home;
    private AutoCompleteTextView BusRouteAutoCompleteTextView;
    private MapView MyMapViewInstance;
    private boolean HasLocation = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private TextView BusRouteTextViewMap, originToCampusTime, CampusToOriginTime;
    private FloatingActionButton floatingActionButton;
    private FirebaseAuth mAuth;
    private boolean SuperUser = false;
    private MaterialButton ViewBusSchedules;
    ScholarData FinalScholarData ;

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // MapBox -  Getting Instance @ Api-Key
        Mapbox.getInstance(getContext(), getString(R.string.MapBoxAccessTokenPublic));

        // Fetching The View - For further operations
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Elements Initialization @ From HomeFragment.xml
        BusRouteAutoCompleteTextView = view.findViewById(R.id.BusRouteAutoCompleteTextView);
        FrameLayout_Home = view.findViewById(R.id.FrameLayout_Home);
        MyMapViewInstance = view.findViewById(R.id.MapViewInstance);
        mFusedLocationProviderClient = new FusedLocationProviderClient(getActivity());
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        BusRouteTextViewMap = view.findViewById(R.id.BusRouteTextViewMap);
        originToCampusTime = view.findViewById(R.id.originToCampusTime);
        CampusToOriginTime = view.findViewById(R.id.CampustoOriginTime);
        ViewBusSchedules = view.findViewById(R.id.ViewBusSchedules);

        // Firebase Instances
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Code : In-Respect to use the mapbox API
        MyMapViewInstance.onCreate(savedInstanceState);
        MyMapViewInstance.getMapAsync(this);

        // Code For floatingActionButton :
        floatingActionButton.setOnClickListener(v -> {
            floatingActionButton.animate().rotation(floatingActionButton.getRotation()-360).setDuration(2000).start();
            Toast.makeText(getActivity(), String.valueOf(FinalScholarData.toString()), Toast.LENGTH_SHORT).show();
        });

        ViewBusSchedules.setOnClickListener(v->{
            Toast.makeText(getActivity(), "This functionality will be available later...", Toast.LENGTH_SHORT).show();
        });

        BusRouteAutoCompleteTextView.setOnClickListener(v -> {
            if(!SuperUser){
                Toast.makeText(getActivity(), "Only super user can access this feature.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(), "Please enable this feature", Toast.LENGTH_SHORT).show();
            }
        });

        // Modify the role of Routes DropDown List According to the User's Profile
        SetupRoutesDropDown();

        return view;
    }

    private void ApplyingDynamicChanges(ScholarData FetchedScholarData) {
        // Update Time According To Bus Schedule
        SyncBusSchedule(FetchedScholarData.getRoute());

        // MapBox - Camera Position Manager
        CameraPositionManagerMiniMap(FetchedScholarData.getPointLatitude(), FetchedScholarData.getPointLongitude());

        // SuperUser
        SuperUser = FetchedScholarData.isSuper();

        FinalScholarData = FetchedScholarData;
    }


    private void SyncBusSchedule(int RouteNumber) {
        // Updating the Route Number of User
        String RouteNumberText = BusRouteAutoCompleteTextView.getText().toString() + String.valueOf(RouteNumber);
        BusRouteAutoCompleteTextView.setText(RouteNumberText);


        // Updating the originToCampusTime && CampusToOriginTime
        FirebaseFirestore.getInstance().collection("BUSES")
                .whereEqualTo("Route", RouteNumber)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                BusesData TravellingData = document.toObject(BusesData.class);
                                originToCampusTime.setText(TravellingData.getOriginToCampusTime());
                                CampusToOriginTime.setText(TravellingData.getCampusToOriginTime());
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void SetupRoutesDropDown() {
        BusRouteAutoCompleteTextView.setInputType(InputType.TYPE_NULL);
    }

    public void GettingThingsReady() {
        String UserDocumentID = null;
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            UserDocumentID = currentUser.getEmail();
            DocumentReference docRef = FirebaseFirestore.getInstance().collection("identifying_information").document(UserDocumentID);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    ScholarData CurrentScholarDataObj = documentSnapshot.toObject(ScholarData.class);
                    ApplyingDynamicChanges(CurrentScholarDataObj);

                }
            });
        } else {
            Toast.makeText(getActivity(), "Sorry Unable to find the information... Try again later ", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        GettingThingsReady();
        mapboxMap.getUiSettings().setAttributionEnabled(false);
        mapboxMap.getUiSettings().setLogoEnabled(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        RequestPermissionsFromUser();
    }

    private void CameraPositionManagerLargeMap() {
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(destination.latitude(), destination.longitude()))
                        .zoom(18)
                        .tilt(14)
                        .build();
                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000);
            }
        });
    }

    private void CameraPositionManagerMiniMap(double... Location) {
        RequestPermissionsFromUser();
        mapboxMap.setStyle(Style.OUTDOORS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                Point MyLocation = Point.fromLngLat(Location[0], Location[1]);
                origin = MyLocation;
                mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Location location = task.getResult();
                        CameraPosition position = new CameraPosition.Builder()
                                .target(new LatLng(Location[0], Location[1]))
                                .zoom(15)
                                .tilt(1)
                                .build();
                        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000);
                        MarkerOptions markerOptions = new MarkerOptions();
                        Point BusLocation = Point.fromLngLat(28.583333, 77.175797);
                        List<Address> addresses;
                        try {
                            Geocoder geocoder = new Geocoder(getContext());
                            addresses = geocoder.getFromLocationName("R K Puram", 1);
                            if (addresses != null) {
                                BusLocation = Point.fromLngLat(addresses.get(0).getLongitude(), addresses.get(0).getLatitude());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        destination = BusLocation;
                        markerOptions.position(new LatLng(MyLocation.longitude(), MyLocation.latitude()));
                        mapboxMap.addMarker(markerOptions);
                    }
                });
            }
        });

    }

    private void RequestPermissionsFromUser() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, 100);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, 100);
            }
        } else {
            HasLocation = true;
        }
    }


}
