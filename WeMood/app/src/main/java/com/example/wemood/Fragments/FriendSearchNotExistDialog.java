package com.example.wemood.Fragments;

/**
 * @author Alpha Hou
 *
 * @version 2.0
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * Class name: FriendSearchNotExistDialog
 *
 * Version 2.0
 *
 * Date: November 7, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */

public class FriendSearchNotExistDialog extends DialogFragment {

    /**
     * Will Create a view of the FriendSearchNotExistDialog
     * the system will inform the user if the name they searched does not exist.
     * @param savedInstanceState
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setTitle("Not Exist")
                .setMessage("The name you searched does not exist.")
                .setNeutralButton("OK", null).create();
    }
}
