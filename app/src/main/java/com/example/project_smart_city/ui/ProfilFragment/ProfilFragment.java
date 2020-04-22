package com.example.project_smart_city.ui.ProfilFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.project_smart_city.R;


public class ProfilFragment extends Fragment implements View.OnClickListener{

    private ProfilViewModel profilViewModel;
    public Button buttonToPref;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profilViewModel =
                ViewModelProviders.of(this).get(ProfilViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profil, container, false);
        buttonToPref = root.findViewById(R.id.gererPreference);
        buttonToPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implémenter création new frag sur le choix des preférences
                System.out.println("it works !");
            }
        });

        final TextView textView = root.findViewById(R.id.tilte_profil);
        profilViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }

    @Override
    public void onClick(View view) {

    }
}