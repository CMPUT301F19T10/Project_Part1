package com.example.wemood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wemood.Fragments.FriendUnfollowFragmentDialog;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;


public class FriendsExist  extends AppCompatActivity implements FriendUnfollowFragmentDialog.OnFragmentInteractionListener{

    ListView friendmoodList;
    ArrayAdapter<Mood> moodAdapter;
    ArrayList<Mood> moodDataList;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String userName;
    private FirebaseFirestore db;
    private CollectionReference collectionReference;

    private TextView userNameView;
    private String searchName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_exist_page);

        // Initialize FireBase Auth
        mAuth = FirebaseAuth.getInstance();
        // Initialize Database
        db = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();
        userName = user.getDisplayName();
        user = mAuth.getCurrentUser();

        userNameView = findViewById(R.id.friend_view);
        friendmoodList = findViewById(R.id.mood_list);

        moodDataList = new ArrayList<>();
        Intent intent=getIntent();
        searchName=intent.getStringExtra("searchName");


        Button backButton = findViewById(R.id.back_button);
        Button unfollowButton = findViewById(R.id.unfollow_button);
        setBackButton(backButton);
        setUnfollowButton(unfollowButton);

        setSearchName();
        getMostRecentMood(searchName);

    }


    public void getMostRecentMood(final String searchName){
        collectionReference = db.collection("Users")
                .document(searchName)
                .collection("MoodList");
        collectionReference
                .orderBy("datetime", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Mood mood = document.toObject(Mood.class);
                            mood.setUsername(searchName);
                            moodDataList.add(mood);
                        }
                        Collections.sort(moodDataList, Collections.reverseOrder());
                        moodAdapter = new FriendMoodList(getBaseContext(), moodDataList);
                        friendmoodList.setAdapter(moodAdapter);
                    }
                });
    }

    public void setSearchName(){
        // Get and display username
        userNameView.setText(searchName);
    }

    public void setBackButton(Button backButton){
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setUnfollowButton(Button unfollowButton){
        unfollowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FriendUnfollowFragmentDialog().show(getSupportFragmentManager(), "Unfollow Pressed");
            }
        });
    }

    @Override
    public void UnfollowRequest(){
        collectionReference = db.collection("Users");
        collectionReference.document(userName)
                .update("friendList", FieldValue.arrayRemove(searchName));
    }

}
