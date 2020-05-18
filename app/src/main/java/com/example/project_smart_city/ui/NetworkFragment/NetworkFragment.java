package com.example.project_smart_city.ui.NetworkFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_smart_city.DatabaseHandler;
import com.example.project_smart_city.MainActivity;
import com.example.project_smart_city.Network;
import com.example.project_smart_city.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class NetworkFragment extends Fragment {

    private Button btnCreate;
    private ScrollView currentFrag;
    private DatabaseHandler db;
    private ArrayList<List> networkList;

    @SuppressLint({"SetTextI18n", "ShowToast"})
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_network, container, false);

        db = new DatabaseHandler(getContext(), null, null, 1);
        db.getWritableDatabase();

        networkList = db.loadNetworks();

        TableLayout tableLayout = root.findViewById(R.id.tableLayout_network_available);
        TableLayout tableLayout1 = root.findViewById(R.id.tableLayout_network_available1);

        System.out.println(db.loadNetworks());

        for (int i = 0; i<networkList.size(); ++i){
                    float density = Objects.requireNonNull(getContext()).getResources().getDisplayMetrics().density;
                    float px = 72 * density;
                    float dp = 400 / density;
                    TableRow row = new TableRow(getContext());
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(lp);
                    TextView name = new TextView(getContext());
                    name.setText(networkList.get(i).get(1).toString());
                    name.setTypeface(null, Typeface.BOLD);
                    name.setGravity(Gravity.CENTER);
                    name.setWidth((int) px);
                    TextView description = new TextView(getContext());
                    description.setText(networkList.get(i).get(2).toString());
                    description.setBackgroundResource(R.color.lightfade);
                    px = 135 * density;
                    description.setWidth((int) px);
                    TextView status = new TextView(getContext());
                    status.setText(networkList.get(i).get(3).toString());
                    status.setTypeface(null, Typeface.BOLD);
                    status.setGravity(Gravity.CENTER);
                    px = 55 * density;
                    status.setWidth((int) px);
                    TextView member = new TextView(getContext());
                    String[] members = networkList.get(i).get(6).toString().split(";");

                    int k = members.length;
                    member.setGravity(Gravity.CENTER);
                    member.setBackgroundResource(R.color.lightfade);
                    member.setWidth((int) px);
                    member.setText(Integer.toString(k)); // change to count actual members
                    row.setBackgroundResource(R.drawable.border);
                    row.addView(name);
                    row.addView(description);
                    row.addView(status);
                    row.addView(member);
                    Fragment finalFrag = this;
                    int finalI = i;
                    // YOURS NETWORK
                    if(networkList.get(i).get(6).toString().contains(Integer.toString(MainActivity.getUser().getId()))) {
                        tableLayout1.addView(row);
                        row.setOnClickListener(view -> {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Open " + name.getText().toString());
                            builder.setMessage(name.getText().toString() + " network is about to let you in back, is this what you want ?");
                            builder.setPositiveButton("Yes", (dialogInterface, i15) -> {
                                db.getWritableDatabase();
                                Network network = db.findNetwork(networkList.get(finalI).get(1).toString());

                                ActualNetworkFragment actualNetworkFragment = new ActualNetworkFragment(network.getName());

                                FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                                FragmentTransaction transaction = fm.beginTransaction();
                                transaction.remove(finalFrag);

                                transaction.replace(R.id.linearLayout_empty, actualNetworkFragment).addToBackStack(null).commit();
                            });
                            builder.setNegativeButton("No", (dialogInterface, i16) -> dialogInterface.cancel());
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                        });
                    }
                    // NETWORK AVAILABLE
                    else {
                        tableLayout.addView(row);
                        row.setOnClickListener(view -> {
                            if(status.getText().toString().equals("Private")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Join " + name.getText().toString());
                                builder.setMessage(name.getText().toString() + " network is a private network. \n Would you like to send a request membership to join the network ?");
                                builder.setPositiveButton("Yes", (dialogInterface, i1) -> {
                                    db.getWritableDatabase();
                                    Network network = db.findNetwork(name.getText().toString());
                                    if(network.addRequestToList(Integer.toString(MainActivity.getUser().getId()))){
                                        db.updateNetwork(network);
                                    }
                                    else {
                                        Toast.makeText(getContext(),"You already send a request to join this network",Toast.LENGTH_SHORT).show();
                                    }

                                });
                                builder.setNegativeButton("No", (dialogInterface, i12) -> {
                                    dialogInterface.cancel();
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Open " + name.getText().toString());
                                builder.setMessage(name.getText().toString() + " network is about to let you in. \n You will be a new member is this network.");
                                builder.setPositiveButton("Yes", (dialogInterface, i13) -> {
                                    db.getWritableDatabase();
                                    Network network = db.findNetwork(name.getText().toString());
                                    System.out.println(network);
                                    network.addMemberToList(Integer.toString(MainActivity.getUser().getId()));
                                    System.out.println(network);
                                    db.getWritableDatabase();
                                    db.updateNetwork(network);

                                    ActualNetworkFragment actualNetworkFragment = new ActualNetworkFragment(network.getName());
                                    FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                                    FragmentTransaction transaction = fm.beginTransaction();
                                    transaction.remove(finalFrag);
                                    transaction.replace(R.id.linearLayout_empty, actualNetworkFragment).addToBackStack(null).commit();
                                });
                                builder.setNegativeButton("No", (dialogInterface, i14) -> dialogInterface.cancel());
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }

                        });
                    }

        }

        db.close();
        this.currentFrag = root.findViewById(R.id.fragment_scrollViewPageOne);

        this.btnCreate = root.findViewById(R.id.network_createBtn);
        btnCreate.setOnClickListener(view -> {
            NetworkCreateFragment networkCreateFragment = new NetworkCreateFragment();
            FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.remove(this);

            transaction.replace(R.id.linearLayout_empty, networkCreateFragment).addToBackStack(null).commit();

        });
        final TextView textView = root.findViewById(R.id.title_network);
        textView.setText(R.string.network);
        return root;
    }

}