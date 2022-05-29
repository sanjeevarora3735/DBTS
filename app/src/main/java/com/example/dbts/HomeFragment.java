package com.example.dbts;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineJoin;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.core.constants.Constants;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class HomeFragment extends Fragment implements OnMapReadyCallback, Callback<DirectionsResponse> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters

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
    private TextView BusRouteTextViewMap;
    private FloatingActionButton floatingActionButton;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
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
    }

    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

        // Code : In-Respect to use the mapbox API
        MyMapViewInstance.onCreate(savedInstanceState);
        MyMapViewInstance.getMapAsync(this);

        // Code For floatingActionButton :
        floatingActionButton.setOnClickListener(v -> {
            CameraPositionManagerMiniMap();
        });

        // Code : Getting The Current Location Of The User
        BusRouteTextViewMap = view.findViewById(R.id.BusRouteTextViewMap);
        Geocoder geocoder = new Geocoder(getContext());
        try {
            Log.d("Geocoder : ", String.valueOf(geocoder.getFromLocationName("Moti Bagh", 1)));
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Setting-up the InputType to BusRouteAutoCompleteTextView
        BusRouteAutoCompleteTextView.setInputType(InputType.TYPE_NULL);

        // Binding Of DropdownList For Selecting BusRoutes From The AutoComplete
        String[] BusRoutes = getResources().getStringArray(R.array.Routes);
        ArrayAdapter<String> stringArrayAdapter;
        BusRouteAutoCompleteTextView.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.dropdown_routes, BusRoutes));


        return view;
    }


    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;

        CameraPositionManagerMiniMap();
        mapboxMap.getUiSettings().setAttributionEnabled(false);
        mapboxMap.getUiSettings().setLogoEnabled(false);
    }

    private void CameraPositionManagerLargeMap() {
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
//                initSource(style);
//                initLayers(style);

                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(destination.latitude(), destination.longitude()))
                        .zoom(18)
                        .tilt(14)
                        .build();
                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000);//                getRoute(mapboxMap, origin, destination);
            }
        });
    }

    private void CameraPositionManagerMiniMap() {
        mapboxMap.setStyle(Style.OUTDOORS, new Style.OnStyleLoaded() {
            @SuppressLint("MissingPermission")
            @Override
            public void onStyleLoaded(@NonNull Style style) {

                mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Location location = task.getResult();
                        CameraPosition position = new CameraPosition.Builder()
                                .target(new LatLng(location.getLatitude(), location.getLongitude()))
                                .zoom(15)
                                .tilt(1)
                                .build();
                        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000);
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
            Toast.makeText(getContext(), "Location Permission Activated", Toast.LENGTH_SHORT).show();
        }
    }

    private void getRoute(MapboxMap mapboxMap, Point origin, Point destination) {
        client = MapboxDirections.builder()
                .origin(origin)
                .destination(destination)
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .accessToken(Mapbox.getAccessToken())
                .build();
        client.enqueueCall(this);
    }

    private void initLayers(@NonNull Style loadedMapStyle) {
        LineLayer routeLayer = new LineLayer(ROUTE_LAYER_ID, ROUTE_SOURCE_ID);

        routeLayer.setProperties(
                PropertyFactory.lineCap(Property.LINE_CAP_ROUND),
                lineJoin(Property.LINE_JOIN_ROUND),
                lineWidth(5f),
                lineColor(Color.parseColor("#1a73e8"))
        );
        loadedMapStyle.addLayer(routeLayer);

        loadedMapStyle.addImage(RED_PIN_ICON_ID, BitmapUtils.getBitmapFromDrawable(
                getResources().getDrawable(R.drawable.red_marker)));

        loadedMapStyle.addLayer(new SymbolLayer(ICON_LAYER_ID, ICON_SOURCE_ID).withProperties(
                iconImage(RED_PIN_ICON_ID),
                iconIgnorePlacement(true),
                iconAllowOverlap(true),
                iconOffset(new Float[]{0f, -9f})));
    }

    private void initSource(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addSource(new GeoJsonSource(ROUTE_SOURCE_ID));

        GeoJsonSource iconGeoJsonSource = new GeoJsonSource(ICON_SOURCE_ID, FeatureCollection.fromFeatures(new Feature[]{
                Feature.fromGeometry(Point.fromLngLat(origin.longitude(), origin.latitude())),
                Feature.fromGeometry(Point.fromLngLat(destination.longitude(), destination.latitude()))}));
        loadedMapStyle.addSource(iconGeoJsonSource);
    }

    @Override
    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {

        if (response.body() == null) {
            Toast.makeText(getContext(), "No routes found, make sure you set the right user and access token ", Toast.LENGTH_SHORT).show();
            getRoute(mapboxMap, origin, destination);
            return;
        } else if (response.body().routes().size() < 1) {
            Toast.makeText(getContext(), "No routes found", Toast.LENGTH_SHORT).show();
            getRoute(mapboxMap, origin, destination);

            return;
        }

        DirectionsRoute currentRoute = response.body().routes().get(0);

        if (mapboxMap != null) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    GeoJsonSource source = style.getSourceAs(ROUTE_SOURCE_ID);
                    if (source != null) {
                        source.setGeoJson(LineString.fromPolyline(currentRoute.geometry(), Constants.PRECISION_6));
                    }
                }
            });
        }

    }

    @Override
    public void onFailure(Call<DirectionsResponse> call, Throwable t) {

    }
}
