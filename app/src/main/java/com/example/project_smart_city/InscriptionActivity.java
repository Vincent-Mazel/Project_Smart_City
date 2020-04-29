package com.example.project_smart_city;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;


public class InscriptionActivity extends AppCompatActivity implements View.OnClickListener{

    private DatePickerDialog pickerDialog;
    private TextView textViewBirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_inscription);

        textViewBirthday = findViewById(R.id.inscription_birthdayText);
        textViewBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                Context myContext = getApplicationContext();

                pickerDialog = new DatePickerDialog(InscriptionActivity.this, R.style.DatePickerDialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                textViewBirthday.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                            }
                        }, year, month, day);
                pickerDialog.show();
            }
        });




    }

    @Override
    public void onClick(View view) {

    }
}
