package com.example.project_smart_city;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_smart_city.ui.ChoicesFragment.ChoiceFragment;

import java.util.Calendar;


public class InscriptionActivity extends AppCompatActivity implements View.OnClickListener{

    private DatePickerDialog pickerDialog;
    private TextView textViewBirthday;
    private static ScrollView scrollView;
    private Boolean isChoiceOpen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_inscription);

        scrollView = findViewById(R.id.inscription_scrollView);
        textViewBirthday = findViewById(R.id.inscription_birthdayText);
        textViewBirthday.setOnClickListener(v -> {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            // date picker dialog
            Context myContext = getApplicationContext();

            pickerDialog = new DatePickerDialog(InscriptionActivity.this, R.style.DatePickerDialogTheme,
                    (view, year1, monthOfYear, dayOfMonth) -> textViewBirthday.setText(dayOfMonth + "/" + monthOfYear + "/" + year1), year, month, day);
            pickerDialog.show();
        });


        Button button = findViewById(R.id.incription_buttonValidate);
        button.setText("Faîtes vos choix");
        button.setOnClickListener(view -> {
            if(!isChoiceOpen){
                ChoiceFragment choiceFragment = new ChoiceFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction
                        .add(R.id.inscription_layout, choiceFragment)
                        .addToBackStack(null)
                        .commit();
                isChoiceOpen = true;
            }
        });
    }

    public static ScrollView getScrollView(){
        return scrollView;
    }

    @Override
    public void onClick(View view) {

    }
}
