package com.example.wemood.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.wemood.R;

public class FriendFollowFragment extends DialogFragment {

    private TextView information;
//    private OnFragmentInteractionListener listener;

//    public interface OnFragmentInteractionListener{
////        void onYesPressed();
////        void onNoPressed();
//    }
//
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
//        View view = LayoutInflater.from(getActivity()).inflate(R.layout.friend_unfollow_fragment, null);

//        information = view.findViewById(R.id.unfollow_confirmation);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
//                .setView(view)
                .setTitle("Follow Request")
                .setMessage("Your request will be sent. You can follow the user after approval.")
                .setNeutralButton("Cancel", null)
                .setPositiveButton("Send", null).create();




//                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        listener.onYesPressed(currentRide);
//                    }}).create();
    }

}