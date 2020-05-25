package com.example.project_smart_city.ui.Shopping;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.example.project_smart_city.DatabaseHandler;
import com.example.project_smart_city.MainActivity;
import com.example.project_smart_city.Offer;
import com.example.project_smart_city.R;
import com.example.project_smart_city.Shop;

import java.util.ArrayList;
import java.util.Objects;


public class ShoppingFragment extends Fragment implements LocationListener{

    private ShoppingViewModel shoppingViewModel;
    private ViewGroup viewNavigation;
    private Switch aSwitch;
    private Spinner spinner;
    private TextView switchText;
    private LinearLayout linearLayout; // Might change to a list with one LinearLayout for each shop.
    private double latitude;
    private double longitude;
    private DatabaseHandler db;

    @SuppressLint("MissingPermission")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        db = new DatabaseHandler(getContext(), null, null, 1);
        db.getWritableDatabase();
        shoppingViewModel =
                ViewModelProviders.of(this).get(ShoppingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_shoppingsearching, container, false);
        this.aSwitch = root.findViewById(R.id.shopping_switchProxAnn);
        this.spinner = root.findViewById(R.id.shopping_spinner);


        this.viewNavigation = root.findViewById(R.id.frag_shop_offers);

        viewNavigation.setVisibility(View.GONE);

        this.linearLayout = root.findViewById(R.id.Shopping_Shop1);


        ImageView imageViewFade = root.findViewById(R.id.shopFade);
        ImageView imageViewClsoe = root.findViewById(R.id.close);
        imageViewClsoe.setOnClickListener(view -> closeShopOffer());
        imageViewFade.setOnClickListener(view -> closeShopOffer());


        ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        LocationManager locationManager = (LocationManager) Objects.requireNonNull(getContext()).getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);


        loadShopByName();


        String[] arraySpinner = new String[]{
                "All",
                "Sport",
                "Well Being",
                "Lux",
                "Food",
                "Clothes"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        final TextView textView = root.findViewById(R.id.title_shoppingSearch);
        shoppingViewModel.getText().observe(this, textView::setText);
        return root;
    }

    @SuppressLint("SetTextI18n")
    private void loadShopByName() {
        linearLayout.removeAllViews();
        ArrayList<Shop> arrayList = db.loadShop();
        for (int i = 0; i < arrayList.size(); ++i) {
            Shop shop = arrayList.get(i);

            LayoutInflater newView = (LayoutInflater) Objects.requireNonNull(this.getContext()).getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert newView != null;
            View v = newView.inflate(R.layout.shop, null);

            TextView name = v.findViewById(R.id.shopping_fullNameShop);
            TextView description = v.findViewById(R.id.shopping_descriptionShopOne);
            TextView dist = v.findViewById(R.id.shopping_distanceOne);
            ImageView picture = v.findViewById(R.id.shopping_shopOne);


            picture.setImageBitmap(DatabaseHandler.getImage(shop.getPicture()));
            name.setText(shop.getName());
            description.setText(shop.getDescription());
            // Pythagore in order to find distances
            double AB = (shop.getLatitude() - latitude);
            double BC = (shop.getLongitude() - longitude);
            double squareAC = (AB*AB) + (BC*BC);
            double AC = Math.sqrt(squareAC);

            // found on the internet. To convert distances from 2 points you need to multiply the hypotenuse by 111 and you'll have your result in km.
            long distance = Math.round(AC*111);
            dist.setText(Math.toIntExact(distance) + " km");

            v.setOnClickListener(view -> {
                openShopOffers(shop.getId());
            });
            linearLayout.addView(v, 0, new ViewGroup.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)));
        }
    }


    @SuppressLint("SetTextI18n")
    private void openShopOffers(int id_shop){
        Transition transitionTop = new Slide(Gravity.TOP);
        transitionTop.addTarget(R.id.frag_shop_offers);
        TransitionManager.beginDelayedTransition(viewNavigation, transitionTop);
        viewNavigation.setVisibility(View.VISIBLE);
        ArrayList<Offer> offers = db.loadOfferByShop(id_shop);
        for (int i = 0 ; i<offers.size(); ++i){
            Offer offer = offers.get(i);

            LayoutInflater newView = (LayoutInflater) Objects.requireNonNull(this.getContext()).getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert newView != null;
            View v = newView.inflate(R.layout.shopoffers, null);

            TextView name = v.findViewById(R.id.offer_name);
            TextView description = v.findViewById(R.id.offer_description);
            TextView price = v.findViewById(R.id.offer_price);
            ImageView picture = v.findViewById(R.id.offer_picture);

            name.setText(offer.getName());
            description.setText(offer.getDescription());
            price.setText(offer.getPrice() + " € ");
            picture.setImageBitmap(DatabaseHandler.getImage(offer.getPicture()));

            LinearLayout linearOffers = MainActivity.getScrollView().findViewById(R.id.linear_offers);
            linearOffers.addView(v, 0, new ViewGroup.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)));

        }

    }

    private void closeShopOffer(){
        LinearLayout linearOffers = MainActivity.getScrollView().findViewById(R.id.linear_offers);
        Transition transitionTop = new Slide(Gravity.TOP);
        transitionTop.addTarget(R.id.frag_shop_offers);
        TransitionManager.beginDelayedTransition(viewNavigation, transitionTop);
        linearOffers.removeAllViews();
        viewNavigation.setVisibility(View.GONE);

}

    @SuppressLint("SetTextI18n")
    @Override
    public void onLocationChanged(Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d("Latitude","status");
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.d("Latitude","disable");
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo!
                    aSwitch.setClickable(false);
                }
            }
        }
    }

}