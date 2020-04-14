package com.example.project_smart_city;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;


import com.example.project_smart_city.ChoicesFragment.PageAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView = findViewById(R.id.bottomAppBar);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //this.configureViewPager();


    }

    public void configureBottomApp(){
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> updateMainFragment(menuItem.getItemId()));
    }

    public Boolean updateMainFragment(Integer integer){
        switch (integer){
            case R.id.action_home:

        }
        return true;
    }


    private void configureViewPager() {
        // 1 - Get ViewPager from layout
        ViewPager pager = (ViewPager)findViewById(R.id.viewPagerChoices);
        // 2 - Set Adapter PageAdapter and glue it together
        pager.setAdapter(new PageAdapter(getSupportFragmentManager(), getResources().getIntArray(R.array.colorPagesViewPager)) {
        });
    }
}

