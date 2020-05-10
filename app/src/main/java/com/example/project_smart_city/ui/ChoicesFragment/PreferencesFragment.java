package com.example.project_smart_city.ui.ChoicesFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

import com.example.project_smart_city.DatabaseHandler;
import com.example.project_smart_city.InscriptionActivity;
import com.example.project_smart_city.LoginActivity;
import com.example.project_smart_city.MainActivity;
import com.example.project_smart_city.R;
import com.example.project_smart_city.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class PreferencesFragment extends Fragment {

    private User userLogged;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_choice, container, false);
        final TextView title = root.findViewById(R.id.text_choices);
        final TextView intro = root.findViewById(R.id.fragment_choice_intro);
        intro.setText(R.string.you_can_change_int);
        title.setText(R.string.interests);
        LinearLayout linearLayoutList = root.findViewById(R.id.fragment_choice_listChoices);

        Button btnValidate = root.findViewById(R.id.button_save_choices);


        final ScrollView scrollMain = MainActivity.getScrollView();
        final ScrollView scrollInscription = InscriptionActivity.getScrollView();


        DatabaseHandler db = new DatabaseHandler(getContext(), null, null, 1);
        db.getWritableDatabase();

        // collect data about the user logged;
        if(Objects.requireNonNull(getActivity()).getLocalClassName().equals("MainActivity")){
            userLogged = db.findHandler(MainActivity.getUser().getEmail());
        }
        if (Objects.requireNonNull(getActivity().getLocalClassName().equals("InscriptionActivity"))){
            userLogged = db.findHandler(InscriptionActivity.getUser().getEmail());
        }

        ArrayList<String> listInterests = new ArrayList<>();
        listInterests.add("Weather");
        listInterests.add("Traffic");
        listInterests.add("Calendar");
        listInterests.add("Alarm");
        listInterests.add("Animation");

        for(int i = 0; i< listInterests.size(); ++i){
            LayoutInflater newView = (LayoutInflater) Objects.requireNonNull(this.getContext()).getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert newView != null;
            @SuppressLint("InflateParams") View v = newView.inflate(R.layout.layout_listchoices, null);

            CheckBox checkBox = v.findViewById(R.id.choice_checkBox);
            checkBox.setText(listInterests.get(i));
            checkBox.setId(R.id.choice_checkBox + i);

            if(!(userLogged.getListInterests() == null) && userLogged.getListInterests().contains(listInterests.get(i))){
                checkBox.setChecked(true);
            }
            linearLayoutList.addView(v);
        }


        if(Objects.requireNonNull(getActivity()).getLocalClassName().equals("MainActivity")){
            scrollMain.post(() -> scrollMain.smoothScrollTo(0,scrollMain.getBottom()));
            btnValidate.setText(R.string.saveInterests);
            btnValidate.setOnClickListener(view -> {

                ArrayList<String> listInterestsUser = new ArrayList<>();
                for(int i = 0; i<listInterests.size(); ++i){
                    CheckBox box = root.findViewById(R.id.choice_checkBox + i);
                    if(box.isChecked()){
                        listInterestsUser.add(box.getText().toString());
                    }
                }
                // convert to JSON object to insert into db;
                JSONObject json = new JSONObject();
                try {
                    json.put("interestArray", new JSONArray(listInterestsUser));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                db.updataInterestList(userLogged.getId(), json);
                db.close();

                String arrayList = json.toString();
                userLogged.setListInterests(arrayList);


                // convert JSON object to String and insert them into new ArrayList;
                try {
                    JSONObject jsonObject = new JSONObject(arrayList);
                    JSONArray interests = jsonObject.optJSONArray("interestArray");
                    listInterestsUser.clear();
                    for(int i = 0; i<interests.length(); ++i){
                        String str_value = interests.optString(i);
                        listInterestsUser.add(str_value);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                scrollMain.post(() -> scrollMain.smoothScrollTo(0, scrollMain.getTop()));
                Toast.makeText(getContext(), "Choices saved !", Toast.LENGTH_SHORT).show();
            });
        }
        if(getActivity().getLocalClassName().equals("InscriptionActivity")){
            scrollInscription.post(() -> scrollInscription.smoothScrollTo(0, 3500));
            btnValidate.setText(R.string.save_and_SignIn);
            btnValidate.setOnClickListener(view -> {

                ArrayList<String> listInterestsUser = new ArrayList<>();
                for(int i = 0; i<listInterests.size(); ++i){
                    CheckBox box = root.findViewById(R.id.choice_checkBox + i);
                    if(box.isChecked()){
                        listInterestsUser.add(box.getText().toString());
                    }
                }
                JSONObject json = new JSONObject();
                try {
                    json.put("interestArray", new JSONArray(listInterestsUser));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String arrayList = json.toString();
                db.updataInterestList(userLogged.getId(),json); // update db
                db.close();
                userLogged.setListInterests(arrayList);
                Intent i = new Intent(getActivity(), LoginActivity.class);
                i.putExtra("UserLogin", InscriptionActivity.getUser());
                startActivity(i);
                getActivity().finish();
            });
        }






        db.close();
        return root;
    }

}
