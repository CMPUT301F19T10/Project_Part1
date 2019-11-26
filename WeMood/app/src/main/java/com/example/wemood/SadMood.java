package com.example.wemood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;

import static android.content.ContentValues.TAG;

public class SadMood extends AppCompatActivity {

    private ImageButton backButton;
    private ListView moodList;
    private ArrayList<Mood> moodDataList;
    private ArrayAdapter<Mood> moodAdapter;
    private String userName;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private CollectionReference collectionReference;
    private static final int k = 10;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sad_mood);

        backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go back to MoodHistory Activity
                finish();
            }
        });

        updateList();

        moodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SadMood.this, EditMood.class);
                intent.putExtra("string", moodDataList.get(position).getEmotionalState());
                Mood mood = moodDataList.get(position);
                intent.putExtra("mood", mood);
                i = position;
                startActivityForResult(intent, k);
            }
        });

        moodList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(SadMood.this)
                        .setTitle("Do you want to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //remove the corresponding mood
                                Mood mood = moodDataList.get(position);
                                db.collection("Users").document(userName).collection("MoodList").document(mood.getDatetime().toString()).delete();;
                                moodDataList.remove(position);
                                FirebaseStorage storage = FirebaseStorage.getInstance();
                                StorageReference image = storage.getReference().child("ImageFolder/" + userName + "/" + mood.getDatetime().toString());
                                image.delete();
                                moodAdapter = new FriendMoodList(getBaseContext(), moodDataList);
                                moodList.setAdapter(moodAdapter);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });
    }

    /**
     * Grab the moodList from the FireBase and update the local listView
     */
    public void updateList() {
        moodList = findViewById(R.id.moodList);
        moodDataList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userName = user.getDisplayName();

        collectionReference = db.collection("Users")
                .document(userName)
                .collection("MoodList");

        collectionReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Mood mood = document.toObject(Mood.class);
                                mood.setUsername(userName);
                                String flag = mood.getEmotionalState();
                                if (flag.equals("sad")) {
                                    moodDataList.add(mood);
                                    Collections.sort(moodDataList, Collections.reverseOrder());
                                    moodAdapter = new FriendMoodList(getBaseContext(), moodDataList);
                                    moodList.setAdapter(moodAdapter);
                                }
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    /**
     * Real-time update
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == k) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Mood mood = (Mood) data.getSerializableExtra("mood");
                moodDataList.set(i, mood);
                if (!mood.getEmotionalState().equals("sad")) {
                    moodDataList.remove(i);
                }
                moodAdapter = new FriendMoodList(getBaseContext(), moodDataList);
                moodList.setAdapter(moodAdapter);
            }
        }
    }

}