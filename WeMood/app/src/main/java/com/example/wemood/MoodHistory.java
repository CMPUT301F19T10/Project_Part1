package com.example.wemood;

/**
 * @author Zuhao Yang
 *
 * @version 1.0
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Class name: MoodHistory
 *
 * Version 1.0
 *
 * Date: November 20, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */

public class MoodHistory extends AppCompatActivity {

    private ImageButton backButton;
    private Button happyButton;
    private Button sadButton;
    private Button lonelyButton;
    private Button angryButton;
    private Button tiredButton;

    /**
     * Initialize the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_history);

        // Create references of the buttons
        backButton = findViewById(R.id.back);
        happyButton = findViewById(R.id.happy);
        sadButton = findViewById(R.id.sad);
        lonelyButton = findViewById(R.id.lonely);
        angryButton = findViewById(R.id.angry);
        tiredButton = findViewById(R.id.tired);

        // Go back to ProfileFragment Fragment
        backProfile();

        // Go to HappyMood Activity
        goHappy();

        // Go to SadMood Activity
        goSad();

        // Go to LonelyMood Activity
        goLonely();

        // Go to AngryMood Activity
        goAngry();

        // Go to TiredMood Activity
        goTired();
    }

    /**
     * Go back to ProfileFragment Fragment
     */
    public void backProfile() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go back to ProfileFragment Fragment
                finish();
            }
        });
    }

    /**
     * Go to HappyMood Activity
     */
    public void goHappy() {
        happyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to HappyMood Activity
                Intent intent = new Intent(MoodHistory.this, HappyMood.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Go to SadMood Activity
     */
    public void goSad() {
        sadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to SadMood Activity
                Intent intent = new Intent(MoodHistory.this, SadMood.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Go to LonelyMood Activity
     */
    public void goLonely() {
        lonelyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to LonelyMood Activity
                Intent intent = new Intent(MoodHistory.this, LonelyMood.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Go to AngryMood Activity
     */
    public void goAngry() {
        angryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to AngryMood Activity
                Intent intent = new Intent(MoodHistory.this, AngryMood.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Go to TiredMood Activity
     */
    public void goTired() {
        tiredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to TiredMood Activity
                Intent intent = new Intent(MoodHistory.this, TiredMood.class);
                startActivity(intent);
            }
        });
    }

}
