package com.example.project_smart_city.NetworkFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.project_smart_city.R;


public class NetworkFragment extends Fragment {

    private NetworkViewModel NetworkViewModel;
    private Context mContext;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NetworkViewModel =
                ViewModelProviders.of(this).get(NetworkViewModel.class);
        View root = inflater.inflate(R.layout.fragment_network, container, false);
        final TextView textView = root.findViewById(R.id.text_network);
        NetworkViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mContext = context;
    }
}