package com.example.wemood.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.wemood.R;

public class FriendUnfollowFragment extends DialogFragment {

    private TextView information;
//    private FriendFollowFragment.OnFragmentInteractionListener listener;

//    public interface OnFragmentInteractionListener{
////        void onYesPressed();
////        void onNoPressed();
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof FriendFollowFragment.OnFragmentInteractionListener){
//            listener = (FriendFollowFragment.OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

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