package com.example.wemood.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class FriendSearchNotExistDialog extends DialogFragment {

    /**
     * Will Create a view of the FriendSearchNotExistDialog
     * the system will inform the user if the name they searched does not exist.
     * @param savedInstanceState
     * @return
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
