package com.example.project_smart_city.ChoicesFragment;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.project_smart_city.R;

public class FragmentChoice extends Fragment {

    public FragmentChoice(){

    }

    // 1 - Create keys for our Bundle
    private static final String KEY_POSITION="position";
    private static final String PREFERENCE="Préférences";
    private static final String CHOIX="CHOIX";
    private int cpt =0;




    // 2 - Method that will create a new instance of PageFragment, and add data to its bundle.
    public static FragmentChoice newInstance(int position) {

        // 2.1 Create new fragment
        FragmentChoice frag = new FragmentChoice();

        // 2.2 Create bundle and add it some data
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);

        return(frag);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        // 3 - Get layout of PageFragment
        View result = inflater.inflate(R.layout.fragment_choice, container, false);
        // 4 - Get widgets from layout and serialise it
        LinearLayout rootView=  result.findViewById(R.id.fragment_layout_choice);
        TextView textView=  result.findViewById(R.id.fragment_page_title);
        TextView intro = result.findViewById(R.id.frag_choices_intro);
        Button button= result.findViewById(R.id.button_save_choices);
        SearchView searchView = result.findViewById(R.id.fragment_choice_searchView);
        View image = result.findViewById(R.id.fragment_choice_arrow);
        ListView listView = result.findViewById(R.id.fragment_choice_listChoices);

        // 5 - Get data from Bundle (created in method newInstance)
        int position = getArguments().getInt(KEY_POSITION, -1);

        // 6 - Update widgets with it
        if(position%2 ==0){
            textView.setText("CHOIX");
            searchView.setVisibility(View.GONE);
            image.setBackgroundResource(R.drawable.ic_compare_arrows_black_24dp);
            intro.setText("Choisissez vos choix");
            button.setVisibility(View.GONE);
        }
        else{
            intro.setText("Choisissez vos préférences");

            textView.setText("PREFERENCES");
            button.setText("SAUVEGARDER");
            image.setVisibility(View.GONE);
        }
        return result;
    }
}
