package com.example.project_smart_city.ui.NetworkFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.project_smart_city.MainActivity;
import com.example.project_smart_city.R;

public class NetworkCreateFragment extends Fragment {

    private NetworkViewModel networkViewModel;
    private TextView network_name;
    private TextView network_description;
    private Boolean isPrivate;
    private Switch aSwitch;
    private Button buttonCreate;
    private LinearLayout currentLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        networkViewModel =
                ViewModelProviders.of(this).get(NetworkViewModel.class);
        View root = inflater.inflate(R.layout.fragment_createnetwork, container, false);


        this.currentLayout = root.findViewById(R.id.fragment_LinearPageTwo);

        this.buttonCreate = root.findViewById(R.id.fragment_createNetworkBtn);
        buttonCreate.setOnClickListener(view -> createNetwork());

        return root;
    }

    public void createNetwork(){
        //* RECUPERATION DES INFO DU NEW NETWORK *\\
        network_name = MainActivity.getScrollView().findViewById(R.id.fragment_createNetwork_name);
        String sNetwork_name = network_name.getText().toString(); // NAME
        network_description = MainActivity.getScrollView().findViewById(R.id.fragment_createNetwork_description);
        String sNetwork_description = network_name.getText().toString(); // DESCRIPTION
        aSwitch = MainActivity.getScrollView().findViewById(R.id.fragment_createNetwork_SwitchStatut);
        isPrivate = aSwitch.isChecked(); // STATUT

        if(sNetwork_description.matches("") || sNetwork_name.matches("")){
            Toast.makeText(MainActivity.getScrollView().getContext(), "Please, fill required fields.", Toast.LENGTH_SHORT).show();
        }
        else{
            ActualNetworkFragment newNetwork = new ActualNetworkFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_createNetwork, newNetwork).commit();
            currentLayout.setVisibility(View.GONE);
            // Need to add le tout à la BD + call le bon réseau social.
        }

    }
}
