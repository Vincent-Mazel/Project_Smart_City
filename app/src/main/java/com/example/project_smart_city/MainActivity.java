package com.example.project_smart_city;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static ScrollView scrollView;
    private static NavigationView navigationView;
    private static NavigationView navigationViewFade;
    private static User userLoged;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        scrollView = findViewById(R.id.scroll_main);
        Intent i = getIntent();
        userLoged = (User)i.getSerializableExtra("UserLogin");

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,R.id.navigation_shopping , R.id.navigation_network, R.id.navigation_profil).
              build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }



    public void onClickBot(View view){


        scrollView.post(new Runnable() { public void run() { scrollView.fullScroll(View.FOCUS_DOWN); } });
    }

    public static ScrollView getScrollView(){
        return scrollView;
    }

    public void logOut(View v){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        this.finish();
    }

    public void trierCommerces(View v){
        // algo à implémenter pour trier les commerces
        // Just a thought : implement on the server directly.


        Switch aSwitch = findViewById(R.id.shopping_switchProxAnn);
        TextView switchText = findViewById(R.id.shopping_textViewSwitch);
        if(aSwitch.isChecked()){
            switchText.setText("Proximité");
        }
        else
            switchText.setText("Annuaire");
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static User getUser(){
        return userLoged;
    }
}

