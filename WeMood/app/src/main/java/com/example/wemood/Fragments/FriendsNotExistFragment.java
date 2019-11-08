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
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.wemood.R;

/**
 * Class name: FriendsNotExistFragment
 *
 * Version 1.0
 *
 * Date: November 7, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */
public class FriendsNotExistFragment extends Fragment{

    /**
     * Constructor
     */
    public FriendsNotExistFragment() {
        // Required empty public constructor
    }

    /**
     * Constructor
     * @return
     */
    public static FriendsNotExistFragment newInstance() {
        FriendsNotExistFragment fragment = new FriendsNotExistFragment();
        return fragment;
    }

    /**
     * Initialize the FriendNotExistFragment
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Create the view of the FriendNotExistFragment will lock moods history of this friend and will be able to
     * send the follow request message by clicking the button "Follow"
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.friend_locked_page, container, false);
//        TextView contentTv = rootView.findViewById(R.id.content_tv);
//        contentTv.setText(mContentText);

        final Button cancelButton = rootView.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FriendsFragment friendsFragment = new FriendsFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, friendsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Button followButton = rootView.findViewById(R.id.follow_button);
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showEditDialog(mModify);
                new FriendFollowFragment().show(getFragmentManager(), "Follow Pressed");
            }
        });

//        public void showEditDialog(View v) {
//            FriendFollowFragment = new FriendFollowFragment(this, R.style.AdInfoDialog, onClickListener);
//            FriendFollowFragment.show();
//        }

        return rootView;
    }
}