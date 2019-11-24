package com.example.wemood;
/**
 * @author Boyuan Dong
 *
 * @version 1.0
 */
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wemood.Fragments.RequestFragmentDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Class name: MoodDetailClicked
 *
 * Version 1.0
 *
 * Date: November 7, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */


public class MoodDetailClicked extends AppCompatActivity {
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private StorageReference image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_detail_clicked);
        View view =  findViewById(R.id.mood_detail_layout);

        Mood mood = (Mood) getIntent().getSerializableExtra("Mood");

        TextView FriendUername = findViewById(R.id.friend_mood_username);
        TextView FriendMoodExplanation = findViewById(R.id.friend_mood_explanation);
        TextView FriendMoodReason = findViewById(R.id.friend_mood_reason);
        TextView FriendMoodDate = findViewById(R.id.friend_mood_date);
        TextView FriendMoodTime = findViewById(R.id.friend_mood_time);
        TextView FriendMoodSocialSituation = findViewById(R.id.friend_mood_social_situation);
        TextView FriendMoodLocation = findViewById(R.id.friend_mood_location);
        final ImageView FriendMoodPhoto = view.findViewById(R.id.friend_mood_photo);
        ImageView FriendMoodState = findViewById(R.id.friend_mood_state);


        // set mood properties shown in the list by call mood getters
        FriendUername.setText(mood.getUsername());
        FriendMoodExplanation.setText(mood.getExplanation());
        FriendMoodReason.setText(mood.getComment());
        // Get the new format of the date and time and set textview
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        FriendMoodDate.setText(dateFormat.format(mood.getDatetime().getTime()));
        FriendMoodTime.setText(timeFormat.format(mood.getDatetime().getTime()));
        FriendMoodSocialSituation.setText(mood.getSocialSituation());
        FriendMoodLocation.setText(mood.getLocation());


        // Get and display figure
        // Get storage and image
        storage = getStorage();
        image = storage.getReference().child("ImageFolder/" + mood.getUsername() + "/" + mood.getDatetime().toString());
        image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(final Uri uri) {
                Picasso.get().load(uri).into(FriendMoodPhoto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                FriendMoodPhoto.setImageResource(R.drawable.default_photo);
            }
        });

        // Classify the moods by different mood states
        // set the background color by different mood states
        String emotionalState = mood.getEmotionalState();
        switch (emotionalState){
            case "happy":
                view.setBackgroundColor(Color.rgb(253,91,91));
                view.getBackground().setAlpha(200);
                Bitmap bMap = BitmapFactory.decodeResource(view.getResources(), R.drawable.happy_marker);
                Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 100, 100, true);
                FriendMoodState.setImageBitmap(bMapScaled);
                break;
            case "sad":
                view.setBackgroundColor(Color.rgb(106,106,240));
                view.getBackground().setAlpha(200);
                Bitmap bMap1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.sad_marker);
                Bitmap bMapScaled1 = Bitmap.createScaledBitmap(bMap1, 100, 100, true);
                FriendMoodState.setImageBitmap(bMapScaled1);
                break;
            case "tired":
                view.setBackgroundColor(Color.rgb(121,121,121));
                view.getBackground().setAlpha(200);
                Bitmap bMap2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.tired_marker);
                Bitmap bMapScaled2 = Bitmap.createScaledBitmap(bMap2, 100, 100, true);
                FriendMoodState.setImageBitmap(bMapScaled2);
                break;
            case "angry":
                view.setBackgroundColor(Color.rgb(250,233,90));
                view.getBackground().setAlpha(200);
                Bitmap bMap3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.angry_marker);
                Bitmap bMapScaled3 = Bitmap.createScaledBitmap(bMap3, 100, 100, true);
                FriendMoodState.setImageBitmap(bMapScaled3);
                break;
            case "lonely":
                view.setBackgroundColor(Color.rgb(255,152,0));
                view.getBackground().setAlpha(200);
                Bitmap bMap4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.tired_marker);
                Bitmap bMapScaled4 = Bitmap.createScaledBitmap(bMap4, 100, 100, true);
                FriendMoodState.setImageBitmap(bMapScaled4);
                break;

        }
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


}
