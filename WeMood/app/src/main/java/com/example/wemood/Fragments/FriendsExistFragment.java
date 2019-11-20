package com.example.wemood.Fragments;
/**
 * @author Alpha Hou
 *
 * @version 1.0
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.wemood.FriendMoodList;
import com.example.wemood.Mood;
import com.example.wemood.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
/**
 * Class name: FriendsExistFragment
 *
 * Version 1.0
 *
 * Date: November 7, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */

public class FriendsExistFragment extends Fragment{

    ListView friendmoodList;
    ArrayAdapter<Mood> moodAdapter;
    ArrayList<Mood> moodDataList;

    /**
     * Empty constructor
     */
    public FriendsExistFragment() {
        // Required empty public constructor
    }

    /**
     * Constructor
     * @return
     */
    public static FriendsExistFragment newInstance() {
        FriendsExistFragment fragment = new FriendsExistFragment();
        return fragment;
    }

    /**
     * Initialize the Fragment
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Create the view of the FriendExistFragment and will be able to unfollow the alrady exist friend by clcking "Unfollw" button.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.friend_exist_page, container, false);
//        TextView contentTv = rootView.findViewById(R.id.content_tv);
//        contentTv.setText(mContentText);

        friendmoodList = rootView.findViewById(R.id.mood_list);
        moodDataList = new ArrayList<>();

        Date dateTime2 = new Date(2019,9-1,20,22,30);

        moodDataList.add(new Mood(dateTime2,"sad","I am sad","I am so sad today","Alone","Home","Zoey"));
        Collections.sort(moodDataList, Collections.reverseOrder());
        moodAdapter = new FriendMoodList(getContext(), moodDataList);
        friendmoodList.setAdapter(moodAdapter);

        final Button backButton = rootView.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FriendsFragment friendsFragment = new FriendsFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, friendsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Button unfollowButton = rootView.findViewById(R.id.unfollow_button);
        unfollowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FriendUnfollowFragment().show(getFragmentManager(), "Unfollow Pressed");
            }
        });

        return rootView;
    }

    // Will implement later
//    @Override
//    public void onYesPressed(){
//
//    }
//
//    @Override
//    public void onNoPressed(){
//    }
}