package com.example.project_smart_city.ui.NewsFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project_smart_city.MainActivity;
import com.example.project_smart_city.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class NewsFragment extends Fragment{



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
            HB.setVisibility(View.VISIBLE);
        } else {
            HB.setVisibility(View.GONE);
        }
        String interest = MainActivity.getUser().getListInterests();
        if (interest == null) {
            TextView textView = new TextView(getContext());
            textView.setText("You don't have any interest, change that in your profile !");
            textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
            linearLayout.addView(textView);

        }

        double latitude = MainActivity.getUser().getLatitude();
        double longitude = MainActivity.getUser().getLongitude();
        assert interest != null;
        if (interest.contains("Weather")) {


            // Using OpenWeather API.
            String APIKey = "165d89700121b7f67b883bb9884c0689";
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?lat="+ latitude + "&lon=" + longitude +"&appid=" + APIKey);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                line = br.readLine();
                JSONObject jsonObject = new JSONObject(line);

                String city = jsonObject.getString("name");
                JSONObject jsonObject1 = jsonObject.getJSONObject("main");
                String temp = jsonObject1.getString("temp");
                String temp1 = jsonObject1.getString("feels_like");
                double t = Double.parseDouble(temp) - 273.15;
                double t1 = Double.parseDouble(temp1) - 273.15;
                jsonObject1 = jsonObject.getJSONObject("wind");
                String wind = jsonObject1.getString("speed");
                String visibility = jsonObject.getString("visibility");
                JSONArray jsonArray = jsonObject.getJSONArray("weather");
                jsonObject1 = jsonArray.getJSONObject(0);
                String weather = jsonObject1.getString("description");

                // inflate layout weather

                LayoutInflater newView = (LayoutInflater) Objects.requireNonNull(this.getContext()).getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                assert newView != null;
                @SuppressLint("InflateParams") View v = newView.inflate(R.layout.weather_layout, null);

                TextView city_name = v.findViewById(R.id.city_name);
                city_name.setText(city);
                TextView temperature = v.findViewById(R.id.temp);
                temperature.setText((int) t + " °C");
                TextView viewWind = v.findViewById(R.id.wind);
                viewWind.setText(wind + " m/h");
                TextView vis = v.findViewById(R.id.visibility);
                vis.setText("visibility : " + visibility.substring(0,3) + "%");
                TextView main = v.findViewById(R.id.main);
                main.setText(weather);
                TextView tempFeels = v.findViewById(R.id.feelslike);
                tempFeels.setText("Feels like : " + (int) t1 + " °C");

                linearLayout.addView(v);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

            //linearLayout.addView(calendarView);
            linearLayout.addView(button);
        }
        if (interest.contains("Traffic")){
            try {

                // NEED TO PARSE TRAFFIC
                URL url = new URL("https://traffic.ls.hereapi.com/traffic/6.3/incidents.json?apiKey=QwobaSv1M-I76N3Gzo5Vn1WSwJoWI5DV_eEHR7RQEvs&bbox="+ Double.toString(latitude - 0.25).substring(0,6)+","+Double.toString(longitude - 0.25).substring(0,6)+";"+ Double.toString(latitude + 0.25).substring(0,6) + "," + Double.toString(longitude+0.25).substring(0,6) +"&criticality=minor");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                line = br.readLine();

                JSONObject jsonObject = new JSONObject(line);
                System.out.println(line);


                LayoutInflater newView = (LayoutInflater) Objects.requireNonNull(this.getContext()).getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                assert newView != null;
                @SuppressLint("InflateParams") View v = newView.inflate(R.layout.weather_layout, null);



            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return root;
    }
}