package com.example.wemood;
/**
 * @author Ziyi Ye
 *
 * @version 1.0
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.Date;

/**
 * Class name: AddMoodActivity
 *
 * Version 1.0
 *
 * Date: November 4, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */

public class AddMoodActivity extends AppCompatActivity{
    Mood mood;
    private StorageReference Folder;
    ImageView imageView;
    Uri imageUri;
    private static final int PICK_IMAGE = 100;
    String situationString, emotionString;
    private FirebaseFirestore db;
    String name;
    Date currentTime = Calendar.getInstance().getTime();


    /**
     * Initialize
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mood);

        db = FirebaseFirestore.getInstance();
        Folder = FirebaseStorage.getInstance().getReference().child("ImageFolder");
        final CollectionReference collectionReference = db.collection("Users");

        imageView = (ImageView) findViewById(R.id.imageView);
        Button choosePhoto = (Button) findViewById(R.id.choosePhoto);
        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });


        setSituationSpinner();
        setEmotionSpinner();

        /**
         * press the Add Mood button
         * put the mood to fireBase
         */
        Button Add = findViewById(R.id.add);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                EditText exp = findViewById(R.id.reason);
                String explanation = exp.getText().toString();
                EditText titl = findViewById(R.id.title);
                String title = titl.getText().toString();

                if (containsSpace(title)){
                    Toast.makeText(AddMoodActivity.this, "title has no more than 3 words", Toast.LENGTH_SHORT).show();
                }else{
                    name = user.getDisplayName();
                    DocumentReference docRef = db.collection("Users").document(name);
                    db = FirebaseFirestore.getInstance();

                    addMood(currentTime, emotionString, explanation, situationString, title, docRef);
                    //add image to storage if it is not null
                    if (imageUri != null){
                        StorageReference Image = Folder.child("image");
                        Image.putFile(imageUri);
                    }

                    finish();
                }


            }
        });
    }

    /**
     * add a mood to fireBase
     * @param currentTime
     * @param emotionString
     * @param explanation
     * @param situationString
     * @param title
     * @param docRef
     */
    public void addMood(Date currentTime, String emotionString, String explanation, String situationString, String title, DocumentReference docRef){
        mood = new Mood(currentTime, emotionString, explanation, situationString, title);
        //put the mood to fireBase
        docRef.collection("MoodList").document(currentTime.toString()).set(mood);

    }
    /**
     * check if title has more than 3 words
     * @param comment
     * @return whether comment has more than 3 words
     */
    public boolean containsSpace(String comment){
        String Comment = comment.trim();
        int numSpace = 0;
        for(int i =0;i< Comment.length(); i++){
            if (Character.isWhitespace(Comment.charAt(i))){
                numSpace++;
            }
        }
        if (numSpace > 2){
            return true;
        }else{
            return false;
        }
    }

    /**
     * initialize the situation spinner
     * use situation spinner to select an emotion
     */
    private void setSituationSpinner() {
        Spinner situation = findViewById(R.id.situations);
        ArrayAdapter<CharSequence> sitAdapter = ArrayAdapter.createFromResource(this, R.array.situations, android.R.layout.simple_spinner_item);
        sitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        situation.setAdapter(sitAdapter);
        situation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("choose a situation")){
                    situationString = "";
                }else{
                    situationString = parent.getItemAtPosition(position).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }


    /**
     * initialize the emotion spinner
     * use emotion spinner to select an emotion
     */
    private void setEmotionSpinner() {
        Spinner emotion = findViewById(R.id.emotionals);
        ArrayAdapter<CharSequence> emoAdapter = ArrayAdapter.createFromResource(this, R.array.emotionals, android.R.layout.simple_spinner_item);
        emoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        emotion.setAdapter(emoAdapter);
        emotion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("choose an emotion")){
                    emotionString = "";
                }
                else{
                    emotionString = parent.getItemAtPosition(position).toString();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    /**
     * get and show the selected image in the imageView
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == PICK_IMAGE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                imageUri = data.getData();
                imageView.setImageURI(imageUri);

            }
        }
    }

}