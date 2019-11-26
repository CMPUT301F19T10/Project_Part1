package com.example.wemood;
/**
 * @author Boyuan Dong
 *
 * @version 1.0
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
/**
 * Class name: FriendMoodList
 *
 * Version 1.0
 *
 * Date: November 7, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */

public class FriendMoodList extends ArrayAdapter<Mood> {
    private ArrayList<Mood> moods;
    private Context context;

    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private StorageReference image;

    /**
     * Constructor to get the context and list of most recent friend moods.
     * @param context
     * @param moods
     */
    public FriendMoodList(Context context, ArrayList<Mood> moods) {
        super(context,0,moods);
        this.moods = moods;
        this.context = context;
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
     * Get the view of the friendmoodlist. Will display the detail information of moods.
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.home_content,parent,false);
        }

        // for each mood get the textview and images by id's
        Mood mood = moods.get(position);

        TextView FriendUername =view.findViewById(R.id.friend_mood_username);
        TextView FriendMoodExplanation = view.findViewById(R.id.friend_mood_explanation);
        TextView FriendMoodReason = view.findViewById(R.id.friend_mood_reason);
        TextView FriendMoodDate =view.findViewById(R.id.friend_mood_date);
        TextView FriendMoodTime =view.findViewById(R.id.friend_mood_time);
        TextView FriendMoodSocialSituation =view.findViewById(R.id.friend_mood_social_situation);
        TextView FriendMoodLocation =view.findViewById(R.id.friend_mood_location);
        final ImageView FriendMoodPhoto = view.findViewById(R.id.friend_mood_photo);
        ImageView FriendMoodState = view.findViewById(R.id.friend_mood_state);

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

        updateImage(mood, FriendMoodPhoto);

        // Classify the moods by different mood states
        // set the background color by different mood states
        String emotionalState = mood.getEmotionalState();
        switch (emotionalState) {
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
                Bitmap bMap4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.loney_marker);
                Bitmap bMapScaled4 = Bitmap.createScaledBitmap(bMap4, 100, 100, true);
                FriendMoodState.setImageBitmap(bMapScaled4);
                break;
        }

        return view;
    }

    // Get and display figure
    // Get storage and image
    public void updateImage(Mood mood, final ImageView FriendMoodPhoto) {
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
    }

}
