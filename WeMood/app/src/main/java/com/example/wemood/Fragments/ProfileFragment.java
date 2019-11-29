package com.example.wemood.Fragments;

/**
 * @author Zuhao Yang
 *
 * @version 2.0
 */

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.wemood.LogSignInActivity;
import com.example.wemood.MoodHistory;
import com.example.wemood.R;
import com.example.wemood.User;

import com.google.android.gms.tasks.Continuation;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

/**
 * Class name: ProfileFragment
 *
 * Version 2.0
 *
 * Date: November 20, 2019
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

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private CollectionReference collectionReference;
    private DocumentReference documentReference;

    private View rootView;
    private ImageView figureView;
    private TextView moodsView;
    private TextView followingView;
    private TextView userNameView;
    private TextView userIDView ;
    private TextView emailView;
    private TextView phoneView;
    private EditText editPhoneView;
    private ImageButton cameraButton;
    private Button historyButton;
    private RadioButton logoutButton;

    private FirebaseStorage storage;
    private StorageReference folder;
    private StorageReference Image;
    private Uri imageUri;
    private String downloadUri;
    private static final int PICK_IMAGE = 100;

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
     * Get storage
     * @return the storage instance
     */
    public FirebaseStorage getStorage() {
        storage = FirebaseStorage.getInstance();

        return storage;
    }

    /**
     * Initialize the fragment
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get database and current user
        db = getDatabase();
        user = getUser();

        // Get username for later reference
        userName = user.getDisplayName();

        // Get document reference
        documentReference = db.collection("Users")
                .document(userName);

        // Get collection reference
        collectionReference = db.collection("Users")
                .document(userName)
                .collection("MoodList");

        // Get storage
        storage = getStorage();
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
        cameraButton = rootView.findViewById(R.id.camera);
        moodsView = rootView.findViewById(R.id.moods);
        followingView = rootView.findViewById(R.id.following);
        userNameView = rootView.findViewById(R.id.username);
        userIDView = rootView.findViewById(R.id.userID);
        emailView = rootView.findViewById(R.id.email);
        phoneView = rootView.findViewById(R.id.phone);
        editPhoneView = rootView.findViewById(R.id.editPhone);
        historyButton = rootView.findViewById(R.id.history);
        logoutButton = rootView.findViewById(R.id.logout);

        // Update Figure
        updateFigure();

        // Update Moods Number
        updateMoods();

        // Update Following Number
        updateFollowing();

        // Display personal information
        displayInfo();

        // Update Phone Number
        updatePhoneNumber();

        // Go to My Mood History
        goHistory();

        // Log Out
        logout();

        return rootView;
    }

    /**
     * Update figure
     */
    public void updateFigure() {
        // Get folder
        folder = storage.getReference().child("ProfileFolder");

        // Click on figureView to choose a photo from gallery
        figureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });

        // Click on cameraButton to choose a photo from gallery
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });
    }

    /**
     * Get and show the selected image then upload it
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                imageUri = data.getData();

                // Add figure to storage if it is not null
                if (imageUri != null) {
                    Image = folder.child(userName);
                    Image.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return Image.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                downloadUri = task.getResult().toString();

                                // Get database and current user
                                db = getDatabase();
                                user = getUser();

                                // Get username for later reference
                                userName = user.getDisplayName();

                                // Get document reference
                                documentReference = db.collection("Users")
                                        .document(userName);

                                // Update figure online after edition
                                documentReference
                                        .update("figure", downloadUri)
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

                                // Update figure locally after edition
                                documentReference
                                        .get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                User user = documentSnapshot.toObject(User.class);
                                                figureView.setBackgroundColor(Color.rgb(0,0,0));
                                                Picasso.get().load(user.getFigure()).into(figureView);
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
            }
        }
    }

    /**
     * Update Moods Number
     */
    public void updateMoods() {
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

    /**
     * Update Following Number
     */
    public void updateFollowing() {
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
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, e.toString());
            }
        });
    }

    /**
     * Display personal information
     * (username, userID, email, phone number, etc.)
     */
    public void displayInfo() {
        // Display username
        userNameView.setText(userName);

        // Get and display userID
        userID = user.getUid();
        userIDView.setText("User ID: " + userID);

        // Get and display email
        email = user.getEmail();
        emailView.setText("Email: " + email);

        // Get and display phone from current document
        documentReference
                .get()
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

        // Get and display figure at the beginning
        documentReference
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        if (user.getFigure() != null) {
                            figureView.setBackgroundColor(Color.rgb(0,0,0));
                            Picasso.get().load(user.getFigure()).into(figureView);
                        } else {
                            figureView.setImageResource(R.drawable.default_figure);
                        }
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
     */
    public void updatePhoneNumber() {
        editPhoneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPhone = editPhoneView.getText().toString();

                // Check the validity
                // Should not be empty and the length should be less than 10
                if (TextUtils.isEmpty(newPhone)) {
                    editPhoneView.setError("Empty Phone Number!");
                } else if (newPhone.length() < 10) {
                    editPhoneView.setError("Less than 10 digits!");
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
                    documentReference
                            .get()
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
            }
        });
    }

    /**
     * Go to MoodHistory Activity
     */
    public void goHistory() {
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to MoodHistory Activity
                Intent intent = new Intent(getActivity(), MoodHistory.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Log out from the current account
     */
    public void logout() {
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Log out i.e. Go back to log/sign in activity
                Intent intent = new Intent(getActivity(), LogSignInActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Real-time update
     */
    @Override
    public void onResume() {
        super.onResume();

        // Delay the updating methods to get the latest reference
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 2500ms
                updateMoods();
                updateFollowing();
            }
        }, 2500);
    }

}
