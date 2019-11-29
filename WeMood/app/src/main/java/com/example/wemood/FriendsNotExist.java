package com.example.wemood;

/**
 * @author Alpha Hou
 *
 * @version 1.0
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.wemood.Fragments.FriendFollowFragmentDialog;
import com.example.wemood.Fragments.FriendUnfollowFragmentDialog;
import com.example.wemood.Fragments.FriendsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
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

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private CollectionReference collectionReference;
    private DocumentReference documentReference;
    private FirebaseStorage storage;
    private StorageReference image;

    private ImageView figureView;
    private TextView userNameView;
    private TextView moodsView;
    private TextView followingView;

    /**
     * Get storage
     * @return the storage instance
     */
    public FirebaseStorage getStorage() {
        storage = FirebaseStorage.getInstance();
        return storage;
    }

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

        storage = getStorage();

        figureView = findViewById(R.id.friend_photo);
        moodsView = findViewById(R.id.mood_num);
        followingView = findViewById(R.id.following_num);

        Button cancelButton = findViewById(R.id.cancel_button);
        Button followButton = findViewById(R.id.follow_button);

        getPhoto();
        updateMoods();
        updateFollowing();
        setSearchName();
        setCancelButton(cancelButton);
        setFollowButton(followButton);
    }

    public void getPhoto(){
        // Get and display figure
        // Get storage and image
        image = storage.getReference().child("ProfileFolder/" + searchName);
        image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(final Uri uri) {
                Picasso.get().load(uri).fit().into(figureView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                figureView.setImageResource(R.drawable.default_figure);
            }
        });
    }

    public void updateMoods() {
        collectionReference = db.collection("Users")
                .document(searchName)
                .collection("MoodList");
        collectionReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Count the number of moods
                            int numMoods = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Increment by 1 per iteration
                                numMoods += 1;
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            String moodsDisplay = "Moods\n%d";
                            moodsDisplay = String.format(moodsDisplay, numMoods);
                            moodsView.setText(moodsDisplay);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void updateFollowing() {
        // Get collection reference
        documentReference = db.collection("Users")
                .document(searchName);
        documentReference
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        ArrayList<String> friendList = user.getFriendList();
                        int numFollowing = friendList.size();
                        String followingDisplay = "Following\n%d";
                        followingDisplay = String.format(followingDisplay, numFollowing);
                        followingView.setText(followingDisplay);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FriendsNotExist.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
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
                .update("waitFriendList", FieldValue.arrayUnion(userName));
    }
}
