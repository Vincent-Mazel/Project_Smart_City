package com.example.project_smart_city.ui.Shopping;

import android.os.Bundle;
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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.example.project_smart_city.R;


public class ShoppingFragment extends Fragment {

    private ShoppingViewModel shoppingViewModel;
    private ViewGroup viewNavigation;
    private Switch aSwitch;
    private Spinner spinner;
    private TextView switchText;
    private LinearLayout linearLayout; // Might change to a list with one LinearLayout for each shop.

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        shoppingViewModel =
                ViewModelProviders.of(this).get(ShoppingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_shoppingsearching, container, false);
        this.aSwitch = root.findViewById(R.id.shopping_switchProxAnn);
        this.spinner = root.findViewById(R.id.shopping_spinner);
        this.switchText = root.findViewById(R.id.shopping_textViewSwitch);

        this.viewNavigation = root.findViewById(R.id.frag_shop_offers);

        viewNavigation.setVisibility(View.GONE);

        this.linearLayout = root.findViewById(R.id.Shopping_Shop1);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("need to open the shop");
                openShopOffers();
            }
        });

        ImageView imageViewFade = root.findViewById(R.id.shopFade);
        ImageView imageViewClsoe = root.findViewById(R.id.close);
        imageViewClsoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeShopOffer();
            }
        });
        imageViewFade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeShopOffer();
            }
        });


        String[] arraySpinner = new String[]{
                "All",
                "Sport",
                "Bien être",
                "Luxe",
                "Alimetaire",
                "Art vivant",
                "Vetement"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        final TextView textView = root.findViewById(R.id.title_shoppingSearch);
        shoppingViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    public void openShopOffers(){
        Transition transitionTop = new Slide(Gravity.TOP);
        transitionTop.addTarget(R.id.frag_shop_offers);
        TransitionManager.beginDelayedTransition(viewNavigation, transitionTop);
        viewNavigation.setVisibility(View.VISIBLE);
    }

    public void closeShopOffer(){
        Transition transitionTop = new Slide(Gravity.TOP);
        transitionTop.addTarget(R.id.frag_shop_offers);
        TransitionManager.beginDelayedTransition(viewNavigation, transitionTop);
        viewNavigation.setVisibility(View.GONE);
    }
}