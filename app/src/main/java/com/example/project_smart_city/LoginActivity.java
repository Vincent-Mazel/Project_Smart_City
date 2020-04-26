package com.example.project_smart_city;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

    }

    public void inscription(View v){
        Intent i = new Intent(this, InscriptionActivity.class);
        startActivity(i);
    }
    @Override
    public void onClick(View view) {

    }
}
