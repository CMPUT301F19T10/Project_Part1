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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
//        ImageView FriendMoodPhoto = view.findViewById(R.id.friend_mood_photo);
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
//        FriendMoodLocation.setText(mood.getLocation());
        //        FriendMoodState.setText(mood.getEmotionalState());


        // Classify the moods by different mood states
        // set the background color by different mood states
        String emotionalState = mood.getEmotionalState();
        if (emotionalState == "happy"){
            view.setBackgroundColor(Color.RED);
            Bitmap bMap = BitmapFactory.decodeResource(view.getResources(), R.drawable.happy);
            Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 100, 100, true);
            FriendMoodState.setImageBitmap(bMapScaled);
        }else if (emotionalState == "sad"){
            view.setBackgroundColor(Color.BLUE);
            Bitmap bMap = BitmapFactory.decodeResource(view.getResources(), R.drawable.sad);
            Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 100, 100, true);
            FriendMoodState.setImageBitmap(bMapScaled);
        }
        else if (emotionalState == "tired"){
            view.setBackgroundColor(Color.YELLOW);
            Bitmap bMap = BitmapFactory.decodeResource(view.getResources(), R.drawable.tired);
            Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 100, 100, true);
            FriendMoodState.setImageBitmap(bMapScaled);
        }

        return view;
    }


}
