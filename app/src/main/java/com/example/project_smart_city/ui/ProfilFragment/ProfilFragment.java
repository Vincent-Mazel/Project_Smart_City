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

import com.example.project_smart_city.DatabaseHandler;
import com.example.project_smart_city.MainActivity;
import com.example.project_smart_city.User;
import com.example.project_smart_city.ui.ChoicesFragment.ChoiceFragment;
import com.example.project_smart_city.ui.ChoicesFragment.PreferencesFragment;
import com.google.android.material.navigation.NavigationView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static com.example.project_smart_city.R.id;
import static com.example.project_smart_city.R.id.text_pseudo;
import static com.example.project_smart_city.R.layout;


public class ProfilFragment extends Fragment implements View.OnClickListener {

    private ProfilViewModel profilViewModel;
    private Button buttonToPref;
    private Button btnSaveChenges;
    private ImageView profilPicture;
    private NavigationView menu;
    private ViewGroup viewNavigation;
    private ImageView fade;
    static final int RESULT_LOAD_IMG = 1;
    private ImageView profilPictureOpenMenu;
    private ImageView imagePref;
    private User user = MainActivity.getUser();
    private DatabaseHandler db;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        this.profilViewModel =
                ViewModelProviders.of(this).get(ProfilViewModel.class);
        View root = inflater.inflate(layout.fragment_profil_main, container, false);

        this.menu = root.findViewById(id.navigationView2);
        this.btnSaveChenges = root.findViewById(id.profil_btnSaveChange);
        this.buttonToPref = root.findViewById(id.gererPreference);

        TextView view_text_pseudo = root.findViewById(id.text_pseudo);
        TextView view_text_weight = root.findViewById(id.text_poids);
        TextView view_text_size = root.findViewById(id.text_taille);
        TextView view_text_email = root.findViewById(id.text_email);

        view_text_email.setText(this.user.getEmail());
        view_text_pseudo.setText(this.user.getPseudo());
        view_text_size.setText(Integer.toString(this.user.getSize()));
        view_text_weight.setText(Integer.toString(this.user.getWeight()));


        this.profilPicture = root.findViewById(id.fragmentProfil_profilPicture2);
        this.profilPictureOpenMenu = root.findViewById(id.fragmentProfil_profilPicture);




        this.fade = root.findViewById(id.FADE);
        this.viewNavigation = root.findViewById(id.frag_prof_menu);

        db = new DatabaseHandler(getContext(), null, null, 1);
        db.getWritableDatabase();

        profilPicture.setImageBitmap(db.getProfilPicture(user.getId()));
        profilPictureOpenMenu.setImageBitmap(db.getProfilPicture(user.getId()));
        db.close();



        profilPictureOpenMenu.setOnClickListener(view ->
               openMenu());


        this.imagePref = root.findViewById(id.fragment_profil_PrefLogo);
        imagePref.setOnClickListener(view -> {
            // OPEN PREF
            PreferencesFragment preferencesFragment = new PreferencesFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(id.fragment_profil, preferencesFragment).commit();
            ScrollView scrollView = MainActivity.getScrollView();
            scrollView.post(() -> scrollView.smoothScrollTo(0, scrollView.getBottom()));
            viewNavigation.setVisibility(View.GONE);

        });

        profilPicture.setOnClickListener(view -> {

            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);

            // Save new profile picture



        });

        viewNavigation.setVisibility(View.GONE);

        buttonToPref.setOnClickListener(view -> {
            ChoiceFragment nextFrag= new ChoiceFragment();
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                    .replace(id.fragment_profil, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();

        });


        btnSaveChenges.setOnClickListener(view -> {
            //System.out.println("button saved change clicked");
            saveChangement();
        });

        fade.setOnClickListener(view -> closeMenu());

        TextView title = root.findViewById(id.tilte_profil);
        profilViewModel.getText().observe(this, s -> title.setText(s));



        return root;
    }


    private void saveChangement(){
        TextView pseudo = MainActivity.getScrollView().findViewById(text_pseudo);
        TextView poids = MainActivity.getScrollView().findViewById(id.text_poids);
        TextView taille = MainActivity.getScrollView().findViewById(id.text_taille);
        TextView email = MainActivity.getScrollView().findViewById(id.text_email);

        // add to database //
    }
    private void openMenu() {
        TextView textView_prenom = MainActivity.getScrollView().findViewById(id.profil_prenom);
        textView_prenom.setText(this.user.getName());
        TextView textView_nom = MainActivity.getScrollView().findViewById(id.profil_nom);
        textView_nom.setText(this.user.getSurname());
        TextView textView_sexe = MainActivity.getScrollView().findViewById(id.profil_sexe);
        textView_sexe.setText(this.user.getSexe());


        Transition transitionTop = new Slide(Gravity.RIGHT);
        transitionTop.addTarget(id.frag_prof_menu);
        TransitionManager.beginDelayedTransition(viewNavigation, transitionTop);
        viewNavigation.setVisibility(View.VISIBLE);
        ScrollView scrollView = MainActivity.getScrollView();
        scrollView.post(() -> scrollView.smoothScrollTo(0, scrollView.getTop()));
    }

    private void closeMenu(){
        Transition transitionTop = new Slide(Gravity.RIGHT);
        transitionTop.addTarget(id.frag_prof_menu);
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
                db.getWritableDatabase();
                byte[] newData = DatabaseHandler.getByte(selectedImage);
                db.updateProfilImg(user.getId(),newData);
                db.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "An error has occurred",Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(getContext(),"Pick a image please", Toast.LENGTH_LONG).show();

        }
    }


    @Override
    public void onClick(View view) {

    }
}