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
 * Class name: FriendFollowFragment
 *
 * Version 1.0
 *
 * Date: November 7, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */

public class FriendFollowFragment extends DialogFragment {

    private TextView information;
    /**
     * Create a dialog to send a follow request to  a User.
     * @param savedInstanceState
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        View view = LayoutInflater.from(getActivity()).inflate(R.layout.friend_unfollow_fragment, null);

//        information = view.findViewById(R.id.unfollow_confirmation);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
//                .setView(view)
                .setTitle("Follow Request")
                .setMessage("Your request will be sent. You can follow the user after approval.")
                .setNeutralButton("Cancel", null)
                .setPositiveButton("Send", null).create();
    }

}