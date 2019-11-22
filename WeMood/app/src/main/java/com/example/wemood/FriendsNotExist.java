package com.example.wemood;
/**
 * @author Alpha Hou
 *
 * @version 1.0
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.wemood.Fragments.FriendFollowFragmentDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * Class name: FriendsNotExist
 *
 * Version 1.0
 *
 * Date: November 7, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */
public class FriendsNotExist extends AppCompatActivity implements FriendFollowFragmentDialog.OnFragmentInteractionListener{
    private String userName;
    private String searchName;
    private int numFollowing = 0;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private CollectionReference collectionReference;

    private ImageView figureView;
    private TextView userNameView;
    private TextView moodsView;
    private TextView followingView;

    /**
     * Initialize the FriendNotExistFragment
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_locked_page);
        Intent intent=getIntent();
        userNameView = findViewById(R.id.friend_view);
        searchName=intent.getStringExtra("searchName");
        // Initialize FireBase Auth
        mAuth = FirebaseAuth.getInstance();
        // Initialize Database
        db = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();
        userName = user.getDisplayName();

        Button cancelButton = findViewById(R.id.cancel_button);
        Button followButton = findViewById(R.id.follow_button);

        setCancelButton(cancelButton);
        setFollowButton(followButton);
        setSearchName();
    }

    public void setSearchName(){
        // Get and display username
        userNameView.setText(searchName);
    }

    public void setCancelButton(Button cancelButton){
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setFollowButton(Button followButton){
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FriendFollowFragmentDialog().show(getSupportFragmentManager(), "Follow Pressed");
            }
        });
    }

    @Override
    public void FollowRequest(){
        collectionReference = db.collection("Users");
        collectionReference.document(searchName)
                .update("waitFriendList",FieldValue.arrayUnion(userName));
    }
}