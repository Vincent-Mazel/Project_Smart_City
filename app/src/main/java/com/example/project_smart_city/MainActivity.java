package com.example.project_smart_city;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.util.Objects;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static ScrollView scrollView;
    private static NavigationView navigationView;
    private static NavigationView navigationViewFade;
    private static User userLoged;
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mContext = getApplicationContext();
        scrollView = findViewById(R.id.scroll_main);
        Intent i = getIntent();
        userLoged = (User)i.getSerializableExtra("UserLogin");
        DatabaseHandler db = new DatabaseHandler(getApplicationContext(), null, null, 1);
        db.getWritableDatabase();
        userLoged = db.findUser(userLoged.getEmail());

        createDataBase();


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
        userLoged = null;
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
            switchText.setText(R.string.by_local);
        }
        else
            switchText.setText(R.string.alphabetical);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static User getUser(){
        DatabaseHandler db = new DatabaseHandler(mContext, null, null, 1);
        db.getWritableDatabase();
        return db.findUser(userLoged.getEmail());
    }


    public void createDataBase(){
        Shop nike = new Shop(43.978917,4.888941,"Nike","Nike's shop @ Avignon. Just do it !","Sport;Clothes");
        Shop ikea = new Shop (43.61092,3.87723,"Ikea", "The wonderful everywhere", "Well Being;");
        Shop Geant = new Shop (43.610201,3.852733,"Geant Casino", "Les prix bas, c'est chez Géant !", "Food");
        Shop louisVuitton = new Shop(48.871657,2.300555,"Louis Vuitton", "Maroquinerie et bagages au monogramme emblématique et collections de mode chics pour cette enseigne de luxe.","Lux;Clothes");

        Bitmap icon = BitmapFactory.decodeResource(this.getResources(),R.drawable.louis_vuitton);
        byte[] data = DatabaseHandler.getByte(icon);
        louisVuitton.setPicture(data);
        icon = BitmapFactory.decodeResource(this.getResources(),R.drawable.geant_casino);
        data = DatabaseHandler.getByte(icon);
        Geant.setPicture(data);
        icon = BitmapFactory.decodeResource(this.getResources(), R.drawable.ikea);
        data = DatabaseHandler.getByte(icon);
        ikea.setPicture(data);
        icon = BitmapFactory.decodeResource(this.getResources(),R.drawable.temp_shop_logo);
        data = DatabaseHandler.getByte(icon);
        nike.setPicture(data);

        DatabaseHandler db = new DatabaseHandler(getApplicationContext(), null, null, 1);
        db.getWritableDatabase();
        if(db.findShop("Nike") == null){
            db.addShop(nike);

            Shop bdNike = db.findShop("Nike");


            Offer offer_nike1 = new Offer("Air max 90",99,"Summer Collection");
            Offer offer_nike2 = new Offer("Sweat Nike", 149,"Old collection");
            offer_nike1.setShop_id(bdNike.getId());
            offer_nike2.setShop_id(bdNike.getId());

            icon = BitmapFactory.decodeResource(this.getResources(), R.drawable.sweat_nike);
            data = DatabaseHandler.getByte(icon);
            offer_nike2.setPicture(data);
            icon = BitmapFactory.decodeResource(this.getResources(), R.drawable.nike_airmax);
            data = DatabaseHandler.getByte(icon);
            offer_nike1.setPicture(data);
            db.addOffer(offer_nike1);
            db.addOffer(offer_nike2);
            nike.addOffer(offer_nike1);
            nike.addOffer(offer_nike2);

        }
        if(db.findShop("Geant Casino") == null){
            db.addShop(Geant);

            Shop bdGeant = db.findShop("Geant Casino");


            Offer offer_geant1 = new Offer("Spaghetti",0.99,"Perfect w/ tomato");
            Offer offer_geant2 = new Offer("Haribo Rainbow", 4.55,"Be careful w/ sugar");
            offer_geant1.setShop_id(bdGeant.getId());
            offer_geant2.setShop_id(bdGeant.getId());

            icon = BitmapFactory.decodeResource(this.getResources(), R.drawable.haribo);
            data = DatabaseHandler.getByte(icon);
            offer_geant2.setPicture(data);
            icon = BitmapFactory.decodeResource(this.getResources(), R.drawable.spaghetti);
            data = DatabaseHandler.getByte(icon);
            offer_geant1.setPicture(data);
            db.addOffer(offer_geant1);
            db.addOffer(offer_geant2);
            Geant.addOffer(offer_geant1);
            Geant.addOffer(offer_geant2);
        }
        if(db.findShop("Ikea") == null){
            db.addShop(ikea);

            Shop bdIkea = db.findShop("Ikea");


            Offer offer_ikea1 = new Offer("Lappviken white",149,"Perfect for your tv");
            Offer offer_ikea2 = new Offer("Adjustable Bed", 599,"Will perfectly fit in your 9m");
            offer_ikea1.setShop_id(bdIkea.getId());
            offer_ikea2.setShop_id(bdIkea.getId());

            icon = BitmapFactory.decodeResource(this.getResources(), R.drawable.lit);
            data = DatabaseHandler.getByte(icon);
            offer_ikea2.setPicture(data);
            icon = BitmapFactory.decodeResource(this.getResources(), R.drawable.lappviken);
            data = DatabaseHandler.getByte(icon);
            offer_ikea1.setPicture(data);
            db.addOffer(offer_ikea1);
            db.addOffer(offer_ikea2);
            ikea.addOffer(offer_ikea1);
            ikea.addOffer(offer_ikea2);
        }
        if(db.findShop("Louis Vuitton") == null){
            db.addShop(louisVuitton);


            Shop bdLouis = db.findShop("Louis Vuitton");


            Offer offer_louis1 = new Offer("Sac alma BB",1099,"En Y you won t be.");
            Offer offer_louis2 = new Offer("Casual talon", 590,"Perfect to match with you lambo LV.");
            offer_louis1.setShop_id(bdLouis.getId());
            offer_louis2.setShop_id(bdLouis.getId());

            icon = BitmapFactory.decodeResource(this.getResources(), R.drawable.talon);
            data = DatabaseHandler.getByte(icon);
            offer_louis2.setPicture(data);
            icon = BitmapFactory.decodeResource(this.getResources(), R.drawable.sac);
            data = DatabaseHandler.getByte(icon);
            offer_louis1.setPicture(data);
            db.addOffer(offer_louis1);
            db.addOffer(offer_louis2);
            louisVuitton.addOffer(offer_louis1);
            louisVuitton.addOffer(offer_louis2);
        }
    }

}

