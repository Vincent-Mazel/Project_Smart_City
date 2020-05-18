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

import com.example.project_smart_city.DatabaseHandler;
import com.example.project_smart_city.MainActivity;
import com.example.project_smart_city.Network;
import com.example.project_smart_city.R;

import java.util.Objects;

public class NetworkCreateFragment extends Fragment {


    private TextView network_name;
    private TextView network_description;
    private Boolean isPrivate;
    private Switch aSwitch;
    private Button buttonCreate;
    private LinearLayout currentLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_createnetwork, container, false);


        this.currentLayout = root.findViewById(R.id.fragment_LinearPageTwo);

        this.buttonCreate = root.findViewById(R.id.fragment_createNetworkBtn);
        buttonCreate.setOnClickListener(view -> createNetwork());

        return root;
    }

    public void createNetwork(){
        //* RECUPERATION DES INFO DU NEW NETWORK *\\
        network_name = MainActivity.getScrollView().findViewById(R.id.fragment_createNetwork_name);

        network_description = MainActivity.getScrollView().findViewById(R.id.fragment_createNetwork_description);

        aSwitch = MainActivity.getScrollView().findViewById(R.id.fragment_createNetwork_SwitchStatut);
        String status;
        if(aSwitch.isChecked()){status = "Private";}
        else {status = "Public";}                                                            // STATUS

        String sNetwork_name = network_name.getText().toString();                            // NAME
        String sNetwork_description = network_description.getText().toString();              // DESCRIPTION
        int networkCreator = MainActivity.getUser().getId();                                 // Creator ID

        if(sNetwork_description.matches("") || sNetwork_name.matches("")){
            Toast.makeText(MainActivity.getScrollView().getContext(), "Please, fill required fields.", Toast.LENGTH_SHORT).show();
        }
        else{
            Network newNetwork = new Network(sNetwork_name,sNetwork_description, status, networkCreator);
            DatabaseHandler db = new DatabaseHandler(getContext(), null, null, 1);
            db.getWritableDatabase();
            if(db.findNetwork(newNetwork.getName()) == null ){
                db.addNetwork(newNetwork);
                // call the new network;
                ActualNetworkFragment actualNetworkFragment = new ActualNetworkFragment(db.findNetwork(newNetwork.getName()).getName());
                FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                transaction.remove(this);
                transaction.replace(R.id.linearLayout_empty, actualNetworkFragment).addToBackStack(null).commit();
            }
            else {
                Toast.makeText(MainActivity.getScrollView().getContext(), "This network already exists. Join it or create another one.", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }

    }
}
