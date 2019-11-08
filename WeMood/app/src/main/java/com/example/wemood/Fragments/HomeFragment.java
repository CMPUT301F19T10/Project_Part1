package com.example.wemood.Fragments;
/**
 * @author Boyuan Dong
 *
 * @version 1.0
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.wemood.FriendMoodList;
import com.example.wemood.Mood;
import com.example.wemood.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
/**
 * Class name: HomeFragment
 *
 * Version 1.0
 *
 * Date: November 7, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */

/**
 * Will connect to the firebase later
 */
public class HomeFragment extends Fragment {

    // firendmoodlist stored a listview of all friend's most recent moods
    ListView friendmoodList;
    ArrayAdapter<Mood> moodAdapter;
    ArrayList<Mood> moodDataList;

    /**
     * Constructor
     */
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Constructor
     * @return
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    /**
     * Initialize the HomeFragment
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Create a view of the HomeFragment to display friends' most recent moods.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        // The bell message button
        RadioButton BellButton = (RadioButton) rootView.findViewById(R.id.friend_request_bell);
        // Click the BellButton jump to the FrendRequestMessage Fragment page
        BellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FriendRequestMessageFragment friendRequestMessageFragment = new FriendRequestMessageFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, friendRequestMessageFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        friendmoodList = (ListView) rootView.findViewById(R.id.home_friend_moods);
        moodDataList = new ArrayList<>();
        //add some data to mooddatalist to test
        //set dateTime1

        Date dateTime1 = new Date(2019,9-1,9,12,30);
        //set dateTime2

        Date dateTime2 = new Date(2019,9-1,20,22,30);
        //set dateTime3

        Date dateTime3 = new Date(2019,8-1,27,10,28);
        //set dateTime4
        Date dateTime4 = new Date(2018,5-1,7,9,30);
        moodDataList.add(new Mood(dateTime1,"happy","I am happy","I am so happy today","With a crowd","U of A","Boyuan"));
        moodDataList.add(new Mood(dateTime2,"sad","I am sad","I am so sad today","Alone","Home","Zoey"));
        moodDataList.add(new Mood(dateTime3,"tired","I am tired","I am so tired today","With a person","Winsor Park","Anna"));
        moodDataList.add(new Mood(dateTime4,"happy","I am happy","I am very happy these days","With two persons","U of A","Alpha"));
        // Sort the datalist by datetime
        // newest mood should show on the top of the list
        Collections.sort(moodDataList, Collections.reverseOrder());
        moodAdapter = new FriendMoodList(getContext(), moodDataList);
        friendmoodList.setAdapter(moodAdapter);
        return rootView;
    }



}
