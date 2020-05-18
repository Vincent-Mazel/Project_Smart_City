package com.example.project_smart_city.ui.NetworkFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project_smart_city.DatabaseHandler;
import com.example.project_smart_city.MainActivity;
import com.example.project_smart_city.Network;
import com.example.project_smart_city.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class ActualNetworkFragment extends Fragment {

    private TextView msgToPost;
    private Button btnPost;
    private LinearLayout posts;
    private String author;
    private ViewGroup menu;
    private Boolean isMenuOpen;
    private FrameLayout main;
    private Network network;
    private DatabaseHandler db;
    private String name;
    private TextView status;


    public ActualNetworkFragment(String name) {
        this.name = name;
    }

    @SuppressLint("ResourceAsColor")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        db = new DatabaseHandler(getContext(), null, null, 1);
        db.getWritableDatabase();
        network = db.findNetwork(name);
        db.close();

        View root = inflater.inflate(R.layout.fragment_actualnetwork, container, false);

        this.menu = root.findViewById(R.id.network_frame_menu);
        menu.setVisibility(View.GONE);
        TextView networkName = root.findViewById(R.id.title_actualNetwork);
        networkName.setText(network.getName());




        ImageView menuImage = root.findViewById(R.id.fragment_actualNetwork_menu);
        this.isMenuOpen = false;
        this.main = root.findViewById(R.id.frag_news_frame);
        menuImage.setOnClickListener(view -> {
            if(!isMenuOpen){
                openMenu();
                status = root.findViewById(R.id.network_statut);
                status.setText(network.getStatus());
                TextView creator = root.findViewById(R.id.network_creator);
                String sCreator = db.findUserById(network.getCreator()).getPseudo();
                db.close();
                creator.setText(sCreator);
                Button btn_delete = root.findViewById(R.id.btn_deleteNetwork);
                btn_delete.setOnClickListener(view1 -> {
                    // call delete network and go to network page 1;
                    Objects.requireNonNull(getActivity()).onBackPressed();
                });
            }
            else {
                closeMenu();
            }
        });
        TextView description = root.findViewById(R.id.actualNetwork_description);
        description.setText(network.getDescription());


        this.author = MainActivity.getUser().getPseudo();
        this.btnPost = root.findViewById(R.id.fragment_actualNetwork_BtnPost);
        btnPost.setOnClickListener(view -> postMsg());

        this.posts = root.findViewById(R.id.fragment_actualNetwork_linearViewPosts);

        return root;
    }

    public void postMsg(){
        msgToPost = MainActivity.getScrollView().findViewById(R.id.fragment_actualNetwork_msgToPost);
        String sMsgToPost = msgToPost.getText().toString();
        if(sMsgToPost.matches("")){
            Toast.makeText(MainActivity.getScrollView().getContext(), "At least, write something please !", Toast.LENGTH_SHORT).show();
        }
        else {

            LayoutInflater newView = (LayoutInflater) this.getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = newView.inflate(R.layout.layout_posts, null);

            TextView thePost = v.findViewById(R.id.post_actualPost);
            TextView author = v.findViewById(R.id.post_author);
            TextView date = v.findViewById(R.id.post_date);

            thePost.setText(msgToPost.getText().toString());
            author.setText(MainActivity.getUser().getPseudo()); // need to change w/ actual author.
            @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            date.setText(df.format(Calendar.getInstance().getTime()));


            posts.addView(v, 0, new ViewGroup.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)));
            msgToPost.setText(null);
        }




    }

    private void openMenu(){
        menu.setVisibility(View.VISIBLE);
        isMenuOpen = true;
    }
    private void closeMenu(){
        menu.setVisibility(View.GONE);
        isMenuOpen = false;
    }
}
