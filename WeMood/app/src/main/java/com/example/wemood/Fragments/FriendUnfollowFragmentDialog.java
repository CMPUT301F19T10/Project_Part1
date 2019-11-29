package com.example.wemood.Fragments;
/**
 * @author Alpha Hou
 *
 * @version 1.0
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.wemood.R;

/**
 * Class name: FriendUnfollowFragmentDialog
 *
 * Version 1.0
 *
 * Date: November 7, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */

public class FriendUnfollowFragmentDialog extends DialogFragment {

    private TextView information;
    private OnFragmentInteractionListener listener;
    private String message;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnFragmentInteractionListener)context;
    }

    public interface OnFragmentInteractionListener {
        public void UnfollowRequest();

    }


    // Constructor
    public FriendUnfollowFragmentDialog() {
        //Empty constructor
    }

    /**
     * Create the UnfollowFragment Dialog for the already exist friend of the user choose to unfollow this friend
     * @param savedInstanceState
     * @return
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
