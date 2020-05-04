package com.example.project_smart_city.ui.ProfilFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.example.project_smart_city.MainActivity;
import com.example.project_smart_city.R;
import com.example.project_smart_city.ui.ChoicesFragment.ChoiceFragment;
import com.example.project_smart_city.ui.ChoicesFragment.PreferencesFragment;
import com.google.android.material.navigation.NavigationView;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class ProfilFragment extends Fragment implements View.OnClickListener {

    private ProfilViewModel profilViewModel;
    private Button buttonToPref;
    private Button btnSaveChenges;
    private CircularImageView profilPicture;
    private NavigationView menu;
    private ViewGroup viewNavigation;
    private ImageView fade;
    static final int RESULT_LOAD_IMG = 1;
    private CircularImageView profilPictureOpenMenu;
    private ImageView imagePref;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.profilViewModel =
                ViewModelProviders.of(this).get(ProfilViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profil_main, container, false);

        this.menu = root.findViewById(R.id.navigationView2);
        this.btnSaveChenges = root.findViewById(R.id.profil_btnSaveChange);
        this.buttonToPref = root.findViewById(R.id.gererPreference);


        this.profilPicture = root.findViewById(R.id.fragmentProfil_profilPicture2);

        this.fade = root.findViewById(R.id.FADE);
        this.viewNavigation = root.findViewById(R.id.frag_prof_menu);



        this.profilPictureOpenMenu = root.findViewById(R.id.fragmentProfil_profilPicture);

        profilPictureOpenMenu.setOnClickListener(view -> openMenu());

        this.imagePref = root.findViewById(R.id.fragment_profil_PrefLogo);
        imagePref.setOnClickListener(view -> {
            // OPEN PREF
            System.out.println("PREF OPENING BITACH");
            PreferencesFragment preferencesFragment = new PreferencesFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_profil, preferencesFragment).commit();
            ScrollView scrollView = MainActivity.getScrollView();
            scrollView.post(() -> scrollView.smoothScrollTo(0, scrollView.getBottom()));
            viewNavigation.setVisibility(View.GONE);

        });

        profilPicture.setOnClickListener(view -> {

            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);

        });

        viewNavigation.setVisibility(View.GONE);

        buttonToPref.setOnClickListener(view -> {
            ChoiceFragment nextFrag= new ChoiceFragment();
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_profil, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();

        });


        btnSaveChenges.setOnClickListener(view -> {
            //System.out.println("button saved change clicked");
            saveChangement();
        });

        fade.setOnClickListener(view -> closeMenu());

        TextView title = root.findViewById(R.id.tilte_profil);
        profilViewModel.getText().observe(this, s -> title.setText(s));



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
        scrollView.post(() -> scrollView.smoothScrollTo(0, scrollView.getTop()));
    }

    private void closeMenu(){
        Transition transitionTop = new Slide(Gravity.RIGHT);
        transitionTop.addTarget(R.id.frag_prof_menu);
        TransitionManager.beginDelayedTransition(viewNavigation, transitionTop);
        viewNavigation.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                profilPicture.setImageBitmap(selectedImage);
                profilPictureOpenMenu.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Une erreur s'est produite",Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(getContext(),"Vous n'avez pas choisi d'image", Toast.LENGTH_LONG).show();

        }
    }


    @Override
    public void onClick(View view) {

    }
}