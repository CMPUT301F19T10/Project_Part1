package com.example.wemood;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;

public class MyMoodList{
    private static final String TAG = "MyMoodList";
    private CollectionReference collectionReference;
    private static ArrayList<Mood> myMoods;
    private String userName;

    public MyMoodList() {
        myMoods = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        this.userName = user.getDisplayName();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        this.collectionReference = db.collection("MoodList");
    }

    public static ArrayList<Mood> getMyMoods() {
        return myMoods;
    }

    public void addMood(Mood mood){
        collectionReference.document(this.userName).set(mood);
    }

    public void removeMood(Mood mood){
    }
}
