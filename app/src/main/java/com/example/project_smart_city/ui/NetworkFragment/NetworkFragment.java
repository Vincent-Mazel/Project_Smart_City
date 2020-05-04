package com.example.project_smart_city.ui.NetworkFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.project_smart_city.R;

import java.util.Objects;


public class NetworkFragment extends Fragment {

    private NetworkViewModel networkViewModel;
    private Button btnCreate;
    private ScrollView currentFrag;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        networkViewModel =
                ViewModelProviders.of(this).get(NetworkViewModel.class);
        View root = inflater.inflate(R.layout.fragment_network, container, false);

        this.currentFrag = root.findViewById(R.id.fragment_scrollViewPageOne);

        this.btnCreate = root.findViewById(R.id.network_createBtn);
        btnCreate.setOnClickListener(view -> {
            NetworkCreateFragment nextFrag= new NetworkCreateFragment();
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_networkMainPage,nextFrag)
                    .addToBackStack(null)
                    .commit();
            currentFrag.setVisibility(View.GONE);
        });
        final TextView textView = root.findViewById(R.id.title_network);
        networkViewModel.getText().observe(this, s -> textView.setText(s));
        return root;
    }
}