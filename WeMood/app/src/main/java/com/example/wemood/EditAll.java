package com.example.wemood;
/**
 * @author Ziyi Ye
 *
 * @version 1.0
 */
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


/**
 * Class name: AddMoodActivity
 *
 * Version 1.0
 *
 * Date: November 4, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */

/**
 * Be able to edit a selected mood
 */
public class EditAll extends AppCompatActivity {
    private String emotion;
    private Uri imageUri;
    private FirebaseFirestore db;
    private Mood mood;
    private StorageReference Folder;
    private ImageView imageView;
    private CollectionReference collectionReference;
    private FirebaseAuth mAuth;
    private String situationString, emotionString;
    private String downloadUri;
    private static final int PICK_IMAGE = 100;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mood);


        backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go back to MoodHistory Activity
                finish();
            }
        });

        Intent intent = getIntent();
        emotion = intent.getStringExtra("string");
        db = FirebaseFirestore.getInstance();
        mood = (Mood) intent.getSerializableExtra("mood");

        setSituationSpinner();
        setEmotionSpinner();

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        final String userName = user.getDisplayName();

        //open ImageView
        Folder = FirebaseStorage.getInstance().getReference().child("ImageFolder").child(userName);
        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });

        collectionReference = db.collection("Users")
                .document(userName)
                .collection("MoodList");
        setEditText(mood);
        //click edit button
        Button Edit = findViewById(R.id.add);
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //collect new mood information
                EditText r = findViewById(R.id.reason);
                final String newReason = r.getText().toString();
                EditText t = findViewById(R.id.title);
                final String newTitle = t.getText().toString();
                if (containsSpace(newTitle)) {
                    Toast.makeText(EditAll.this, "Title has no more than 3 words", Toast.LENGTH_SHORT).show();
                } else if (emotionString.equals("")) {
                    Toast.makeText(EditAll.this, "You must select an emotion!", Toast.LENGTH_SHORT).show();
                } else if (newTitle.equals("")) {
                    Toast.makeText(EditAll.this, "You must enter a title!", Toast.LENGTH_SHORT).show();
                }
                else {
                    final DocumentReference docRef = db.collection("Users").document(userName);

                    // if image changes then need to update uri
                    if (imageUri != null) {
                        final StorageReference Image = Folder.child(mood.getDatetime().toString());
                        Image.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                return Image.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    //set new information to the mood
                                    downloadUri = task.getResult().toString();
                                    mood.setUri(downloadUri);
                                    mood.setComment(newReason);
                                    mood.setExplanation(newTitle);
                                    mood.setEmotionalState(emotionString);
                                    mood.setSocialSituation(situationString);
                                    db.collection("MoodList").document(mood.getDatetime().toString())
                                            .delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    docRef.collection("MoodList").document(mood.getDatetime().toString()).set(mood);
                                                }
                                            });
                                    returnMood(mood);
                                }
                            }
                        });
                    }else{
                        // if image doesn't change then no need to update uri
                        mood.setComment(newReason);
                        mood.setExplanation(newTitle);
                        mood.setEmotionalState(emotionString);
                        mood.setSocialSituation(situationString);
                        db.collection("MoodList").document(mood.getDatetime().toString())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        docRef.collection("MoodList").document(mood.getDatetime().toString()).set(mood);
                                    }
                                });
                        returnMood(mood);
                    }

                }
            }
        });

        Button Delete = findViewById(R.id.delete);
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(EditAll.this)
                        .setTitle("Do you want to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Delete();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

    /**
     * Set the spinner of situations
     */
    private void setSituationSpinner() {
        String[] situations= {"choose a situation","alone", "with one other person", "with two to several people", "with a crowd"};

        if (mood.getSocialSituation().equals("alone")){
            String temp = situations[0];
            situations[0] = situations[1];
            situations[1] = temp;
        }else if (mood.getSocialSituation().equals("with one other person")){
            String temp = situations[0];
            situations[0] = situations[2];
            situations[2] = temp;
        }else if (mood.getSocialSituation().equals("with two to several people")){
            String temp = situations[0];
            situations[0] = situations[3];
            situations[3] = temp;
        }else if (mood.getSocialSituation().equals("with a crowd")){
            String temp = situations[0];
            situations[0] = situations[4];
            situations[4] = temp;
        }
        Spinner situation = findViewById(R.id.situations);
        ArrayAdapter<String> sitAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, situations);
        //ArrayAdapter<CharSequence> sitAdapter = ArrayAdapter.createFromResource(this, R.array.situations, android.R.layout.simple_spinner_item);
        sitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        situation.setAdapter(sitAdapter);
        situation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("choose a situation")) {
                    situationString = "";
                } else {
                    situationString = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }


    /**
     * return to the tired mood history
     */
    private void Delete() {
        Intent returnIntent = new Intent(this, AllMood.class);
        setResult(5, returnIntent);
        finish();
    }


    /**
     * return the modified tired mood
     */
    private void returnMood(Mood mood) {
        Intent returnIntent = new Intent(this, AllMood.class);
        returnIntent.putExtra("mood", mood);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    /**
     * check if title has more than 3 words
     * @param comment
     * @return whether comment has more than 3 words
     */
    public boolean containsSpace(String comment){
        String Comment = comment.trim();
        int numSpace = 0;
        for (int i =0; i < Comment.length(); i++) {
            if (Character.isWhitespace(Comment.charAt(i))) {
                numSpace++;
            }
        }
        if (numSpace > 2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * set mood information to the edittext
     * @param mood
     */
    public void setEditText(Mood mood){
        //get edit text
        EditText reason = findViewById(R.id.reason);
        reason.setText(mood.getComment());
        EditText title = findViewById(R.id.title);
        title.setText(mood.getExplanation());

        //get image if it exists
        if (mood.getUri() != null) {
            Picasso.get().load(mood.getUri()).into(imageView);
        }else{
            imageView.setImageResource(R.drawable.default_photo);
        }
    }

    /**
     * initialize the emotion spinner
     * use emotion spinner to select an emotion
     */
    public void setEmotionSpinner() {
        Spinner e = findViewById(R.id.emotionals);
        String[] emotions= {"happy", "sad", "lonely", "angry", "tired"};
        if (emotion.equals("sad")){
            String temp = emotions[0];
            emotions[0] = emotions[1];
            emotions[1] = temp;
        }else if (emotion.equals("lonely")){
            String temp = emotions[0];
            emotions[0] = emotions[2];
            emotions[2] = temp;
        }else if (emotion.equals("angry")){
            String temp = emotions[0];
            emotions[0] = emotions[3];
            emotions[3] = temp;
        }else if (emotion.equals("tired")){
            String temp = emotions[0];
            emotions[0] = emotions[4];
            emotions[4] = temp;
        }

        ArrayAdapter<String> emoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, emotions);
        emoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        e.setAdapter(emoAdapter);
        e.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("choose an emotion")) {
                    emotionString = "";
                }
                else {
                    emotionString = parent.getItemAtPosition(position).toString();
                    View v = findViewById(R.id.editBackground);
                    ImageView FriendMoodState = v.findViewById(R.id.friendMoodState);
                    ImageButton backButton = v.findViewById(R.id.back);
                    switch (emotionString) {
                        case "happy":
                            v.setBackgroundColor(Color.rgb(253,91,91));
                            v.getBackground().setAlpha(200);
                            backButton.setBackgroundColor(Color.rgb(253,91,91));
                            backButton.getBackground().setAlpha(0);
                            Bitmap bMap = BitmapFactory.decodeResource(view.getResources(), R.drawable.happy_marker);
                            Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 100, 100, true);
                            FriendMoodState.setImageBitmap(bMapScaled);
                            break;
                        case "sad":
                            v.setBackgroundColor(Color.rgb(106,106,240));
                            v.getBackground().setAlpha(200);
                            backButton.setBackgroundColor(Color.rgb(106,106,240));
                            backButton.getBackground().setAlpha(0);
                            Bitmap bMap1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.sad_marker);
                            Bitmap bMapScaled1 = Bitmap.createScaledBitmap(bMap1, 100, 100, true);
                            FriendMoodState.setImageBitmap(bMapScaled1);
                            break;
                        case "tired":
                            v.setBackgroundColor(Color.rgb(121,121,121));
                            v.getBackground().setAlpha(200);
                            backButton.setBackgroundColor(Color.rgb(121,121,121));
                            backButton.getBackground().setAlpha(0);
                            Bitmap bMap2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.tired_marker);
                            Bitmap bMapScaled2 = Bitmap.createScaledBitmap(bMap2, 100, 100, true);
                            FriendMoodState.setImageBitmap(bMapScaled2);
                            break;
                        case "angry":
                            v.setBackgroundColor(Color.rgb(250,233,90));
                            v.getBackground().setAlpha(200);
                            backButton.setBackgroundColor(Color.rgb(250,233,90));
                            backButton.getBackground().setAlpha(0);
                            Bitmap bMap3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.angry_marker);
                            Bitmap bMapScaled3 = Bitmap.createScaledBitmap(bMap3, 100, 100, true);
                            FriendMoodState.setImageBitmap(bMapScaled3);
                            break;
                        case "lonely":
                            v.setBackgroundColor(Color.rgb(255,152,0));
                            v.getBackground().setAlpha(200);
                            backButton.setBackgroundColor(Color.rgb(255,152,0));
                            backButton.getBackground().setAlpha(0);
                            Bitmap bMap4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.lonely_marker);
                            Bitmap bMapScaled4 = Bitmap.createScaledBitmap(bMap4, 100, 100, true);
                            FriendMoodState.setImageBitmap(bMapScaled4);
                            break;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

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
