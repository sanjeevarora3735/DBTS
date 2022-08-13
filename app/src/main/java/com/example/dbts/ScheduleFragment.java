package com.example.dbts;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Some Variable Initialization :
    private LinearLayout StoppagePointLinearLayout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int Route_Number;
    private View view;
    private static int Counter = 0;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
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
        // Inflate the layout for this fragment
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (view == null) {
            Toast.makeText(getContext(),String.valueOf(++Counter) , Toast.LENGTH_SHORT).show();
             view = inflater.inflate(R.layout.fragment_schedule, container, false);
        } else {
            ((ViewGroup) view.getParent()).removeView(view);
        }

        // FindViewByID
        StoppagePointLinearLayout = view.findViewById(R.id.StoppagePointLinearLayout);

        return view;
    }

    @Override
    public void onStart() {
        SetupStoppagePoints(6);
        super.onStart();
    }

    public void SetupStoppagePoints(int route_number) {
        FirebaseFirestore.getInstance().collection("BUSES")
                .whereEqualTo("Route", route_number)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                BusesData TravellingData = document.toObject(BusesData.class);
                                for (int Counter = 0; Counter < TravellingData.getStations().size(); Counter++) {
                                    boolean CampusPosition = Counter == TravellingData.getStations().size() - 1;
                                    CreateStoppageField_ConstraintLayouts(route_number,TravellingData.getStations().get(Counter),TravellingData.getStations_Time().get(Counter),Counter,CampusPosition,false);
                                }
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    private void CreateStoppageField_ConstraintLayouts(int route_number, String StoppagePointName, String StoppagePointTime,int Counter,boolean CampusArrived,boolean Active) {
        ConstraintLayout constraintLayout = new ConstraintLayout(getActivity());
        constraintLayout.setId(route_number+23951+Counter); // setting a unique id
        ConstraintLayout.LayoutParams constraintLayoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        constraintLayoutParams.setMargins(0, 0, 0, 10);
        constraintLayout.setLayoutParams(constraintLayoutParams);
        constraintLayout.setPadding(50, 20, 55, 20);
        constraintLayout.setBackground(getResources().getDrawable(R.drawable.onlystroke));

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

        TextView Time = new TextView(getContext());
        Time.setId(route_number+79823+Counter);
        Time.setText(StoppagePointTime);
        Time.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
        Time.setTextColor(getResources().getColor(R.color.black));
        constraintLayout.addView(Time);
        constraintSet.connect(Time.getId(),ConstraintSet.END,constraintLayout.getId(),ConstraintSet.END,0);
        constraintSet.connect(Time.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,0);
        constraintSet.constrainWidth(Time.getId(),ConstraintSet.WRAP_CONTENT);
        constraintSet.applyTo(constraintLayout);

        TextView BusStoppagePointName = new TextView(getContext());
        BusStoppagePointName.setId(route_number+18350+Counter);
        BusStoppagePointName.setText(StoppagePointName.toUpperCase(Locale.ROOT));
        BusStoppagePointName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        BusStoppagePointName.setTextColor(getResources().getColor(R.color.black));
        constraintSet.connect(BusStoppagePointName.getId(),ConstraintSet.START,constraintLayout.getId(),ConstraintSet.START,0);
        constraintSet.connect(BusStoppagePointName.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,0);
        constraintSet.constrainWidth(BusStoppagePointName.getId(),ConstraintSet.WRAP_CONTENT);
        constraintSet.applyTo(constraintLayout);

        //for Active Bus Stoppage Point Name - Active = true
        if(Active) {
            BusStoppagePointName.setTypeface(null, Typeface.BOLD);
            TextView CurrentStoppagePoint = new TextView(getContext());
            CurrentStoppagePoint.setId(route_number + 101);
            CurrentStoppagePoint.setText("  (Active)");
            CurrentStoppagePoint.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
            CurrentStoppagePoint.setTextColor(getResources().getColor(R.color.black));
            constraintLayout.setBackgroundColor(getResources().getColor(R.color.ActiveBusStoppagePointName));
            constraintSet.connect(CurrentStoppagePoint.getId(), ConstraintSet.START, BusStoppagePointName.getId(), ConstraintSet.END, 0);
            constraintSet.connect(CurrentStoppagePoint.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 0);
            constraintSet.constrainWidth(CurrentStoppagePoint.getId(), ConstraintSet.WRAP_CONTENT);
            constraintSet.applyTo(constraintLayout);
            constraintLayout.addView(CurrentStoppagePoint);
        }

        // onClick Event :)

        constraintLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.ActiveBusStoppagePointName));
                Vibrator vibe = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(200);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        constraintLayout.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                }, 1000);
                return false;
            }
        });

        // Background Colour of Constraint Layout - When StoppagePoint is Campus :)
        if(CampusArrived){
            constraintLayout.setBackgroundColor(getResources().getColor(R.color.Campus));
            constraintLayout.setOnLongClickListener(v->{
                Vibrator vibe = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(200);
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.Campus));
                return false;
            });
        }


        constraintLayout.addView(BusStoppagePointName);
        constraintSet.applyTo(constraintLayout);
        StoppagePointLinearLayout.addView(constraintLayout);
    }

}
