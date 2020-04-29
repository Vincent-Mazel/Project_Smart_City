package com.example.project_smart_city;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);



        TextView signIn = findViewById(R.id.login_textView_signin);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInscription(view);
            }
        });

    }

    public void LogIn(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void openInscription(View v){
        Intent i = new Intent(this, InscriptionActivity.class);
        startActivity(i);
    }
    @Override
    public void onClick(View view) {

    }
}
