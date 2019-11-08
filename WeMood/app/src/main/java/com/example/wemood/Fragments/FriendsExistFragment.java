package com.example.wemood.Fragments;

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
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class FriendsExistFragment extends Fragment{

    ListView friendmoodList;
    ArrayAdapter<Mood> moodAdapter;
    ArrayList<Mood> moodDataList;

    public FriendsExistFragment() {
        // Required empty public constructor
    }

    public static FriendsExistFragment newInstance() {
        FriendsExistFragment fragment = new FriendsExistFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.friend_exist_page, container, false);
//        TextView contentTv = rootView.findViewById(R.id.content_tv);
//        contentTv.setText(mContentText);

        friendmoodList = rootView.findViewById(R.id.mood_list);
        moodDataList = new ArrayList<>();
//        Calendar dateTime1 = Calendar.getInstance();
//        dateTime1.set(Calendar.YEAR,2019);
//        dateTime1.set(Calendar.MONTH,9-1);
//        dateTime1.set(Calendar.DAY_OF_MONTH,9);
//        dateTime1.set(Calendar.HOUR_OF_DAY,12);
//        dateTime1.set(Calendar.MINUTE,30);
        Date dateTime1 = new Date(2019,9-1,9,12,30);
        //set dateTime2
//        Calendar dateTime2 = Calendar.getInstance();
//        dateTime2.set(Calendar.YEAR,2019);
//        dateTime2.set(Calendar.MONTH,9-1);
//        dateTime2.set(Calendar.DAY_OF_MONTH,20);
//        dateTime2.set(Calendar.HOUR_OF_DAY,22);
//        dateTime2.set(Calendar.MINUTE,30);
        Date dateTime2 = new Date(2019,9-1,20,22,30);
        //set dateTime3
//        Calendar dateTime3 = Calendar.getInstance();
//        dateTime3.set(Calendar.YEAR,2019);
//        dateTime3.set(Calendar.MONTH,8-1);
//        dateTime3.set(Calendar.DAY_OF_MONTH,27);
//        dateTime3.set(Calendar.HOUR_OF_DAY,10);
//        dateTime3.set(Calendar.MINUTE,28);
        Date dateTime3 = new Date(2019,8-1,27,10,28);
        //set dateTime4
//        Calendar dateTime4 = Calendar.getInstance();
//        dateTime4.set(Calendar.YEAR,2018);
//        dateTime4.set(Calendar.MONTH,5-1);
//        dateTime4.set(Calendar.DAY_OF_MONTH,7);
//        dateTime4.set(Calendar.HOUR_OF_DAY,9);
//        dateTime4.set(Calendar.MINUTE,30);
        Date dateTime4 = new Date(2018,5-1,7,9,30);
//        moodDataList.add(new Mood(dateTime1,"happy","I am happy","I am so happy today","With a crowd","U of A","Zoey"));
        moodDataList.add(new Mood(dateTime2,"sad","I am sad","I am so sad today","Alone","Home","Zoey"));
//        moodDataList.add(new Mood(dateTime3,"tired","I am tired","I am so tired today","With a person","Winsor Park","Zoey"));
//        moodDataList.add(new Mood(dateTime4,"happy","I am happy","I am very happy these days","With two persons","U of A","Zoey"));
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

//    @Override
//    public void onYesPressed(){
//
//    }
//
//    @Override
//    public void onNoPressed(){
//    }
}