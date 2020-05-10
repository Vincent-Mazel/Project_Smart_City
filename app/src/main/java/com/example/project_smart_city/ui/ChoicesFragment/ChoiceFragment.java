package com.example.project_smart_city.ui.ChoicesFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.project_smart_city.DatabaseHandler;
import com.example.project_smart_city.InscriptionActivity;
import com.example.project_smart_city.MainActivity;
import com.example.project_smart_city.R;
import com.example.project_smart_city.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;


public class ChoiceFragment extends Fragment {

    private Boolean isPrefOpen = false;
    private DatabaseHandler db;
    private User userLogged;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ChoiceViewModel choiceViewModel = ViewModelProviders.of(this).get(ChoiceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_choice, container, false);
        final TextView textView = root.findViewById(R.id.text_choices);


        LinearLayout linearLayoutList = root.findViewById(R.id.fragment_choice_listChoices);

        final ScrollView scrollMain = MainActivity.getScrollView();
        final ScrollView scrollInscription = InscriptionActivity.getScrollView();




        final TextView textView1 = root.findViewById(R.id.fragment_choice_intro);
        textView1.setText(R.string.intro_change);


        ArrayList<String> choicesList = new ArrayList<>();

        choicesList.add("Art");
        choicesList.add("Cars");
        choicesList.add("Cinema");
        choicesList.add("DIY");
        choicesList.add("Gaming");
        choicesList.add("Gardening");
        choicesList.add("Home");
        choicesList.add("Restaurant");
        choicesList.add("Sciences");
        choicesList.add("Sport");
        choicesList.add("Technology");


        db = new DatabaseHandler(getContext(), null, null, 1);
        db.getWritableDatabase();

        // collect information about the user logged;
        if(Objects.requireNonNull(getActivity()).getLocalClassName().equals("MainActivity")){
            userLogged = db.findHandler(MainActivity.getUser().getEmail());
        }
        if (Objects.requireNonNull(getActivity().getLocalClassName().equals("InscriptionActivity"))){
            userLogged = db.findHandler(InscriptionActivity.getUser().getEmail());
        }

        for(int i = 0; i< choicesList.size(); ++i){
            LayoutInflater newView = (LayoutInflater) Objects.requireNonNull(this.getContext()).getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert newView != null;
            @SuppressLint("InflateParams") View v = newView.inflate(R.layout.layout_listchoices, null);

            CheckBox checkBox = v.findViewById(R.id.choice_checkBox);
            checkBox.setId(R.id.choice_checkBox + i);
            checkBox.setText(choicesList.get(i));
            if(!(userLogged.getListChoices() == null) && userLogged.getListChoices().contains(checkBox.getText().toString())){
                checkBox.setChecked(true);
            }

            linearLayoutList.addView(v);
        }




        Button btnSaveChoices = root.findViewById(R.id.button_save_choices);
        btnSaveChoices.setText(R.string.enregistrer_ces_changements);

        btnSaveChoices.setOnClickListener(view -> {
            Toast.makeText(getContext(), "Choices saved ", Toast.LENGTH_SHORT).show();
            ArrayList<String> listChoicesUser = new ArrayList<>();
            for(int i = 0; i<choicesList.size(); ++i){
                CheckBox box = root.findViewById(R.id.choice_checkBox + i);
                if(box.isChecked()){
                    listChoicesUser.add(box.getText().toString());
                }
            }




            if(!isPrefOpen){
                if(getActivity().getLocalClassName().equals("InscriptionActivity")){

                    JSONObject json = new JSONObject();
                    try {
                        json.put("choicesArray", new JSONArray(listChoicesUser));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String arrayList = json.toString();


                    db.updataChoicesList(userLogged.getId(),json); // update db
                    db.close();


                    userLogged.setListChoices(arrayList);

                    PreferencesFragment nextFrag= new PreferencesFragment();
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                            .add(R.id.inscription_layout, nextFrag, "findThisFragment")
                            .addToBackStack(null)
                            .commit();
                    isPrefOpen = true;
                }
                if(getActivity().getLocalClassName().equals("MainActivity")){




                    // convert to JSON object to insert into db;
                    JSONObject json = new JSONObject();
                    try {
                        json.put("choicesArray", new JSONArray(listChoicesUser));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    db.updataChoicesList(userLogged.getId(), json);
                    db.close();

                    String arrayList = json.toString();
                    userLogged.setListChoices(arrayList);


                    // convert JSON object to String and insert them into new ArrayList;
                    try {
                        JSONObject jsonObject = new JSONObject(arrayList);
                        JSONArray choices = jsonObject.optJSONArray("choicesArray");
                        listChoicesUser.clear();
                        for(int i=0; i<choices.length(); ++i){
                            String str_value = choices.optString(i);
                            listChoicesUser.add(str_value);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }




                    scrollMain.post(() -> scrollMain.smoothScrollTo(0,scrollMain.getTop()));
                }
            }
        });

        if(Objects.requireNonNull(getActivity()).getLocalClassName().equals("MainActivity")){
            scrollMain.post(() -> scrollMain.smoothScrollTo(0, scrollMain.getBottom()));
        }
        if(getActivity().getLocalClassName().equals("InscriptionActivity")){
            scrollInscription.post(() -> scrollInscription.smoothScrollTo(0, scrollInscription.getBottom()));
        }

        choiceViewModel.getText().observe(this, textView::setText);

        return root;
    }


    public void chargeChoices(){


    }
}