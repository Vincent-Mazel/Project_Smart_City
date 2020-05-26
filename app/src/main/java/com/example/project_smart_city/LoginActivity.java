package com.example.project_smart_city;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        TextView text_email = findViewById(R.id.login_email);
        TextView text_password = findViewById(R.id.login_password);

        String email = text_email.getText().toString();
        String password = text_password.getText().toString();

        DatabaseHandler databaseHandler = new DatabaseHandler(this, null, null, 1);
        User user = new User();
        user = databaseHandler.findUser(email);
        if(user == null){
            Toast.makeText(this, "Email not in database, Sign In !", Toast.LENGTH_SHORT).show();
        }
        else {
            if(password.equals(user.getPassword())){
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("UserLogin", user);
                startActivity(i);
            }
            else {
                Toast.makeText(this, "Password is not correct. Try again please.", Toast.LENGTH_SHORT).show();
                text_password.setError("wrong password");
            }
        }





    }
    public void openInscription(View v){
        Intent i = new Intent(this, InscriptionActivity.class);
        startActivity(i);
    }
    @Override
    public void onClick(View view) {

    }
}
