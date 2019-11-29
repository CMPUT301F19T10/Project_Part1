package com.example.wemood.Fragments;
/**
 * @author Boyuan Dong
 *
 * @version 2.0
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;
import com.example.wemood.FriendMoodList;
import com.example.wemood.FriendRequestMessageActivity;
import com.example.wemood.Mood;
import com.example.wemood.MoodDetailClicked;
import com.example.wemood.R;
import com.example.wemood.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Collections;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import static android.content.ContentValues.TAG;
/**
 * Class name: HomeFragment
 *
 * Version 2.0
 *
 * Date: November 7, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */

/**
 * Will connect to the FireBase later
 */
public class HomeFragment extends Fragment {

    // friendMoodList stored a ListView of all friend's most recent moods
    private ListView friendMoodList;
    private ArrayAdapter<Mood> moodAdapter;
    public  ArrayList<Mood> moodDataList;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private CollectionReference collectionReference;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String userName;

    /**
     * Constructor
     */
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Constructor
     * @return fragment
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    /**
     * Initialize the HomeFragment
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize FireBase Auth
        mAuth = FirebaseAuth.getInstance();
        // Initialize Database
        db = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();
        userName = user.getDisplayName();
        user = mAuth.getCurrentUser();
    }

    /**
     * Create a view of the HomeFragment to display friends' most recent moods.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return rootView
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        // The bell message button
        RadioButton BellButton = rootView.findViewById(R.id.friend_request_bell);
        // Click the BellButton jump to the FriendRequestMessage Fragment page
        BellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), FriendRequestMessageActivity.class);
                startActivity(i);
                (getActivity()).overridePendingTransition(0, 0);
            }
        });

        friendMoodList = rootView.findViewById(R.id.home_friend_moods);

        swipeRefreshLayout = rootView.findViewById(R.id.home_swipe_container);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                moodDataList = new ArrayList<>();
                getMoodList();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },2000);
            }
        });

        moodDataList = new ArrayList<>();
        getMoodList();
        return rootView;
    }


    /**
     * This function get user's friend list and then get each friend's most recent mood
     */
    public void getMoodList() {
        collectionReference = db.collection("Users");
        collectionReference.document(userName)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        ArrayList<String> friendList = user.getFriendList();
                        if (!friendList.isEmpty()) {
                            for (String friend: friendList) {
                                collectionReference.document(friend).get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                User friend = documentSnapshot.toObject(User.class);
                                                String friendName = friend.getUserName();
                                                getMostRecentMood(friendName);
                                            }
                                        });
                            }
                        } else {
                            Collections.sort(moodDataList, Collections.reverseOrder());
                            if (getContext() != null) {
                                moodAdapter = new FriendMoodList(getContext(), moodDataList);
                                friendMoodList.setAdapter(moodAdapter);
                            }
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
     * This method get each friend's most recent mood
     * @param friendUserName
     */
    public void getMostRecentMood(final String friendUserName) {
        collectionReference = db.collection("Users")
                .document(friendUserName)
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
                            mood.setUsername(friendUserName);
                            moodDataList.add(mood);
                        }
                        Collections.sort(moodDataList, Collections.reverseOrder());
                        if (getContext() != null) {
                            moodAdapter = new FriendMoodList(getContext(), moodDataList);
                            friendMoodList.setAdapter(moodAdapter);
                        }
                        friendMoodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(getActivity(), MoodDetailClicked.class);
                                Mood mood = moodDataList.get(i);
                                intent.putExtra("Mood", mood);
                                startActivity(intent);
                                (getActivity()).overridePendingTransition(0, 0);
                            }
                        });
                    }
                });
    }
}
