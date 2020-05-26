package com.example.project_smart_city.ui.NetworkFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project_smart_city.R;

import java.util.Objects;

public class NetworkViewModel extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.empty_layout, container, false);
        NetworkFragment networkFragment = new NetworkFragment();
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.linearLayout_empty,networkFragment)
                .addToBackStack(null)
                .commit();


        return root;
    }
}