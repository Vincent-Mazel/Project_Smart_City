package com.example.project_smart_city;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        Button button = findViewById(R.id.login_login);
        button.setText("LOGIN");

        TextView title = findViewById(R.id.title_login);
        title.setText("Smart City");

        TextView notRegistred = findViewById(R.id.login_textView_inscription);
        notRegistred.setText("Pas encore inscrit ?");
        TextView signIn = findViewById(R.id.login_textView_signin);
        signIn.setText("Inscrivez-vous !");
        signIn.setOnClickListener(view -> openInscription(view));

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
