package com.example.project_smart_city.ui.ChoicesFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.project_smart_city.MainActivity;
import com.example.project_smart_city.R;

import java.util.ArrayList;


public class ChoiceFragment extends Fragment {

    private ChoiceViewModel choiceViewModel;
    private LinearLayout linearLayoutList;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        choiceViewModel =
                ViewModelProviders.of(this).get(ChoiceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_choice, container, false);
        final TextView textView = root.findViewById(R.id.text_choices);


        this.linearLayoutList = root.findViewById(R.id.fragment_choice_listChoices);
        ScrollView scrollView = MainActivity.getScrollView();
        scrollView.post(new Runnable() { public void run() { scrollView.smoothScrollTo(0, scrollView.getBottom());} });

        chargeChoices();

        Button btnSaveChoices = root.findViewById(R.id.button_save_choices);
        btnSaveChoices.setText("Save your Choices");
        btnSaveChoices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save changement to DB
                Toast.makeText(MainActivity.getScrollView().getContext(), "Choix sauvegardés !", Toast.LENGTH_SHORT).show();
            }
        });
        choiceViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }


    public void chargeChoices(){
        // SQL request to charge every choices possible.



        ArrayList<String> strings = new ArrayList<>();
        strings.add("Sport");
        strings.add("Maison");
        strings.add("Loisir");
        strings.add("Art");
        strings.add("Restaurant");
        strings.add("Maison");
        strings.add("Loisir");
        strings.add("Art");
        strings.add("Restaurant");
        strings.add("Maison");
        strings.add("Loisir");
        strings.add("Art");
        strings.add("Restaurant");
        strings.add("Maison");
        strings.add("Loisir");
        strings.add("Art");
        strings.add("Restaurant");

        for(int i = 0; i< strings.size(); ++i){
            LayoutInflater newView = (LayoutInflater) this.getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = newView.inflate(R.layout.layout_listchoices, null);

            TextView choice = v.findViewById(R.id.choice_actualChoice);
            choice.setText(strings.get(i));

            linearLayoutList.addView(v);
        }
    }
}