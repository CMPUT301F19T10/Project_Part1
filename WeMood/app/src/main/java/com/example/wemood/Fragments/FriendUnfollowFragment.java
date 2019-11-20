package com.example.wemood.Fragments;
/**
 * @author Alpha Hou
 *
 * @version 1.0
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * Class name: FriendUnfollowFragment
 *
 * Version 1.0
 *
 * Date: November 7, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */

public class FriendUnfollowFragment extends DialogFragment {

    private TextView information;

    /**
     * Create the UnfollowFragment Dialog for the already exist friend of the user choose to unfollow this friend
     * @param savedInstanceState
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        View view = LayoutInflater.from(getActivity()).inflate(R.layout.friend_follow_fragment, null);

//        information = view.findViewById(R.id.follow_request);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
//                .setView(view)
                .setTitle("Unfollow Confirmation")
                .setMessage("Are you sure you want to unfollow this friend?")
                .setNeutralButton("No", null)
                .setPositiveButton("Yes", null).create();
    }

}