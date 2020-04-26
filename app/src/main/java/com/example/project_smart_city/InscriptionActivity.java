package com.example.project_smart_city;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class InscriptionActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_inscription);

    }

    @Override
    public void onClick(View view) {

    }
}
