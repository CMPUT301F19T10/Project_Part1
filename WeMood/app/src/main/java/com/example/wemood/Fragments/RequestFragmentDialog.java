package com.example.wemood.Fragments;

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

public class RequestFragmentDialog extends DialogFragment {
    private TextView FriendReqestTitle;
    private TextView RequestPermission;
    private View message;

    public RequestFragmentDialog(View view) {
        message = view;
    }

    public RequestFragmentDialog() {
        //Empty constructor
    }

//    private OnFragmentInteractionListener listener;
//
//    public interface OnFragmentInteractionListener {
//        void DeclineRequest(String message);
//        void AcceceptRequest(String message);
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener){
//            listener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

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