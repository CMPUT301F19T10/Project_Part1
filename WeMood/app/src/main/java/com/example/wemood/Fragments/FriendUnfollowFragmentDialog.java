package com.example.wemood.Fragments;
/**
 * @author Alpha Hou
 *
 * @version 2.0
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * Class name: FriendUnfollowFragmentDialog
 *
 * Version 2.0
 *
 * Date: November 7, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */

public class FriendUnfollowFragmentDialog extends DialogFragment {

    private OnFragmentInteractionListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnFragmentInteractionListener)context;
    }

    public interface OnFragmentInteractionListener {
        void UnfollowRequest();

    }


    // Constructor
    public FriendUnfollowFragmentDialog() {
        //Empty constructor
    }

    /**
     * Create the UnfollowFragment Dialog for the already exist friend of the user choose to unfollow this friend
     * @param savedInstanceState
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setTitle("Unfollow Confirmation")
                .setMessage("Are you sure you want to unfollow this friend?")
                .setNeutralButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.UnfollowRequest();
                    }
                })
                .create();
    }
}
