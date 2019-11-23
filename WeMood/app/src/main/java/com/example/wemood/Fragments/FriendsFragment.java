package com.example.wemood.Fragments;
/**
 * @author Alpha Hou
 *
 * @version 1.0
 */
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.wemood.FriendNameList;
import com.example.wemood.FriendsExist;
import com.example.wemood.FriendsNotExist;
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

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Class name: FriendsFragment
 *
 * Version 1.0
 *
 * Date: November 7, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */

/**
 * Will connect to the firebase later
 */
public class FriendsFragment extends Fragment {
    ListView friendList;
    ArrayAdapter<String> friendAdapter;
    ArrayList<String> friendDataList;
    EditText searchTextView;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private CollectionReference collectionReference;
    private DocumentReference documentReference;

    private String userName;
    private Button searchButton;


    /**
     * Constructor
     */
    public FriendsFragment() {
        // Required empty public constructor
    }

    /**
     * Constructor
     * @return
     */
    public static FriendsFragment newInstance() {
        FriendsFragment fragment = new FriendsFragment();
        return fragment;
    }

    /**
     * Initialize the FriendsFragment
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
     * Create the view of the FriendsFragment. Will display a list of friends' username and will be
     * able to search a user by his/her username and click the "Search" button. Will be able to see the
     * friend's information by clicking the username on the list.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);

        searchTextView = (EditText) rootView.findViewById(R.id.friend_input);
        searchButton = rootView.findViewById(R.id.friend_search);

        friendList = (ListView) rootView.findViewById(R.id.friend_list_content);

        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        friendDataList = new ArrayList<>();

        getFriendNameList();

        setSearchButton(searchButton);
    }

    public void setSearchButton(Button searchButton){

        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String searchName = searchTextView.getText().toString();
                collectionReference = db.collection("Users");
                collectionReference.document(searchName).get()/////////////////////////////////////////////
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    if(documentSnapshot.getData() != null){
                                        collectionReference = db.collection("Users");
                                        collectionReference.document(userName).get()/////////////////////////////////////////////
                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        if(documentSnapshot!=null) {
                                                            User user = documentSnapshot.toObject(User.class);
                                                            ArrayList<String> friendArrayList = user.getFriendList();
                                                            friendDataList.addAll(friendArrayList);
                                                            System.out.println(friendDataList);
                                                        }
                                                        friendAdapter = new FriendNameList(getContext(), friendDataList);
                                                        friendList.setAdapter(friendAdapter);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                                                        Log.d(TAG, e.toString());
                                                    }
                                                });
                                        if(friendDataList.contains(searchName)){
                                            Intent i = new Intent(getActivity().getApplicationContext(), FriendsExist.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("searchName", searchName);
                                            i.putExtras(bundle);
                                            getActivity().startActivity(i);
                                        }
                                        else{
                                            Log.d(TAG, "Cached document data: " + documentSnapshot.getData());
                                            Intent i = new Intent(getActivity().getApplicationContext(), FriendsNotExist.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("searchName", searchName);
                                            i.putExtras(bundle);
                                            getActivity().startActivity(i);
                                        }
                                    }
                                    else{
                                        Log.d(TAG, "Cached get failed: ", task.getException());
                                        new FriendSearchNotExistDialog().show(getFragmentManager(), "Not Exist");
                                    }
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, e.toString());
                            }
                        });
            }
        });
    }

    public void getFriendNameList(){
        collectionReference = db.collection("Users");
        collectionReference.document(userName).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot!=null) {
                            User user = documentSnapshot.toObject(User.class);
                            ArrayList<String> friendArrayList = user.getFriendList();
                            friendDataList.addAll(friendArrayList);
                        }
                        friendAdapter = new FriendNameList(getContext(), friendDataList);
                        friendList.setAdapter(friendAdapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }





}