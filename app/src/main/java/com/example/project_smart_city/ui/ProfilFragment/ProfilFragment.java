package com.example.project_smart_city.ui.ProfilFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.project_smart_city.R;
import com.example.project_smart_city.ui.ChoicesFragment.ChoiceFragment;

import java.util.Objects;


public class ProfilFragment extends Fragment implements View.OnClickListener{

    private ProfilViewModel profilViewModel;
    private Button buttonToPref;
    private ScrollView sc;
    private Button btnSaveChenges;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        profilViewModel =
                ViewModelProviders.of(this).get(ProfilViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profil, container, false);



        buttonToPref = root.findViewById(R.id.gererPreference);
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

        btnSaveChenges = root.findViewById(R.id.profil_btnSaveChange);
        btnSaveChenges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("choix saved");

            }
        });
        TextView title = root.findViewById(R.id.tilte_profil);
        profilViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                title.setText(s);
            }
        });

        return root;
    }


    @Override
    public void onClick(View view) {

    }
}