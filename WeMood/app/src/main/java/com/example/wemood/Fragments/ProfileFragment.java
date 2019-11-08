package com.example.wemood.Fragments;

/**
 * @author Zuhao Yang
 *
 * @version 1.0
 */

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.wemood.LogSignInActivity;
import com.example.wemood.R;
import com.example.wemood.User;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static android.content.ContentValues.TAG;

/**
 * Class name: ProfileFragment
 *
 * Version 1.0
 *
 * Date: November 4, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */

public class ProfileFragment extends Fragment {

    // Other class attributes are also defined here
    private String userName;
    private String userID;
    private String email;
    private String phone;
    private String newPhone;

    private int numFollowers = 0;
    private int numFollowing = 0;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private CollectionReference collectionReference;
    private DocumentReference documentReference;

    private View rootView;
    private ImageView figureView;
    private TextView moodsView;
    private TextView followersView;
    private TextView followingView;
    private TextView userNameView;
    private TextView userIDView ;
    private TextView emailView;
    private TextView phoneView;
    private EditText editPhoneView;
    private Button historyButton;
    private RadioButton logoutButton;

    /**
     * Required empty public constructor
     */
    public ProfileFragment() {}

    /**
     * Constructor
     * @return profile fragment
     */
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    /**
     * Initialize the fragment
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Inflate the layout for this fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return the root view of this fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Create the references of views and buttons
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        figureView = rootView.findViewById(R.id.figure);
        moodsView = rootView.findViewById(R.id.moods);
        followersView = rootView.findViewById(R.id.followers);
        followingView = rootView.findViewById(R.id.following);
        userNameView = rootView.findViewById(R.id.username);
        userIDView = rootView.findViewById(R.id.userID);
        emailView = rootView.findViewById(R.id.email);
        phoneView = rootView.findViewById(R.id.phone);
        editPhoneView = rootView.findViewById(R.id.editPhone);
        historyButton = rootView.findViewById(R.id.history);
        logoutButton = rootView.findViewById(R.id.logout);

        // Display personal information
        displayInfo(userNameView, userIDView, emailView, phoneView);

        // Update Phone Number
        updatePhoneNumber(phoneView, editPhoneView);

        // Update Moods Number
        updateMoods(moodsView);

        // Update Followers Number
        updateFollowers(numFollowers);

        // Update Following Number
        updateFollowing(numFollowing);

        // Log Out
        logout(logoutButton);

        return rootView;
    }

    /**
     * Get current user
     * @return the current user
     */
    public FirebaseUser getUser() {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        return user;
    }

    /**
     * Get database
     * @return the database instance
     */
    public FirebaseFirestore getDatabase() {
        db = FirebaseFirestore.getInstance();

        return db;
    }

    /**
     * Display personal information
     * (username, userID, email, phone number, etc.)
     * @param userNameView
     * @param userIDView
     * @param emailView
     * @param phoneView
     */
    public void displayInfo(TextView userNameView, TextView userIDView, TextView emailView, final TextView phoneView) {

        // Get database and current user
        getDatabase();
        user = getUser();

        // Get and display username
        userName = user.getDisplayName();
        userNameView.setText(userName);

        // Get and display userID
        userID = user.getUid();
        userIDView.setText("User ID: " + userID);

        // Get and display email
        email = user.getEmail();
        emailView.setText("Email: " + email);

        // Get document reference
        documentReference = db.collection("Users")
                .document(userName);

        // Get and display phone from current document
        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        phone = user.getPhone();
                        phoneView.setText("Phone No.: " + phone);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, e.toString());
            }
        });
    }

    /**
     * Update Phone Number then
     * display the latest Phone Number
     * @param phoneView
     * @param editPhoneView
     */
    public void updatePhoneNumber(final TextView phoneView, final EditText editPhoneView) {
        // Get database and current user
        getDatabase();
        user = getUser();

        // Get username for later reference
        userName = user.getDisplayName();

        // Get document reference
        documentReference = db.collection("Users")
                .document(userName);

        editPhoneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPhone = editPhoneView.getText().toString();
                // Check the validity
                // Should not be empty and the length should be less than 10
                if (TextUtils.isEmpty(newPhone) || !(newPhone.length() < 10)) {
                    editPhoneView.setError("Invalid Phone!");
                } else {
                    editPhoneView.setError(null);
                    // Update current phone number
                    documentReference
                            .update("phone", newPhone)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot successfully updated!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error updating document", e);
                                }
                            });
                    // Get and display new phone number
                    documentReference.get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    User user = documentSnapshot.toObject(User.class);
                                    phone = user.getPhone();
                                    phoneView.setText("New Phone No.: " + phone);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, e.toString());
                        }
                    });
                 }

            }
        });
    }

    /**
     * Update Moods Number
     * @param moodsView
     */
    public void updateMoods(final TextView moodsView) {
        // Get database and current user
        getDatabase();
        user = getUser();

        // Get username for later reference
        userName = user.getDisplayName();

        // Get collection reference
        collectionReference = db.collection("Users")
                .document(userName)
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
                            moodsView.setText("Moods\n" + String.valueOf(numMoods));
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    /**
     * Update Followers Number
     * @param numFollowers
     */
    public void updateFollowers(int numFollowers) {
        // Display latest followers number
        followersView.setText("Followers\n" + String.valueOf(numFollowers));
    }

    /**
     * Update Following Number
     * @param numFollowing
     */
    public void updateFollowing(int numFollowing) {
        // Display latest following number
        followingView.setText("Following\n" + String.valueOf(numFollowing));
    }

    /**
     * Log out from the current account
     * @param logoutButton
     */
    public void logout(RadioButton logoutButton) {
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Log out i.e. Go back to log/sign in activity
                Intent intent = new Intent(getActivity(), LogSignInActivity.class);
                startActivity(intent);
            }
        });
    }

}
