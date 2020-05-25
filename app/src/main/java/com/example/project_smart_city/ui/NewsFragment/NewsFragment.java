package com.example.project_smart_city.ui.NewsFragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.project_smart_city.MainActivity;
import com.example.project_smart_city.R;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class NewsFragment extends Fragment implements LocationListener {

    private NewsViewModel newsViewModel;
    private double latitude,longitude;

    @SuppressLint({"SetTextI18n", "RestrictedApi", "MissingPermission"})
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_actuality, container, false);

        TextView title = root.findViewById(R.id.title_news);
        title.setText("News");
        TextView HB = root.findViewById(R.id.HB);
        Date today = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String sToday = simpleDateFormat.format(today);

        LinearLayout linearLayout = root.findViewById(R.id.inflator);
        // Check if it is its birthday;
        if (MainActivity.getUser().getBirthday().equals(sToday)) {
            HB.setText("Happy Birthday " + MainActivity.getUser().getPseudo() + " ! ");
        }
        String interest = MainActivity.getUser().getListInterests();
        if (interest == null) {
            TextView textView = new TextView(getContext());
            textView.setText("You don't have any interest, change that in your profile !");
            textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
            linearLayout.addView(textView);
        } else {
            linearLayout.removeAllViews();
        }

        assert interest != null;
        if (interest.contains("Calendar")) {
            CalendarView calendarView = new CalendarView(Objects.requireNonNull(getContext()));
            Button button = new Button(getContext());
            button.setText("Manage your calendar");
            button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            button.setOnClickListener(view -> {
                Calendar cal = Calendar.getInstance();
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("beginTime", cal.getTimeInMillis());
                intent.putExtra("allDay", true);
                intent.putExtra("rrule", "FREQ=YEARLY");
                intent.putExtra("endTime", cal.getTimeInMillis() + 60 * 60 * 1000);
                intent.putExtra("title", "A Test Event from android app");
                startActivity(intent);
            });

            linearLayout.addView(calendarView);
            linearLayout.addView(button);
        }
        if (interest.contains("Weather")) {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            LocationManager locationManager = (LocationManager) Objects.requireNonNull(getContext()).getSystemService(Context.LOCATION_SERVICE);
            assert locationManager != null;
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            // SA PUTAIN DE MARCHE PAS LA VIE DE MOI C DE LA PUTAIN DE MERDE LA GESTION DE LA LOCATION

        }

        return root;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onLocationChanged(Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d("Latitude","status");
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.d("Latitude","disable");
    }

    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo!
                }
            }
        }
    }

}