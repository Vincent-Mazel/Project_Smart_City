package com.example.project_smart_city.ui.NewsFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.project_smart_city.R;

public class NewsFragment extends Fragment {

    private NewsViewModel newsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newsViewModel =
                ViewModelProviders.of(this).get(NewsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_actuality, container, false);
        final TextView textView = root.findViewById(R.id.title_news);
        newsViewModel.getText().observe(this, s -> textView.setText(s));
        return root;
    }
}