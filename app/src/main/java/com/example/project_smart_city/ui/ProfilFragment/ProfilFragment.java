package com.example.project_smart_city.ui.ProfilFragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.example.project_smart_city.MainActivity;
import com.example.project_smart_city.R;
import com.example.project_smart_city.ui.ChoicesFragment.ChoiceFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;


public class ProfilFragment extends Fragment implements View.OnClickListener{

    private ProfilViewModel profilViewModel;
    private Button buttonToPref;
    private Button btnSaveChenges;
    private View profilPicture;
    private NavigationView menu;
    private ViewGroup viewNavigation;
    private ImageView fade;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.profilViewModel =
                ViewModelProviders.of(this).get(ProfilViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profil_main, container, false);

        this.menu = root.findViewById(R.id.navigationView2);
        this.btnSaveChenges = root.findViewById(R.id.profil_btnSaveChange);
        this.buttonToPref = root.findViewById(R.id.gererPreference);
        this.profilPicture = root.findViewById(R.id.fragmentProfil_profilPicture);
        this.fade = root.findViewById(R.id.FADE);
        this.viewNavigation = root.findViewById(R.id.frag_prof_menu);



        viewNavigation.setVisibility(View.GONE);

        buttonToPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChoiceFragment nextFrag= new ChoiceFragment();
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_profil, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });


        btnSaveChenges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //System.out.println("button saved change clicked");
                saveChangement();
            }
        });

        profilPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenu();
            }
        });

        fade.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {closeMenu();}});

        TextView title = root.findViewById(R.id.tilte_profil);
        profilViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                title.setText(s);
            }
        });



        return root;
    }


    private void saveChangement(){
        TextView pseudo = MainActivity.getScrollView().findViewById(R.id.text_pseudo);
        TextView poids = MainActivity.getScrollView().findViewById(R.id.text_poids);
        TextView taille = MainActivity.getScrollView().findViewById(R.id.text_taille);
        TextView email = MainActivity.getScrollView().findViewById(R.id.text_email);

        // add to database //
    }
    private void openMenu() {
        Transition transitionTop = new Slide(Gravity.RIGHT);
        transitionTop.addTarget(R.id.frag_prof_menu);
        TransitionManager.beginDelayedTransition(viewNavigation, transitionTop);
        viewNavigation.setVisibility(View.VISIBLE);
        ScrollView scrollView = MainActivity.getScrollView();
        scrollView.post(new Runnable() { public void run() { scrollView.smoothScrollTo(0, scrollView.getTop());} });
    }

    private void closeMenu(){
        Transition transitionTop = new Slide(Gravity.RIGHT);
        transitionTop.addTarget(R.id.frag_prof_menu);
        TransitionManager.beginDelayedTransition(viewNavigation, transitionTop);
        viewNavigation.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {

    }
}