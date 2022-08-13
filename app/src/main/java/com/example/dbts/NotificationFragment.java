package com.example.dbts;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View view;
    private ScrollView MessagePanelScrollView;
    private FloatingActionButton NavigateBottom;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        view = inflater.inflate(R.layout.fragment_notification, container, false);

        MessagePanelScrollView = view.findViewById(R.id.MessagePanelScrollView);
        NavigateBottom = view.findViewById(R.id.NavigateBottom);

        NavigateBottom.setOnClickListener(view -> {
            MessagePanelScrollView.fullScroll(MessagePanelScrollView.FOCUS_DOWN);
        });

        MessagePanelScrollView.getViewTreeObserver()
                .addOnScrollChangedListener(() -> {
                    if (MessagePanelScrollView.getChildAt(0).getBottom()
                            <= (MessagePanelScrollView.getHeight() + MessagePanelScrollView.getScrollY())) {
                        //scroll view is at bottom
                        NavigateBottom.setVisibility(View.GONE);
                    } else {
                        //scroll view is not at bottom
                        NavigateBottom.setVisibility(View.VISIBLE);
                    }
                });


        view.findViewById(R.id.MessagePanel).setOnClickListener(v -> {
            initialJoining(null);
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initialJoining(null);
    }

    private void initialJoining(String Via) {
        GenerateTextView("DATE", null);
        String Message = "You joined using this server's invite link";
        GenerateTextView("JOINING", Message);
    }

    private void GenerateTextView(String InformationType, String InfoMessage) {
        LinearLayout MessagePanel = view.findViewById(R.id.MessagePanel);
        TextView SimpleTextView = new TextView(getContext());
        SimpleTextView.setId(View.generateViewId());
        switch (InformationType) {
            case "DATE":
                String TodayDate = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(new Date());
                SimpleTextView.setText(TodayDate);
                break;
            case "JOINING":
                SimpleTextView.setText(InfoMessage);
                break;
        }
        SimpleTextView.setBackground(getResources().getDrawable(R.drawable.round_date_textview));
        SimpleTextView.setPadding(15, 10, 15, 10);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayoutParams.gravity = Gravity.CENTER;
        linearLayoutParams.setMargins(0, 0, 0, 20);
        SimpleTextView.setLayoutParams(linearLayoutParams);
        SimpleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
//        SimpleTextView.setTypeface(null, Typeface.BOLD);
//        SimpleTextView.setTextColor(getResources().getColor(R.color.white));

        MessagePanel.addView(SimpleTextView);

        MessagePanelScrollView.fullScroll(MessagePanelScrollView.getBottom());

    }
}