package com.example.project_smart_city;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity{

    private static ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        scrollView = findViewById(R.id.scroll_main);

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

    public void onClickTop(View view){
        scrollView.post(new Runnable() { public void run() { scrollView.fullScroll(View.FOCUS_UP); } });
    }

    public static ScrollView getScrollView(){
        return scrollView;
    }
}

