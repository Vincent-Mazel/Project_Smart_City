package com.example.project_smart_city.ui.ChoicesFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.project_smart_city.MainActivity;
import com.example.project_smart_city.R;


public class ChoiceFragment extends Fragment {

    private ChoiceViewModel choiceViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        choiceViewModel =
                ViewModelProviders.of(this).get(ChoiceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_choice, container, false);
        final TextView textView = root.findViewById(R.id.text_choices);
        final TextView frag_choices_intro = root.findViewById(R.id.frag_choices_intro);
        frag_choices_intro.setText("Un petit peu de text pour présenter les choix");

        ScrollView scrollView = MainActivity.getScrollView();
        scrollView.post(new Runnable() { public void run() { scrollView.smoothScrollTo(0, scrollView.getBottom());} });

        choiceViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }
}