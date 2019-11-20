package com.example.wemood.Fragments;
/**
 * @author Boyuan Dong
 *
 * @version 1.0
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.wemood.R;
/**
 * Class name: RequestFragmentDialog
 *
 * Version 1.0
 *
 * Date: November 7, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */
public class RequestFragmentDialog extends DialogFragment {
    private TextView FriendReqestTitle;
    private TextView RequestPermission;
    private View message;

    /**
     * Constructor
     * @param view
     */
    public RequestFragmentDialog(View view) {
        message = view;
    }

    // Constructor
    public RequestFragmentDialog() {
        //Empty constructor
    }

    /**
     * Will Create a view of the RequestFragmentDialog
     * so that user can choose to accept the friend request or decline this request message.
     * @param savedInstanceState
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.friend_request_dialog, null);
        RequestPermission = view.findViewById(R.id.friend_request_permission_text);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Friend Request")
                .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        message.setBackgroundColor(Color.GRAY);
                    }
                })
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        message.setBackgroundColor(Color.GREEN);
                    }
                }).create();

    }

}