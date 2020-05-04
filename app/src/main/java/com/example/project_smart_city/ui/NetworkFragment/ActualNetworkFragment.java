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
import androidx.lifecycle.ViewModelProviders;

import com.example.project_smart_city.MainActivity;
import com.example.project_smart_city.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ActualNetworkFragment extends Fragment {

    private NetworkViewModel networkViewModel;
    private TextView msgToPost;
    private Button btnPost;
    private LinearLayout posts;
    private TextView author;
    private TextView networkName;
    private ViewGroup menu;
    private Boolean isMenuOpen;
    private FrameLayout main;

    @SuppressLint("ResourceAsColor")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        networkViewModel =
                ViewModelProviders.of(this).get(NetworkViewModel.class);
        View root = inflater.inflate(R.layout.fragment_actualnetwork, container, false);
        this.menu = root.findViewById(R.id.network_frame_menu);
        menu.setVisibility(View.GONE);
        this.networkName = root.findViewById(R.id.title_actualNetwork);

        ImageView menuImage = root.findViewById(R.id.fragment_actualNetwork_menu);
        this.isMenuOpen = false;
        this.main = root.findViewById(R.id.frag_news_frame);
        menuImage.setOnClickListener(view -> {
            if(!isMenuOpen){
                openMenu();
                main.setBackgroundColor(R.color.fade);

            }
            else {
                main.setBackground(null);
                closeMenu();
            }
        });


        networkName.setText("Network_name"); // getNetworkName()
        //this.author = getPseudo // à compléter;
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
            author.setText("Pseudo"); // need to change w/ actual author.
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            date.setText(df.format(Calendar.getInstance().getTime()));


            posts.addView(v, 0, new ViewGroup.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)));
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
