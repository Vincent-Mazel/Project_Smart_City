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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.project_smart_city.InscriptionActivity;
import com.example.project_smart_city.MainActivity;
import com.example.project_smart_city.R;

import java.util.ArrayList;
import java.util.Objects;


public class ChoiceFragment extends Fragment {

    private ChoiceViewModel choiceViewModel;
    private LinearLayout linearLayoutList;
    private Boolean isPrefOpen = false;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        choiceViewModel =
                ViewModelProviders.of(this).get(ChoiceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_choice, container, false);
        final TextView textView = root.findViewById(R.id.text_choices);


        this.linearLayoutList = root.findViewById(R.id.fragment_choice_listChoices);

        final ScrollView scrollMain = MainActivity.getScrollView();
        final ScrollView scrollInscription = InscriptionActivity.getScrollView();




        final TextView textView1 = root.findViewById(R.id.fragment_choice_intro);
        textView1.setText("Vous pourrez toujours les changer par la suite");



        chargeChoices();

        Button btnSaveChoices = root.findViewById(R.id.button_save_choices);
        btnSaveChoices.setText("Save your Choices");
        btnSaveChoices.setOnClickListener(view -> {
            Toast.makeText(getContext(), "Choix enregistrés !", Toast.LENGTH_SHORT).show();
            if(!isPrefOpen){
                if(getActivity().getLocalClassName().equals("InscriptionActivity")){
                    // CREATE IN DATABASE
                    PreferencesFragment nextFrag= new PreferencesFragment();
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                            .add(R.id.inscription_layout, nextFrag, "findThisFragment")
                            .addToBackStack(null)
                            .commit();
                    isPrefOpen = true;
                }
                if(getActivity().getLocalClassName().equals("MainActivity")){
                    // UPDATE DATABASE
                    scrollMain.post(() -> scrollMain.smoothScrollTo(0,scrollMain.getTop()));
                }
            }
        });

        if(getActivity().getLocalClassName().equals("MainActivity")){
            scrollMain.post(() -> scrollMain.smoothScrollTo(0, scrollMain.getBottom()));
        }
        if(getActivity().getLocalClassName().equals("InscriptionActivity")){
            scrollInscription.post(() -> scrollInscription.smoothScrollTo(0, scrollInscription.getBottom()));
        }

        choiceViewModel.getText().observe(this, textView::setText);

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