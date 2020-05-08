package com.example.project_smart_city.ui.ChoicesFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.project_smart_city.InscriptionActivity;
import com.example.project_smart_city.MainActivity;
import com.example.project_smart_city.R;

import java.util.ArrayList;

public class PreferencesFragment extends Fragment {

    private ChoiceViewModel choiceViewModel;
    private LinearLayout linearLayoutList;
    private Button btnValidate;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        choiceViewModel =
                ViewModelProviders.of(this).get(ChoiceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_choice, container, false);
        final TextView title = root.findViewById(R.id.text_choices);
        final TextView intro = root.findViewById(R.id.fragment_choice_intro);
        intro.setText("Vos préférences sont également modifiables à tout moment.");
        title.setText("Préférences");
        linearLayoutList = root.findViewById(R.id.fragment_choice_listChoices);

        btnValidate = root.findViewById(R.id.button_save_choices);


        final ScrollView scrollMain = MainActivity.getScrollView();
        final ScrollView scrollInscription = InscriptionActivity.getScrollView();



        if(getActivity().getLocalClassName().equals("MainActivity")){
            scrollMain.post(() -> scrollMain.smoothScrollTo(0,scrollMain.getBottom()));
            btnValidate.setText("Sauvergarder ces préférences");
            btnValidate.setOnClickListener(view -> {
                scrollMain.post(() -> scrollMain.smoothScrollTo(0, scrollMain.getTop()));
                // UPDATE DB
            });
        }
        if(getActivity().getLocalClassName().equals("InscriptionActivity")){
            scrollInscription.post(() -> scrollInscription.smoothScrollTo(0, 3500));
            btnValidate.setText("Sauvegarder & entrer dans SmartCity");
            btnValidate.setOnClickListener(view -> {
                // send every data to the DataBase.  saveNewUserInDB();
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                getActivity().finish();
            });
        }



        chargePref();

        return root;
    }

    public void chargePref(){
        // SQL request to charge every pref possible.
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Meteo");
        strings.add("Trafic");
        strings.add("Event");
        strings.add("Sport");
        strings.add("Animation");
        strings.add("Cinema");
        for(int i = 0; i< strings.size(); ++i){
            LayoutInflater newView = (LayoutInflater) this.getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = newView.inflate(R.layout.layout_listchoices, null);

            TextView choice = v.findViewById(R.id.choice_actualChoice);
            choice.setText(strings.get(i));

            linearLayoutList.addView(v);
        }
    }


}
