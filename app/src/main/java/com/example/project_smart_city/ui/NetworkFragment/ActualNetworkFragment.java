package com.example.project_smart_city.ui.NetworkFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_smart_city.DatabaseHandler;
import com.example.project_smart_city.MainActivity;
import com.example.project_smart_city.Network;
import com.example.project_smart_city.Post;
import com.example.project_smart_city.R;
import com.example.project_smart_city.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class ActualNetworkFragment extends Fragment {

    private TextView msgToPost;
    private Button btnPost;
    private LinearLayout posts;
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
        this.network = db.findNetwork(name);
        db.close();

        View root = inflater.inflate(R.layout.fragment_actualnetwork, container, false);

        this.menu = root.findViewById(R.id.network_frame_menu);
        menu.setVisibility(View.GONE);
        TextView networkName = root.findViewById(R.id.title_actualNetwork);
        networkName.setText(network.getName());


        this.posts = root.findViewById(R.id.fragment_actualNetwork_linearViewPosts);
        // LOAD POST
        loadPost();


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
                LinearLayout linearLayout = root.findViewById(R.id.scrollviewPendingRequests);

                if(network.getListRequest() != null){
                    String[] sRequests = network.getListRequest().split(";;");

                    for(int i = 0; i<sRequests.length;++i){
                        float density = Objects.requireNonNull(getContext()).getResources().getDisplayMetrics().density;
                        float px = 150 * density;
                        LinearLayout linearLayout1 = new LinearLayout(getContext());
                        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
                        linearLayout1.setBackgroundResource(R.drawable.border);
                        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        linearLayout1.setLayoutParams(lp1);
                        sRequests[i] = sRequests[i].replace(";", "");
                        User user = db.findUserById(Integer.parseInt(sRequests[i]));
                        TextView pseudo = new TextView(getContext());
                        pseudo.setText(user.getPseudo());
                        pseudo.setWidth((int) px);
                        pseudo.setPadding(4,0,0,0);
                        TextView age = new TextView(getContext());
                        String birthYEAR = user.getBirthday().substring(5);
                        int actualDate = new Date().getYear() + 1900;
                        int intAge = actualDate - Integer.parseInt(birthYEAR);
                        age.setText(intAge + " y.o");
                        px = 60 * density;
                        age.setWidth((int) px);
                        ImageView sexe = new ImageView(getContext());
                        if(user.getSexe().equals("Male")){
                            sexe.setImageResource(R.drawable.logo_homme);
                        }
                        else if (user.getSexe().equals("Female")){
                            sexe.setImageResource(R.drawable.logo_femme);
                        }
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(16*(int) density, LinearLayout.LayoutParams.MATCH_PARENT);
                        sexe.setLayoutParams(lp);
                        linearLayout1.addView(pseudo);
                        linearLayout1.addView(age);
                        linearLayout1.addView(sexe);
                        linearLayout.addView(linearLayout1);
                        int finalI = i;
                        linearLayout1.setOnClickListener(view12 -> {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Add " + pseudo.getText().toString() + " to " + network.getName());
                            builder.setMessage("Do you want to add " + pseudo.getText().toString() + " to the network ?\n He will be able to post here and to see each post from other member.");
                            builder.setPositiveButton("Yes", (dialogInterface, i15) -> {
                                network.removeRequest(sRequests[finalI]);
                                db.updateNetwork(network);
                                Toast.makeText(getContext(), pseudo.getText().toString() + " is now a member of " + network.getName(), Toast.LENGTH_SHORT).show();

                                ActualNetworkFragment actualNetworkFragment = new ActualNetworkFragment(network.getName());
                                FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                                FragmentTransaction transaction = fm.beginTransaction();
                                transaction.remove(this);
                                transaction.replace(R.id.linearLayout_empty, actualNetworkFragment).addToBackStack(null).commit();
                            });
                            builder.setNegativeButton("Later", (dialogInterface, i16) -> dialogInterface.cancel());
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        });
                    }
                }

                if(sCreator.equals(MainActivity.getUser().getPseudo())){
                    btn_delete.setText(R.string.delete_network);
                    btn_delete.setOnClickListener(view1 -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Delete " + network.getName());
                        builder.setMessage("You are about to delete your network. Are you sur about that ? ");
                        builder.setPositiveButton("Yes", (dialogInterface, i13) -> {
                            db.deleteNetwork(network.getId());
                            NetworkFragment networkFragment = new NetworkFragment();
                            FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                            transaction.remove(this);
                            transaction.replace(R.id.linearLayout_empty, networkFragment).addToBackStack(null).commit();
                        });
                        builder.setNegativeButton("No", (dialogInterface, i14) -> dialogInterface.cancel());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                    });
                }
                else {
                    btn_delete.setText(R.string.leave_network);
                    btn_delete.setOnClickListener(view1 -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Leave " + network.getName());
                        builder.setMessage("You are about to leave " + network.getName() + " .\nAre you sur about that ? ");
                        builder.setPositiveButton("Yes", (dialogInterface, i13) -> {
                            network.removeMember(Integer.toString(MainActivity.getUser().getId()));
                            db.updateNetwork(network);
                            NetworkFragment networkFragment = new NetworkFragment();
                            FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                            transaction.remove(this);
                            transaction.replace(R.id.linearLayout_empty, networkFragment).addToBackStack(null).commit();
                        });
                        builder.setNegativeButton("No", (dialogInterface, i14) -> dialogInterface.cancel());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                    });
                }

            }
            else {
                closeMenu();
            }
        });
        TextView description = root.findViewById(R.id.actualNetwork_description);
        description.setText(network.getDescription());


        this.btnPost = root.findViewById(R.id.fragment_actualNetwork_BtnPost);
        btnPost.setOnClickListener(view -> postMsg());


        System.out.println(posts);

        return root;
    }

    public void loadPost(){
        ArrayList<Post> arrayList = db.loadPost(network.getId());
        for(int i=0; i<arrayList.size();++i){
            Post post = arrayList.get(i);
            User uAuthor = db.findUserById(post.getAuthor_id());

            LayoutInflater newView = (LayoutInflater) Objects.requireNonNull(this.getContext()).getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert newView != null;
            @SuppressLint("InflateParams") View v = newView.inflate(R.layout.layout_posts, null);

            TextView thePost = v.findViewById(R.id.post_actualPost);
            TextView author = v.findViewById(R.id.post_author);
            TextView date = v.findViewById(R.id.post_date);
            ImageView authorsProfilPicture = v.findViewById(R.id.post_pictureAuthor);


            authorsProfilPicture.setImageBitmap(db.getProfilPicture(uAuthor.getId()));
            thePost.setText(post.getData());
            author.setText(uAuthor.getPseudo());
            date.setText(post.getDate());

            posts.addView(v, 0, new ViewGroup.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)));
        }

    }

    public void postMsg(){
        msgToPost = MainActivity.getScrollView().findViewById(R.id.fragment_actualNetwork_msgToPost);
        String sMsgToPost = msgToPost.getText().toString();
        if(sMsgToPost.matches("")){
            Toast.makeText(MainActivity.getScrollView().getContext(), "At least, write something please !", Toast.LENGTH_SHORT).show();
        }
        else {

            LayoutInflater newView = (LayoutInflater) Objects.requireNonNull(this.getContext()).getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert newView != null;
            @SuppressLint("InflateParams") View v = newView.inflate(R.layout.layout_posts, null);

            TextView thePost = v.findViewById(R.id.post_actualPost);
            TextView author = v.findViewById(R.id.post_author);
            TextView date = v.findViewById(R.id.post_date);
            ImageView authorsProfilPicture = v.findViewById(R.id.post_pictureAuthor);


            authorsProfilPicture.setImageBitmap(db.getProfilPicture(MainActivity.getUser().getId()));

            thePost.setText(msgToPost.getText().toString());
            author.setText(MainActivity.getUser().getPseudo()); // need to change w/ actual author.
            @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            date.setText(df.format(Calendar.getInstance().getTime()));


            posts.addView(v, 0, new ViewGroup.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)));

            // add to DB;
            Post post = new Post(MainActivity.getUser().getId(),network.getId(),df.format(Calendar.getInstance().getTime()),msgToPost.getText().toString());
            db.addPost(post);
            msgToPost.setText(null);
        }




    }

    public void deletePost(int idPost){
        db.deleteNetwork(idPost);
        ActualNetworkFragment actualNetworkFragment = new ActualNetworkFragment(network.getName());
        FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.remove(this);
        transaction.replace(R.id.linearLayout_empty, actualNetworkFragment).addToBackStack(null).commit();

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
